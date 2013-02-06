package eu.nazgee.box2dloader.entities;

import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.recipes.IRecipeBody;
import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public class PhysicalTiledSprite extends TiledSprite implements IPhysicalEntity {

	private PhysicsConnector mPhysicsConnector;
	private final IRecipeEntity mB2DBodyDesc;
	private Body mBody;

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType, final ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType, pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY, final float pWidth, final float pHeight,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType, final ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType,
				pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final DrawType pDrawType) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager,
			final ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pShaderProgram);
		this.mB2DBodyDesc = pB2DBodyDesc;
	}

	public PhysicalTiledSprite(final IRecipeEntity pB2DBodyDesc, final float pX, final float pY,
			final ITiledTextureRegion pTextureRegion,
			final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.mB2DBodyDesc = pB2DBodyDesc;
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
	public void setPhysicsConnector(final PhysicsConnector mPhysicsConnector) {
		this.mPhysicsConnector = mPhysicsConnector;
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
				if (pEntity instanceof IPhysicalEntity) {
					final IPhysicalEntity phys = (IPhysicalEntity) pEntity;
					phys.dispose(pWorld);
				}
			}
		});
	}
}
