package eu.nazgee.box2dloader.stubs;

import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import eu.nazgee.box2dloader.Consts;


public class StubBody extends StubEntity implements IStubBody, Consts {
	public final String shapeName;
	private final boolean mBullet;

	public static String getStubName() {
		return STUB_BODY;
	}

	public StubBody(final Attributes pAttributes) {
		super(pAttributes);
		mBullet = SAXUtils.getBooleanAttribute(pAttributes, ATTRIBUTE_BODY_BULLET, false);

		shapeName = pAttributes.getValue(ATTRIBUTE_BODY_SHAPE);
		if (shapeName == null) {
			throw new RuntimeException(getClass().getSimpleName() +
					" didn't find " + ATTRIBUTE_BODY_SHAPE + " attribute");
		}
	}

	public boolean isBullet() {
		return mBullet;
	}

}
