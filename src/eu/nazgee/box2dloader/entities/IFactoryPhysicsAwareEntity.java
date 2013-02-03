package eu.nazgee.box2dloader.entities;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public interface IFactoryPhysicsAwareEntity {
	IPhysicsAwareEntity produce(IRecipeEntity pRecipe);
}
