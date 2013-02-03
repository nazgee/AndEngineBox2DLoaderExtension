package eu.nazgee.box2dloader.physics;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.entities.IPhysicalEntity;
import eu.nazgee.box2dloader.recipes.IRecipeBody;

public interface IFactoryBodyListener {
	public void onBodyCreated(IRecipeBody pRecipe, Body pBody, IPhysicalEntity pEntity);
}
