package eu.nazgee.box2dloader.factories;

/**
 * PhysicsEditor Importer Library
 *
 * Usage:
 * - Create an instance of this class
 * - Use the "open" method to load an XML file from PhysicsEditor
 * - Invoke "createBody" to create bodies from library.
 *
 * by Adrian Nilsson (ade at ade dot se)
 * BIG IRON GAMES (bigirongames.org)
 * Date: 2011-08-30
 * Time: 11:51
 */

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.util.Constants;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.math.MathUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import eu.nazgee.box2dloader.entities.IPhysicsAwareEntity;

public class BodyFactory implements IBodyFactory {
	private final HashMap<String, BodyTemplate> shapes = new HashMap<String, BodyTemplate>();
	private float pixelToMeterRatio = PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	private IBodyFactoryListener mListener;
	private PhysicsWorld mPhysicsWorld;

	public BodyFactory(final PhysicsWorld pPhysicsWorld) {
		mPhysicsWorld = pPhysicsWorld;
	}

	public BodyFactory(final PhysicsWorld pPhysicsWorld, final float pixelToMeterRatio) {
		mPhysicsWorld = pPhysicsWorld;
		this.pixelToMeterRatio = pixelToMeterRatio;
	}

	@Override
	public IBodyFactoryListener getListener() {
		return mListener;
	}

	@Override
	public void setListener(IBodyFactoryListener pListener) {
		mListener = pListener; 
	}

	public PhysicsWorld getPhysicsWorld() {
		return mPhysicsWorld;
	}

	public void setPhysicsWorld(PhysicsWorld mPhysicsWorld) {
		this.mPhysicsWorld = mPhysicsWorld;
	}
	/**
	 * Read shapes from an XML file and add to library. Path is relative to your
	 * assets folder so if your file is in "assets/shapes/shapes.xml", the path
	 * should be "shapes/shapes.xml"
	 * 
	 * @param context
	 * @param xmlFile
	 */
	public void open(final Context context, final String xmlFile) {
		append(context, xmlFile, this.pixelToMeterRatio);
	}

	/**
	 * If you wish, you may access the template data and create custom bodies.
	 * Be advised that vertex positions are pre-adjusted for Box2D coordinates
	 * (pixel to meter ratio).
	 * 
	 * @param key
	 * @return
	 */
	public BodyTemplate get(final String key) {
		return this.shapes.get(key);
	}

	@Override
	public Body produce(String pBodyName, IPhysicsAwareEntity pEntity) {

//	public Body createBody(final String name, final IEntity pEntity,
//			final PhysicsWorld pPhysicsWorld) {
		final BodyTemplate bodyTemplate = this.shapes.get(pBodyName);

		if (bodyTemplate == null) {
			throw new RuntimeException("there is no body named " + pBodyName + " defined");
		}

		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyTemplate.isDynamic ? BodyDef.BodyType.DynamicBody
				: BodyDef.BodyType.StaticBody;

		final float[] sceneCenterCoordinates = pEntity.getSceneCenterCoordinates();
		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X]
				/ this.pixelToMeterRatio;
		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y]
				/ this.pixelToMeterRatio;
		final float[] v = new float[2];
		final Transformation trans = pEntity.getLocalToSceneTransformation();
		v[Constants.VERTEX_INDEX_X] = pEntity.getWidth() * 0.5f;
		v[Constants.VERTEX_INDEX_Y] = -100;
		trans.transform(v);

		final float angle = MathUtils.atan2(sceneCenterCoordinates[Constants.VERTEX_INDEX_X] - v[Constants.VERTEX_INDEX_X],
				sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] - v[Constants.VERTEX_INDEX_Y]);
		boxBodyDef.angle = -angle;

		final Body boxBody = mPhysicsWorld.createBody(boxBodyDef);

		for (final FixtureTemplate fixtureTemplate : bodyTemplate.fixtureTemplates) {
			for (int i = 0; i < fixtureTemplate.polygons.length; i++) {
				final PolygonShape shape = new PolygonShape();
				final FixtureDef fixture = fixtureTemplate.fixtureDef;

				shape.set(fixtureTemplate.polygons[i].vertices);

				fixture.shape = shape;
				boxBody.createFixture(fixture);
				shape.dispose();
			}
			for (int i = 0; i < fixtureTemplate.circles.length; i++) {
				final CircleShape shape = new CircleShape();
				final FixtureDef fixture = fixtureTemplate.fixtureDef;
				final CircleTemplate template = fixtureTemplate.circles[i];

				shape.setPosition(new Vector2(template.x, template.y));
				shape.setRadius(template.r);

				fixture.shape = shape;
				boxBody.createFixture(fixture);
				shape.dispose();
			}
		}

		bindEntityToBody(pEntity, boxBody);

		return boxBody;
	}

	protected void bindEntityToBody(IPhysicsAwareEntity pEntity,
			final Body boxBody) {
		// connect entity and body
		final PhysicsConnector connector = new PhysicsConnector(pEntity, boxBody, true, true);
		mPhysicsWorld.registerPhysicsConnector(connector);
		pEntity.setPhysicsConnector(connector);
	}

	private void append(final Context context, final String name, final float pixelToMeterRatio) {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			final SAXParser parser = factory.newSAXParser();
			final ShapeLoader handler = new ShapeLoader(shapes, pixelToMeterRatio);
			parser.parse(context.getAssets().open(name), handler);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	protected static class ShapeLoader extends DefaultHandler {
		public static final String TAG_BODY = "body";
		public static final String TAG_FIXTURE = "fixture";
		public static final String TAG_POLYGON = "polygon";
		public static final String TAG_CIRCLE = "circle";
		public static final String TAG_VERTEX = "vertex";
		public static final String TAG_NAME = "name";
		public static final String TAG_X = "x";
		public static final String TAG_Y = "y";
		public static final String TAG_R = "r";
		public static final String TAG_DENSITY = "density";
		public static final String TAG_RESTITUTION = "restitution";
		public static final String TAG_FRICTION = "friction";
		public static final String TAG_FILTER_CATEGORY_BITS = "filter_categoryBits";
		public static final String TAG_FILTER_GROUP_INDEX = "filter_groupIndex";
		public static final String TAG_FILTER_MASK_BITS = "filter_maskBits";
		public static final String TAG_ISDYNAMIC = "dynamic";
		public static final String TAG_ISSENSOR = "isSensor";

		private final float pixelToMeterRatio;
		private StringBuilder builder;
		private final HashMap<String, BodyTemplate> shapes;
		private BodyTemplate currentBody;
		private final ArrayList<Vector2> currentPolygonVertices = new ArrayList<Vector2>();
		private final CircleTemplate currentCircle = new CircleTemplate(0, 0, 0);
		private final ArrayList<FixtureTemplate> currentFixtures = new ArrayList<FixtureTemplate>();
		private final ArrayList<PolygonTemplate> currentPolygons = new ArrayList<PolygonTemplate>();
		private final ArrayList<CircleTemplate> currentCircles = new ArrayList<CircleTemplate>();

		protected ShapeLoader(final HashMap<String, BodyTemplate> shapes,
				final float pixelToMeterRatio) {
			this.shapes = shapes;
			this.pixelToMeterRatio = pixelToMeterRatio;
		}

		@Override
		public void characters(final char[] ch, final int start, final int length)
				throws SAXException {
			super.characters(ch, start, length);
			builder.append(ch, start, length);
		}

		@Override
		public void endElement(final String uri, final String localName, final String name)
				throws SAXException {
			super.endElement(uri, localName, name);

			if (localName.equalsIgnoreCase(TAG_POLYGON)) {
				for (int i = 0; i < (currentPolygonVertices.size()/2); i++) {
					final int high = currentPolygonVertices.size() - 1 - i;
					final int low = i;

					final Vector2 tmp = currentPolygonVertices.get(low);
					currentPolygonVertices.set(low, currentPolygonVertices.get(high));
					currentPolygonVertices.set(high, tmp);
				}
				currentPolygons.add(new PolygonTemplate(currentPolygonVertices));
			} else if (localName.equalsIgnoreCase(TAG_CIRCLE)) {
				currentCircles.add(new CircleTemplate(
						currentCircle.x,
						-currentCircle.y,
						currentCircle.r));
			} else if (localName.equalsIgnoreCase(TAG_FIXTURE)) {
				final FixtureTemplate fixture = currentFixtures.get(currentFixtures
						.size() - 1);
				fixture.setPolygons(currentPolygons);
				fixture.setCircles(currentCircles);
			} else if (localName.equalsIgnoreCase(TAG_BODY)) {
				currentBody.setFixtures(currentFixtures);
				shapes.put(currentBody.name, currentBody);
			}

			builder.setLength(0);
		}

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
			builder = new StringBuilder();
		}

		@Override
		public void startElement(final String uri, final String localName, final String name,
				final Attributes attributes) throws SAXException {
			super.startElement(uri, localName, name, attributes);
			builder.setLength(0);
			if (localName.equalsIgnoreCase(TAG_BODY)) {
				this.currentFixtures.clear();
				this.currentBody = new BodyTemplate();
				this.currentBody.name = attributes.getValue(TAG_NAME);
				this.currentBody.isDynamic = attributes.getValue(TAG_ISDYNAMIC)
						.equalsIgnoreCase("true");
			} else if (localName.equalsIgnoreCase(TAG_FIXTURE)) {
				final FixtureTemplate fixture = new FixtureTemplate();
				currentPolygons.clear();
				currentCircles.clear();
				final float restitution = Float.parseFloat(attributes
						.getValue(TAG_RESTITUTION));
				final float friction = Float.parseFloat(attributes
						.getValue(TAG_FRICTION));
				final float density = Float.parseFloat(attributes
						.getValue(TAG_DENSITY));
				final short category = parseShort(attributes
						.getValue(TAG_FILTER_CATEGORY_BITS));
				final short groupIndex = parseShort(attributes
						.getValue(TAG_FILTER_GROUP_INDEX));
				final short maskBits = parseShort(attributes
						.getValue(TAG_FILTER_MASK_BITS));
				final boolean isSensor = attributes.getValue(TAG_ISSENSOR)
						.equalsIgnoreCase("true");
				fixture.fixtureDef = PhysicsFactory.createFixtureDef(density,
						restitution, friction, isSensor, category, maskBits,
						groupIndex);
				currentFixtures.add(fixture);
			} else if (localName.equalsIgnoreCase(TAG_POLYGON)) {
				currentPolygonVertices.clear();
			} else if (localName.equalsIgnoreCase(TAG_VERTEX)) {
				currentPolygonVertices.add(new Vector2(
						Float.parseFloat(attributes.getValue(TAG_X)) / this.pixelToMeterRatio,
						-Float.parseFloat(attributes.getValue(TAG_Y)) / this.pixelToMeterRatio));
			} else if (localName.equalsIgnoreCase(TAG_CIRCLE)) {
				currentCircle.r = Float.parseFloat(attributes.getValue(TAG_R))
						/ this.pixelToMeterRatio;
				currentCircle.x = Float.parseFloat(attributes.getValue(TAG_X))
						/ this.pixelToMeterRatio;
				currentCircle.y = Float.parseFloat(attributes.getValue(TAG_Y))
						/ this.pixelToMeterRatio;
			}
		}
	}

	private static short parseShort(final String val) {
		final int intVal = Integer.parseInt(val);
		return (short) intVal;
	}

	private static class BodyTemplate {
		public String name;
		public boolean isDynamic = true;
		public FixtureTemplate[] fixtureTemplates;

		public void setFixtures(final ArrayList<FixtureTemplate> fixtureTemplates) {
			this.fixtureTemplates = fixtureTemplates
					.toArray(new FixtureTemplate[fixtureTemplates.size()]);
		}
	}

	private static class FixtureTemplate {
		public PolygonTemplate[] polygons;
		public CircleTemplate[] circles;
		public FixtureDef fixtureDef;

		public void setPolygons(final ArrayList<PolygonTemplate> polygonTemplates) {
			polygons = polygonTemplates
					.toArray(new PolygonTemplate[polygonTemplates.size()]);
		}

		public void setCircles(final ArrayList<CircleTemplate> circleTemplates) {
			circles = circleTemplates
					.toArray(new CircleTemplate[circleTemplates.size()]);
		}
	}

	private static class PolygonTemplate {
		public Vector2[] vertices;

		public PolygonTemplate(final ArrayList<Vector2> vectorList) {
			vertices = vectorList.toArray(new Vector2[vectorList.size()]);
		}
	}

	private static class CircleTemplate {
		public float x;
		public float y;
		public float r;

		public CircleTemplate(final float x, final float y, final float r) {
			;
			this.x = x;
			this.y = y;
			this.r = r;
		}
	}
}