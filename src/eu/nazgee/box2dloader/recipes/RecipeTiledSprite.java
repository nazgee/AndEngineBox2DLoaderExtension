package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class RecipeTiledSprite extends RecipeEntity {
	public final String textureNames[];

	public static String getStubName() {
		return RECIPE_TILEDSPRITE;
	}

	public RecipeTiledSprite(final Attributes pAttributes) {
		super(pAttributes);
		String textures = pAttributes.getValue(ATTRIBUTE_TILEDSPRITE_TEXTURE_REGIONS);

		textureNames = textures.split(" ");
		if (textureNames == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find " + ATTRIBUTE_TILEDSPRITE_TEXTURE_REGIONS + " attribute");
		}
	}
}
