package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class FactoryRecipeWorkerJointRevolution extends FactoryRecipeWorker {
	public FactoryRecipeWorkerJointRevolution(IFactoryRecipe pFactory,
			IFactoryRecipeWorker ... helpers) {
		super(pFactory, helpers);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		return pRecipeName.equals(RecipeJointRevolution.getRecipeJointType());
	}

	@Override
	public IRecipe parse(String pRecipeName, Attributes pAttributes) {
		return new RecipeJointRevolution(pAttributes);
	}
}