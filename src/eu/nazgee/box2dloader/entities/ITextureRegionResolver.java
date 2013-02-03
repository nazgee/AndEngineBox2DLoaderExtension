package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.texture.region.ITextureRegion;

public interface ITextureRegionResolver {
	ITextureRegion getTexture(String pKey);
}