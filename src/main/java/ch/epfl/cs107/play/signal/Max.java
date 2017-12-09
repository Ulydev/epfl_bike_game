package ch.epfl.cs107.play.signal;

/**
 * Computes the maximum of two signals, similar to OR-gates in logical systems.
 */
public class Max implements Signal {

    private final Signal first, second;

    /**
     * Creates a new maximum.
     * @param first any signal, not null
     * @param second any signal, not null
     */
    public Max(Signal first, Signal second) {
        this.first = first;
        this.second = second;
    }
    
    @Override
    public float getIntensity() {
        return Math.max(first.getIntensity(), second.getIntensity());
    }
    
}
