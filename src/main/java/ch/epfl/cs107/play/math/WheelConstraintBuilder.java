package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;

/**
 * Helps build a wheel constraint from scratch.
 */
public class WheelConstraintBuilder extends ConstraintBuilder {
    
    private WheelJointDef wheelJointDef;

    // Internal use only
    WheelConstraintBuilder() {
        jointDef = wheelJointDef = new WheelJointDef();
    }
    
    /**
     * Sets attachment point on first entity, in local coordinates.
     * @param vector location in first entity referential, not null
     */
    public void setFirstAnchor(Vector vector) {
        wheelJointDef.localAnchorA.set(vector.x, vector.y);
    }
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        return new Vector(wheelJointDef.localAnchorA.x, wheelJointDef.localAnchorA.y);
    }
    
    /**
     * Sets attachment point on second entity, in local coordinates.
     * @param vector location in second entity referential, not null
     */
    public void setSecondAnchor(Vector vector) {
        wheelJointDef.localAnchorB.set(vector.x, vector.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        return new Vector(wheelJointDef.localAnchorB.x, wheelJointDef.localAnchorB.y);
    }
    
    /**
     * Sets the spring axis, with respect to the first entity.
     * @param axis a direction, not null and non zero
     */
    public void setAxis(Vector axis) {
        wheelJointDef.localAxisA.set(axis.x, axis.y);
    }
    
    /** @return the spring axis, with respect to the first entity, not null */
    public Vector getAxis() {
        return new Vector(wheelJointDef.localAxisA.x, wheelJointDef.localAxisA.y);
    }
    
    /**
     * Sets whether motor is enabled.
     * @param enabled if true, an angular force is applied to match the desired speed
     */
    public void setMotorEnabled(boolean enabled) {
        wheelJointDef.enableMotor = enabled;
    }
    
    /** @return whether motor is enabled */
    public boolean isMotorEnabled() {
        return wheelJointDef.enableMotor;
    }
    
    /**
     * Sets the maximal force to be applied.
     * @param torque maximal angular force
     */
    public void setMotorMaxTorque(float torque) {
        wheelJointDef.maxMotorTorque = torque;
    }
    
    /** @return the maximal angular force */
    public float getMotorMaxTorque() {
        return wheelJointDef.maxMotorTorque;
    }
    
    /**
     * Sets the desired angular velocity
     * @param speed angular velocity, in radians per second
     */
    public void setMotorSpeed(float speed) {
        wheelJointDef.motorSpeed = speed;
    }
    
    /** @return the desired angular velocity, in radians per second */
    public float getMotorSpeed() {
        return wheelJointDef.motorSpeed;
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        wheelJointDef.frequencyHz = frequency;
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return wheelJointDef.frequencyHz;
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        wheelJointDef.dampingRatio = damping;
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return wheelJointDef.dampingRatio;
    }
    
    @Override
    public WheelConstraint build() {
        WheelConstraint constraint = new WheelConstraint();
        wheelJointDef.userData = constraint;
        constraint.joint = constraint.wheelJoint = (WheelJoint)world.world.createJoint(wheelJointDef);
        constraint.world = world;
        return constraint;
    }
    
}
