package ch.epfl.cs107.play.window;

import ch.epfl.cs107.play.math.Attachable;

/**
 * Represents a context frame, which can act as a canvas.
 * Moreover, the camera can be attached to any positionable entity.
 */
public interface Window extends Canvas, Attachable {
    
    // TODO JavaFX backend?
    // TODO OpenGL backend?
    
    /** @return whether the windows is active */
    public Button getFocus();
    
    /** @return associated mouse controller */
    public Mouse getMouse();
    
    /** @return associated keyboard controller */
    public Keyboard getKeyboard();
    
    // TODO gamepads
    
    /** @return whether the user tried to close the window */
    public boolean isCloseRequested();
    
    // TODO this may require delta time, e.g. for mouse interpolation
    public void update();
    
    /** Destroys and closes the window */
    public void dispose();
    
}
