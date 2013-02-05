package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;
import eu.nazgee.box2dloader.recipes.RecipeBodySprite;

class FactoryPhysicalBodySpriteWorker extends FactoryPhysicalWorker {
	protected final ITextureRegionResolver mResolver;

	public FactoryPhysicalBodySpriteWorker(ITextureRegionResolver pResolver, VertexBufferObjectManager pVBO, IFactoryPhysicalWorker ... helpers) {
		super(pVBO, helpers);
		this.mResolver = pResolver;
	}

	@Override
	public boolean understandsRecipe(IRecipeEntity pRecipe) {
		return (pRecipe instanceof RecipeBodySprite);
	}

	@Override
	public IPhysicalEntity build(IRecipeEntity pRecipe) {
		RecipeBodySprite recipe = ((RecipeBodySprite) pRecipe);
		IPhysicalEntity product = new PhysicalSprite(recipe, recipe.getX(), recipe.getY(), mResolver.getTexture(recipe.textureName), mVBO);
		configure(pRecipe, product);
		return product;
	}
}