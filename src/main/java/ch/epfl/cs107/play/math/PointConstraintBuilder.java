package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

/**
 * Helps build a point constraint from scratch.
 */
public class PointConstraintBuilder extends ConstraintBuilder {
    
     private MouseJointDef mouseJointDef;
    
    // Internal use only
    PointConstraintBuilder() {
        jointDef = mouseJointDef = new MouseJointDef();
    }
    
    /**
     * Sets the expected location of the entity origin.
     * @param point expected location, not null
     */
    public void setPoint(Vector point) {
        mouseJointDef.target.set(point.x, point.y);
    }
    
    /** @return expected location, not null */
    public Vector getPoint() {
        return new Vector(mouseJointDef.target.x, mouseJointDef.target.y);
    }
    
    /**
     * Sets the maximal force applied to body, as large distances can imply massive changes.
     * @param force maximal force
     */
    public void setMaxForce(float force) {
        mouseJointDef.maxForce = force;
    }
    
    /** @return maximal force applied to entity */
    public float getMaxForce() {
        return mouseJointDef.maxForce;
    }
    
    /**
     * Sets the frequency of associated spring.
     * @param frequency any positive value in hertz, or zero to disable elasticity
     */
    public void setFrequency(float frequency) {
        mouseJointDef.frequencyHz = frequency;
    }
    
    /** @return frequency of associated spring, non negative */
    public float getFrequency() {
        return mouseJointDef.frequencyHz;
    }
    
    /**
     * Sets the damping ratio of associated spring.
     * @param damping ratio, between 0.0 and 1.0
     */
    public void setDamping(float damping) {
        if (damping < 0.0f || damping > 1.0f)
            throw new IllegalArgumentException();
        mouseJointDef.dampingRatio = damping;
    }
    
    /** @return the damping ratio of associated spring, between 0.0 and 1.0 */
    public float getDamping() {
        return mouseJointDef.dampingRatio;
    }
    
    @Override
    public PointConstraint build() {
        PointConstraint constraint = new PointConstraint();
        mouseJointDef.userData = constraint;
        constraint.joint = constraint.mouseJoint = (MouseJoint)world.world.createJoint(mouseJointDef);
        constraint.world = world;
        return constraint;
    }
    
}
