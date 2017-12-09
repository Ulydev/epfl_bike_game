package ch.epfl.cs107.play.window.swing;

import java.awt.Graphics2D;

/**
 * Represent a renderable element in a Swing context.
 */
public interface Item extends Comparable<Item> {

    /** @return render priority, lower-values drawn first */
    public float getDepth();
    
    @Override
    public default int compareTo(Item other) {
        return Float.compare(getDepth(), other.getDepth());
    }
    
    /**
     * Renders the item.
     * @param g target context, not null
     */
    public void render(Graphics2D g);
    
}
