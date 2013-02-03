package eu.nazgee.box2dloader.entities;

import org.andengine.entity.IEntity;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public interface IPhysicsAwareEntity extends IEntity {
	public Body getBody();
	public void setBody(Body pBody);
	public void setPhysicsConnector(PhysicsConnector pConnector);
	public PhysicsConnector getPhysicsConnector();
	public IRecipeEntity getRecipe();
	public void dispose(final PhysicsWorld pWorld);
}
