package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

/**
 * Helps build a weld constraint from scratch.
 */
public class WeldConstraintBuilder extends ConstraintBuilder {
    
    private WeldJointDef weldJointDef;

    // Internal use only
    WeldConstraintBuilder() {
        jointDef = weldJointDef = new WeldJointDef();
    }
    
    /**
     * Sets attachment point on first entity, in local coordinates.
     * @param vector location in first entity referential, not null
     */
    public void setFirstAnchor(Vector vector) {
        weldJointDef.localAnchorA.set(vector.x, vector.y);
    }
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        return new Vector(weldJointDef.localAnchorA.x, weldJointDef.localAnchorA.y);
    }
    
    /**
     * Sets attachment point on second entity, in local coordinates.
     * @param vector location in second entity referential, not null
     */
    public void setSecondAnchor(Vector vector) {
        weldJointDef.localAnchorB.set(vector.x, vector.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        return new Vector(weldJointDef.localAnchorB.x, weldJointDef.localAnchorB.y);
    }
    
    /**
     * Sets desired relative angle between entities.
     * @param angle angle, in radians
     */
    public void setReferenceAngle(float angle) {
        weldJointDef.referenceAngle = angle;
    }
    
    /** @return desired relative angle between bodies, in radians */
    public float getReferenceAngle() {
        return weldJointDef.referenceAngle;
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        weldJointDef.frequencyHz = frequency;
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return weldJointDef.frequencyHz;
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        weldJointDef.dampingRatio = damping;
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return weldJointDef.dampingRatio;
    }
    
    @Override
    public WeldConstraint build() {
        WeldConstraint constraint = new WeldConstraint();
        weldJointDef.userData = constraint;
        constraint.joint = constraint.weldJoint = (WeldJoint)world.world.createJoint(weldJointDef);
        constraint.world = world;
        return constraint;
    }
    
}
