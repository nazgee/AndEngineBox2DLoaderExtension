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

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.entities.PhysicsAwareEntity;
import eu.nazgee.box2dloader.factories.IBodyFactory;
import eu.nazgee.box2dloader.factories.IJointFactory;
import eu.nazgee.box2dloader.factories.IPhysicsAwareEntityFactory;
import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.IStubEntity;
import eu.nazgee.box2dloader.stubs.IStubJoint;
import eu.nazgee.box2dloader.stubs.IStubParameterCallable;
import eu.nazgee.box2dloader.stubs.StubBody;
import eu.nazgee.box2dloader.stubs.factory.StubsFactory;

public class Loader {

	private final StubsFactory mStubsFactory;
	private IPhysicsAwareEntityFactory mPhysicsAwareEntityFactory;
	private IJointFactory mJointFactory;
	private IBodyFactory mBodyFactory;

	public Loader() {
		mStubsFactory = new StubsFactory();
	}

	public void open(final Context context, final String xmlFile) {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			final SAXParser parser = factory.newSAXParser();
			parser.parse(context.getAssets().open(xmlFile), mStubsFactory);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public IStubEntity getEntityStub(final String key) {
		IStubEntity res = this.mStubsFactory.getManager().mBodyElementHandler.getStub(key);
		if (res == null) {
			res = this.mStubsFactory.getManager().mEntityElementHandler.getStub(key);
		}
		if (res == null) {
			Log.e(getClass().getSimpleName(), "Could not find " + key + " stub in bodies nor entities ("
					+ this.mStubsFactory.getManager().mBodyElementHandler.getStubs().size() + "/"
					+ this.mStubsFactory.getManager().mEntityElementHandler.getStubs().size());
		}
		return res;
	}

	public IPhysicsAwareEntity populatePhysicsAwareEntity(final String key) {
		return populatePhysicsAwareEntity(key, getPhysicsAwareEntityFactory());
	}

	public IPhysicsAwareEntity populatePhysicsAwareEntity(final String key,
			final IPhysicsAwareEntityFactory pFactory) {
		// prepare stub for new physics aware entity
		final IStubEntity stub = getEntityStub(key);

		// create physics aware entity, based on given stub
		final PhysicsAwareEntity result = new PhysicsAwareEntity(stub);

		// create children entities
		stub.callOnChildren(new IStubParameterCallable() {
			private IPhysicsAwareEntity mParent = result;

			@Override
			public void call(final IStub pStub) {
				IPhysicsAwareEntity product = pFactory.produce(pStub);

				if (product != null) {
					mParent.attachChild(product);

					setVisualParent(product);
					pStub.callOnChildren(this);
					setVisualParent( (IPhysicsAwareEntity) product.getParent());
				}
			}

			private void setVisualParent(final IPhysicsAwareEntity mParent) {
				this.mParent = mParent;
			}
		});

		return result;
	}

	public void addPhysics(final IPhysicsAwareEntity pEntityWithoutPhysics) {
		addPhysics(pEntityWithoutPhysics, getBodyFactory(), getJointFactory());
	}

	public void addPhysics(final IPhysicsAwareEntity pEntityWithoutPhysics,
			final IBodyFactory pBodyFactory,
			final IJointFactory pJointFactory) {

		// create all the bodies that are attached to given pEntity
		produceBodies(pEntityWithoutPhysics, pBodyFactory);
		produceJoints(pEntityWithoutPhysics, pJointFactory);
	}

	private void produceBodies(
			final IPhysicsAwareEntity pRootEntity,
			final IBodyFactory pFactory) {

		pRootEntity.callOnChildren(new IEntityParameterCallable() {
			@Override
			public void call(final IEntity pEntity) {
				if (pEntity instanceof IPhysicsAwareEntity) {
					final IPhysicsAwareEntity pentity = (IPhysicsAwareEntity) pEntity;
					produceBodies((IPhysicsAwareEntity) pEntity, pFactory);
					// XXX why is it needed to set rot and pos to 0?
					pentity.setPosition(0, 0);
					pentity.setRotation(0);
				}
			}
		});

		if (pRootEntity.getStub() instanceof StubBody) {
			final StubBody desc = (StubBody) pRootEntity.getStub();

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
			final IPhysicsAwareEntity pEntityWithoutPhysics,
			final IJointFactory pJointFactory) {

		// prepare a stub-to-body mapping
		final HashMap<IStub, IPhysicsAwareEntity> map = new HashMap<IStub, IPhysicsAwareEntity>();
		pEntityWithoutPhysics.callOnChildren(new IEntityParameterCallable() {
			@Override
			public void call(final IEntity pEntity) {
				final IPhysicsAwareEntity awareEntity = (IPhysicsAwareEntity) pEntity;
				map.put(awareEntity.getStub(), awareEntity);
				awareEntity.callOnChildren(this);
			}
		});

		// iterate over all joints in this entity and make them alive
		final Collection<IStubJoint> stubs = mStubsFactory.getManager().mJointElementHandler.getJointsForStubs(map.keySet());
		for (final IStubJoint stub : stubs) {
			final IPhysicsAwareEntity bodyA = map.get(stub.getStubA());
			final IPhysicsAwareEntity bodyB = map.get(stub.getStubB());
			Joint joint = pJointFactory.produce(stub, bodyA, bodyB);

			if (pJointFactory.getListener() != null) {
				pJointFactory.getListener().onJointCreated(stub, joint, bodyA, bodyB);
			}
		}
	}

	public IPhysicsAwareEntityFactory getPhysicsAwareEntityFactory() {
		return mPhysicsAwareEntityFactory;
	}

	public void setPhysicsAwareEntityFactory(IPhysicsAwareEntityFactory mPhysicsAwareEntityFactory) {
		this.mPhysicsAwareEntityFactory = mPhysicsAwareEntityFactory;
	}

	public IJointFactory getJointFactory() {
		return mJointFactory;
	}

	public void setJointFactory(IJointFactory mJointFactory) {
		this.mJointFactory = mJointFactory;
	}

	public IBodyFactory getBodyFactory() {
		return mBodyFactory;
	}

	public void setBodyFactory(IBodyFactory mBodyFactory) {
		this.mBodyFactory = mBodyFactory;
	}
}