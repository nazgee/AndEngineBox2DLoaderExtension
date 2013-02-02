package eu.nazgee.box2dloader.parser;

import java.util.LinkedList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import eu.nazgee.box2dloader.Consts;
import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.StubBody;
import eu.nazgee.box2dloader.stubs.StubBodySprite;
import eu.nazgee.box2dloader.stubs.StubEntity;
import eu.nazgee.box2dloader.stubs.StubJoint;
import eu.nazgee.box2dloader.stubs.StubJointRevolution;
import eu.nazgee.box2dloader.stubs.StubJointRope;
import eu.nazgee.box2dloader.stubs.StubSprite;

public class Parser extends DefaultHandler {
	private final ElementHandlerManager mManager = new ElementHandlerManager();

	@Override
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		super.characters(ch, start, length);
	}

	@Override
	public void startElement(final String pUri, final String pLocalName, final String pName,
			final Attributes pAttributes) throws SAXException {
		super.startElement(pUri, pLocalName, pName, pAttributes);
		final IStub b2dstub = produce(pLocalName, pAttributes);
		mManager.onStartElement(b2dstub);
	}

	@Override
	public void endElement(final String uri, final String localName, final String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		mManager.onEndElement();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		mManager.onStartDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		mManager.onEndDocument();
	}

	public ElementHandlerManager getManager() {
		return mManager;
	}

	protected IStub produce(final String pTag, final Attributes pAttributes) {
		if (pTag.equals(StubEntity.getStubName())) {
			return new StubEntity(pAttributes);
		} else if (pTag.equals(StubBody.getStubName())) {
			return new StubBody(pAttributes);
		} else if (pTag.equals(StubBodySprite.getStubName())) {
			return new StubBodySprite(pAttributes);
		} else if (pTag.equals(StubSprite.getStubName())) {
			return new StubSprite(pAttributes);
		} else if (pTag.equals(StubJoint.getStubName())) {
			final String type = pAttributes.getValue(Consts.JOINT_TYPE);
			if (type.equals(StubJointRevolution.getStubTypeName())) {
				return new StubJointRevolution(pAttributes);
			} else if (type.equals(StubJointRope.getStubTypeName())) {
				return new StubJointRope(pAttributes);
			} else {
				throw new RuntimeException("B2DFactory unknown joint type: " + type);
			}
		} else {
			return null;
		}
	}

	public static class ElementHandlerManager implements IElementsHandler {

		final public ElementHandlerJoint mJointElementHandler;
		final public ElementHandlerBody mBodyElementHandler;
		final public ElementHandlerEntity mEntityElementHandler;

		private final LinkedList<IElementsStubHandler> mHandlers = new LinkedList<IElementsStubHandler>();
		private final Stack<IElementsStubHandler> mHandlersStack = new Stack<IElementsStubHandler>();
		private final Stack<IStub> mStubsStack = new Stack<IStub>();

		public ElementHandlerManager() {
			super();

			mJointElementHandler = new ElementHandlerJoint(this);
			mBodyElementHandler = new ElementHandlerBody(this);
			mEntityElementHandler = new ElementHandlerEntity(this);

			mHandlers.add(mJointElementHandler);
			mHandlers.add(mBodyElementHandler);
			mHandlers.add(mEntityElementHandler);
		}

		@Override
		public void onStartElement(final IStub pStub) {
			for (final IElementsStubHandler handler : mHandlers) {
				if (handler.isSupported(pStub)) {
					mStubsStack.push(pStub);
					mHandlersStack.push(handler).onStartElement(pStub);
					break;
				}
			}
		}

		@Override
		public void onEndElement() {
			if (!mHandlersStack.isEmpty()) {
				mHandlersStack.pop().onEndElement();
				mStubsStack.pop();
			} else {
				Log.w(getClass().getSimpleName(), "handlers stack is empty?");
			}
		}

		public void onStartDocument() {

		}

		public void onEndDocument() {
			// we should have all bodies collected by now. it's time to rebind joint
			// with theirs remote counterparts
			Log.d(getClass().getSimpleName(), "entities=" + mEntityElementHandler.mStubs.size());
			Log.d(getClass().getSimpleName(), "bodies=" + mBodyElementHandler.mStubs.size());
			mJointElementHandler.rebindStubs(mBodyElementHandler);
		}

		public IStub peekPreviousStub() {
			final int size = mStubsStack.size();

			if (size > 1) {
				return mStubsStack.get(size - 2);
			} else {
				return null;
			}
		}

		public IStub peekCurrentStub() {
			return mStubsStack.peek();
		}
	}
}