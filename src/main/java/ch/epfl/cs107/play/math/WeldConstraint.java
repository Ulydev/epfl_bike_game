package ch.epfl.cs107.play.math;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.WeldJoint;

/**
 * Constraints two entities to be attached together, possibly with some angular elasticity.
 */
public class WeldConstraint extends Constraint {
    
    WeldJoint weldJoint;
    
    // For internal use only
    WeldConstraint() {}
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        Vec2 anchor = weldJoint.getLocalAnchorA();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        Vec2 anchor = weldJoint.getLocalAnchorB();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return desired relative angle between entities, in radians */
    public float getReferenceAngle() {
        return weldJoint.getReferenceAngle();
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        weldJoint.setFrequency(frequency);
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return weldJoint.getFrequency();
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        weldJoint.setDampingRatio(damping);
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return weldJoint.getDampingRatio();
    }
    
}
