package eu.nazgee.box2dloader.entities;

import java.util.LinkedList;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public class FactoryWorkerEntity implements IFactoryWorkerEntity {
	private final LinkedList<IFactoryWorkerEntity> mHelpers = new LinkedList<IFactoryWorkerEntity>();
	protected final VertexBufferObjectManager mVBO;

	public FactoryWorkerEntity(VertexBufferObjectManager pVBO, IFactoryWorkerEntity ... helpers) {
		mVBO = pVBO;
		for (IFactoryWorkerEntity helper : helpers) {
			mHelpers.add(helper);
		}
	}

	public void addHelperLast(IFactoryWorkerEntity pHelper) {
		mHelpers.addLast(pHelper);
	}

	public void addHelperFirs(IFactoryWorkerEntity pHelper) {
		mHelpers.addFirst(pHelper);
	}

	@Override
	public boolean understandsRecipe(final IRecipeEntity pRecipe) {
		return helpersUnderstandRecipe(pRecipe);
	}

	@Override
	public IPhysicsAwareEntity build(final IRecipeEntity pRecipe) {
		IPhysicsAwareEntity pae = helpersProduce(pRecipe);
		return pae;
	}

	protected boolean helpersUnderstandRecipe(final IRecipeEntity pRecipe) {
		for (final IFactoryWorkerEntity helper : mHelpers) {
			if (helper.understandsRecipe(pRecipe)) {
				return true;
			}
		}
		return false;
	}

	protected IPhysicsAwareEntity helpersProduce(final IRecipeEntity pRecipe) {
		IPhysicsAwareEntity pae;
		for (final IFactoryWorkerEntity helper : mHelpers) {
			if (!helper.understandsRecipe(pRecipe)) {
				continue;
			}
			pae = helper.build(pRecipe);
			if (pae == null) {
				Log.w(getClass().getSimpleName(), "helper=" + helper + " did not produce what was asked for");
			}
			return pae;
		}
		return null;
	}

	protected void configure(IRecipeEntity pRecipe, IPhysicsAwareEntity product) {
		product.setRotation(pRecipe.getRotation());
		product.setZIndex(pRecipe.getZindex());
		product.setColor(pRecipe.getColorR(), pRecipe.getColorG(), pRecipe.getColorB());
	}
}