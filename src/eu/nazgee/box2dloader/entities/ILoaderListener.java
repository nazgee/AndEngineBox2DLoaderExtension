package eu.nazgee.box2dloader.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.stubs.IStubBody;
import eu.nazgee.box2dloader.stubs.IStubJoint;

public interface ILoaderListener {
	public void onBodyCreated(IStubBody pStub, Body pBody, IPhysicsAwareEntity pEntity);
	public void onJointCreated(IStubJoint pStub, Joint pJoint, IPhysicsAwareEntity pEntityA, IPhysicsAwareEntity pEntityB);
}
