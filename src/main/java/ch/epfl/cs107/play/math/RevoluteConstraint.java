package ch.epfl.cs107.play.math;

import org.jbox2d.common.Vec2;

/**
 * Constraints two entities to be attached at a single point, allowing some rotation. Limits and motor forces can be enabled.
 */
public class RevoluteConstraint extends Constraint {
    
    org.jbox2d.dynamics.joints.RevoluteJoint revoluteJoint;
    
    // For internal use only
    RevoluteConstraint() {}
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        Vec2 anchor = revoluteJoint.getLocalAnchorA();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        Vec2 anchor = revoluteJoint.getLocalAnchorB();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return desired relative angle between entities, in radians */
    public float getReferenceAngle() {
        return revoluteJoint.getReferenceAngle();
    }
    
    /**
     * Sets whether rotation is limited.
     * @param enabled if true, lower and upper angles are used
     */
    public void setLimitEnabled(boolean enabled) {
        revoluteJoint.enableLimit(enabled);
    }
    
    /** @return whether rotation is limited */
    public boolean isLimitEnabled() {
        return revoluteJoint.isLimitEnabled();
    }
    
    /** @return the lower limit for relative angle, in radians */
    public float getLowerAngleLimit() {
        return revoluteJoint.getLowerLimit();
    }
    
    /** @return the upper limit for relative angle, in radians */
    public float getUpperAngleLimit() {
        return revoluteJoint.getUpperLimit();
    }
    
    /**
     * Sets whether motor is enabled.
     * @param enabled if true, an angular force is applied to match the desired speed
     */
    public void setMotorEnabled(boolean enabled) {
        revoluteJoint.enableMotor(enabled);
    }
    
    /** @return whether motor is enabled */
    public boolean isMotorEnabled() {
        return revoluteJoint.isMotorEnabled();
    }
    
    /**
     * Sets the maximal force to be applied.
     * @param torque maximal angular force
     */
    public void setMotorMaxTorque(float torque) {
        revoluteJoint.setMaxMotorTorque(torque);
    }
    
    /** @return the maximal angular force */
    public float getMotorMaxTorque() {
        return revoluteJoint.getMaxMotorTorque();
    }
    
    /**
     * Sets the desired angular velocity
     * @param speed angular velocity, in radians per second
     */
    public void setMotorSpeed(float speed) {
        revoluteJoint.setMotorSpeed(speed);
    }
    
    /** @return the desired angular velocity, in radians per second */
    public float getMotorSpeed() {
        return revoluteJoint.getMotorSpeed();
    }
    
}
