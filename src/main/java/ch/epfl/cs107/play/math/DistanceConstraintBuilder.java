package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

/**
 * Helps build a distance constraint from scratch.
 */
public class DistanceConstraintBuilder extends ConstraintBuilder {

    private DistanceJointDef distanceJointDef;
    
    // Internal use only
    DistanceConstraintBuilder() {
        jointDef = distanceJointDef = new DistanceJointDef();
    }

    /**
     * Sets attachment point on first entity, in local coordinates.
     * @param vector location in first entity referential, not null
     */
    public void setFirstAnchor(Vector vector) {
        distanceJointDef.localAnchorA.set(vector.x, vector.y);
    }
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        return new Vector(distanceJointDef.localAnchorA.x, distanceJointDef.localAnchorA.y);
    }
    
    /**
     * Sets attachment point on second entity, in local coordinates.
     * @param vector location in second entity referential, not null
     */
    public void setSecondAnchor(Vector vector) {
        distanceJointDef.localAnchorB.set(vector.x, vector.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        return new Vector(distanceJointDef.localAnchorB.x, distanceJointDef.localAnchorB.y);
    }
    
    /**
     * Sets reference length of associated spring.
     * @param length any positive value
     */
    public void setReferenceLength(float length) {
        if (length <= 0.0f)
            throw new IllegalArgumentException();
        distanceJointDef.length = length;
    }
    
    /** @return reference length of associated spring, positive */
    public float getReferenceLength() {
        return distanceJointDef.length;
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        distanceJointDef.frequencyHz = frequency;
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return distanceJointDef.frequencyHz;
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        if (damping < 0.0f || damping > 1.0f)
            throw new IllegalArgumentException();
        distanceJointDef.dampingRatio = damping;
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return distanceJointDef.dampingRatio;
    }
    
    @Override
    public DistanceConstraint build() {
        DistanceConstraint constraint = new DistanceConstraint();
        distanceJointDef.userData = constraint;
        constraint.joint = constraint.distanceJoint = (DistanceJoint)world.world.createJoint(distanceJointDef);
        constraint.world = world;
        return constraint;
    }
    
}
