package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

/**
 * Helps build an entity from scratch.
 */
public class EntityBuilder {

    private World world;
    private BodyDef bodyDef;
    
    // For internal use only
    EntityBuilder(World world) {
        this.world = world;
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
    }
    
    /**
     * Sets whether this entity is immovable and unrotatable.
     * @param fixed new state
     */
    public void setFixed(boolean fixed) {
        bodyDef.type = fixed ? BodyType.STATIC : BodyType.DYNAMIC;
    }
    
    /** @return whether this entity is immovable and unrotatable */
    public boolean isFixed() {
        return bodyDef.type == BodyType.STATIC;
    }
    
    /**
     * Sets whether this entity is unrotatable.
     * @param fixed new state
     */
    public void setRotationFixed(boolean fixed) {
        bodyDef.fixedRotation = fixed;
    }
    
    /** @return whether this entity is unrotatable */
    public boolean isRotationFixed() {
        return bodyDef.fixedRotation;
    }
    
    /**
     * Sets the initial linear position of the entity.
     * @param position new initial location, not null
     */
    public void setPosition(Vector position) {
        bodyDef.position.set(position.x, position.y);
    }
    
    /** @return the initial linear position of the entity, not null */
    public Vector getPosition() {
        return new Vector(bodyDef.position.x, bodyDef.position.y);
    }
    
    /**
     * Sets the initial angular position of the entity.
     * @param position new initial angle, in radians
     */
    public void setAngularPosition(float position) {
        bodyDef.angle = position;
    }
    
    /** @return the initial angular position of the entity, in radians */
    public float getAngularPosition() {
        return bodyDef.angle;
    }
    
    /**
     * Sets the initial linear velocity of the entity.
     * @param velocity new initial velocity, not null
     */
    public void setVelocity(Vector velocity) {
        bodyDef.linearVelocity.set(velocity.x, velocity.y);
    }
    
    /** @return the initial linear velocity of the entity, not null */
    public Vector getVelocity() {
        return new Vector(bodyDef.linearVelocity.x, bodyDef.linearVelocity.y);
    }
    
    /**
     * Sets the initial angular velocity of the entity.
     * @param velocity new initial velocity, in radians per second
     */
    public void setAngularVelocity(float velocity) {
        bodyDef.angularVelocity = velocity;
    }
    
    /** @return the initial angular velocity of the entity, in radians per second */
    public float getAngularVelocity() {
        return bodyDef.angularVelocity;
    }
    
    /**
     * Sets whether the world should consider this entity as a high speed object
     * @param bullet bullet new state
     */
    public void setBullet(boolean bullet) {
    	bodyDef.bullet = bullet;
    }
    
    /** @return whether the world is considering this entity as a high speed object */
    public boolean isBullet() {
    	return bodyDef.bullet;
    }
    
    /**
     * Creates and register a new entity into the world.
     * @return the newly created entity, not null
     */
    public Entity build() {
        Entity entity = new Entity();
        entity.world = world;
        entity.body = world.world.createBody(bodyDef);
        float s = entity.body.m_xf.q.s;
        float c = entity.body.m_xf.q.c;
        float x = entity.body.m_xf.p.x;
        float y = entity.body.m_xf.p.y;
        Transform transform = new Transform(
            c, -s, x,
            s, c, y
        );
        entity.transform = transform;
        entity.body.m_userData = entity;
        return entity;
    }
    
}
