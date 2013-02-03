package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class RecipeSprite extends RecipeEntity {
	public final String textureName;

	public static String getStubName() {
		return RECIPE_SPRITE;
	}

	public RecipeSprite(final Attributes pAttributes) {
		super(pAttributes);
		textureName = pAttributes.getValue(ATTRIBUTE_SPRITE_TEXTURE_REGION);
		if (textureName == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find " + ATTRIBUTE_SPRITE_TEXTURE_REGION + " attribute");
		}
	}
}
