package eu.nazgee.box2dloader.parser;

import eu.nazgee.box2dloader.parser.Parser.ElementHandlerManager;
import eu.nazgee.box2dloader.stubs.IStub;

public interface IElementsStubHandler extends IElementsHandler {
	public boolean isSupported(IStub pStub);
	ElementHandlerManager getManager();
}