package ch.epfl.cs107.play.math;

/**
 * Represents any constraint available in a physical world. Usually, two entities are linked together.
 */
public abstract class Constraint {

    World world;
    org.jbox2d.dynamics.joints.Joint joint;
    
    // For internal use only
    Constraint() {}
    
    /** @return associated world, null if destroyed */
    /*
    public World getWorld() {
        return world;
    }
    */
    
    /** @return whether it is still in the world */
    public boolean isAlive() {
        return world != null;
    }

    /** @return the first body associated to this constraint, not null */
    public Entity getFirstBody() {
        return (Entity)joint.m_edgeA.other.m_userData;
    }
    
    /** @return the second body associated to this constraint, not null */
    public Entity getSecondBody() {
        return (Entity)joint.m_edgeB.other.m_userData;
    }
    
    /** Destroys constraint. */
    public void destroy() {
        if (world != null) {
            world.world.destroyJoint(joint);
            unregister();
        }
    }
    
    // Internal callback, when a constraint is destroyed
    void unregister() {
        world = null;
    }
    
}
