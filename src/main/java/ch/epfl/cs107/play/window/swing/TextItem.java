package ch.epfl.cs107.play.window.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.geom.Rectangle2D;

/**
 * Draw a single string.
 */
public class TextItem implements Item {

    private String text;
    private float fontSize;
    private Transform transform;
    private Color fillColor;
    private Color outlineColor;
	private float thickness;
    private Font font;
    private Vector anchor;
	private float depth;
	private float alpha;

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
    public TextItem(String text, float fontSize, Transform transform, Color fillColor, Color outlineColor, float thickness, boolean bold, boolean italics, Vector anchor, float depth, float alpha) {
        this.text = text;
        this.fontSize = fontSize;
        this.transform = transform;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.thickness = thickness;
        this.font = new Font(null, Font.PLAIN | (bold ? Font.BOLD : 0) | (italics ? Font.ITALIC : 0), 1);
        this.anchor = anchor;
        this.depth = depth;
        this.alpha = alpha;
    }
	
	@Override
	public float getDepth() {
		return depth;
	}

	@Override
	public void render(Graphics2D g) {
        
        // Keep current state, in order to restore it later
		Font origFont = g.getFont();
		Color origColor = g.getColor();
		AffineTransform origTransform = g.getTransform();
        Composite origComposite = null;
        if (alpha < 1.0f) {
            origComposite = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

		// Flip vertically (since g2d's text origin is top-left)
		AffineTransform a = new AffineTransform(
            transform.m00, transform.m10,
            -transform.m01, -transform.m11,
		    transform.m02 + transform.m01, transform.m12 + transform.m11
        );

        // Define intrisic text properties
		g.setFont(font);
		g.setColor(fillColor);
		AffineTransform ax = (AffineTransform) origTransform.clone();
		ax.concatenate(a);
		g.setTransform(ax);
		g.scale(fontSize, fontSize);

		// Estimate text size
		FontMetrics metrics = g.getFontMetrics(font);
        Rectangle2D bounds = metrics.getStringBounds(text, g);
        float x = (float)bounds.getMinX() + (float)bounds.getWidth() * anchor.getX();
        float y = (float)bounds.getMinY() + (float)bounds.getHeight() * anchor.getY();
		g.drawString(text, -x, -y);
		
        // If requested, also draw outline
		if (outlineColor != null && thickness > 0.0f) {
			g.setColor(outlineColor);
			GlyphVector gv = font.createGlyphVector(g.getFontRenderContext(), text);
			Shape shape = gv.getOutline();
			g.setStroke(new BasicStroke(thickness));
			g.translate(-x, -y);
			g.draw(shape);
		}

        // Restore old properties
		g.setTransform(origTransform);
		g.setFont(origFont);
		g.setColor(origColor);
        if (origComposite != null)
            g.setComposite(origComposite);
	}

}
