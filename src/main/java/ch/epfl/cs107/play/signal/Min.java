package ch.epfl.cs107.play.signal;

/**
 * Computes the minimum of two signals, similar to AND-gates in logical systems.
 */
public class Min implements Signal {
    
    private final Signal first, second;

    /**
     * Creates a new minimum.
     * @param first any signal, not null
     * @param second any signal, not null
     */
    public Min(Signal first, Signal second) {
        this.first = first;
        this.second = second;
    }
    
    @Override
    public float getIntensity() {
        return Math.min(first.getIntensity(), second.getIntensity());
    }
    
}
