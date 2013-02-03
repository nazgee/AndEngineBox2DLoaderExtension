package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;


public interface IFactoryRecipeParser {
	boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes);
	IRecipe parse(final String pRecipeName, final Attributes pAttributes);
}