package eu.nazgee.box2dloader.stubs;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.xml.sax.Attributes;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.entities.PhysicsAwareSprite;

public class StubBodySprite extends StubBody {
	public final String textureName;

	public static String getStubName() {
		return STUB_BODY_WITH_SPRITE;
	}

	public StubBodySprite(final Attributes pAttributes) {
		super(pAttributes);
		textureName = pAttributes.getValue(ATTRIBUTE_SPRITE_TEXTURE_REGION);
		if (textureName == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find " + ATTRIBUTE_SPRITE_TEXTURE_REGION + " attribute");
		}
	}

	public IPhysicsAwareEntity populate(final ITextureRegion pRegion, final VertexBufferObjectManager pVBO) {
		final PhysicsAwareSprite face = new PhysicsAwareSprite(this, this.getX(), this.getY(), pRegion, pVBO);
		face.setRotation(this.rotation);
		face.setZIndex(zindex);
		face.setColor(color_r, color_g, color_b);
		return face;
	}



}
