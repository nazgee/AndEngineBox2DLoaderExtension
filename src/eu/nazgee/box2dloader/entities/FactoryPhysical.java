package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public class FactoryPhysical implements IFactoryPhysical {
	FactoryPhysicalWorker mWorker;

	public FactoryPhysical(final ITextureRegionResolver pResolver, final VertexBufferObjectManager pVBO) {
		super();

		mWorker = new FactoryPhysicalWorker(pVBO);
		mWorker.addHelperLast(new FactoryPhysicalTiledSpriteWorker(pResolver, pVBO));
		mWorker.addHelperLast(new FactoryPhysicalSpriteWorker(pResolver, pVBO));
		mWorker.addHelperLast(new FactoryPhysicalBodyTiledSpriteWorker(pResolver, pVBO));
		mWorker.addHelperLast(new FactoryPhysicalBodySpriteWorker(pResolver, pVBO));
		mWorker.addHelperLast(new FactoryPhysicalEntityWorker(pVBO));
	}

	@Override
	public IPhysicalEntity produce(IRecipeEntity pRecipe) {
		IPhysicalEntity product = mWorker.build(pRecipe);
		if (product == null) {
			Log.e(getClass().getSimpleName(), "failed producing " + pRecipe.getTag());
		} else {
			Log.d(getClass().getSimpleName(), "produced " + pRecipe.getTag() + "(" + product.getTag() + ")");
		}
		return product;
	}
}
