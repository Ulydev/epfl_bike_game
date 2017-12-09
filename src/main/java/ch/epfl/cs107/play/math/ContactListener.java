package ch.epfl.cs107.play.math;

/**
 * Event handler for physical contact between entities. As these events occur during simulation, some operations are forbidden during callbacks.
 */
public interface ContactListener {
    
    /**
     * Called when a new contact is created.
     * @param contact new contact object, not null
     */
    public void beginContact(Contact contact);
    
    /**
     * Called when a contact ceases to exist. After this event, the contact is destroyed.
     * @param contact old contact object, not null
     */
    public void endContact(Contact contact);
    
}
