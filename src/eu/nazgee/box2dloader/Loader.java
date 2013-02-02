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
import eu.nazgee.box2dloader.parser.Parser;
import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.IStubEntity;
import eu.nazgee.box2dloader.stubs.IStubJoint;
import eu.nazgee.box2dloader.stubs.IStubParameterCallable;
import eu.nazgee.box2dloader.stubs.StubBody;
import eu.nazgee.box2dloader.stubs.StubEntity;

public class Loader {

	private final Parser mHandler;

	public Loader() {
		mHandler = new Parser();
	}

	public void open(final Context context, final String xmlFile) {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			final SAXParser parser = factory.newSAXParser();
			parser.parse(context.getAssets().open(xmlFile), mHandler);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public IStubEntity getEntityStub(final String key) {
		IStubEntity res = this.mHandler.getManager().mBodyElementHandler.getStub(key);
		if (res == null) {
			res = this.mHandler.getManager().mEntityElementHandler.getStub(key);
		}
		if (res == null) {
			Log.e(getClass().getSimpleName(), "Could not find " + key + " stub in bodies nor entities ("
					+ this.mHandler.getManager().mBodyElementHandler.getStubs().size() + "/"
					+ this.mHandler.getManager().mEntityElementHandler.getStubs().size());
		}
		return res;
	}

	public IPhysicsAwareEntity populatePhysicsAwareEntity(final String key, final IPhysicsAwareEntityFactory pFactory) {
		// prepare stub for new physics aware entity
		final IStubEntity stub = getEntityStub(key);

		// create physics aware entity, based on given stub
		final PhysicsAwareEntity result = new PhysicsAwareEntity(stub);

		// create children entities
		stub.callOnChildren(new IStubParameterCallable() {
			private int mCounter;
			private IPhysicsAwareEntity mParent = result;

			@Override
			public void call(final IStub pStub) {
				mCounter++;
				Log.d(getClass().getSimpleName(), "creating physics aware entity from " + (pStub).getTag() + " stub; i=" + mCounter);

				IPhysicsAwareEntity product = pFactory.produce(pStub);

				if (product != null) {
					mParent.attachChild(product);

					setVisualParent(product);
					Log.d(getClass().getSimpleName(), ((StubEntity) pStub).getTag() + " children count: " + pStub.getChildCount());
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
			if (desc.isBullet()) {
				Log.i("loader", "setting " + desc.getTag() + " as bullet");
			}

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
		final Collection<IStubJoint> stubs = mHandler.getManager().mJointElementHandler.getJointsForStubs(map.keySet());
		for (final IStubJoint stub : stubs) {
			final IPhysicsAwareEntity bodyA = map.get(stub.getStubA());
			final IPhysicsAwareEntity bodyB = map.get(stub.getStubB());
			Joint joint = pJointFactory.produce(stub, bodyA, bodyB);

			if (pJointFactory.getListener() != null) {
				pJointFactory.getListener().onJointCreated(stub, joint, bodyA, bodyB);
			}
		}
	}
}