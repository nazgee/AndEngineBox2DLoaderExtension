package eu.nazgee.box2dloader.stubs;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import eu.nazgee.box2dloader.Consts;
import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.entities.PhysicsAwareEntity;

public class StubEntity extends Stub implements IStubEntity, Consts {
	public final float x;
	public final float y;
	public final int zindex;
	public final float rotation;
	public final float color_r;
	public final float color_g;
	public final float color_b;

	public static String getStubName() {
		return STUB_ENTITY;
	}

	public StubEntity(final Attributes pAttributes) {
		super(pAttributes);

		x = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_X, 0);
		y = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_Y, 0);
		zindex = SAXUtils.getIntAttribute(pAttributes, ATTRIBUTE_ZINDEX, 0);
		rotation = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_ROTATION, 0);
		color_r = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_COLOR_R, 1);
		color_g = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_COLOR_G, 1);
		color_b = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_COLOR_B, 1);
	}

	public IPhysicsAwareEntity populatePhysicsAwareEntity(final VertexBufferObjectManager pVBO) {
		//		Debug.i(name + " - new " + getClass().getSimpleName() + " x=" + getX() + "; y=" +getY());
		final PhysicsAwareEntity face = new PhysicsAwareEntity(this, this.getX(), this.getY());
		face.setRotation(this.rotation);
		face.setZIndex(zindex);
		face.setColor(color_r, color_g, color_b);
		return face;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
}