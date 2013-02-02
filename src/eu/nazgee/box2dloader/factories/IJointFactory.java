package eu.nazgee.box2dloader.factories;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.stubs.IStubJoint;

public interface IJointFactory {
	Joint produce(final IStubJoint pJointStub, final IPhysicsAwareEntity pStubA, final IPhysicsAwareEntity pStubB);
	IJointFactoryListener getListener();
	void setListener(IJointFactoryListener pListener);
}
