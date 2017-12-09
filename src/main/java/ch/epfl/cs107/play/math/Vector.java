package ch.epfl.cs107.play.math;

import java.io.Serializable;

/**
 * Represents an immutable 2D floating-point vector.
 */
public final class Vector implements Serializable {
    
    /** The zero vector (0, 0) */
    public static final Vector ZERO = new Vector(0.0f, 0.0f);
    
    /** The unit X vector (1, 0) */
    public static final Vector X = new Vector(1.0f, 0.0f);
    
    /** The unit Y vector (0, 1) */
    public static final Vector Y = new Vector(0.0f, 1.0f);
    
    public final float x;
    public final float y;
    
    /**
     * Creates a new vector.
     * @param x abscissa
     * @param y ordinate
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /** @return abscissa */
    public float getX() {
        return x;
    }

    /** @return ordinate */
    public float getY() {
        return y;
    }
    
    /** @return euclidian length */
    public float getLength() {
        return (float)Math.sqrt(x * x + y * y);
    }
    
    /** @return angle in standard trigonometrical system, in radians */
    public float getAngle() {
        return (float)Math.atan2(y, x);
    }
    
    /** @return negated vector */
    public Vector opposite() {
        return new Vector(-x, -y);
    }
    
    /**
     * @param other right-hand operand, not null
     * @return sum, not null
     */
    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }
    
    /**
     * @param x right-hand abcissa
     * @param y right-hand ordinate
     * @return sum, not null
     */
    public Vector add(float x, float y) {
        return new Vector(this.x + x, this.y + y);
    }
    
    /**
     * @param other right-hand operand, not null
     * @return difference, not null
     */
    public Vector sub(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }
    
    /**
     * @param x right-hand abcissa
     * @param y right-hand ordinate
     * @return difference, not null
     */
    public Vector sub(float x, float y) {
        return new Vector(this.x - x, this.y - y);
    }

    /**
     * @param other right-hand operand, not null
     * @return component-wise multiplication, not null
     */
    public Vector mul(Vector other) {
        return new Vector(x * other.x, y * other.y);
    }

    /**
     * @param x right-hand abcissa
     * @param y right-hand ordinate
     * @return component-wise multiplication, not null
     */
    public Vector mul(float x, float y) {
        return new Vector(this.x * x, this.y * y);
    }
    
    /**
     * @param s right-hand operand
     * @return scaled vector, not null
     */
    public Vector mul(float s) {
        return new Vector(this.x * s, this.y * s);
    }
    
    /**
     * @param other right-hand operand, not null
     * @return component-wise division, not null
     */
    public Vector div(Vector other) {
        return new Vector(x / other.x, y / other.y);
    }

    /**
     * @param x right-hand abcissa
     * @param y right-hand ordinate
     * @return component-wise division, not null
     */
    public Vector div(float x, float y) {
        return new Vector(this.x / x, this.y / y);
    }
    
    /**
     * @param s right-hand operand
     * @return scaled vector, not null
     */
    public Vector div(float s) {
        return new Vector(this.x / s, this.y / s);
    }
    
    /**
     * @param other right-hand operand, not null
     * @return dot product
     */
    public float dot(Vector other) {
        return x * other.x + y * other.y;
    }
    
    /**
     * @param other right-hand operand, not null
     * @return component-wise minimum, not null
     */
    public Vector min(Vector other) {
        return new Vector(Math.min(x, other.x), Math.min(y, other.y));
    }
    
    /** @return smallest component */
    public float min() {
        return Math.min(x, y);
    }
    
    /**
     * @param other right-hand operand, not null
     * @return component-wise maximum, not null
     */
    public Vector max(Vector other) {
        return new Vector(Math.max(x, other.x), Math.max(y, other.y));
    }
    
    /** @return largest component */
    public float max() {
        return Math.max(x, y);
    }
    
    /**
     * Computes unit vector of same direction, or (1, 0) if zero.
     * @return rescaled vector, not null
     */
    public Vector normalized() {
        float length = getLength();
        if (length > 1e-6)
            return div(length);
        return Vector.X;
    }
    
    /**
     * Resizes vector to specified length, or (<code>length</code>, 0) if zero.
     * @param length new length
     * @return rescaled vector, not null
     */
    public Vector resized(float length) {
        return normalized().mul(length);
    }
    
    /**
     * Computes mirrored vector, with respect to specified normal.
     * @param normal vector perpendicular to the symmetry plane, not null
     * @return rotated vector, not null
     */
    public Vector mirrored(Vector normal) {
        normal = normal.normalized();
        return sub(normal.mul(2.0f * dot(normal)));
    }
	
    /**
     * Computes rotated vector, in a counter-clockwise manner.
     * @param angle rotation, in radians
     * @return rotated vector, not null
     */
	public Vector rotated(double angle) {
        float c = (float)Math.cos(angle);
        float s = (float)Math.sin(angle);
        return new Vector(x * c - y * s, x * s + y * c);
    }
    
    /** @return vector rotated by -90°, not null */
    public Vector clockwise() {
        return new Vector(-y, x);
    }
    
    /** @return vector rotated by 90°, not null */
    public Vector counterClockwise() {
        return new Vector(y, -x);
    }
    
    /**
     * Computes linear interpolation between two vectors.
     * @param other second vector, not null
     * @param factor weight of the second vector
     * @return interpolated vector, not null
     */
    public Vector mixed(Vector other, float factor) {
        return new Vector(x * (1.0f - factor) + other.x * factor, y * (1.0f - factor) + other.y * factor);
    }
    
    @Override
    public int hashCode() {
        return Float.hashCode(x) ^ Float.hashCode(y);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Vector))
            return false;
        Vector other = (Vector)object;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
    
}
