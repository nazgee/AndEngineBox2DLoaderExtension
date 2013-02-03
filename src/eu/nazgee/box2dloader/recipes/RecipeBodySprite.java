package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class RecipeBodySprite extends RecipeBody {
	public final String textureName;

	public static String getRecipeName() {
		return RECIPE_BODY_WITH_SPRITE;
	}

	public RecipeBodySprite(final Attributes pAttributes) {
		super(pAttributes);
		textureName = pAttributes.getValue(ATTRIBUTE_SPRITE_TEXTURE_REGION);
		if (textureName == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find " + ATTRIBUTE_SPRITE_TEXTURE_REGION + " attribute");
		}
	}
}
