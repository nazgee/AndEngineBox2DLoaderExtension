package eu.nazgee.box2dloader.parser;

import eu.nazgee.box2dloader.parser.Parser.ElementHandlerManager;
import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.IStubBody;

public class ElementHandlerBody extends ElementHandlerEntity {

	public ElementHandlerBody(final ElementHandlerManager pManager) {
		super(pManager);
	}

	@Override
	public boolean isSupported(final IStub pStub) {
		return (pStub instanceof IStubBody);
	}

	@Override
	public IStubBody getStub(final String pStubName) {
		return (IStubBody) super.getStub(pStubName);
	}
}