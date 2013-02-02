package eu.nazgee.box2dloader.stubs;

import org.andengine.util.adt.list.SmartList;
import org.xml.sax.Attributes;

import eu.nazgee.box2dloader.Consts;

public class Stub implements IStub, Consts {
	public SmartList<IStub> mChildren;
	IStub mParent;
	private String mTag;
	private static Integer mUnnamedStubsCounter = 0;

	@Override
	public String getTag() {
		return mTag;
	}

	public Stub(final Attributes pAttributes) {
		mTag = pAttributes.getValue(ATTRIBUTE_TAG);
		if (mTag == null) {
			synchronized (mUnnamedStubsCounter) {
				mTag = "unnamed-" + (++mUnnamedStubsCounter);
			}
		}
	}

	@Override
	public void callOnChildren(final IStubParameterCallable pEntityParameterCallable) {
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
	public IStub getChildByTag(final String pTag) {
		if(this.mChildren == null) {
			return null;
		}
		for(int i = this.mChildren.size() - 1; i >= 0; i--) {
			final IStub child = this.mChildren.get(i);
			if(child.getTag().equals(pTag)) {
				return child;
			}
		}
		return null;
	}

	@Override
	public IStub getParent() {
		return mParent;
	}

	@Override
	public void setParent(final IStub pParent) {
		mParent = pParent;
	}

	@Override
	public void attachChild(final IStub pStub) throws IllegalStateException {
		this.assertBodyDescHasNoParent(pStub);

		if(this.mChildren == null) {
			allocateChildren();
		}
		this.mChildren.add(pStub);
		pStub.setParent(this);
	}

	private void assertBodyDescHasNoParent(final IStub pStub) throws IllegalStateException {
		if(pStub.getParent() != null) {
			final String entityClassName = pStub.getClass().getSimpleName();
			final String currentParentClassName = pStub.getParent().getClass().getSimpleName();
			final String newParentClassName = this.getClass().getSimpleName();
			throw new IllegalStateException("IStubEntity '" + entityClassName +"' already has a parent: '" + currentParentClassName + "'. New parent: '" + newParentClassName + "'!");
		}
	}

	private void allocateChildren() {
		this.mChildren = new SmartList<IStub>(IStub.CHILDREN_CAPACITY_DEFAULT);
	}
}
