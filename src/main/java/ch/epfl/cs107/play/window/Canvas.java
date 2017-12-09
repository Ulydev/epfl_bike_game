package ch.epfl.cs107.play.window;

import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import java.awt.Color;

/**
 * Represents a rendering context, with various drawing capabilities.
 */
public interface Canvas extends Positionable {

    // TODO provide some size/aspect ratio information if needed

    /**
     * Gets image from file system.
     * @param name full name of image, not null
     * @return an image object, null on error
     */
    public Image getImage(String name);

    /**
     * Draws specified image.
     * @param image any image associated to this context, may be null
     * @param transform any affine transform, not null
     * @param alpha transparency, between 0.0 and 1.0
     * @param depth any real, larger values are drawn afterward, i.e. above
     */
    public void drawImage(Image image, Transform transform, float alpha, float depth);

    /**
     * Draws specified image
     * @param shape any shape, may be null
     * @param transform any affine transform, not null
     * @param fillColor color used to fill the shape, may be null
     * @param outlineColor color used to draw shape border, may be null
     * @param thickness border thickness
     * @param alpha transparency, between 0.0 and 1.0
     * @param depth any real, larger values are drawn afterward, i.e. above
     */
    public void drawShape(Shape shape, Transform transform, Color fillColor, Color outlineColor, float thickness, float alpha, float depth);

    /**
     * Creates a new text graphics.
     * @param text content, not null
     * @param transform affine transform, not null
     * @param fontSize size
     * @param fillColor fill color, may be null
     * @param outlineColor outline color, may be null
     * @param thickness outline thickness
     * @param bold whether to use bold font
     * @param italics whether to use italics font
     * @param anchor text anchor
     * @param alpha transparency, between 0 (invisible) and 1 (opaque)
     * @param depth render priority, lower-values drawn first
     */
    public void drawText(String text, float fontSize, Transform transform, Color fillColor, Color outlineColor, float thickness, boolean bold, boolean italics, Vector anchor, float alpha, float depth);
    
}
