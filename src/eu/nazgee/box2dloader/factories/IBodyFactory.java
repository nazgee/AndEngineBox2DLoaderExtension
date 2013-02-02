package eu.nazgee.box2dloader.factories;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;

public interface IBodyFactory {
	Body produce(final String pBodyName, final IPhysicsAwareEntity pEntity);
	IBodyFactoryListener getListener();
	void setListener(IBodyFactoryListener pListener);
}
