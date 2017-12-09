package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 * Helps build a revolute constraint from scratch.
 */
public class RevoluteConstraintBuilder extends ConstraintBuilder {
    
    private RevoluteJointDef revoluteJointDef;
    
    // Internal use only
    RevoluteConstraintBuilder() {
        jointDef = revoluteJointDef = new RevoluteJointDef();
    }
    
    /**
     * Sets attachment point on first entity, in local coordinates.
     * @param vector location in first entity referential, not null
     */
    public void setFirstAnchor(Vector vector) {
        revoluteJointDef.localAnchorA.set(vector.x, vector.y);
    }
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        return new Vector(revoluteJointDef.localAnchorA.x, revoluteJointDef.localAnchorA.y);
    }
    
    /**
     * Sets attachment point on second entity, in local coordinates.
     * @param vector location in second entity referential, not null
     */
    public void setSecondAnchor(Vector vector) {
        revoluteJointDef.localAnchorB.set(vector.x, vector.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        return new Vector(revoluteJointDef.localAnchorB.x, revoluteJointDef.localAnchorB.y);
    }
    
    /**
     * Sets desired relative angle between entities.
     * @param angle angle, in radians
     */
    public void setReferenceAngle(float angle) {
        revoluteJointDef.referenceAngle = angle;
    }
    
    /** @return desired relative angle between entities, in radians */
    public float getReferenceAngle() {
        return revoluteJointDef.referenceAngle;
    }
    
    /**
     * Sets whether rotation is limited.
     * @param enabled if true, lower and upper angles are used
     */
    public void setLimitEnabled(boolean enabled) {
        revoluteJointDef.enableLimit = enabled;
    }
    
    /** @return whether rotation is limited */
    public boolean isLimitEnabled() {
        return revoluteJointDef.enableLimit;
    }
    
    /**
     * Sets the lower limit for relative angle, if enabled.
     * @param angle any angle, in radians
     */
    public void setLowerAngleLimit(float angle) {
        revoluteJointDef.lowerAngle = angle;
    }
    
    /** @return the lower limit for relative angle, in radians */
    public float getLowerAngleLimit() {
        return revoluteJointDef.lowerAngle;
    }
    
    /**
     * Sets the upper limit for relative angle, if enabled.
     * @param angle any angle, in radians
     */
    public void setUpperAngleLimit(float angle) {
        revoluteJointDef.upperAngle = angle;
    }
    
    /** @return the upper limit for relative angle, in radians */
    public float getUpperAngleLimit() {
        return revoluteJointDef.upperAngle;
    }
    
    /**
     * Sets whether motor is enabled.
     * @param enabled if true, an angular force is applied to match the desired speed
     */
    public void setMotorEnabled(boolean enabled) {
        revoluteJointDef.enableMotor = enabled;
    }
    
    /** @return whether motor is enabled */
    public boolean isMotorEnabled() {
        return revoluteJointDef.enableMotor;
    }
    
    /**
     * Sets the maximal force to be applied.
     * @param torque maximal angular force
     */
    public void setMotorMaxTorque(float torque) {
        revoluteJointDef.maxMotorTorque = torque;
    }
    
    /** @return the maximal angular force */
    public float getMotorMaxTorque() {
        return revoluteJointDef.maxMotorTorque;
    }
    
    /**
     * Sets the desired angular velocity
     * @param speed angular velocity, in radians per second
     */
    public void setMotorSpeed(float speed) {
        revoluteJointDef.motorSpeed = speed;
    }
    
    /** @return the desired angular velocity, in radians per second */
    public float getMotorSpeed() {
        return revoluteJointDef.motorSpeed;
    }
    
    @Override
    public RevoluteConstraint build() {
        RevoluteConstraint constraint = new RevoluteConstraint();
        revoluteJointDef.userData = constraint;
        constraint.joint = constraint.revoluteJoint = (RevoluteJoint)world.world.createJoint(revoluteJointDef);
        constraint.world = world;
        return constraint;
    }
    
}
