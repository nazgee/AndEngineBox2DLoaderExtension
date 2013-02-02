package eu.nazgee.box2dloader.stubs;

public interface IStub {
	public static int CHILDREN_CAPACITY_DEFAULT = 1;

	public String getTag();

	public IStub getParent();
	public void setParent(IStub pParent);

	public int getChildCount();
	public IStub getChildByTag(final String pTag);
	public void attachChild(final IStub pStub) throws IllegalStateException;

	public void callOnChildren(final IStubParameterCallable pEntityParameterCallable);
}
