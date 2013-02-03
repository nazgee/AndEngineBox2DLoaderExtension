package eu.nazgee.box2dloader.physics;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicalEntity;
import eu.nazgee.box2dloader.recipes.IRecipeJoint;

public interface IFactoryJoint {
	Joint produce(final IRecipeJoint pJointRecipe, final IPhysicalEntity pRecipeA, final IPhysicalEntity pRecipeB);
	IFactoryJointListener getListener();
	void setListener(IFactoryJointListener pListener);
}
