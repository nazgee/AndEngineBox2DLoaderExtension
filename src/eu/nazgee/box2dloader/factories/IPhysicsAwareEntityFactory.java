package eu.nazgee.box2dloader.factories;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.stubs.IStub;

public interface IPhysicsAwareEntityFactory {
	IPhysicsAwareEntity produce(IStub pStub);
}
