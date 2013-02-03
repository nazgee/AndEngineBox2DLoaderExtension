package eu.nazgee.box2dloader.physics;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicalEntity;
import eu.nazgee.box2dloader.recipes.IRecipeJoint;

public class FactoryJoint implements IFactoryJoint {
	protected IFactoryJointListener mListener;
	protected final PhysicsWorld mWorld;

	public FactoryJoint(IFactoryJointListener mListener, PhysicsWorld mWorld) {
		super();
		this.mListener = mListener;
		this.mWorld = mWorld;
	}

	@Override
	public Joint produce(final IRecipeJoint pJointRecipe, IPhysicalEntity pRecipeA, IPhysicalEntity pRecipeB) {

		if ((pRecipeA != null) && (pRecipeB != null)) {
			final Joint joint = pJointRecipe.physicalize(mWorld, pRecipeA, pRecipeB);
			return joint;
		} else {
			Log.e(getClass().getSimpleName(), "some bodies are not alive for joint " + pJointRecipe.getTag());
			return null;
		}
	}

	@Override
	public IFactoryJointListener getListener() {
		return mListener;
	}

	@Override
	public void setListener(IFactoryJointListener pListener) {
		mListener = pListener;
	}
}