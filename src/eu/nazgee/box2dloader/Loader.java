package eu.nazgee.box2dloader;

/**
 * PhysicsEditor Importer Library
 *
 * Usage:
 * - Create an instance of this class
 * - Use the "open" method to load an XML file from PhysicsEditor
 * - Invoke "createBody" to create bodies from library.
 *
 * by Adrian Nilsson (ade at ade dot se)
 * BIG IRON GAMES (bigirongames.org)
 * Date: 2011-08-30
 * Time: 11:51
 */

import java.util.Collection;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityParameterCallable;

import android.content.Context;
import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IFactoryPhysical;
import eu.nazgee.box2dloader.entities.IPhysicalEntity;
import eu.nazgee.box2dloader.entities.PhysicalEntity;
import eu.nazgee.box2dloader.physics.IFactoryBody;
import eu.nazgee.box2dloader.physics.IFactoryJoint;
import eu.nazgee.box2dloader.recipes.FactoryRecipeBase;
import eu.nazgee.box2dloader.recipes.IRecipe;
import eu.nazgee.box2dloader.recipes.IRecipeEntity;
import eu.nazgee.box2dloader.recipes.IRecipeJoint;
import eu.nazgee.box2dloader.recipes.IRecipeParameterCallable;
import eu.nazgee.box2dloader.recipes.RecipeBody;

public class Loader {

	
	private FactoryRecipeBase mRecipesFactory;
	private IFactoryPhysical mPhysicsAwareEntityFactory;
	private IFactoryJoint mJointFactory;
	private IFactoryBody mBodyFactory;

	public Loader(FactoryRecipeBase pRecipesFactory,
			IFactoryPhysical pPhysicsAwareEntityFactory,
			IFactoryJoint pJointFactory, IFactoryBody pBodyFactory) {
		super();
		this.setRecipesFactory(pRecipesFactory);
		this.mPhysicsAwareEntityFactory = pPhysicsAwareEntityFactory;
		this.mJointFactory = pJointFactory;
		this.mBodyFactory = pBodyFactory;
	}

	public void open(final Context context, final String xmlFile) {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			final SAXParser parser = factory.newSAXParser();
			parser.parse(context.getAssets().open(xmlFile), getRecipesFactory());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public IRecipeEntity getEntityRecipe(final String key) {
		IRecipeEntity res = this.getRecipesFactory().getBodies().get(key);
		if (res == null) {
			res = this.getRecipesFactory().getEntities().get(key);
		}
		if (res == null) {
			Log.e(getClass().getSimpleName(), "Could not find " + key + " stub in bodies nor entities");
		}
		return res;
	}

	public IPhysicalEntity populatePhysicsAwareEntity(final String key) {
		return populatePhysicsAwareEntity(key, getPhysicsAwareEntityFactory());
	}

	public IPhysicalEntity populatePhysicsAwareEntity(final String key,
			final IFactoryPhysical pFactory) {
		// prepare stub for new physics aware entity
		final IRecipeEntity recipe = getEntityRecipe(key);

		// create physics aware entity, based on given stub
		final PhysicalEntity result = new PhysicalEntity(recipe);

		// create children entities
		recipe.callOnChildren(new IRecipeParameterCallable() {
			private IPhysicalEntity mParent = result;

			@Override
			public void call(final IRecipe pRecipe) {
				if (!(pRecipe instanceof IRecipeEntity)) {
					return;
				}
	
				IPhysicalEntity product = pFactory.produce((IRecipeEntity) pRecipe);

				if (product != null) {
					mParent.attachChild(product);

					setVisualParent(product);
					pRecipe.callOnChildren(this);
					setVisualParent( (IPhysicalEntity) product.getParent());
				}
			}

			private void setVisualParent(final IPhysicalEntity mParent) {
				this.mParent = mParent;
			}
		});

		return result;
	}

	public void addPhysics(final IPhysicalEntity pEntityWithoutPhysics) {
		addPhysics(pEntityWithoutPhysics, getBodyFactory(), getJointFactory());
	}

	public void addPhysics(final IPhysicalEntity pEntityWithoutPhysics,
			final IFactoryBody pBodyFactory,
			final IFactoryJoint pJointFactory) {

		// create all the bodies that are attached to given pEntity
		produceBodies(pEntityWithoutPhysics, pBodyFactory);
		produceJoints(pEntityWithoutPhysics, pJointFactory);
	}

	private void produceBodies(
			final IPhysicalEntity pRootEntity,
			final IFactoryBody pFactory) {

		pRootEntity.callOnChildren(new IEntityParameterCallable() {
			@Override
			public void call(final IEntity pEntity) {
				if (pEntity instanceof IPhysicalEntity) {
					final IPhysicalEntity pentity = (IPhysicalEntity) pEntity;
					produceBodies((IPhysicalEntity) pEntity, pFactory);
					// XXX why is it needed to set rot and pos to 0?
					pentity.setPosition(0, 0);
					pentity.setRotation(0);
				}
			}
		});

		if (pRootEntity.getRecipe() instanceof RecipeBody) {
			final RecipeBody desc = (RecipeBody) pRootEntity.getRecipe();

			// create physical body and bind it with entity
			final Body body = pFactory.produce(desc.shapeName, pRootEntity);

			// set body properties
			body.setBullet(desc.isBullet());

			// do some housekeeping
			pRootEntity.setUserData(body);
			pRootEntity.setBody(body);
			pRootEntity.sortChildren(false);

			if (pFactory.getListener() != null) {
				pFactory.getListener().onBodyCreated(desc, body, pRootEntity);
			}
		}
	}

	private void produceJoints(
			final IPhysicalEntity pEntityWithoutPhysics,
			final IFactoryJoint pJointFactory) {

		// prepare a recipe-to-body mapping
		final HashMap<IRecipe, IPhysicalEntity> map = new HashMap<IRecipe, IPhysicalEntity>();
		pEntityWithoutPhysics.callOnChildren(new IEntityParameterCallable() {
			@Override
			public void call(final IEntity pEntity) {
				final IPhysicalEntity awareEntity = (IPhysicalEntity) pEntity;
				map.put(awareEntity.getRecipe(), awareEntity);
				awareEntity.callOnChildren(this);
			}
		});

		// iterate over all joints in this entity and make them alive
		final Collection<IRecipeJoint> stubs = getRecipesFactory().getJointsAtAnchors(map.keySet());
		for (final IRecipeJoint stub : stubs) {
			final IPhysicalEntity bodyA = map.get(stub.getRecipeA());
			final IPhysicalEntity bodyB = map.get(stub.getRecipeB());
			Joint joint = pJointFactory.produce(stub, bodyA, bodyB);

			if (pJointFactory.getListener() != null) {
				pJointFactory.getListener().onJointCreated(stub, joint, bodyA, bodyB);
			}
		}
	}

	public IFactoryPhysical getPhysicsAwareEntityFactory() {
		return mPhysicsAwareEntityFactory;
	}

	public void setPhysicsAwareEntityFactory(IFactoryPhysical pPhysicsAwareEntityFactory) {
		this.mPhysicsAwareEntityFactory = pPhysicsAwareEntityFactory;
	}

	public IFactoryJoint getJointFactory() {
		return mJointFactory;
	}

	public void setJointFactory(IFactoryJoint pJointFactory) {
		this.mJointFactory = pJointFactory;
	}

	public IFactoryBody getBodyFactory() {
		return mBodyFactory;
	}

	public void setBodyFactory(IFactoryBody pBodyFactory) {
		this.mBodyFactory = pBodyFactory;
	}

	public FactoryRecipeBase getRecipesFactory() {
		return mRecipesFactory;
	}

	public void setRecipesFactory(FactoryRecipeBase pRecipesFactory) {
		this.mRecipesFactory = pRecipesFactory;
	}
}