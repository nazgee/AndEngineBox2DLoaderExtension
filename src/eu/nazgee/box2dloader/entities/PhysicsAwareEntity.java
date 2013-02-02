package eu.nazgee.box2dloader.entities;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.stubs.IStubBody;
import eu.nazgee.box2dloader.stubs.IStubEntity;

public class PhysicsAwareEntity extends Entity implements IPhysicsAwareEntity {

	private PhysicsConnector mPhysicsConnector;
	private final IStubEntity mB2DBodyDesc;
	private Body mBody;

	public PhysicsAwareEntity(final IStubEntity pB2DEntity) {
		super();
		Log.d(getClass().getSimpleName(), "created new PhysicsAwareEntity from " + pB2DEntity.getTag() + " stub");
		mB2DBodyDesc = pB2DEntity;
	}

	public PhysicsAwareEntity(final IStubEntity pB2DEntity, final float pX, final float pY, final float pWidth, final float pHeight) {
		super(pX, pY, pWidth, pHeight);
		mB2DBodyDesc = pB2DEntity;
	}

	public PhysicsAwareEntity(final IStubEntity pB2DEntity, final float pX, final float pY) {
		super(pX, pY);
		mB2DBodyDesc = pB2DEntity;
	}

	@Override
	public Body getBody() {
		return mBody;
	}

	@Override
	public void setBody(final Body pBody) {
		mBody = pBody;
	}

	@Override
	public IStubEntity getStub() {
		return mB2DBodyDesc;
	}

	@Override
	public PhysicsConnector getPhysicsConnector() {
		return mPhysicsConnector;
	}

	@Override
	public void setPhysicsConnector(final PhysicsConnector pPhysicsConnector) {
		this.mPhysicsConnector = pPhysicsConnector;
	}

	@Override
	public void dispose(final PhysicsWorld pWorld) {
		if (getStub() instanceof IStubBody) {
			pWorld.unregisterPhysicsConnector(mPhysicsConnector);
			pWorld.destroyBody(mBody);
			detachSelf();
		}
		callOnChildren(new IEntityParameterCallable() {
			@Override
			public void call(final IEntity pEntity) {
				if (pEntity instanceof IPhysicsAwareEntity) {
					final IPhysicsAwareEntity phys = (IPhysicsAwareEntity) pEntity;
					phys.dispose(pWorld);
				}
			}
		});
	}
}