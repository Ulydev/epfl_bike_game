package ch.epfl.cs107.play.math;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;

/**
 * Constraints two entities to be separated by a specified distance, possibly with some elasticy.
 */
public class DistanceConstraint extends Constraint {

    DistanceJoint distanceJoint;
    
    // For internal use only
    DistanceConstraint() {}
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        Vec2 anchor = distanceJoint.getLocalAnchorA();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        Vec2 anchor = distanceJoint.getLocalAnchorB();
        return new Vector(anchor.x, anchor.y);
    }
    
    /**
     * Sets reference length of associated spring.
     * @param length any positive value
     */
    public void setReferenceLength(float length) {
        distanceJoint.setLength(length);
    }
    
    /** @return reference length of associated spring, positive */
    public float getReferenceLength() {
        return distanceJoint.getLength();
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        distanceJoint.setFrequency(frequency);
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return distanceJoint.getFrequency();
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        distanceJoint.setDampingRatio(damping);
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return distanceJoint.getDampingRatio();
    }
    
}
