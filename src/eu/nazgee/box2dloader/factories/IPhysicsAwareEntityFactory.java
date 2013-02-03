package eu.nazgee.box2dloader.factories;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.recipes.IRecipe;

public interface IPhysicsAwareEntityFactory {
	IPhysicsAwareEntity produce(IRecipe pRecipe);
}
