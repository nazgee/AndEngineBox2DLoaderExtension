package eu.nazgee.box2dloader.recipes;

import org.andengine.util.adt.list.SmartList;
import org.xml.sax.Attributes;

import eu.nazgee.box2dloader.Consts;

public class Recipe implements IRecipe, Consts {
	public SmartList<IRecipe> mChildren;
	IRecipe mParent;
	private String mTag;
	private static Integer mUnnamedStubsCounter = 0;

	@Override
	public String getTag() {
		return mTag;
	}

	public Recipe(final Attributes pAttributes) {
		mTag = pAttributes.getValue(ATTRIBUTE_TAG);
		if (mTag == null) {
			synchronized (mUnnamedStubsCounter) {
				mTag = "unnamed-" + (++mUnnamedStubsCounter);
			}
		}
	}

	@Override
	public void callOnChildren(final IRecipeParameterCallable pEntityParameterCallable) {
		if(this.mChildren == null) {
			return;
		}
		this.mChildren.call(pEntityParameterCallable);
	}

	@Override
	public int getChildCount() {
		if(this.mChildren == null) {
			return 0;
		}
		return this.mChildren.size();
	}

	@Override
	public IRecipe getChildByTag(final String pTag) {
		if(this.mChildren == null) {
			return null;
		}
		for(int i = this.mChildren.size() - 1; i >= 0; i--) {
			final IRecipe child = this.mChildren.get(i);
			if(child.getTag().equals(pTag)) {
				return child;
			}
		}
		return null;
	}

	@Override
	public IRecipe getParent() {
		return mParent;
	}

	@Override
	public void setParent(final IRecipe pParent) {
		mParent = pParent;
	}

	@Override
	public void attachChild(final IRecipe pStub) throws IllegalStateException {
		this.assertBodyDescHasNoParent(pStub);

		if(this.mChildren == null) {
			allocateChildren();
		}
		this.mChildren.add(pStub);
		pStub.setParent(this);
	}

	private void assertBodyDescHasNoParent(final IRecipe pStub) throws IllegalStateException {
		if(pStub.getParent() != null) {
			final String entityClassName = pStub.getClass().getSimpleName();
			final String currentParentClassName = pStub.getParent().getClass().getSimpleName();
			final String newParentClassName = this.getClass().getSimpleName();
			throw new IllegalStateException("IStubEntity '" + entityClassName +"' already has a parent: '" + currentParentClassName + "'. New parent: '" + newParentClassName + "'!");
		}
	}

	private void allocateChildren() {
		this.mChildren = new SmartList<IRecipe>(IRecipe.CHILDREN_CAPACITY_DEFAULT);
	}
}
