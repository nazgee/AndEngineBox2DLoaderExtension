package eu.nazgee.box2dloader.factories;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.recipes.IRecipeJoint;

public interface IJointFactoryListener {
	public void onJointCreated(IRecipeJoint pRecipe, Joint pJoint, IPhysicsAwareEntity pEntityA, IPhysicsAwareEntity pEntityB);
}
