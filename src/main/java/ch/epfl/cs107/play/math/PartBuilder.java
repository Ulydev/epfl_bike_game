package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.FixtureDef;

/**
 * Helps build a entity from scratch.
 */
public class PartBuilder {
    
    private FixtureDef fixtureDef;
    private Shape shape;
    private Entity entity;

    // Created by Body only
    PartBuilder(Entity entity) {
        this.entity = entity;
        fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
    }
    
    /**
     * Sets whether this part is hidden and act only as a sensor.
     * @param ghost new state
     */
    public void setGhost(boolean ghost) {
        fixtureDef.isSensor = ghost;
    }
    
    /** @return whether this part is hidden and act only as a sensor */
    public boolean isGhost() {
        return fixtureDef.isSensor;
    }
    
    /**
     * Sets the friction coefficient.
     * @param friction any non negative value
     */
    public void setFriction(float friction) {
        fixtureDef.friction = friction;
    }
    
    /** @return the friction coefficient, non negative */
    public float getFriction() {
        return fixtureDef.friction;
    }
    
    /**
     * Sets the restitution coefficient.
     * @param restitution any non negative value
     */
    public void setRestitution(float restitution) {
        fixtureDef.restitution = restitution;
    }
    
    /** @return the restitution coefficient, non negative */
    public float getRestitution() {
        return fixtureDef.restitution;
    }
    
    /**
     * Sets density, as final mass depends on the actual shape.
     * @param density any non negative value
     */
    public void setDensity(float density) {
        fixtureDef.density = density;
    }
    
    /** @return density */
    public float getDensity() {
        return fixtureDef.density;
    }
    
    /**
     * Sets the shape.
     * @param shape any shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /** @return the shape */
    public Shape getShape() {
        return shape;
    }
    
    /** Selects which collision categories this part reacts to.
     * Usually, just one bit is set (by default, the first one, i.e. <code>0x1</code>).
     * @param bits bitfield specifying which categories affect this part
     */
    public void setCollisionSignature(int bits) {
        fixtureDef.filter.categoryBits = bits;
    }
    
    /**
     * Selects shich collision groups this part affects.
     * @param bits bitfield, 0 to affect everybody
     */
    public void setCollisionEffect(int bits) {
        fixtureDef.filter.maskBits = bits;
    }

    /** @return collision categories of this part */
    public int getCollisionSignature() {
        return fixtureDef.filter.categoryBits;
    }

    /**
     * Creates a new part.
     * @return the newly created part
     */
    public Part build() {
        Part part = shape.build(fixtureDef, entity);
        entity.parts.add(part);
        return part;
    }
    
}
