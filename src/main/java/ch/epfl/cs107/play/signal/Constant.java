package ch.epfl.cs107.play.signal;

/**
 * Signal placeholder.
 */
public class Constant implements Signal {

    private float intensity;

    /**
     * Creates a new constant.
     * @param intensity any real value
     */
    public Constant(float intensity) {
        this.intensity = intensity;
    }

    @Override
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the value of the constant.
     * @param intensity any real value
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    
}
