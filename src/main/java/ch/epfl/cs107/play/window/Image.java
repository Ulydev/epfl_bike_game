package ch.epfl.cs107.play.window;

/**
 * Context-agnostic immutable image.
 */
public interface Image {

    /** @return width, in pixels */
    public int getWidth();
    
    /** @return height, in pixels */
    public int getHeight();
    
}
