package eu.nazgee.box2dloader.recipes;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;

public interface IRecipeJoint extends IRecipe {
	public void setBodyA(IRecipeBody pRecipe);
	public void setBodyB(IRecipeBody pRecipe);
	public IRecipeBody getRecipeA();
	public IRecipeBody getRecipeB();
	public boolean isSane();
	String getTagRemote();
	public Vector2 getAnchorOfLocalBody(Vector2 pReuse, IPhysicsAwareEntity pEntity);
	public Joint physicalize(	final PhysicsWorld pWorld,
			final IPhysicsAwareEntity pLocal,
			final IPhysicsAwareEntity pRemote);
}
