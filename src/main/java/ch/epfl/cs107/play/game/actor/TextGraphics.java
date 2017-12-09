package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Attachable;
import java.awt.Color;

import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/**
 * Contains information to render a single string, which can be attached to any positionable.
 */
public class TextGraphics extends Node implements Attachable, Graphics {
    
	private String text;
    private float fontSize;
	private Color fillColor;
	private Color outlineColor;
    private float thickness;
	private boolean bold;
	private boolean italics;
    private Vector anchor;
	private float alpha;
	private float depth;

    /**
     * Creates a new text graphics.
     * @param text content, not null
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
    public TextGraphics(String text, float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold, boolean italics, Vector anchor, float alpha, float depth) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;
        this.fontSize = fontSize;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.thickness = thickness;
        this.bold = bold;
        this.italics = italics;
        this.anchor = anchor;
        this.alpha = alpha;
        this.depth = depth;
    }

    /**
     * Creates a new text graphics.
     * @param text content, not null
     * @param fontSize size
     * @param fillColor fill color, may be null
     * @param outlineColor outline color, may be null
     * @param thickness outline thickness
     * @param bold whether to use bold font
     * @param italics whether to use italics font
     * @param anchor text anchor
     */
    public TextGraphics(String text, float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold, boolean italics, Vector anchor) {
        this(text, fontSize, fillColor, outlineColor, thickness, bold, italics, anchor, 1.0f, 0.0f);
    }
    
    /**
     * Creates a new text graphics.
     * @param text content, not null
     * @param fontSize size
     * @param fillColor fill color, may be null
     */
    public TextGraphics(String text, float fontSize, Color fillColor) {
        this(text, fontSize, fillColor, null, 0.0f, false, false, Vector.ZERO);
    }

    /**
     * Sets text content.
     * @param text content, not null
     */
    public void setText(String text) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;
    }

    /** @return text content, not null */
    public String getText() {
        return text;
    }

    /**
     * Sets font size.
     * @param fontSize size 
     */
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    /** @return font size */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * Sets fill color.
     * @param fillColor color, may be null
     */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
    
    /** @return fill color, may be null */
	public Color getFillColor() {
		return fillColor;
	}

    /**
     * Sets outline color.
     * @param outlineColor color, may be null
     */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

    /** @return outline color, may be null */
	public Color getOutlineColor() {
		return outlineColor;
	}

    /**
     * Sets outline thickness.
     * @param thickness outline thickness
     */
    public void setThickness(float thickness) {
		this.thickness = thickness;
	}
    
    /** @return outline thickness */
	public float getThickness() {
		return thickness;
	}

    /**
     * Sets bold font.
     * @param bold whether to use bold font
     */
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    /** @return whether to use bold font */
    public boolean isBold() {
        return bold;
    }

    /**
     * Sets italics font.
     * @param italics whether to use italics font
     */
    public void setItalics(boolean italics) {
        this.italics = italics;
    }

    /** @return whether to use italics font */
    public boolean isItalics() {
        return italics;
    }

    /**
     * Sets text anchor, i.e. how to orient it.
     * @param anchor text anchor
     */
    public void setAnchor(Vector anchor) {
        this.anchor = anchor;
    }

    /** @return text anchor */
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
        canvas.drawText(text, fontSize, getTransform(), fillColor, outlineColor, thickness, bold, italics, anchor, alpha, depth);
	}

}
