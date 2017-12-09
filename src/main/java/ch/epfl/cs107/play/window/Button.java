package ch.epfl.cs107.play.window;

import java.io.Serializable;

/**
 * Contains the current and previous states of a button.
 */
public final class Button implements Serializable {
    
    private final boolean previous, current;

    /**
     * Creates a new button state.
     * @param previous previous state
     * @param current current state
     */
    public Button(boolean previous, boolean current) {
        this.previous = previous;
        this.current = current;
    }

    /**
     * Creates a new button state.
     * @param current previous and current state
     */
    public Button(boolean current) {
        this.previous = current;
        this.current = current;
    }
    
    /** @return whether the button is currently pressed */
    public boolean isDown() {
        return current;
    }
    
    /** @return whether the button is currently released */
    public boolean isUp() {
        return !current;
    }
    
    /** @return whether the button was released, regardless of current state */
    public boolean wasDown() {
        return previous;
    }
    
    /** @return whether the button was pressed, regardless of current state */
    public boolean wasUp() {
        return !previous;
    }
    
    /** @return whether the button was just pressed */
    public boolean isPressed() {
        return !previous && current;
    }

    /** @return whether the button was just released */
    public boolean isReleased() {
        return previous && !current;
    }
    
    /**
     * Creates an new state, given this state.
     * @param next the new state, used to compute new transition
     * @return a new button state, not null
     */
    public Button updated(boolean next) {
        return new Button(current, next);
    }
    
    @Override
    public int hashCode() {
        return (previous ? 0 : 1) + (current ? 0 : 2);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Button))
            return false;
        Button other = (Button)object;
        return previous == other.previous && current == other.current;
    }

    @Override
    public String toString() {
        if (previous) {
            if (current)
                return "DOWN";
            return "RELEASED";
        }
        if (current)
            return "PRESSED";
        return "UP";
    }
    
}
