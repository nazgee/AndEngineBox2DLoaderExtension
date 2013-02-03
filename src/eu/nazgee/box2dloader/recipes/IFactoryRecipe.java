package eu.nazgee.box2dloader.recipes;

import java.util.Collection;
import java.util.HashMap;

public interface IFactoryRecipe {

	public Collection<IRecipeJoint> getJointsAtAnchors(final Collection<IRecipe> pRecipes);
	public HashMap<String, IRecipeEntity> getEntities();
	public HashMap<String, IRecipeBody> getBodies();
	public HashMap<IRecipeBody, Collection<IRecipeJoint>> getJoints();

	public IRecipe peekRecipePrevious();
	public IRecipe peekRecipeCurrent();
}