package eu.nazgee.box2dloader.recipes;

import java.util.Collection;
import java.util.HashMap;

import org.xml.sax.helpers.DefaultHandler;

abstract public class FactoryRecipeBase extends DefaultHandler {
		abstract public Collection<IRecipeJoint> getJointsAtAnchors(final Collection<IRecipe> pRecipes);
		abstract public HashMap<String, IRecipeEntity> getEntities();
		abstract public HashMap<String, IRecipeBody> getBodies();
		abstract public HashMap<IRecipeBody, Collection<IRecipeJoint>> getJoints();

}
