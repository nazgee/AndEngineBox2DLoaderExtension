package eu.nazgee.box2dloader.entities;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;


public interface IFactoryWorkerEntity {
	boolean understandsRecipe(final IRecipeEntity pRecipe);
	IPhysicsAwareEntity build(final IRecipeEntity pRecipe);
}