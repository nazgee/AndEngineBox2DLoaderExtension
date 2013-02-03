package eu.nazgee.box2dloader.stubs.factory;

import java.util.HashMap;

import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.factory.StubsFactory.ElementHandlerManager;

public abstract class ElementsHandlerBase implements IElementsStubHandler {
	private final ElementHandlerManager mManager;
	protected HashMap<String, IStub> mStubs = new HashMap<String, IStub>();

	public ElementsHandlerBase(final ElementHandlerManager pManager) {
		super();
		this.mManager = pManager;
	}

	@Override
	public ElementHandlerManager getManager() {
		return mManager;
	}

	@Override
	public void onStartElement(final IStub pStub) {

		// make sure that children-parent relationship is maintained
		final IStub parent = getManager().peekPreviousStub();
		if (parent != null) {
			parent.attachChild(pStub);
		} else {
		}

		mStubs.put(pStub.getTag(), pStub);
	}
}