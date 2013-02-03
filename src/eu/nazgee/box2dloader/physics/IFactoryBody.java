package eu.nazgee.box2dloader.physics;

import com.badlogic.gdx.physics.box2d.Body;

import eu.nazgee.box2dloader.entities.IPhysicalEntity;

public interface IFactoryBody {
	Body produce(final String pBodyName, final IPhysicalEntity pEntity);
	IFactoryBodyListener getListener();
	void setListener(IFactoryBodyListener pListener);
}
