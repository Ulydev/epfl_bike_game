package ch.epfl.cs107.play.math;

import org.jbox2d.common.Vec2;

/**
 * Constraints two entities to be connected by a slider. The second entity can move along an axis, free to rotate.
 */
public class PrismaticConstraint extends Constraint {
    
    org.jbox2d.dynamics.joints.PrismaticJoint prismaticJoint;
    
    // For internal use only
    PrismaticConstraint() {}
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        Vec2 anchor = prismaticJoint.getLocalAnchorA();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        Vec2 anchor = prismaticJoint.getLocalAnchorB();
        return new Vector(anchor.x, anchor.y);
    }
    
    /** @return the spring axis, with respect to the first entity, not null */
    public Vector getFirstAxis() {
        Vec2 axis = prismaticJoint.getLocalAxisA();
        return new Vector(axis.x, axis.y);
    }
    
    /** @return desired relative angle between entities, in radians */
    public float getReferenceAngle() {
        return prismaticJoint.getReferenceAngle();
    }
    
    /**
     * Sets whether translation is limited.
     * @param enabled if true, lower and upper translations are used
     */
    public void setLimitEnabled(boolean enabled) {
        prismaticJoint.enableLimit(enabled);
    }
    
    /** @return the lower limit for relative translation */
    public float getLowerTranslationLimit() {
        return prismaticJoint.getLowerLimit();
    }
    
    /** @return the upper limit for relative translation */
    public float getUpperTranslationLimit() {
        return prismaticJoint.getUpperLimit();
    }
    
    /**
     * Sets whether motor is enabled.
     * @param enabled if true, an angular force is applied to match the desired speed
     */
    public void setMotorEnabled(boolean enabled) {
        prismaticJoint.enableMotor(enabled);
    }
    
    /** @return whether motor is enabled */
    public boolean isMotorEnabled() {
        return prismaticJoint.isMotorEnabled();
    }
    
    /**
     * Sets the maximal force to be applied.
     * @param torque maximal angular force
     */
    public void setMotorMaxTorque(float torque) {
        prismaticJoint.setMaxMotorForce(torque);
    }
    
    /** @return the maximal angular force */
    public float getMotorMaxTorque() {
        return prismaticJoint.getMaxMotorForce();
    }
    
    /**
     * Sets the desired angular velocity
     * @param speed angular velocity, in radians per second
     */
    public void setMotorSpeed(float speed) {
        prismaticJoint.setMotorSpeed(speed);
    }
    
    /** @return the desired angular velocity, in radians per second */
    public float getMotorSpeed() {
        return prismaticJoint.getMotorSpeed();
    }
    
    
}
