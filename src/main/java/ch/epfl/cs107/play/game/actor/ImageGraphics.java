package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Attachable;
import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Image;

/**
 * Contains information to render a single image, which can be attached to any positionable.
 */
public class ImageGraphics extends Node implements Graphics {

    private String name;
    private float width;
    private float height;
    private Vector anchor;
    private float alpha;
    private float depth;

    /**
     * Creates a new image graphics.
     * @param name image name, may be null
     * @param width actual image width, before transformation
     * @param height actual image height, before transformation
     * @param anchor image anchor, not null
     * @param alpha transparency, between 0 (invisible) and 1 (opaque)
     * @param depth render priority, lower-values drawn first
     */
    public ImageGraphics(String name, float width, float height, Vector anchor, float alpha, float depth) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.anchor = anchor;
        this.alpha = alpha;
        this.depth = depth;
    }

    /**
     * Creates a new image graphics.
     * @param name image name, not null
     * @param width actual image width, before transformation
     * @param height actual image height, before transformation
     * @param anchor image anchor, not null
     */
    public ImageGraphics(String name, float width, float height, Vector anchor) {
        this(name, width, height, anchor, 1.0f, 0.0f);
    }
    
    /**
     * Creates a new image graphics.
     * @param name image name, not null
     * @param width actual image width, before transformation
     * @param height actual image height, before transformation
     */
    public ImageGraphics(String name, float width, float height) {
        this(name, width, height, Vector.ZERO);
    }
   
    /**
     * Sets image name.
     * @param name new image name, may be null
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return image name, may be null */
    public String getName() {
        return name;
    }

    /**
     * Sets actual image width, before transformation.
     * @param width image width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /** @return actual image width, before transformation */
    public float getWidth() {
        return width;
    }

    /**
     * Sets actual image height, before transformation.
     * @param height image height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /** @return actual image height, before transformation */
    public float getHeight() {
        return height;
    }

    /**
     * Sets image anchor location, i.e. where is the center of the image.
     * @param anchor image anchor, not null
     */
    public void setAnchor(Vector anchor) {
        this.anchor = anchor;
    }

    /** @return image anchor, not null */
    public Vector getAnchor() {
        return anchor;
    }
    
    /**
     * Sets transparency.
     * @param alpha transparency, between 0 (invisible) and 1 (opaque)
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /** @return transparency, between 0 (invisible) and 1 (opaque) */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Sets rendering depth.
     * @param depth render priority, lower-values drawn first
     */
    public void setDepth(float depth) {
        this.depth = depth;
    }

    /** @return render priority, lower-values drawn first */
    public float getDepth() {
        return depth;
    }
    
    @Override
    public void draw(Canvas canvas) {
        if (name == null)
            return;
        Image image = canvas.getImage(name);
        Transform transform = Transform.I.translated(-anchor.x, -anchor.y).scaled(width, height).transformed(getTransform());
        canvas.drawImage(image, transform, alpha, depth);
    }

}
