package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.RopeJoint;
import org.jbox2d.dynamics.joints.RopeJointDef;

/**
 * Helps build a rope constraint from scratch.
 */
public class RopeConstraintBuilder extends ConstraintBuilder {

    private RopeJointDef ropeJointDef;
    
    // Internal use only
    RopeConstraintBuilder() {
        jointDef = ropeJointDef = new RopeJointDef();
    }
    
    /**
     * Sets attachment point on first entity, in local coordinates.
     * @param vector location in first entity referential, not null
     */
    public void setFirstAnchor(Vector vector) {
        ropeJointDef.localAnchorA.set(vector.x, vector.y);
    }
    
    /** @return attachment point on first entity, in local coordinates, not null */
    public Vector getFirstAnchor() {
        return new Vector(ropeJointDef.localAnchorA.x, ropeJointDef.localAnchorA.y);
    }
    
    /**
     * Sets attachment point on second entity, in local coordinates.
     * @param vector location in second entity referential, not null
     */
    public void setSecondAnchor(Vector vector) {
        ropeJointDef.localAnchorB.set(vector.x, vector.y);
    }
    
    /** @return attachment point on second entity, in local coordinates, not null */
    public Vector getSecondAnchor() {
        return new Vector(ropeJointDef.localAnchorB.x, ropeJointDef.localAnchorB.y);
    }
    
    /**
     * Sets maximal allowed length.
     * @param length maximum distance, positive
     */
    public void setMaxLength(float length) {
        ropeJointDef.maxLength = length;
    }
    
    /** @return maximal allowed length */
    public float getMaxLength() {
        return ropeJointDef.maxLength;
    }
    
    @Override
    public RopeConstraint build() {
        RopeConstraint constraint = new RopeConstraint();
        ropeJointDef.userData = constraint;
        constraint.joint = constraint.ropeJoint = (RopeJoint)world.world.createJoint(ropeJointDef);
        constraint.world = world;
        return constraint;
    }
    
}
