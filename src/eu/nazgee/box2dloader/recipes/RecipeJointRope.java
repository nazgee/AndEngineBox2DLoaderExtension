package eu.nazgee.box2dloader.recipes;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;

public class RecipeJointRope extends RecipeJoint {

	private final float mMaxLength;

	public static String getRecipeJointType() {
		return JOINT_TYPE_ROPE;
	}

	public RecipeJointRope(final Attributes pAttributes) {
		super(pAttributes);
		mMaxLength = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_JOINT_ROPE_MAX, 0);
	}

	@Override
	public Joint physicalize(	final PhysicsWorld pWorld,
			final IPhysicsAwareEntity pLocal,
			final IPhysicsAwareEntity pRemote) {

		final Vector2 v = Vector2Pool.obtain();

		final RopeJointDef ropedef = new RopeJointDef();

		// Initialize basic joint parameters
		// we are NOT calling .initialize(), so take care!
		ropedef.collideConnected = isCollideConnected();
		ropedef.bodyA = pLocal.getBody();
		ropedef.bodyB = pRemote.getBody();
		ropedef.localAnchorA.set(getAnchorOfLocalBody(v, pLocal));
		ropedef.localAnchorB.set(getAnchorOfRemoteBody(v, pLocal, pRemote));
		ropedef.maxLength = mMaxLength;

		final Joint j = pWorld.createJoint(ropedef);

		Vector2Pool.recycle(v);
		return j;
	}
}