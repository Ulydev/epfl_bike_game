package ch.epfl.cs107.play.window;

import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

/**
 * Represents the mouse pointer.
 */
public interface Mouse extends Positionable {
        
    @Override
    public default Transform getTransform() {
        Vector position = getPosition();
        return new Transform(1.0f, 0.0f, position.x, 0.0f, 1.0f, position.y);
    }
    
    
    public Button getButton(int index);
    
    public default Button getLeftButton() {
        return getButton(0);
    }
    
    public default Button getMiddleButton() {
        return getButton(1);
    }
    
    public default Button getRightButton() {
        return getButton(2);
    }
    
    // TODO wheel/scroll if needed
    
}
