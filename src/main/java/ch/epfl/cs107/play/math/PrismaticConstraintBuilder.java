package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;

/**
 * Helps build a prismatic constraint from scratch.
 */
public class PrismaticConstraintBuilder extends ConstraintBuilder {
    
    private PrismaticJointDef prismaticJointDef;
    
    // Internal use only
    PrismaticConstraintBuilder() {
        jointDef = prismaticJointDef = new PrismaticJointDef();
    }
    
    /**
     * Sets attachment point on first entity, in local coordinates.
     * @param vector location in first entity referential, not null
     */
    public void setFirstAnchor(Vector vector) {
        prismaticJointDef.localAnchorA.set(vector.x, vector.y);
    }
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        return new Vector(prismaticJointDef.localAnchorA.x, prismaticJointDef.localAnchorA.y);
    }
    
    /**
     * Sets attachment point on second entity, in local coordinates.
     * @param vector location in second entity referential, not null
     */
    public void setSecondAnchor(Vector vector) {
        prismaticJointDef.localAnchorB.set(vector.x, vector.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        return new Vector(prismaticJointDef.localAnchorB.x, prismaticJointDef.localAnchorB.y);
    }
    
    /**
     * Sets the spring axis, with respect to the first entity.
     * @param axis a direction, not null and non zero
     */
    public void setAxis(Vector axis) {
        prismaticJointDef.localAxisA.set(axis.x, axis.y);
    }
    
    /** @return the spring axis, with respect to the first entity, not null */
    public Vector getAxis() {
        return new Vector(prismaticJointDef.localAxisA.x, prismaticJointDef.localAxisA.y);
    }
    
    /**
     * Sets desired relative angle between entities.
     * @param angle angle, in radians
     */
    public void setReferenceAngle(float angle) {
        prismaticJointDef.referenceAngle = angle;
    }
    
    /** @return desired relative angle between entities, in radians */
    public float getReferenceAngle() {
        return prismaticJointDef.referenceAngle;
    }
    
    /**
     * Sets whether translation is limited.
     * @param enabled if true, lower and upper translations are used
     */
    public void setLimitEnabled(boolean enabled) {
        prismaticJointDef.enableLimit = enabled;
    }
    
    /** @return whether translation is limited */
    public boolean isLimitEnabled() {
        return prismaticJointDef.enableLimit;
    }
    
    /**
     * Sets the lower limit for relative translation, if enabled.
     * @param angle any distance
     */
    public void setLowerTranslationLimit(float angle) {
        prismaticJointDef.lowerTranslation = angle;
    }
    
    /** @return the lower limit for relative translation */
    public float getLowerTranslationLimit() {
        return prismaticJointDef.lowerTranslation;
    }
    
    /**
     * Sets the upper limit for relative translation, if enabled.
     * @param angle any distance
     */
    public void setUpperTranslationLimit(float angle) {
        prismaticJointDef.upperTranslation = angle;
    }
    
    /** @return the upper limit for relative translation */
    public float getUpperTranslationLimit() {
        return prismaticJointDef.upperTranslation;
    }
    
    /**
     * Sets whether motor is enabled.
     * @param enabled if true, an angular force is applied to match the desired speed
     */
    public void setMotorEnabled(boolean enabled) {
        prismaticJointDef.enableMotor = enabled;
    }
    
    /** @return whether motor is enabled */
    public boolean isMotorEnabled() {
        return prismaticJointDef.enableMotor;
    }
    
    /**
     * Sets the maximal force to be applied.
     * @param torque maximal angular force
     */
    public void setMotorMaxTorque(float torque) {
        prismaticJointDef.maxMotorForce = torque;
    }
    
    /** @return the maximal angular force */
    public float getMotorMaxTorque() {
        return prismaticJointDef.maxMotorForce;
    }
    
    /**
     * Sets the desired angular velocity
     * @param speed angular velocity, in radians per second
     */
    public void setMotorSpeed(float speed) {
        prismaticJointDef.motorSpeed = speed;
    }
    
    /** @return the desired angular velocity, in radians per second */
    public float getMotorSpeed() {
        return prismaticJointDef.motorSpeed;
    }
    
    @Override
    public PrismaticConstraint build() {
        PrismaticConstraint constraint = new PrismaticConstraint();
        prismaticJointDef.userData = constraint;
        constraint.joint = constraint.prismaticJoint = (PrismaticJoint)world.world.createJoint(prismaticJointDef);
        constraint.world = world;
        return constraint;
    }
    
}
