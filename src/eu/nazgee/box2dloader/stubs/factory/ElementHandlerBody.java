package eu.nazgee.box2dloader.stubs.factory;

import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.IStubBody;
import eu.nazgee.box2dloader.stubs.factory.StubsFactory.ElementHandlerManager;

public class ElementHandlerBody extends ElementHandlerEntity {

	public ElementHandlerBody(final ElementHandlerManager pManager) {
		super(pManager);
	}

	@Override
	public boolean canHandle(final IStub pStub) {
		return (pStub instanceof IStubBody);
	}

	@Override
	public IStubBody getStub(final String pStubName) {
		return (IStubBody) super.getStub(pStubName);
	}
}