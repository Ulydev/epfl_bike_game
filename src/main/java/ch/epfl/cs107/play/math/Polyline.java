package ch.epfl.cs107.play.math;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

/**
 * Represents an open or closed sequence of segments. As it has no mass, it can only be used in fixed bodies.
 */
public final class Polyline extends Shape {

    private final boolean closed;
    private final List<Vector> points;
    private float[] lengths;
    private float length;
    
    /**
     * Creates a new polyline.
     * @param closed whether last point is connected to the first one
     * @param points at least two points, not null
     */
    public Polyline(boolean closed, List<Vector> points) {
        if (points.size() < 2)
            throw new IllegalArgumentException("At least two points are required");
        this.closed = closed;
        this.points = new ArrayList<>(points);
        initialize();
    }
    
    /**
     * Creates a new polyline.
     * @param points at least two points, not null
     */
    public Polyline(List<Vector> points) {
        this(false, points);
    }
    
    /**
     * Creates a new polyline.
     * @param closed whether last point is connected to the first one
     * @param points at least two points, not null
     */
    public Polyline(boolean closed, Vector... points) {
        this(closed, Arrays.asList(points));
    }
    
    /**
     * Creates a new polyline.
     * @param points at least two points, not null
     */
    public Polyline(Vector... points) {
        this(false, points);
    }
    
    /**
     * Creates a new polyline.
     * @param closed whether last point is connected to the first one
     * @param points at least two points, not null
     */
    public Polyline(boolean closed, float... points) {
        if (points.length % 2 != 0)
            throw new IllegalArgumentException("An even number of coordinates is expected");
        if (points.length < 4)
            throw new IllegalArgumentException("At least two points are required");
        this.closed = closed;
        this.points = new ArrayList<>(points.length / 2);
        for (int i = 0; i < points.length; i += 2)
            this.points.add(new Vector(points[i], points[i + 1]));
        initialize();
    }
    
    /**
     * Creates a new polyline.
     * @param points at least two points, not null
     */
    public Polyline(float... points) {
        this(false, points);
    }
    
    // Internal helper used to compute additional properties
    private void initialize() {
        int count = points.size();
        length = 0.0f;
        lengths = new float[count];
        for (int i = 1; i < count; ++i) {
            Vector from = points.get(i - 1);
            Vector to = points.get(i);
            length += lengths[i - 1] = to.sub(from).getLength();
        }
        if (closed) {
            Vector from = points.get(count - 1);
            Vector to = points.get(0);
            length += lengths[count - 1] = to.sub(from).getLength();
        }
    }

    /** @return whether last point is connected to the first one */
    public boolean isClosed() {
        return closed;
    }
    
    /** @return an immutable list of points, not null */
    public List<Vector> getPoints() {
        return Collections.unmodifiableList(points);
    }

    @Override
    public float getArea() {
        return 0.0f;
    }

    @Override
    public float getPerimeter() {
        return length;
    }

    @Override
    public Vector sample(Random random) {
        
        // Choose a uniform location along the line
        float offset = random.nextFloat() * length;
        
        // Find on which segment it is located
        int index = 0;
        while (offset > lengths[index]) {
            offset -= lengths[index];
            ++index;
        }
        
        // Compute actual location
        Vector start = points.get(index);
        Vector end = points.get((index + 1) % points.size());
        return start.mixed(end, offset / lengths[index]);
    }

    @Override
    public Path2D toPath() {

        Path2D path = new Path2D.Float();
		Vector point = points.get(0);
		path.moveTo(point.x, point.y);
		for (int i = 1; i < points.size(); ++i) {
			point = points.get(i);
			path.lineTo(point.x, point.y);
		}
        if (closed)
            path.closePath();
		return path;
    }
    
    @Override
    Part build(FixtureDef fixtureDef, Entity entity) {
        
        // Forbid dynamic polylines
        if (!entity.isFixed())
            throw new IllegalArgumentException("Polyline cannot be used with moveable entities");
        
        // Instanciate the actual body part
        Part part = new Part();
        part.entity = entity;
        fixtureDef.userData = part;
        part.fixtures = new ArrayList<>();
        
        // For each segment
        EdgeShape shape = new EdgeShape();
        fixtureDef.shape = shape;
        int size = points.size();
        Vector point;
        if (closed) {
            shape.m_hasVertex0 = true;
            shape.m_hasVertex3 = true;
            for (int i = 0; i < size; ++i) {
                
                // Define vertices
                point = points.get(i);
                shape.m_vertex0.set(point.x, point.y);
                point = points.get((i + 1) % size);
                shape.m_vertex1.set(point.x, point.y);
                point = points.get((i + 2) % size);
                shape.m_vertex2.set(point.x, point.y);
                point = points.get((i + 3) % size);
                shape.m_vertex3.set(point.x, point.y);
                
                // Instanciate fixture
                Fixture fixture = entity.body.createFixture(fixtureDef);
                part.fixtures.add(fixture);
            }
            
        } else {
            for (int i = 1; i < size; ++i) {

                // Define vertices
                shape.m_hasVertex0 = i > 1;
                if (shape.m_hasVertex0) {
                    point = points.get(i - 2);
                    shape.m_vertex0.set(point.x, point.y);
                }
                point = points.get(i - 1);
                shape.m_vertex1.set(point.x, point.y);
                point = points.get(i);
                shape.m_vertex2.set(point.x, point.y);    
                shape.m_hasVertex3 = i < size - 1;
                if (shape.m_hasVertex3) {
                    point = points.get(i + 1);
                    shape.m_vertex3.set(point.x, point.y);
                }

                // Instanciate fixture
                Fixture fixture = entity.body.createFixture(fixtureDef);
                part.fixtures.add(fixture);
            }
        }
        return part;
    }
    
}
