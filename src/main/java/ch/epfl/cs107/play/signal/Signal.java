package ch.epfl.cs107.play.signal;

/**
 * Represents an object that can emit a signal.
 */
public interface Signal {

    /** @return signal intensity, usually between 0.0 and 1.0 */
    public float getIntensity();
    
}
