package eu.nazgee.box2dloader.recipes;

import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import com.badlogic.gdx.math.Vector2;

import eu.nazgee.box2dloader.Consts;
import eu.nazgee.box2dloader.entities.IPhysicalEntity;

public abstract class RecipeJoint extends Recipe implements IRecipeJoint, Consts {

	private IRecipeBody mBodyA;
	private IRecipeBody mBodyB;
	private final String mTagRemote;
	private final float mX;
	private final float mY;
	private float mRemoteX = 0;
	private float mRemoteY = 0;
	private boolean mDeriveRemoteXYfromLocalXY = true;
	private final boolean mCollideConnected;

	public static String getRecipeName() {
		return RECIPE_JOINT;
	}

	public RecipeJoint(final Attributes pAttributes) {
		super(pAttributes);

		mTagRemote = pAttributes.getValue(ATTRIBUTE_JOINT_REMOTE);
		if (mTagRemote == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find remote attribute");
		}

		mX = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_X, 0);
		mY = SAXUtils.getFloatAttribute(pAttributes, ATTRIBUTE_Y, 0);

		try {
			mRemoteX = SAXUtils.getFloatAttributeOrThrow(pAttributes, ATTRIBUTE_JOINT_REMOTE_X);
			mRemoteY = SAXUtils.getFloatAttributeOrThrow(pAttributes, ATTRIBUTE_JOINT_REMOTE_Y);
			mDeriveRemoteXYfromLocalXY = false;
		} catch (final Exception e) {
			mDeriveRemoteXYfromLocalXY = true;
		}

		mCollideConnected = SAXUtils.getBooleanAttribute(pAttributes, ATTRIBUTE_JOINT_COLLIDE, false);
	}

	@Override
	public String getTagRemote() {
		return mTagRemote;
	}

	@Override
	public void setBodyA(final IRecipeBody pBody) {
		mBodyA = pBody;
	}

	@Override
	public IRecipeBody getRecipeA() {
		return mBodyA;
	}

	@Override
	public void setBodyB(final IRecipeBody pBody) {
		mBodyB = pBody;
	}

	@Override
	public IRecipeBody getRecipeB() {
		return mBodyB;
	}

	@Override
	public Vector2 getAnchorOfLocalBody(final Vector2 pReuse, final IPhysicalEntity pEntity) {
		pReuse.set(	(pEntity.getWidth() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT) * getX(),
				(pEntity.getHeight() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT) * getY());
		return pReuse;
	}

	public Vector2 getAnchorOfRemoteBody(final Vector2 pReuse, final IPhysicalEntity pLocalEntity, final IPhysicalEntity pRemoteEntity) {
		if (isDeriveRemoteXYfromLocalXY()) {
			getAnchorOfLocalBody(pReuse, pLocalEntity);
			pReuse.set(pLocalEntity.getBody().getWorldPoint(pReuse));
			pReuse.set(pRemoteEntity.getBody().getLocalPoint(pReuse));
			return pReuse;
		} else {
			pReuse.set(	(pRemoteEntity.getWidth() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT) * getRemoteX(),
					(pRemoteEntity.getHeight() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT) * getRemoteY());
			return pReuse;
		}
	}

	public boolean isDeriveRemoteXYfromLocalXY() {
		return mDeriveRemoteXYfromLocalXY;
	}

	private float getY() {
		return mY;
	}

	private float getX() {
		return mX;
	}

	private float getRemoteY() {
		return mRemoteY;
	}

	private float getRemoteX() {
		return mRemoteX;
	}

	@Override
	public boolean isSane() {
		return ((mBodyA != null) && (mBodyB != null));
	}

	public boolean isCollideConnected() {
		return mCollideConnected;
	}
}
