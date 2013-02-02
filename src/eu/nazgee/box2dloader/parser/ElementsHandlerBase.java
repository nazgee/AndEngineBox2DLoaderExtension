package eu.nazgee.box2dloader.parser;

import java.util.HashMap;

import android.util.Log;
import eu.nazgee.box2dloader.parser.Parser.ElementHandlerManager;
import eu.nazgee.box2dloader.stubs.IStub;

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

		Log.d(getClass().getSimpleName(), "onStartElement " + pStub.getTag());

		// make sure that children-parent relationship is maintained
		final IStub parent = getManager().peekPreviousStub();
		if (parent != null) {
			parent.attachChild(pStub);
			Log.d(getClass().getSimpleName(), "onStartElement; " + pStub.getTag() +" is a child of "+ parent.getTag() + " and has " + parent.getChildCount() + " siblings");
		} else {
			Log.d(getClass().getSimpleName(), "starting top-level element: " + pStub.getTag());
		}

		mStubs.put(pStub.getTag(), pStub);
	}
}