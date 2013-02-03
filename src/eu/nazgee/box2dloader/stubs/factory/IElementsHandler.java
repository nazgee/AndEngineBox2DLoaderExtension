package eu.nazgee.box2dloader.stubs.factory;

import eu.nazgee.box2dloader.stubs.IStub;

public interface IElementsHandler {
	public void onStartElement(IStub pStub);
	public void onEndElement();
}