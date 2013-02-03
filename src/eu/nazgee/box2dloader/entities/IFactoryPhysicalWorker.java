package eu.nazgee.box2dloader.entities;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;


public interface IFactoryPhysicalWorker {
	boolean understandsRecipe(final IRecipeEntity pRecipe);
	IPhysicalEntity build(final IRecipeEntity pRecipe);
}