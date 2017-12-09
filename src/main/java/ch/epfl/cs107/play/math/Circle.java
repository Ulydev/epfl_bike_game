package ch.epfl.cs107.play.math;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Random;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

/**
 * Represents an immutable circle.
 */
public final class Circle extends Shape {
    
    private final float radius;
    private final Vector center;

    /**
     * Creates a new circle.
     * @param radius size, not negative
     * @param center origin, not null
     */
    public Circle(float radius, Vector center) {
        if (center == null)
            throw new NullPointerException();
        this.radius = radius;
        this.center = center;
    }

    /**
     * Creates a new circle.
     * @param radius size, not negative
     */
    public Circle(float radius) {
        this(radius, Vector.ZERO);
    }
    
    /** @return size of circle */
    public float getRadius() {
        return radius;
    }

    /** @return origin of circle, not null */
    public Vector getCenter() {
        return center;
    }

    @Override
    public float getArea() {
        return (float)Math.PI * radius * radius;
    }

    @Override
    public float getPerimeter() {
        return 2.0f * (float)Math.PI * radius;
    }

    @Override
    public Vector sample(Random random) {
        
        // Sample random angle and distance (density increase quadratically)
        double distance = Math.sqrt(random.nextDouble()) * radius;
        double angle = random.nextDouble() * 2.0 * Math.PI;
        
        // Compute actual location
        return new Vector(
            center.x + (float)(distance * Math.cos(angle)),
            center.y + (float)(distance * Math.sin(angle))
        );
    }

    @Override
    public Path2D toPath() {

        Ellipse2D ellipse = new Ellipse2D.Float(
            center.x - radius,
		    center.y - radius,
            radius * 2,
            radius * 2
        );
		return new Path2D.Float(ellipse);
    }
    
    @Override
    Part build(FixtureDef fixtureDef, Entity entity) {
        
        // Create Box2D circle shape
        CircleShape shape = new CircleShape();
        shape.m_radius = radius;
        shape.m_p.x = center.x;
        shape.m_p.y = center.y;
        fixtureDef.shape = shape;
        
        // Instanciate the actual body part
        Part part = new Part();
        part.entity = entity;
        fixtureDef.userData = part;
        Fixture fixture = entity.body.createFixture(fixtureDef);
        part.fixtures = Arrays.asList(fixture);
        return part;
    }
    
}
