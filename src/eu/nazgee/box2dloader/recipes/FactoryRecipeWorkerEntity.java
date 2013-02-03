package eu.nazgee.box2dloader.recipes;

import org.xml.sax.Attributes;

public class FactoryRecipeWorkerEntity extends FactoryRecipeWorker {
	public FactoryRecipeWorkerEntity(IFactoryRecipe pFactory,
			IFactoryRecipeWorker ... helpers) {
		super(pFactory, helpers);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		return pRecipeName.equals(RecipeEntity.getRecipeName());
	}

	@Override
	public IRecipe parse(String pRecipeName, final Attributes pAttributes) {
		IRecipeEntity r = new RecipeEntity(pAttributes);
		mFactory.getEntities().put(r.getTag(), r);
		return r; 
	}
}
