package ch.epfl.cs107.play.math;

import java.util.List;
import org.jbox2d.dynamics.Fixture;

/**
 * Represents a sub component of an entity.
 */
public class Part {

    Entity entity;
    List<Fixture> fixtures;
    
    // For internal use only
    Part() {}

    /** @return associated entity, null if destroyed */
    public Entity getEntity() {
        return entity;
    }
    
    /** @return whether it is still in the world */
    public boolean isAlive() {
        return entity != null;
    }

    /** @return whether this part is hidden and act only as a sensor */
    public boolean isGhost() {
        return fixtures.get(0).m_isSensor;
    }
    
    /**
     * Sets the friction coefficient.
     * @param friction any non negative value
     */
    public void setFriction(float friction) {
        for (Fixture fixture : fixtures)
            fixture.setFriction(friction);
    }
    
    /** @return the friction coefficient, non negative */
    public float getFriction() {
        return fixtures.get(0).m_friction;
    }
    
    /**
     * Sets the restitution coefficient.
     * @param restitution any non negative value
     */
    public void setRestitution(float restitution) {
        for (Fixture fixture : fixtures)
            fixture.setRestitution(restitution);
    }
    
    /** @return the restitution coefficient, non negative */
    public float getRestitution() {
        return fixtures.get(0).m_restitution;
    }
    
    // TODO access to density, mass, area if needed

    /** @return collision categories of this part */
    public int getCollisionSignature() {
        return fixtures.get(0).m_filter.categoryBits;
    }

    /** @return collision categories affected by this part */
    public int getCollisionEffect() {
        return fixtures.get(0).m_filter.maskBits;
    }

    /** @return selected group index */
    public int getCollisionGroup() {
        return fixtures.get(0).m_filter.groupIndex;
    }
    
    /** Destroys part. */
    public void destroy() {
        if (entity != null) {
            for (Fixture fixture : fixtures)
                entity.body.destroyFixture(fixture);
            unregister();
        }
    }
    
    // Internal callback, when a part is destroyed
    void unregister() {
        if (entity != null)
        	entity.parts.remove(this);
        entity = null;
    }
    
}
