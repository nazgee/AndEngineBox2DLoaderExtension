package eu.nazgee.box2dloader.parser;

import java.util.HashMap;

import eu.nazgee.box2dloader.parser.Parser.ElementHandlerManager;
import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.IStubEntity;

public class ElementHandlerEntity extends ElementsHandlerBase {

	public ElementHandlerEntity(final ElementHandlerManager pManager) {
		super(pManager);
	}

	@Override
	public void onEndElement() {
	}

	@Override
	public boolean isSupported(final IStub pStub) {
		return (pStub instanceof IStubEntity);
	}

	public IStubEntity getStub(final String pStubName) {
		return (IStubEntity) mStubs.get(pStubName);
	}

	public HashMap<String, IStub> getStubs() {
		return mStubs;
	}
}