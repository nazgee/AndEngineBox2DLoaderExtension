package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;
import eu.nazgee.box2dloader.recipes.RecipeBodyTiledSprite;
import eu.nazgee.box2dloader.recipes.RecipeTiledSprite;

class FactoryPhysicalTiledSpriteWorker extends FactoryPhysicalWorker {
	protected final ITextureRegionResolver mResolver;

	public FactoryPhysicalTiledSpriteWorker(ITextureRegionResolver pResolver, VertexBufferObjectManager pVBO, IFactoryPhysicalWorker ... helpers) {
		super(pVBO, helpers);
		this.mResolver = pResolver;
	}

	@Override
	public boolean understandsRecipe(IRecipeEntity pRecipe) {
		return (pRecipe instanceof RecipeTiledSprite);
	}

	@Override
	public IPhysicalEntity build(IRecipeEntity pRecipe) {
		RecipeBodyTiledSprite recipe = ((RecipeBodyTiledSprite) pRecipe);
		ITextureRegion[] textures = new ITextureRegion[recipe.textureNames.length];
		for (int i = 0; i < textures.length; i++) {
			textures[i] = mResolver.getTexture(recipe.textureNames[i]);
		}
		TiledTextureRegion tiled = new TiledTextureRegion(textures[0].getTexture(), textures);
		IPhysicalEntity product = new PhysicalTiledSprite(recipe, recipe.getX(), recipe.getY(), tiled, mVBO);
		configure(pRecipe, product);
		return product;
	}
}