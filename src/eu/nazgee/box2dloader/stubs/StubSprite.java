package eu.nazgee.box2dloader.stubs;

import org.xml.sax.Attributes;

public class StubSprite extends StubEntity {
	public final String textureName;

	public static String getStubName() {
		return STUB_SPRITE;
	}

	public StubSprite(final Attributes pAttributes) {
		super(pAttributes);
		textureName = pAttributes.getValue(ATTRIBUTE_SPRITE_TEXTURE_REGION);
		if (textureName == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find " + ATTRIBUTE_SPRITE_TEXTURE_REGION + " attribute");
		}
	}
}
