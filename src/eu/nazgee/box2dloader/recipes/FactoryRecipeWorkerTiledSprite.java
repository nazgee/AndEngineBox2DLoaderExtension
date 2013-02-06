package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class FactoryRecipeWorkerTiledSprite extends FactoryRecipeWorker {
	public FactoryRecipeWorkerTiledSprite(IFactoryRecipe pFactory,
			IFactoryRecipeWorker ... helpers) {
		super(pFactory, helpers);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		return pRecipeName.equals(RecipeTiledSprite.getRecipeName());
	}

	@Override
	public IRecipe parse(String pRecipeName, Attributes pAttributes) {
		IRecipeEntity r = new RecipeTiledSprite(pAttributes);
		mFactory.getEntities().put(r.getTag(), r);
		return r; 
	}
}
