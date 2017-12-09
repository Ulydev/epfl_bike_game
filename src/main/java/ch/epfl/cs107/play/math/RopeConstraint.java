package ch.epfl.cs107.play.math;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.RopeJoint;

/**
 * Constraints two entities to be separated by a specified distance, at most.
 */
public class RopeConstraint extends Constraint {
    
    RopeJoint ropeJoint;

    // For internal use only
    RopeConstraint() {}
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        Vec2 anchor = ropeJoint.getLocalAnchorA();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        Vec2 anchor = ropeJoint.getLocalAnchorB();
        return new Vector(anchor.x, anchor.y);
    }
    
    /**
     * Sets maximal allowed length.
     * @param length maximum distance, positive
     */
    public void setMaxLength(float length) {
        ropeJoint.setMaxLength(length);
    }
    
    /** @return maximal allowed length */
    public float getMaxLength() {
        return ropeJoint.getMaxLength();
    }
    
}
