package eu.nazgee.box2dloader.recipes;

import java.util.Collection;
import java.util.LinkedList;

import org.xml.sax.Attributes;

import eu.nazgee.box2dloader.Consts;

public class FactoryRecipeWorkerJoint extends FactoryRecipeWorker {
	public FactoryRecipeWorkerJoint(IFactoryRecipe pFactory, IFactoryRecipeWorker ... helpers) {
		super(pFactory, helpers);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		if (!pRecipeName.equals(RecipeJoint.getRecipeName())) {
			return false;
		}
		final String type = pAttributes.getValue(Consts.JOINT_TYPE);
		return helpersUnderstandRecipe(type, pAttributes);
	}

	@Override
	public IRecipe parse(String pRecipeName, Attributes pAttributes) {
		if (!pRecipeName.equals(RecipeJoint.getRecipeName())) {
			return null;
		}
		final String type = pAttributes.getValue(Consts.JOINT_TYPE);

		IRecipeJoint recipe = (IRecipeJoint) helpersProduce(type, pAttributes);

		// this cast will fail if joint is embedded in a non-body recipe
		final IRecipeBody body = (IRecipeBody) mFactory.peekRecipeCurrent();
		recipe.setBodyA(body);

		Collection<IRecipeJoint> jointsList = mFactory.getJoints().get(body);
		if (jointsList == null) {
			jointsList = new LinkedList<IRecipeJoint>();
		}
		jointsList.add(recipe);
		mFactory.getJoints().put(body, jointsList);

		return recipe;
	}
}