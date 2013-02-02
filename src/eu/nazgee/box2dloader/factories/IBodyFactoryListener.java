package eu.nazgee.box2dloader.factories;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.stubs.IStubBody;

public interface IBodyFactoryListener {
	public void onBodyCreated(IStubBody pStub, Body pBody, IPhysicsAwareEntity pEntity);
}
