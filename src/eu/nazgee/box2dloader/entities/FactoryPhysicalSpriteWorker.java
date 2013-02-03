package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;
import eu.nazgee.box2dloader.recipes.RecipeSprite;

class FactoryPhysicalSpriteWorker extends FactoryPhysicalWorker {
	protected final ITextureRegionResolver mResolver;

	public FactoryPhysicalSpriteWorker(ITextureRegionResolver pResolver, VertexBufferObjectManager pVBO, IFactoryPhysicalWorker ... helpers) {
		super(pVBO, helpers);
		this.mResolver = pResolver;
	}

	@Override
	public boolean understandsRecipe(IRecipeEntity pRecipe) {
		return (pRecipe instanceof RecipeSprite);
	}

	@Override
	public IPhysicalEntity build(IRecipeEntity pRecipe) {
		RecipeSprite stub = ((RecipeSprite) pRecipe);
		IPhysicalEntity product = new PhysicalSprite(stub, stub.getX(), stub.getY(), mResolver.getTexture(stub.textureName), mVBO);
		configure(pRecipe, product);
		return product;
	}
}