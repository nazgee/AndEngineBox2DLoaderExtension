package eu.nazgee.box2dloader.recipes;

import java.util.LinkedList;

import org.xml.sax.Attributes;

import android.util.Log;

public class FactoryRecipeParser implements IFactoryRecipeParser {
	private final LinkedList<IFactoryRecipeParser> mHelpers = new LinkedList<IFactoryRecipeParser>();

	public FactoryRecipeParser(IFactoryRecipeParser ... helpers) {
		for (IFactoryRecipeParser helper : helpers) {
			mHelpers.add(helper);
		}
	}

	public void addHelperLast(IFactoryRecipeParser pHelper) {
		mHelpers.addLast(pHelper);
	}

	public void addHelperFirs(IFactoryRecipeParser pHelper) {
		mHelpers.addFirst(pHelper);
	}

	@Override
	public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
		return helpersUnderstandRecipe(pRecipeName, pAttributes);
	}

	@Override
	public IRecipe parse(String pRecipeName, Attributes pAttributes) {
		IRecipe recipe = helpersProduce(pRecipeName, pAttributes);
		return recipe;
	}

	protected boolean helpersUnderstandRecipe(String pRecipeName, final Attributes pAttributes) {
		for (final IFactoryRecipeParser helper : mHelpers) {
			if (helper.understandsRecipe(pRecipeName, pAttributes)) {
				return true;
			}
		}
		return false;
	}

	protected IRecipe helpersProduce(String pRecipeName, Attributes pAttributes) {
		IRecipe recipe;
		for (final IFactoryRecipeParser helper : mHelpers) {
			if (!helper.understandsRecipe(pRecipeName, pAttributes)) {
				continue;
			}
			recipe = helper.parse(pRecipeName, pAttributes);
			if (recipe == null) {
				Log.w(getClass().getSimpleName(), "helper=" + helper + " did not produce what was asked for");
			}
			return recipe;
		}
		return null;
	}
}