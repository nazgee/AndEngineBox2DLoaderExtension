package eu.nazgee.box2dloader.recipes;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.SAXUtils;
import org.andengine.util.math.MathUtils;
import org.xml.sax.Attributes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import eu.nazgee.box2dloader.entities.IPhysicalEntity;

public class RecipeJointRevolution extends RecipeJoint {

	private final float mLowerAngle;
	private final float mUpperAngle;

	public static String getRecipeJointType() {
		return JOINT_TYPE_REVOLUTION;
	}

	public RecipeJointRevolution(final Attributes pAttributes) {
		super(pAttributes);
		mLowerAngle = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_JOINT_REVOLUTION_MIN, 0);
		mUpperAngle = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_JOINT_REVOLUTION_MAX, 0);
	}

	@Override
	public Joint physicalize(	final PhysicsWorld pWorld,
			final IPhysicalEntity pLocal,
			final IPhysicalEntity pRemote) {

		final Vector2 v = Vector2Pool.obtain();

		final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();

		// Initialize basic joint parameters
		// we are NOT calling .initialize(), so take care!
		revoluteJointDef.collideConnected = isCollideConnected();
		revoluteJointDef.bodyA = pLocal.getBody();
		revoluteJointDef.bodyB = pRemote.getBody();
		revoluteJointDef.localAnchorA.set(getAnchorOfLocalBody(v, pLocal));
		revoluteJointDef.localAnchorB.set(getAnchorOfRemoteBody(v, pLocal, pRemote));
		revoluteJointDef.referenceAngle = pRemote.getBody().getAngle() - pLocal.getBody().getAngle();

		// TODO parse motors correctly
		revoluteJointDef.enableMotor = false;
		revoluteJointDef.motorSpeed = 10;
		revoluteJointDef.maxMotorTorque = 20;
		revoluteJointDef.enableLimit = true;
		revoluteJointDef.upperAngle = MathUtils.degToRad(mUpperAngle);
		revoluteJointDef.lowerAngle = MathUtils.degToRad(mLowerAngle);

		final Joint j = pWorld.createJoint(revoluteJointDef);

		Vector2Pool.recycle(v);
		return j;
	}
}