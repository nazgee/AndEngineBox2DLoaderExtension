package eu.nazgee.box2dloader.physics;

import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicalEntity;
import eu.nazgee.box2dloader.recipes.IRecipeJoint;

public interface IFactoryJointListener {
	public void onJointCreated(IRecipeJoint pRecipe, Joint pJoint, IPhysicalEntity pEntityA, IPhysicalEntity pEntityB);
}
