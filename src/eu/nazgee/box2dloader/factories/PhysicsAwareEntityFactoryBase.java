package eu.nazgee.box2dloader.factories;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.stubs.IStub;
import eu.nazgee.box2dloader.stubs.StubBodySprite;
import eu.nazgee.box2dloader.stubs.StubEntity;
import eu.nazgee.box2dloader.stubs.StubSprite;

public abstract class PhysicsAwareEntityFactoryBase implements IPhysicsAwareEntityFactory {

	private VertexBufferObjectManager mVBO;

	public PhysicsAwareEntityFactoryBase(VertexBufferObjectManager mVBO) {
		super();
		this.mVBO = mVBO;
	}

	@Override
	public IPhysicsAwareEntity produce(IStub pStub) {
		IPhysicsAwareEntity physicsAwareEntity = null;

		if (pStub instanceof StubBodySprite) {
			StubBodySprite stub = ((StubBodySprite) pStub);
			physicsAwareEntity = stub.populate(getTextureFromKey(stub.textureName), mVBO);
		} else if (pStub instanceof StubSprite) {
			StubSprite stub = ((StubSprite) pStub);
			physicsAwareEntity = stub.populate(getTextureFromKey(stub.textureName), mVBO);
		} else if (pStub instanceof StubEntity) {
			physicsAwareEntity = ((StubEntity) pStub).populatePhysicsAwareEntity(mVBO);
		}

		return physicsAwareEntity;
	}

	public abstract ITextureRegion getTextureFromKey(String pKey);
}
