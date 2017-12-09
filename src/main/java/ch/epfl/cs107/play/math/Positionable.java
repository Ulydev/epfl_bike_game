package ch.epfl.cs107.play.math;

/**
 * Represents an object that can be defined by an affine transform.
 */
public interface Positionable {

    /** @return affine transform, not null */
    public Transform getTransform();
    
    /** @return origin, not null */
    public default Vector getPosition() {
        return getTransform().getOrigin();
    }
    
    /** @return linear velocity, not null */
    public Vector getVelocity();
    
}
