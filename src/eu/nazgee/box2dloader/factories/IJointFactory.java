package eu.nazgee.box2dloader.factories;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.recipes.IRecipeJoint;

public interface IJointFactory {
	Joint produce(final IRecipeJoint pJointRecipe, final IPhysicsAwareEntity pRecipeA, final IPhysicsAwareEntity pRecipeB);
	IJointFactoryListener getListener();
	void setListener(IJointFactoryListener pListener);
}
