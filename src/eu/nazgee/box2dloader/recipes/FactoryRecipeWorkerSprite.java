package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class FactoryRecipeWorkerSprite extends FactoryRecipeWorker {
	public FactoryRecipeWorkerSprite(IFactoryRecipe pFactory,
			IFactoryRecipeWorker ... helpers) {
		super(pFactory, helpers);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		return pRecipeName.equals(RecipeSprite.getRecipeName());
	}

	@Override
	public IRecipe parse(String pRecipeName, Attributes pAttributes) {
		IRecipeEntity r = new RecipeSprite(pAttributes);
		mFactory.getEntities().put(r.getTag(), r);
		return r; 
	}
}
