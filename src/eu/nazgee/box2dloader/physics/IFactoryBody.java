package eu.nazgee.box2dloader.physics;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;

public interface IFactoryBody {
	Body produce(final String pBodyName, final IPhysicsAwareEntity pEntity);
	IFactoryBodyListener getListener();
	void setListener(IFactoryBodyListener pListener);
}
