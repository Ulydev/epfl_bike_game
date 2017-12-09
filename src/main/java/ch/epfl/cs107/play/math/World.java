package ch.epfl.cs107.play.math;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.joints.Joint;

/**
 * Contains a whole physical world.
 */
public class World {
    
    // Box2D world, not null
    org.jbox2d.dynamics.World world;
    
    // Elapsed time
    private final float timeStep;
    private float timeAccumulator;
    
    // Iterate over entity linked list
    class EntityListIterator implements ListIterator<Entity> {

        
        Body previous;
        Body next = world.getBodyList();
        int previousIndex = -1;
        int nextIndex = 0;
        
        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Entity next() {
            if (next == null)
                throw new NoSuchElementException();
            Entity entity = (Entity)next.m_userData;
            previous = next;
            next = next.m_next;
            ++previousIndex;
            ++nextIndex;
            return entity;
        }

        @Override
        public boolean hasPrevious() {
            return previous != null;
        }

        @Override
        public Entity previous() {
            if (previous == null)
                throw new NoSuchElementException();
            Entity entity = (Entity)previous.m_userData;
            next = previous;
            previous = previous.m_prev;
            --previousIndex;
            --nextIndex;
            return entity;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return previousIndex;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("List is read-only");
        }

        @Override
        public void set(Entity e) {
            throw new UnsupportedOperationException("List is read-only");
        }

        @Override
        public void add(Entity e) {
            throw new UnsupportedOperationException("List is read-only");
        }
        
    }
    
    // Read-only view of entities
    private class EntityList extends AbstractSequentialList<Entity> {

        @Override
        public ListIterator<Entity> listIterator(int i) {
            return new EntityListIterator();
        }

        @Override
        public int size() {
            return world.getBodyCount();
        }
        
    }
    
    // Entity list proxy
    EntityList entities = new EntityList();
    
    // Internal contact container
    static class ContactContainer {
        Contact first;
        Contact second;
    }
    
    // Mapping between vanilla contacts and simplified proxies
    private Map<org.jbox2d.dynamics.contacts.Contact, ContactContainer> contacts;
    
    /** Creates a new empty world. */
    public World() {
        
        // Prepare internal state
        world = new org.jbox2d.dynamics.World(new Vec2(0.0f, 0.0f));
        world.setAutoClearForces(false);
        timeStep = 1.0f / 120.0f;
        
        // Add destruction listener to remove proxies on indirect deletion
        world.setDestructionListener(new org.jbox2d.callbacks.DestructionListener() {
            
            @Override
            public void sayGoodbye(Joint joint) {
                Constraint constraint = (Constraint)joint.m_userData;
                constraint.unregister();
            }

            @Override
            public void sayGoodbye(Fixture fixture) {
                Part part = (Part)fixture.m_userData;
                part.unregister();
            }
            
        });
        
        // Add contact listener to cache events
        contacts = new HashMap<>();
        world.setContactListener(new org.jbox2d.callbacks.ContactListener() {
            
            @Override
            public void beginContact(org.jbox2d.dynamics.contacts.Contact contact) {
                
                // Only keep contacts that have listeners
                List<ContactListener> first = ((Part)contact.m_fixtureA.m_userData).entity.contactListeners;
                List<ContactListener> second = ((Part)contact.m_fixtureB.m_userData).entity.contactListeners;
                if (first.isEmpty() && second.isEmpty())
                    return;
                
                // Create contact proxies
                ContactContainer container = new ContactContainer();
                container.first = new Contact((Part)contact.m_fixtureA.m_userData, (Part)contact.m_fixtureB.m_userData);
                container.second = new Contact((Part)contact.m_fixtureB.m_userData, (Part)contact.m_fixtureA.m_userData);
                contacts.put(contact, container);
                
                // Notify
                for (ContactListener listener : first)
                    listener.beginContact(container.first);
                for (ContactListener listener : second)
                    listener.beginContact(container.second);
            }

            @Override
            public void endContact(org.jbox2d.dynamics.contacts.Contact contact) {
                
                // Check that we actually know about this one
                ContactContainer container = contacts.get(contact);
                if (container == null)
                    return;
                
                // Notify and unregister
                List<ContactListener> first = ((Part)contact.m_fixtureA.m_userData).entity.contactListeners;
                List<ContactListener> second = ((Part)contact.m_fixtureB.m_userData).entity.contactListeners;
                for (ContactListener listener : first)
                    listener.endContact(container.first);
                for (ContactListener listener : second)
                    listener.endContact(container.second);
                container.first.alive = false;
                container.second.alive = false;
                contacts.remove(contact);
            }

            @Override
            public void preSolve(org.jbox2d.dynamics.contacts.Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(org.jbox2d.dynamics.contacts.Contact contact, ContactImpulse impulse) {}
            
        });
        
    }
    
    /** @return global force, not null */
    public Vector getGravity() {
        Vec2 gravity = world.getGravity();
        return new Vector(gravity.x, gravity.y);
    }
    
    /**
     * Sets global force.
     * @param gravity force, not null
     */
    public void setGravity(Vector gravity) {
        world.setGravity(new Vec2(gravity.x, gravity.y));
    }
    
    /** @return a new entity builder */
    public EntityBuilder createEntityBuilder() {
        return new EntityBuilder(this);
    }
    
    /** @return a new distance constraint builder */
    public DistanceConstraintBuilder createDistanceConstraintBuilder() {
        DistanceConstraintBuilder builder = new DistanceConstraintBuilder();
        builder.world = this;
        return builder;
    }
    
    /** @return a new point constraint builder */
    public PointConstraintBuilder createPointConstraintBuilder() {
        PointConstraintBuilder builder = new PointConstraintBuilder();
        builder.world = this;
        return builder;
    }
    
    /** @return a new prismatic constraint builder */
    public PrismaticConstraintBuilder createPrismaticConstraintBuilder() {
        PrismaticConstraintBuilder builder = new PrismaticConstraintBuilder();
        builder.world = this;
        return builder;
    }
    
    /** @return a new revolute constraint builder */
    public RevoluteConstraintBuilder createRevoluteConstraintBuilder() {
        RevoluteConstraintBuilder builder = new RevoluteConstraintBuilder();
        builder.world = this;
        return builder;
    }
    
    /** @return a new rope constraint builder */
    public RopeConstraintBuilder createRopeConstraintBuilder() {
        RopeConstraintBuilder builder = new RopeConstraintBuilder();
        builder.world = this;
        return builder;
    }
    
    /** @return a new weld constraint builder */
    public WeldConstraintBuilder createWeldConstraintBuilder() {
        WeldConstraintBuilder builder = new WeldConstraintBuilder();
        builder.world = this;
        return builder;
    }
    
    /** @return a new wheel constraint builder */
    public WheelConstraintBuilder createWheelConstraintBuilder() {
        WheelConstraintBuilder builder = new WheelConstraintBuilder();
        builder.world = this;
        return builder;
    }
    
    /** @return read-only list of entities */
    public List<Entity> getEntities() {
        return entities;
    }
    
    // Internal comparator used to order impacts from nearest to farest
    private static final Comparator<Impact> TRACE_IMPACT_COMPARATOR = new Comparator<Impact>() {
        @Override
        public int compare(Impact a, Impact b) {
            return Float.compare(a.getFraction(), b.getFraction());
        }
    };
    
    /**
     * Computes a list of entities that intersect a segment.
     * @param start the origin of the segment
     * @param end the end point
     * @return an ordered list of entities
     */
    public List<Impact> trace(Vector start, Vector end) {
        final List<Impact> impacts = new ArrayList<>();
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
                Impact impact = new Impact(
                    (Part)fixture.m_userData,
                    new Vector(point.x, point.y),
                    new Vector(normal.x, normal.y),
                    fraction
                );
                impacts.add(impact);
                return -1.0f;
            }
        };
        Vec2 point1 = new Vec2(start.x, start.y);
        Vec2 point2 = new Vec2(end.x, end.y);
        world.raycast(callback, point1, point2);
        Collections.sort(impacts, TRACE_IMPACT_COMPARATOR); 
        return impacts;
    }
    
    /**
     * Simulates a single step.
     * @param deltaTime time elapsed since last update, non-negative
     */
    public void update(float deltaTime) {
        
        // Simulate physics
        if (deltaTime < 0.0f)
            throw new IllegalArgumentException();
        timeAccumulator += deltaTime;
        while (timeAccumulator >= timeStep) {
            timeAccumulator -= timeStep;
            world.step(timeStep, 8, 3);
        }
        world.clearForces();
        
        // Update bodies
        // TODO use residual time for extrapolation if needed
        for (org.jbox2d.dynamics.Body body = world.getBodyList(); body != null; body = body.m_next) {
            // TODO ignore static/sleeping bodies if needed
            Entity proxy = (Entity)body.m_userData;
            proxy.transform = null;
        }
    }
    
    
}
