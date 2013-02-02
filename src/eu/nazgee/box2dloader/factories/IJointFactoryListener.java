package eu.nazgee.box2dloader.factories;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.stubs.IStubJoint;

public interface IJointFactoryListener {
	public void onJointCreated(IStubJoint pStub, Joint pJoint, IPhysicsAwareEntity pEntityA, IPhysicsAwareEntity pEntityB);
}
