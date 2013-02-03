package eu.nazgee.box2dloader.factories;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.entities.PhysicsAwareEntity;
import eu.nazgee.box2dloader.entities.PhysicsAwareSprite;
import eu.nazgee.box2dloader.recipes.IRecipe;
import eu.nazgee.box2dloader.recipes.RecipeBodySprite;
import eu.nazgee.box2dloader.recipes.RecipeEntity;
import eu.nazgee.box2dloader.recipes.RecipeSprite;

public abstract class PhysicsAwareEntityFactoryBase implements IPhysicsAwareEntityFactory {

	private VertexBufferObjectManager mVBO;

	public PhysicsAwareEntityFactoryBase(VertexBufferObjectManager mVBO) {
		super();
		this.mVBO = mVBO;
	}

	@Override
	public IPhysicsAwareEntity produce(IRecipe pRecipe) {
		if (!(pRecipe instanceof RecipeEntity)) {
			return null;
		}

		RecipeEntity stubEntity = ((RecipeEntity) pRecipe);

		IPhysicsAwareEntity product;
		if (stubEntity instanceof RecipeBodySprite) {
			RecipeBodySprite stub = ((RecipeBodySprite) stubEntity);
			product = new PhysicsAwareSprite(stub, stub.getX(), stub.getY(), getTextureFromKey(stub.textureName), mVBO);
		} else if (stubEntity instanceof RecipeSprite) {
			RecipeSprite stub = ((RecipeSprite) stubEntity);
			product = new PhysicsAwareSprite(stub, stub.getX(), stub.getY(), getTextureFromKey(stub.textureName), mVBO);
		} else  {
			RecipeEntity stub = stubEntity;
			product = new PhysicsAwareEntity(stub, stub.getX(), stub.getY());
		}

		product.setRotation(stubEntity.rotation);
		product.setZIndex(stubEntity.zindex);
		product.setColor(stubEntity.color_r, stubEntity.color_g, stubEntity.color_b);

		return product;
	}

	public abstract ITextureRegion getTextureFromKey(String pKey);
}
