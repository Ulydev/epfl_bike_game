package ch.epfl.cs107.play.math;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * Represents an immutable 2D affine transformation.
 */
public final class Transform implements Serializable {
    
    /** The identity transform **/
    public static final Transform I = new Transform(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    
    /** X scale */
    public final float m00;
    
    /** X shear */
    public final float m01;
    
    /** X translation */
    public final float m02;
    
    /** Y shear */
    public final float m10;
    
    /** Y scale */
    public final float m11;
    
    /** Y translation */
    public final float m12;

    /**
     * Creates a new transform.
     * @param m00 X scale
     * @param m01 X shear
     * @param m02 X translation
     * @param m10 Y shear
     * @param m11 Y scale
     * @param m12 Y translate
     */
    public Transform(float m00, float m01, float m02, float m10, float m11, float m12) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
    }
    
    
    /** @return X-axis, not null */
    public Vector getX() {
        return new Vector(m00, m10);
    }
    
    /** @return Y-axis, not null */
    public Vector getY() {
        return new Vector(m01, m11);
    }
    
    /** @return translation vector, not null */
    public Vector getOrigin() {
        return new Vector(m02, m12);
    }
    
    /** @return angle, in radians */
    public float getAngle() {
        return (float)Math.atan2(m01, m00);
    }
    
    /**
     * Transforms point.
     * @param x abcissa
     * @param y ordinate
     * @return transformed point, not null
     */
    public Vector onPoint(float x, float y) {
        return new Vector(
            x * m00 + y * m01 + m02,
            x * m10 + y * m11 + m12
        );
    }
    
    /**
     * Transforms point.
     * @param p point, not null
     * @return transformed point, not null
     */
    public Vector onPoint(Vector p) {
        return onPoint(p.x, p.y);
    }
    
    /**
     * Transforms vector.
     * @param x abcissa
     * @param y ordinate
     * @return transformed vector, not null
     */
    public Vector onVector(float x, float y) {
        return new Vector(
            x * m00 + y * m01,
            x * m10 + y * m11
        );
    }
    
    /**
     * Transforms vector.
     * @param v point, not null
     * @return transformed vector, not null
     */
    public Vector onVector(Vector v) {
        return onVector(v.x, v.y);
    }
    
    /**
     * Appends another transform (applied after this transform).
     * @param t transform, not null
     * @return extended transform, not null
     */
    public Transform transformed(Transform t) {
        return new Transform(
            t.m00 * m00 + t.m01 * m10, t.m00 * m01 + t.m01 * m11, t.m00 * m02 + t.m01 * m12 + t.m02,
            t.m10 * m00 + t.m11 * m10, t.m10 * m01 + t.m11 * m11, t.m10 * m02 + t.m11 * m12 + t.m12
        );
    }
    
    /**
     * Appends translation (applied after this transform).
     * @param dx X translation
     * @param dy Y translation
     * @return extended transform, not null
     */
    public Transform translated(float dx, float dy) {
        return new Transform(
            m00, m01, m02 + dx,
            m10, m11, m12 + dy
        );
    }
    
    /**
     * Appends translation (applied after this transform).
     * @param d translation, not null
     * @return extended transform, not null
     */
    public Transform translated(Vector d) {
        return translated(d.x, d.y);
    }
    
    /**
     * Appends scale (applied after this transform).
     * @param sx X scale
     * @param sy Y scale
     * @return extended transform, not null
     */
    public Transform scaled(float sx, float sy) {
        return new Transform(
            m00 * sx, m01 * sx, m02 * sx,
            m10 * sy, m11 * sy, m12 * sy
        );
    }
    
    /**
     * Appends scale (applied after this transform).
     * @param s scale
     * @return extended transform, not null
     */
    public Transform scaled(float s) {
        return scaled(s, s);
    }
    
    // TODO scale in specific direction, according to vector if needed
    // TODO scale using specific center of transformation if needed
    
    /**
     * Appends rotation around origin (applied after this transform).
     * @param a angle, in radians
     * @return extended transform, not null
     */
    public Transform rotated(float a) {
        float c = (float)Math.cos(a);
        float s = (float)Math.sin(a);
        return new Transform(
            c * m00 - s * m10, c * m01 - s * m11, c * m02 - s * m12,
            s * m00 + c * m10, s * m01 + c * m11, s * m02 + c * m12
        );
    }
    
    /**
     * Appends rotation around specified point (applied after this transform).
     * @param a angle, in radians
     * @param center rotation axis, not null
     * @return extended transform, not null
     */
    public Transform rotated(float a, Vector center) {
        return
            translated(-center.x, -center.y).
            rotated(a).
            translated(center);
    }
    
    // TODO 90, 180, 270 degrees rotation if needed
    
    // TODO flip h/v, mirror if needed
    
    /** @return transform inverse, not null */
    public Transform inverted() {
        float det = 1.0f / (m00 * m11 - m01 * m10);
        float a = m11 * det;
        float b = -m01 * det;
        float c = -m10 * det;
        float d = m00 * det;
        return new Transform(
            a, b, -(a * m02 + b * m12),
            c, d, -(c * m02 + d * m12)
        );
    }

    @Override
    public int hashCode() {
        return
            Float.hashCode(m00) ^ Float.hashCode(m01) ^ Float.hashCode(m02) ^ 
            Float.hashCode(m10) ^ Float.hashCode(m11) ^ Float.hashCode(m12);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Transform))
            return false;
        Transform other = (Transform)object;
        return
            m00 == other.m00 && m01 == other.m01 && m02 == other.m02 &&
            m10 == other.m10 && m11 == other.m11 && m12 == other.m12;
    }
    
    @Override
    public String toString() {
        return String.format("[%f, %f, %f, %f, %f, %f]", m00, m01, m02, m10, m11, m12);
    }
    
    /** @return AWT affine transform equivalent, not null */
    public AffineTransform getAffineTransform() {
        return new AffineTransform(
            m00, m10,
            m01, m11,
            m02, m12 
        );
    }
    
}
