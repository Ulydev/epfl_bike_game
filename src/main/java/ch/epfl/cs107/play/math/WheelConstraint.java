package ch.epfl.cs107.play.math;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.WheelJoint;

/**
 * Constraints two entities to be act as a car and a wheel. The second entity can move along an axis, constrained by a spring. A motor can be enabled.
 */
public class WheelConstraint extends Constraint {
    
    WheelJoint wheelJoint;
    
    // For internal use only
    WheelConstraint() {}
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        Vec2 anchor = wheelJoint.getLocalAnchorA();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        Vec2 anchor = wheelJoint.getLocalAnchorB();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return the spring axis, with respect to the first entity, not null */
    public Vector getFirstAxis() {
        Vec2 axis = wheelJoint.getLocalAxisA();
        return new Vector(axis.x, axis.y);
    }
    
    /**
     * Sets whether motor is enabled.
     * @param enabled if true, an angular force is applied to match the desired speed
     */
    public void setMotorEnabled(boolean enabled) {
        wheelJoint.enableMotor(enabled);
    }
    
    /** @return whether motor is enabled */
    public boolean isMotorEnabled() {
        return wheelJoint.isMotorEnabled();
    }
    
    /**
     * Sets the maximal force to be applied.
     * @param torque maximal angular force
     */
    public void setMotorMaxTorque(float torque) {
        wheelJoint.setMaxMotorTorque(torque);
    }
    
    /** @return the maximal angular force */
    public float getMotorMaxTorque() {
        return wheelJoint.getMaxMotorTorque();
    }
    
    /**
     * Sets the desired angular velocity
     * @param speed angular velocity, in radians per second
     */
    public void setMotorSpeed(float speed) {
        wheelJoint.setMotorSpeed(speed);
    }
    
    /** @return the desired angular velocity, in radians per second */
    public float getMotorSpeed() {
        return wheelJoint.getMotorSpeed();
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        wheelJoint.setSpringFrequencyHz(frequency);
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return wheelJoint.getSpringFrequencyHz();
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        wheelJoint.setSpringDampingRatio(damping);
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return wheelJoint.getSpringDampingRatio();
    }
    
}
