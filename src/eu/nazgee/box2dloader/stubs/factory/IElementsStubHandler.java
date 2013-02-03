package eu.nazgee.box2dloader.stubs.factory;

import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.factory.StubsFactory.ElementHandlerManager;

public interface IElementsStubHandler extends IElementsHandler {
	public boolean canHandle(IStub pStub);
	ElementHandlerManager getManager();
}