package eu.nazgee.box2dloader.recipes;

import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import eu.nazgee.box2dloader.Consts;

public class RecipeEntity extends Recipe implements IRecipeEntity, Consts {
	public final float x;
	public final float y;
	public final int zindex;
	public final float rotation;
	public final float color_r;
	public final float color_g;
	public final float color_b;

	public static String getRecipeName() {
		return RECIPE_ENTITY;
	}

	public RecipeEntity(final Attributes pAttributes) {
		super(pAttributes);

		x = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_X, 0);
		y = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_Y, 0);
		zindex = SAXUtils.getIntAttribute(pAttributes, ATTRIBUTE_ZINDEX, 0);
		rotation = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_ROTATION, 0);
		color_r = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_COLOR_R, 1);
		color_g = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_COLOR_G, 1);
		color_b = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_COLOR_B, 1);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
}