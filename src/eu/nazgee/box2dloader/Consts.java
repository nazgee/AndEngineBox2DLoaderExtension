package eu.nazgee.box2dloader;

import org.andengine.util.level.constants.LevelConstants;


public interface Consts {
	public static final String ATTRIBUTE_TAG = "tag";
	public static final String ATTRIBUTE_X = "x";
	public static final String ATTRIBUTE_Y = "y";
	public static final String ATTRIBUTE_ZINDEX = "zindex";
	public static final String ATTRIBUTE_ROTATION = "rotation";
	public static final String ATTRIBUTE_COLOR_R = LevelConstants.TAG_LEVEL_ENTITY_ATTRIBUTE_COLOR_R;
	public static final String ATTRIBUTE_COLOR_G = LevelConstants.TAG_LEVEL_ENTITY_ATTRIBUTE_COLOR_G;
	public static final String ATTRIBUTE_COLOR_B = LevelConstants.TAG_LEVEL_ENTITY_ATTRIBUTE_COLOR_B;

	// joint
	public static final String ATTRIBUTE_JOINT_REMOTE = "remote";
	public static final String ATTRIBUTE_JOINT_COLLIDE = "collide"; // whether connected elements shall collide
	public static final String ATTRIBUTE_JOINT_REMOTE_X = "remote-x"; // joint location in remote body
	public static final String ATTRIBUTE_JOINT_REMOTE_Y = "remote-y"; // joint location in remote body

	// joint-revolution
	public static final String ATTRIBUTE_JOINT_REVOLUTION_MIN = "min";
	public static final String ATTRIBUTE_JOINT_REVOLUTION_MAX = "max";

	// joint-revolution
	public static final String ATTRIBUTE_JOINT_ROPE_MAX = ATTRIBUTE_JOINT_REVOLUTION_MAX;


	// body
	public static final String ATTRIBUTE_BODY_BULLET = "bullet";
	public static final String ATTRIBUTE_BODY_SHAPE = "shape";

	// sprite
	public static final String ATTRIBUTE_SPRITE_TEXTURE_REGION = "texture-region";

	public static final String STUB_ENTITY = "entity";
	public static final String STUB_BODY = "body";
	public static final String STUB_BODY_WITH_SPRITE = "body-sprite";
	public static final String STUB_SPRITE = "sprite";
	public static final String STUB_JOINT = "joint";

	// joints types
	public static final String JOINT_TYPE = "type";
	public static final String JOINT_TYPE_REVOLUTION = "revolution";
	public static final String JOINT_TYPE_ROPE = "rope";
}
