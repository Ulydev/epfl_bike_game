package ch.epfl.cs107.play.math;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Simple implementation of contact listener, which tracks touching entities.
 */
public class BasicContactListener implements ContactListener {
    
    // Keep track of entities and their count
    private final Map<Entity, Integer> entities;

    /** Creates a new listener */
    public BasicContactListener() {
        entities = new HashMap<>();
    }

    /** @return read-only collection of touching entities, not null */
    public Set<Entity> getEntities() {
        return Collections.unmodifiableSet(entities.keySet());
    }
    
    @Override
    public void beginContact(Contact contact) {
        if (isAllowed(contact)) {
            Entity entity = contact.getOther().getEntity();
            int count = entities.getOrDefault(entity, 0);
            entities.put(entity, count + 1);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Entity entity = contact.getOther().getEntity();
        int count = entities.getOrDefault(entity, 0) - 1;
        if (count <= 0)
            entities.remove(entity);
        else
            entities.put(entity, count);
    }
    
    /** @return true if the list of contacts is not empty */
    public boolean hasContacts() {
        return entities.size() > 0;      
    }
    
    public boolean hasContactWith(Entity entity) {
        return getEntities().contains(entity);
    }
    /**
     * Checks whether this contact should be registered.
     * @param contact candidate, not null
     * @return false to ignore it
     */
    protected boolean isAllowed(Contact contact) {
        return true;
    }
    
}
