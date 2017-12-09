package ch.epfl.cs107.play.signal;

/**
 * Invert the value, similar to NOT-gate in logical systems.
 */
public class Invert implements Signal {

    private final Signal signal;

    /**
     * Creates a new negation.
     * @param signal any signal, not null
     */
    public Invert(Signal signal) {
        if (signal == null)
            throw new NullPointerException();
        this.signal = signal;
    }
    
    @Override
    public float getIntensity() {
        return 1.0f - signal.getIntensity();
    }

}
