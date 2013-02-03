package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public class FactoryPhysicsAwareEntity implements IFactoryPhysicsAwareEntity {
	FactoryWorkerEntity mWorker;

	public FactoryPhysicsAwareEntity(final ITextureRegionResolver pResolver, final VertexBufferObjectManager pVBO) {
		super();

		mWorker = new FactoryWorkerEntity(pVBO);
		mWorker.addHelperLast(new FactoryWorkerPhysicsAwareSprite(pResolver, pVBO));
		mWorker.addHelperLast(new FactoryWorkerPhysicsAwareBodySprite(pResolver, pVBO));
		mWorker.addHelperLast(new FactoryWorkerPhysicsAwareEntity(pVBO));
	}

	@Override
	public IPhysicsAwareEntity produce(IRecipeEntity pRecipe) {
		IPhysicsAwareEntity product = mWorker.build(pRecipe);
		if (product == null) {
			Log.e(getClass().getSimpleName(), "failed producing " + pRecipe.getTag());
		} else {
			Log.d(getClass().getSimpleName(), "produced " + pRecipe.getTag() + "(" + product.getTag() + ")");
		}
		return product;
	}
}
