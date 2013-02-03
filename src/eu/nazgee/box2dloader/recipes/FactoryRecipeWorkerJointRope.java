package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class FactoryRecipeWorkerJointRope extends FactoryRecipeWorker {
	public FactoryRecipeWorkerJointRope(IFactoryRecipe pFactory,
			IFactoryRecipeWorker ... helpers) {
		super(pFactory, helpers);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		return pRecipeName.equals(RecipeJointRope.getRecipeJointType());
	}

	@Override
	public IRecipe parse(String pRecipeName, Attributes pAttributes) {
		return new RecipeJointRope(pAttributes);
	}
}