package eu.nazgee.box2dloader.recipes;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

public class FactoryRecipe extends FactoryRecipeBase {

	protected final FactoryRecipeWorker mWorker = new FactoryRecipeWorker(this);
	private final Stack<IRecipe> mRecipesStack = new Stack<IRecipe>();

	private final HashMap<String, IRecipeEntity> mEntities = new HashMap<String, IRecipeEntity>();
	private final HashMap<String, IRecipeBody> mBodies = new HashMap<String, IRecipeBody>();
	private final HashMap<IRecipeBody, Collection<IRecipeJoint>> mJoints = new HashMap<IRecipeBody, Collection<IRecipeJoint>>();

	public FactoryRecipe() {
		mWorker.addHelperLast(new FactoryRecipeWorkerEntity(this));
		mWorker.addHelperLast(new FactoryRecipeWorkerSprite(this));
		mWorker.addHelperLast(new FactoryRecipeWorkerBody(this));
		mWorker.addHelperLast(new FactoryRecipeWorkerBodySprite(this));
		mWorker.addHelperLast(new FactoryRecipeWorkerJoint(this,
				new FactoryRecipeWorkerJointRevolution(this),
				new FactoryRecipeWorkerJointRope(this)));

	}

	@Override
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		super.characters(ch, start, length);
	}

	@Override
	public void startElement(final String pUri, final String pLocalName, final String pName,
			final Attributes pAttributes) throws SAXException {
		super.startElement(pUri, pLocalName, pName, pAttributes);
		IRecipe r = mWorker.parse(pLocalName, pAttributes);

		if (r == null) {
			Log.e(getClass().getSimpleName(), "some serious trouble when baking " + pLocalName);
		} else {
			Log.e(getClass().getSimpleName(), "baked " + pLocalName + "(" + r.getTag() + ")");
		}

		mRecipesStack.push(r);
		// make sure that children-parent relationship is maintained
		final IRecipe parent = peekRecipePrevious();
		if (parent != null) {
			parent.attachChild(r);
		}

	}

	@Override
	public void endElement(final String uri, final String localName, final String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		mRecipesStack.pop();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		Log.d(getClass().getSimpleName(), "mEntities.size()=" + getEntities().size());
		Log.d(getClass().getSimpleName(), "mBodies.size()=" + getBodies().size());
		Log.d(getClass().getSimpleName(), "mJoints.size()=" + getJoints().size());

		// we should have all bodies collected by now. it's time to rebind
		// joints with remote counterparts
		for (final Collection<IRecipeJoint> joints : getJoints().values()) {
			for (final IRecipeJoint joint : joints) {

				final IRecipeBody body = getBodies().get(joint.getTagRemote());
				if (body != null) {
					joint.setBodyB(body);
				} else {
					Log.e(getClass().getSimpleName(), "rebinding " + joint.getTag() + " with " + joint.getTagRemote() + " failed :(");
				}
			}
		}
	}
	@Override
	public Collection<IRecipeJoint> getJointsAtAnchors(final Collection<IRecipe> pRecipes) {
		if (pRecipes == null) {
			return null;
		}

		final Collection<IRecipeJoint> ret = new LinkedList<IRecipeJoint>();

		for (final IRecipe recipe : pRecipes) {
			if (recipe != null) {
				final Collection<IRecipeJoint> joints = getJoints().get(recipe);
				if (joints != null) {
					ret.addAll(joints);
				}
			}
		}

		return ret;
	}
	@Override
	public HashMap<String, IRecipeEntity> getEntities() {
		return mEntities;
	}
	@Override
	public HashMap<String, IRecipeBody> getBodies() {
		return mBodies;
	}
	@Override
	public HashMap<IRecipeBody, Collection<IRecipeJoint>> getJoints() {
		return mJoints;
	}
	@Override
	public IRecipe peekRecipePrevious() {
		final int size = mRecipesStack.size();

		if (size > 1) {
			return mRecipesStack.get(size - 2);
		} else {
			return null;
		}
	}

	@Override
	public IRecipe peekRecipeCurrent() {
		return mRecipesStack.peek();
	}















}