package eu.nazgee.box2dloader.stubs;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;

public interface IStubJoint extends IStub {
	public void setStubA(IStubBody pStub);
	public void setStubB(IStubBody pStub);
	public IStubBody getStubA();
	public IStubBody getStubB();
	public boolean isSane();
	String getTagRemote();
	public Vector2 getAnchorOfLocalBody(Vector2 pReuse, IPhysicsAwareEntity pEntity);
	public Joint physicalize(	final PhysicsWorld pWorld,
			final IPhysicsAwareEntity pLocal,
			final IPhysicsAwareEntity pRemote);
}
