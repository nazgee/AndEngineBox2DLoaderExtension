package eu.nazgee.box2dloader.physics;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.recipes.IRecipeJoint;

public interface IFactoryJointListener {
	public void onJointCreated(IRecipeJoint pRecipe, Joint pJoint, IPhysicsAwareEntity pEntityA, IPhysicsAwareEntity pEntityB);
}
