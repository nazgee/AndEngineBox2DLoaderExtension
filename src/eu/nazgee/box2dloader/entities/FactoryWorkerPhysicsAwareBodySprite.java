package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;
import eu.nazgee.box2dloader.recipes.RecipeBodySprite;

class FactoryWorkerPhysicsAwareBodySprite extends FactoryWorkerEntity {
	protected final ITextureRegionResolver mResolver;

	public FactoryWorkerPhysicsAwareBodySprite(ITextureRegionResolver pResolver, VertexBufferObjectManager pVBO, IFactoryWorkerEntity ... helpers) {
		super(pVBO, helpers);
		this.mResolver = pResolver;
	}

	@Override
	public boolean understandsRecipe(IRecipeEntity pRecipe) {
		return (pRecipe instanceof RecipeBodySprite);
	}

	@Override
	public IPhysicsAwareEntity build(IRecipeEntity pRecipe) {
		RecipeBodySprite stub = ((RecipeBodySprite) pRecipe);
		IPhysicsAwareEntity product = new PhysicsAwareSprite(stub, stub.getX(), stub.getY(), mResolver.getTexture(stub.textureName), mVBO);
		configure(pRecipe, product);
		return product;
	}
}