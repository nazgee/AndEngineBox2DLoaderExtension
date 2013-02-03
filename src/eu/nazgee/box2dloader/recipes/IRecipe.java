package eu.nazgee.box2dloader.recipes;

public interface IRecipe {
	public static int CHILDREN_CAPACITY_DEFAULT = 1;

	public String getTag();

	public IRecipe getParent();
	public void setParent(IRecipe pParent);

	public int getChildCount();
	public IRecipe getChildByTag(final String pTag);
	public void attachChild(final IRecipe pStub) throws IllegalStateException;

	public void callOnChildren(final IRecipeParameterCallable pEntityParameterCallable);
}
