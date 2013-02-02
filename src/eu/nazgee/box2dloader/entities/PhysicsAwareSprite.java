package eu.nazgee.box2dloader.entities;

import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.stubs.IStubBody;
import eu.nazgee.box2dloader.stubs.IStubEntity;

public class PhysicsAwareSprite extends Sprite implements IPhysicsAwareEntity {

	private PhysicsConnector mPhysicsConnector;
	private final IStubEntity mB2DBodyDesc;
	private Body mBody;

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITextureRegion pTextureRegion,
			final ISpriteVertexBufferObject pSpriteVertexBufferObject,
			final ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject,
				pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITextureRegion pTextureRegion,
			final ISpriteVertexBufferObject pSpriteVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType, final ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType, pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY,
			final ITextureRegion pTextureRegion,
			final ISpriteVertexBufferObject pVertexBufferObject,
			final ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObject, pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY,
			final ITextureRegion pTextureRegion,
			final ISpriteVertexBufferObject pVertexBufferObject) {
		super(pX, pY, pTextureRegion, pVertexBufferObject);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType, final ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType,
				pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicsAwareSprite(final IStubEntity pB2DBodyDesc, final float pX, final float pY,
			final ITextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.mB2DBodyDesc = pB2DBodyDesc;
		Log.d(getClass().getSimpleName(), "created new PhysicsAwareSprite from " + pB2DBodyDesc.getTag() + " stub");
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
	public void setPhysicsConnector(final PhysicsConnector mPhysicsConnector) {
		this.mPhysicsConnector = mPhysicsConnector;
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
