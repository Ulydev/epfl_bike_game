package ch.epfl.cs107.play.math;

import org.jbox2d.common.Vec2;

/**
 * Attach an entity to a world location, often the mouse coordinates.
 * The second entity is ignored.
 */
public class PointConstraint extends Constraint {
    
    org.jbox2d.dynamics.joints.MouseJoint mouseJoint;
    
    // For internal use only
    PointConstraint() {}
    
    /**
     * Sets the expected location of the entity origin.
     * @param point expected location, not null
     */
    public void setPoint(Vector point) {
        mouseJoint.setTarget(new Vec2(point.x, point.y));
    }
    
    /** @return expected location, not null */
    public Vector getPoint() {
        Vec2 anchor = mouseJoint.getTarget();
        return new Vector(anchor.x, anchor.y);
    }
    
    /**
     * Sets the maximal force applied to body, as large distances can imply massive changes.
     * @param force maximal force
     */
    public void setMaxForce(float force) {
        mouseJoint.setMaxForce(force);
    }
    
    /** @return maximal force applied to entity */
    public float getMaxForce() {
        return mouseJoint.getMaxForce();
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        mouseJoint.setFrequency(frequency);
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return mouseJoint.getFrequency();
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        mouseJoint.setDampingRatio(damping);
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return mouseJoint.getDampingRatio();
    }
    
}
