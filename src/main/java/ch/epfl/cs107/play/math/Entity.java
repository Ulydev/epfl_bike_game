package ch.epfl.cs107.play.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/**
 * Represents a complete physical object, that can be simulated in a world.
 */
public class Entity implements Positionable {
    
    // Pointer to associated world, null if destroyed
    World world;
    
    // Box2D body, not null (even after deletion)
    org.jbox2d.dynamics.Body body;
    
    // Transform is set by world, at each update
    Transform transform;
    
    // Internal list of parts
    List<Part> parts;
    
    // Listeners
    List<ContactListener> contactListeners;
    
    // For internal use only
    Entity() {
        parts = new ArrayList<>();
        contactListeners = new ArrayList<>();
    }

    
    /** @return associated world, null if destroyed */
    /*
    public World getWorld() {
        return world;
    }
    */
    /** @return read-only list of entities */
    public List<Entity> getOthers() {
        return world.getEntities();
    }
    /** @return false if the world is destroyed */
    public boolean hasWorld() {
        return world != null;
    }
    
    /** @return whether it is still in the world */
    public boolean isAlive() {
        return world != null;
    }
    
    /** @return whether this entity is immovable or not */
    public boolean isFixed() {
        return body.m_type == BodyType.STATIC;
    }
    
    /** @return whether this entity is unrotatable */
    public boolean isRotationFixed() {
        return body.isFixedRotation();
    }
    
    /**
     * Sets the position of the entity.
     * @param position new linear position, not null
     * @param angle new angular position, in radians
     */
    public void setTransform(Vector position, float angle) {
        body.setTransform(new Vec2(position.x, position.y), angle);
        transform = null;
    }
    
    @Override
    public Transform getTransform() {
        if (transform == null) {
            float c = body.m_xf.q.c;
            float s = body.m_xf.q.s;
            float x = body.m_xf.p.x;
            float y = body.m_xf.p.y;
            transform = new Transform(
                c, -s, x,
                s, c, y
            );
        }
        return transform;
    }
    
    /**
     * Sets the linear position of the entity.
     * @param position new linear position, not null
     */
    public void setPosition(Vector position) {
        setTransform(position, body.getAngle());
    }

    @Override
    public Vector getPosition() {
        return new Vector(body.m_xf.p.x, body.m_xf.p.y);
    }

    /**
     * Sets the linear velocity.
     * @param velocity new velocity, not null
     */
    public void setVelocity(Vector velocity) {
        body.setLinearVelocity(new Vec2(velocity.x, velocity.y));
    }
    
    @Override
    public Vector getVelocity() {
        return new Vector(body.m_linearVelocity.x, body.m_linearVelocity.y);
    }

    /**
     * Sets the angular position (i.e. rotation) of the entity.
     * @param angle new angular position, in radians
     */
    public void setAngularPosition(float angle) {
        setTransform(getPosition(), angle);
    }
    
    /** @return the angular position (i.e. rotation) of the entity, in radians */
    public float getAngularPosition() {
        return body.getAngle();
    }
    
    /**
     * Sets the angular velocity.
     * @param velocity new velocity, in radians per second
     */
    public void setAngularVelocity(float velocity) {
        body.setAngularVelocity(velocity);
    }
    
    /** @return the angular velocity, in radians per second */
    public float getAngularVelocity() {
        return body.m_angularVelocity;
    }
    
    /** @return whether the world is considering this entity as a high speed object */
    public boolean isBullet() {
    	return body.isBullet();
    }
    
    /** @return read-only collection of parts */
    public List<Part> getParts() {
        return Collections.unmodifiableList(parts);
    }
    
    
    /**
     * Applies a force during a single time step.
     * @param force direction and magnitude, not null
     * @param location center of force, at center of mass if null
     */
    public void applyForce(Vector force, Vector location) {
        Vec2 f = new Vec2(force.x, force.y);
        if (location == null)
            body.applyForceToCenter(f);
        else {
            Vec2 l = new Vec2(location.x, location.y);
            body.applyForce(f, l);
        }
    }
    
    /**
     * Applies an impulse.
     * @param impulse direction and magnitude, not null
     * @param location center of force, at center of mass if null
     */
    public void applyImpulse(Vector impulse, Vector location) {
        Vec2 i = new Vec2(impulse.x, impulse.y);
        Vec2 l;
        if (location == null)
            l = body.getWorldCenter();
        else
            l = new Vec2(location.x, location.y);
        body.applyLinearImpulse(i, l);
    }
    
    /**
     * Applies an angular force during a single time step.
     * @param force magnitude, counter-clockwise
     */
    public void applyAngularForce(float force) {
        body.applyTorque(force);
    }
    
    /**
     * Applies an angular impulse.
     * @param impulse magnitude, counter-clockwise
     */
    public void applyAngularImpulse(float impulse) {
        body.applyAngularImpulse(impulse);
    }
    
    /**
     * Adds a new contact listener.
     * @param listener new listener, not null
     */
    public void addContactListener(ContactListener listener) {
        if (listener == null)
            throw new NullPointerException();
        contactListeners.add(listener);
    }
    
    /**
     * Removes an existing contact listener.
     * @param listener listener, not null
     */
    public void removeContactListener(ContactListener listener) {
        contactListeners.remove(listener);
    }
    
    /** @return new part builder */
    public PartBuilder createPartBuilder() {
        return new PartBuilder(this);
    }
    
    /** Destroys entity and associated parts and constraints */
    public void destroy() {
        if (world != null) {
            world.world.destroyBody(body);
            // Note: joints are automatically destroyed by world, using destruction callback to notify our simulator
            world = null;
        }
    }
    
}
