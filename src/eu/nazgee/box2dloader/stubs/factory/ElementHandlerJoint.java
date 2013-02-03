package eu.nazgee.box2dloader.stubs.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import android.util.Log;
import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.IStubBody;
import eu.nazgee.box2dloader.stubs.IStubJoint;
import eu.nazgee.box2dloader.stubs.factory.StubsFactory.ElementHandlerManager;

public class ElementHandlerJoint extends ElementsHandlerBase {
	public HashMap<IStubBody, Collection<IStubJoint>> mJointStubs = new HashMap<IStubBody, Collection<IStubJoint>>();

	public ElementHandlerJoint(final ElementHandlerManager pManager) {
		super(pManager);
	}

	@Override
	public void onStartElement(final IStub pStub) {
		super.onStartElement(pStub);

		final IStubJoint joint = (IStubJoint) pStub;
		// this cast will fail if joint is embedded in a non-body stub
		final IStubBody body = (IStubBody) getManager().peekPreviousStub();
		joint.setStubA(body);

		bindJointWithBody(mJointStubs, body, joint);
	}

	private static void bindJointWithBody(final HashMap<IStubBody, Collection<IStubJoint>> pMap, final IStubBody pBody, final IStubJoint pJoint) {
		Collection<IStubJoint> jointsList = pMap.get(pBody);
		if (jointsList == null) {
			jointsList = new LinkedList<IStubJoint>();
		}
		jointsList.add(pJoint);
		pMap.put(pBody, jointsList);
	}

	@Override
	public void onEndElement() {
	}

	@Override
	public boolean canHandle(final IStub pStub) {
		return (pStub instanceof IStubJoint);
	}

	public void rebindStubs(final ElementHandlerBody mBodyElementHandler) {
		for (final Collection<IStubJoint> joints : mJointStubs.values()) {
			for (final IStubJoint joint : joints) {

				final IStubBody body = mBodyElementHandler.getStub(joint.getTagRemote());
				if (body != null) {
					joint.setStubB(body);
				} else {
					Log.e(getClass().getSimpleName(), "rebinding " + joint.getTag() + " with " + joint.getTagRemote() + " failed :(");
				}
			}
		}
	}

	public Collection<IStubJoint> getJointsForStub(final IStub pStubBody) {
		return mJointStubs.get(pStubBody);
	}

	public Collection<IStubJoint> getJointsAvailable() {
		final Collection<IStubJoint> ret = new LinkedList<IStubJoint>();

		for (final Collection<IStubJoint> joints : mJointStubs.values()) {
			ret.addAll(joints);
		}

		return ret;
	}

	public Collection<IStubJoint> getJointsForStubs(final Collection<IStub> pStubs) {
		if (pStubs == null) {
			return null;
		}

		final Collection<IStubJoint> ret = new LinkedList<IStubJoint>();

		for (final IStub stub : pStubs) {
			if (stub != null) {
				final Collection<IStubJoint> joints = mJointStubs.get(stub);
				if (joints != null) {
					ret.addAll(joints);
				}
			}
		}

		return ret;
	}
}