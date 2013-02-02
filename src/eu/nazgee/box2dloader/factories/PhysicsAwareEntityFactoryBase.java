package eu.nazgee.box2dloader.factories;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;
import eu.nazgee.box2dloader.entities.PhysicsAwareEntity;
import eu.nazgee.box2dloader.entities.PhysicsAwareSprite;
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
		if (!(pStub instanceof StubEntity)) {
			return null;
		}

		StubEntity stubEntity = ((StubEntity) pStub);

		IPhysicsAwareEntity product;
		if (stubEntity instanceof StubBodySprite) {
			StubBodySprite stub = ((StubBodySprite) stubEntity);
			product = new PhysicsAwareSprite(stub, stub.getX(), stub.getY(), getTextureFromKey(stub.textureName), mVBO);
		} else if (stubEntity instanceof StubSprite) {
			StubSprite stub = ((StubSprite) stubEntity);
			product = new PhysicsAwareSprite(stub, stub.getX(), stub.getY(), getTextureFromKey(stub.textureName), mVBO);
		} else  {
			StubEntity stub = stubEntity;
			product = new PhysicsAwareEntity(stub, stub.getX(), stub.getY());
		}

		product.setRotation(stubEntity.rotation);
		product.setZIndex(stubEntity.zindex);
		product.setColor(stubEntity.color_r, stubEntity.color_g, stubEntity.color_b);

		return product;
	}

	public abstract ITextureRegion getTextureFromKey(String pKey);
}
