package eu.nazgee.box2dloader.entities;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public interface IFactoryPhysical {
	IPhysicalEntity produce(IRecipeEntity pRecipe);
}
