package eu.nazgee.box2dloader.entities;

import java.util.LinkedList;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
import eu.nazgee.box2dloader.recipes.IRecipeEntity;

public class FactoryPhysicalWorker implements IFactoryPhysicalWorker {
	private final LinkedList<IFactoryPhysicalWorker> mHelpers = new LinkedList<IFactoryPhysicalWorker>();
	protected final VertexBufferObjectManager mVBO;

	public FactoryPhysicalWorker(VertexBufferObjectManager pVBO, IFactoryPhysicalWorker ... helpers) {
		mVBO = pVBO;
		for (IFactoryPhysicalWorker helper : helpers) {
			mHelpers.add(helper);
		}
	}

	public void addHelperLast(IFactoryPhysicalWorker pHelper) {
		mHelpers.addLast(pHelper);
	}

	public void addHelperFirs(IFactoryPhysicalWorker pHelper) {
		mHelpers.addFirst(pHelper);
	}

	@Override
	public boolean understandsRecipe(final IRecipeEntity pRecipe) {
		return helpersUnderstandRecipe(pRecipe);
	}

	@Override
	public IPhysicalEntity build(final IRecipeEntity pRecipe) {
		IPhysicalEntity pae = helpersProduce(pRecipe);
		return pae;
	}

	protected boolean helpersUnderstandRecipe(final IRecipeEntity pRecipe) {
		for (final IFactoryPhysicalWorker helper : mHelpers) {
			if (helper.understandsRecipe(pRecipe)) {
				return true;
			}
		}
		return false;
	}

	protected IPhysicalEntity helpersProduce(final IRecipeEntity pRecipe) {
		IPhysicalEntity pae;
		for (final IFactoryPhysicalWorker helper : mHelpers) {
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

	protected void configure(IRecipeEntity pRecipe, IPhysicalEntity product) {
		product.setRotation(pRecipe.getRotation());
		product.setZIndex(pRecipe.getZindex());
		product.setColor(pRecipe.getColorR(), pRecipe.getColorG(), pRecipe.getColorB());
	}
}