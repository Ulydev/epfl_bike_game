package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.joints.JointDef;

/**
 * Helps build a constraint from scratch.
 */
public abstract class ConstraintBuilder {

    World world;
    JointDef jointDef;

    // Internal use only
    ConstraintBuilder() {}
    
    /**
     * Sets the first entity associated to this constraint.
     * @param entity an entity in the same world
     */
    public void setFirstEntity(Entity entity) {
        jointDef.bodyA = entity == null ? null : entity.body;
    }
    
    /** @return the first entity associated to this constraint */
    public Entity getFirstEntity() {
        if (jointDef.bodyA == null)
            return null;
        return (Entity)jointDef.bodyA.m_userData;
    }
    
    /**
     * Sets the second entity associated to this constraint.
     * @param entity a entity in the same world
     */
    public void setSecondEntity(Entity entity) {
        jointDef.bodyB = entity == null ? null : entity.body;
    }
    
    /** @return the second entity associated to this constraint */
    public Entity getSecondEntity() {
        if (jointDef.bodyB == null)
            return null;
        return (Entity)jointDef.bodyB.m_userData;
    }
    
    /**
     * Sets whether connected entities should continue to consider each other for collisions.
     * @param collide internal collision enabled
     */
    public void setInternalCollision(boolean collide) {
        jointDef.collideConnected = collide;
    }
    
    /** @return whether connected entities should continue to consider each other for collisions */
    public boolean hasInternalCollision() {
        return jointDef.collideConnected;
    }
    
    /**
     * Creates a new constraint.
     * @return the newly created constraint, not null
     */
    public abstract Constraint build();
    
}
