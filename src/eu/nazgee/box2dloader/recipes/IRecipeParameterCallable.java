package eu.nazgee.box2dloader.recipes;

import org.andengine.util.call.ParameterCallable;

public interface IRecipeParameterCallable extends ParameterCallable<IRecipe> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void call(final IRecipe pEntity);
}