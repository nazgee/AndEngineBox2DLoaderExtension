package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class FactoryRecipeWorkerBodyTiledSprite extends FactoryRecipeWorker {
	
	public FactoryRecipeWorkerBodyTiledSprite(IFactoryRecipe pFactory,
			IFactoryRecipeWorker ... helpers) {
		super(pFactory, helpers);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		return pRecipeName.equals(RecipeBodyTiledSprite.getRecipeName());
	}

	@Override
	public IRecipe parse(String pRecipeName, Attributes pAttributes) {
		IRecipeBody r = new RecipeBodyTiledSprite(pAttributes);
		mFactory.getBodies().put(r.getTag(), r);
		return r; 
	}
}