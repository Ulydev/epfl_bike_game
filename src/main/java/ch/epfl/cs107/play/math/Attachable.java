package ch.epfl.cs107.play.math;

/**
 * Represents a positionable object that can be placed or attached.
 */
public interface Attachable extends Positionable {
    
    /**
     * Chooses reference object.	
     * @param parent any positionable, may be null
     */
    public void setParent(Positionable parent);
    
    /** @return reference object, may be null */
    public Positionable getParent();
    
    /**
     * Sets relative affine transformation.
     * @param transform any transform, not null
     */
    public void setRelativeTransform(Transform transform);

    /** @return relative affine transformation, not null */
    public Transform getRelativeTransform();
    
}
