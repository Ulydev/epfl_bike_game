package ch.epfl.cs107.play.math;

/**
 * Contains impact information, mostly used by ray casting collision tests.
 */
public final class Impact {

    private final Part part;
    private final Vector position;
    private final Vector normal;
    private final float fraction;

    /**
     * Creates a new impact.
     * @param part associated part
     * @param position associated position
     * @param normal associated normal
     * @param fraction hitscan ratio
     */
    public Impact(Part part, Vector position, Vector normal, float fraction) {
        this.part = part;
        this.position = position;
        this.normal = normal;
        this.fraction = fraction;
    }

    /** @return associated part */
    public Part getPart() {
        return part;
    }

    /** @return impact location */
    public Vector getPosition() {
        return position;
    }

    /** @return impact normal */
    public Vector getNormal() {
        return normal;
    }

    /** @return ray fraction at impact location */
    public float getFraction() {
        return fraction;
    }
    
}
