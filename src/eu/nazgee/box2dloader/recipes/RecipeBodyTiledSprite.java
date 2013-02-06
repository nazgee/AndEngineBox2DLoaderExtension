package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class RecipeBodyTiledSprite extends RecipeBody {
	public final String textureNames[];

	public static String getRecipeName() {
		return RECIPE_BODY_WITH_TILEDSPRITE;
	}

	public RecipeBodyTiledSprite(final Attributes pAttributes) {
		super(pAttributes);
		String textures = pAttributes.getValue(ATTRIBUTE_TILEDSPRITE_TEXTURE_REGIONS);

		textureNames = textures.split(" ");
		if (textureNames == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find " + ATTRIBUTE_TILEDSPRITE_TEXTURE_REGIONS + " attribute");
		}
	}
}
