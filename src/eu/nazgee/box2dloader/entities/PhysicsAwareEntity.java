package eu.nazgee.box2dloader.entities;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.recipes.IRecipeBody;
import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public class PhysicsAwareEntity extends Entity implements IPhysicsAwareEntity {

	private PhysicsConnector mPhysicsConnector;
	private final IRecipeEntity mB2DBodyDesc;
	private Body mBody;

	public PhysicsAwareEntity(final IRecipeEntity pB2DEntity) {
		super();
		mB2DBodyDesc = pB2DEntity;
	}

	public PhysicsAwareEntity(final IRecipeEntity pB2DEntity, final float pX, final float pY, final float pWidth, final float pHeight) {
		super(pX, pY, pWidth, pHeight);
		mB2DBodyDesc = pB2DEntity;
	}

	public PhysicsAwareEntity(final IRecipeEntity pB2DEntity, final float pX, final float pY) {
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
	public IRecipeEntity getRecipe() {
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
		if (getRecipe() instanceof IRecipeBody) {
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
