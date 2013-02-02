package eu.nazgee.box2dloader.factories;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.stubs.IStubJoint;

public class JointFactory implements IJointFactory {
	protected IJointFactoryListener mListener;
	protected final PhysicsWorld mWorld;

	public JointFactory(IJointFactoryListener mListener, PhysicsWorld mWorld) {
		super();
		this.mListener = mListener;
		this.mWorld = mWorld;
	}

	@Override
	public Joint produce(final IStubJoint pJointStub, IPhysicsAwareEntity pStubA, IPhysicsAwareEntity pStubB) {

		if ((pStubA != null) && (pStubB != null)) {
			final Joint joint = pJointStub.physicalize(mWorld, pStubA, pStubB);
			return joint;
		} else {
			Log.w(getClass().getSimpleName(), "some bodies are not alive for joint " + pJointStub.getTag());
			return null;
		}
	}

	@Override
	public IJointFactoryListener getListener() {
		return mListener;
	}

	@Override
	public void setListener(IJointFactoryListener pListener) {
		mListener = pListener;
	}
}