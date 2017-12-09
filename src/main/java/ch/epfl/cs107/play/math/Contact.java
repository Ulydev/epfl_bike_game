package ch.epfl.cs107.play.math;

/**
 * Represents a single contact point between two entities.
 */
public final class Contact {
    
    // TODO  provide more information about contact
	// (location, force...) if needed
    
    final Part owner;
    final Part other;
    boolean alive;
    
    // For internal use only
    Contact(Part owner, Part other) {
        this.owner = owner;
        this.other = other;
        alive = true;
    }
    
    /** @return whether this contact still exists */
    public boolean isAlive() {
        return alive;
    }

    /** @return contact listener owner, not null */
    public Part getOwner() {
        return owner;
    }

    /** @return colliding part, not null */
    public Part getOther() {
        return other;
    }
    
}
