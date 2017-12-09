package ch.epfl.cs107.play.signal;

import ch.epfl.cs107.play.window.Keyboard;

/**
 * Signal based on the value of a specific keyboard key.
 */
public class KeyboardSignal implements Signal {

    private final Keyboard keyboard;
    private final int code;

    /**
     * Creates a new keyboard signal.
     * @param keyboard keyboard manager, not null
     * @param code key code to watch
     * @see KeyEvent
     */
    public KeyboardSignal(Keyboard keyboard, int code) {
        if (keyboard == null)
            throw new NullPointerException();
        this.keyboard = keyboard;
        this.code = code;
    }
    
    @Override
    public float getIntensity() {
        return keyboard.get(code).isDown() ? 1.0f : 0.0f;
    }
    
}
