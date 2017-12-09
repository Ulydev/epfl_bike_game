package ch.epfl.cs107.play.math;

import java.awt.geom.Path2D;
import java.util.Random;

/**
 * Base class of all physical shapes.
 */
public abstract class Shape {
    
    // Used for default sampling
    private static final Random RANDOM = new Random();
    
    /** @return shape area */
    public abstract float getArea();
    
    /** @return shape perimeter */
    public abstract float getPerimeter();
    
    // TODO apply transform
	// (or at least some translation, scale, rotation) to shape if needed
    
    /**
     * Sample uniform point inside shape, including border.
     * @param random random number generator, not null
     * @return a uniform sample, not null
     */
    public abstract Vector sample(Random random);
    
    /**
     * Sample uniform point inside shape, including border.
     * @return a uniform sample, not null
     */
    public Vector sample() {
        return sample(RANDOM);
    }
    
    /** @return AWT path used for drawing */
    public abstract Path2D toPath();
    
    // Internal part builder
    abstract Part build(org.jbox2d.dynamics.FixtureDef fixtureDef, Entity entity);
}
