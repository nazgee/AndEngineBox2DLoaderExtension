package eu.nazgee.box2dloader.recipes;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import eu.nazgee.box2dloader.Consts;

public class FactoryRecipes extends DefaultHandler {

	protected final FactoryRecipeParser mProducer = new FactoryRecipeParser();
	private final Stack<IRecipe> mRecipesStack = new Stack<IRecipe>();

	public final HashMap<String, IRecipeEntity> mEntities = new HashMap<String, IRecipeEntity>();
	public final HashMap<String, IRecipeBody> mBodies = new HashMap<String, IRecipeBody>();
	public HashMap<IRecipeBody, Collection<IRecipeJoint>> mJoints = new HashMap<IRecipeBody, Collection<IRecipeJoint>>();

	public FactoryRecipes() {
		mProducer.addHelperLast(new RecipeParserEntity());
		mProducer.addHelperLast(new RecipeParserSprite());
		mProducer.addHelperLast(new RecipeParserBody());
		mProducer.addHelperLast(new RecipeParserBodySprite());
		mProducer.addHelperLast(new RecipeParserJoint(new IFactoryRecipeParser[] { new RecipeParserJointRevolution(),
				new RecipeParserJointRope() }));

	}

	@Override
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		super.characters(ch, start, length);
	}

	@Override
	public void startElement(final String pUri, final String pLocalName, final String pName,
			final Attributes pAttributes) throws SAXException {
		super.startElement(pUri, pLocalName, pName, pAttributes);
		IRecipe r = mProducer.parse(pLocalName, pAttributes);

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
		Log.d(getClass().getSimpleName(), "mEntities.size()=" + mEntities.size());
		Log.d(getClass().getSimpleName(), "mBodies.size()=" + mBodies.size());
		Log.d(getClass().getSimpleName(), "mJoints.size()=" + mJoints.size());

		// we should have all bodies collected by now. it's time to rebind
		// joints with remote counterparts
		for (final Collection<IRecipeJoint> joints : mJoints.values()) {
			for (final IRecipeJoint joint : joints) {

				final IRecipeBody body = mBodies.get(joint.getTagRemote());
				if (body != null) {
					joint.setBodyB(body);
				} else {
					Log.e(getClass().getSimpleName(), "rebinding " + joint.getTag() + " with " + joint.getTagRemote() + " failed :(");
				}
			}
		}
	}

	public Collection<IRecipeJoint> getJointsAtAnchors(final Collection<IRecipe> pRecipes) {
		if (pRecipes == null) {
			return null;
		}

		final Collection<IRecipeJoint> ret = new LinkedList<IRecipeJoint>();

		for (final IRecipe recipe : pRecipes) {
			if (recipe != null) {
				final Collection<IRecipeJoint> joints = mJoints.get(recipe);
				if (joints != null) {
					ret.addAll(joints);
				}
			}
		}

		return ret;
	}

	protected IRecipe peekRecipePrevious() {
		final int size = mRecipesStack.size();

		if (size > 1) {
			return mRecipesStack.get(size - 2);
		} else {
			return null;
		}
	}

	protected IRecipe peekRecipeCurrent() {
		return mRecipesStack.peek();
	}


	protected class RecipeParserJoint extends FactoryRecipeParser {
		private RecipeParserJoint(IFactoryRecipeParser[] helpers) {
			super(helpers);
		}

		@Override
		public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
			if (!pRecipeName.equals(RecipeJoint.getRecipeName())) {
				return false;
			}
			final String type = pAttributes.getValue(Consts.JOINT_TYPE);
			return helpersUnderstandRecipe(type, pAttributes);
		}

		@Override
		public IRecipe parse(String pRecipeName, Attributes pAttributes) {
			if (!pRecipeName.equals(RecipeJoint.getRecipeName())) {
				return null;
			}
			final String type = pAttributes.getValue(Consts.JOINT_TYPE);

			IRecipeJoint recipe = (IRecipeJoint) helpersProduce(type, pAttributes);

			// this cast will fail if joint is embedded in a non-body recipe
			final IRecipeBody body = (IRecipeBody) peekRecipeCurrent();
			recipe.setBodyA(body);

			Collection<IRecipeJoint> jointsList = mJoints.get(body);
			if (jointsList == null) {
				jointsList = new LinkedList<IRecipeJoint>();
			}
			jointsList.add(recipe);
			mJoints.put(body, jointsList);

			return recipe;
		}
	}

	protected class RecipeParserJointRope extends FactoryRecipeParser {
		@Override
		public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
			return pRecipeName.equals(RecipeJointRope.getRecipeJointType());
		}

		@Override
		public IRecipe parse(String pRecipeName, Attributes pAttributes) {
			return new RecipeJointRope(pAttributes);
		}
	}

	protected class RecipeParserJointRevolution extends FactoryRecipeParser {
		@Override
		public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
			return pRecipeName.equals(RecipeJointRevolution.getRecipeJointType());
		}

		@Override
		public IRecipe parse(String pRecipeName, Attributes pAttributes) {
			return new RecipeJointRevolution(pAttributes);
		}
	}

	protected class RecipeParserBodySprite extends FactoryRecipeParser {
		@Override
		public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
			return pRecipeName.equals(RecipeBodySprite.getRecipeName());
		}

		@Override
		public IRecipe parse(String pRecipeName, Attributes pAttributes) {
			IRecipeBody r = new RecipeBodySprite(pAttributes);
			mBodies.put(r.getTag(), r);
			return r; 
		}
	}

	protected class RecipeParserBody extends FactoryRecipeParser {
		@Override
		public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
			return pRecipeName.equals(RecipeBody.getRecipeName());
		}

		@Override
		public IRecipe parse(String pRecipeName, Attributes pAttributes) {
			IRecipeBody r = new RecipeBody(pAttributes);
			mBodies.put(r.getTag(), r);
			return r; 
		}
	}

	protected class RecipeParserSprite extends FactoryRecipeParser {
		@Override
		public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
			return pRecipeName.equals(RecipeSprite.getRecipeName());
		}

		@Override
		public IRecipe parse(String pRecipeName, Attributes pAttributes) {
			IRecipeEntity r = new RecipeSprite(pAttributes);
			mEntities.put(r.getTag(), r);
			return r; 
		}
	}

	protected class RecipeParserEntity extends FactoryRecipeParser {
		@Override
		public boolean understandsRecipe(final String pRecipeName, final Attributes pAttributes) {
			return pRecipeName.equals(RecipeEntity.getRecipeName());
		}

		@Override
		public IRecipe parse(String pRecipeName, final Attributes pAttributes) {
			IRecipeEntity r = new RecipeEntity(pAttributes);
			mEntities.put(r.getTag(), r);
			return r; 
		}
	}

}