package eu.nazgee.box2dloader.factories;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.recipes.IRecipeBody;

public interface IBodyFactoryListener {
	public void onBodyCreated(IRecipeBody pRecipe, Body pBody, IPhysicsAwareEntity pEntity);
}
