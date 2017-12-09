package ch.epfl.cs107.play.window.swing;

import ch.epfl.cs107.play.window.Image;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Swing implementation of an image.
 */
public class SwingImage implements Image {

    // Package-protected, for efficient access
    final BufferedImage image;

    /**
     * Creates an image from specified image.
     * @param image valid image to be copied, not null
     */
	public SwingImage(java.awt.Image image) {
		// See
		// http://stackoverflow.com/questions/196890/java2d-performance-issues
		// http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
		// http://stackoverflow.com/questions/148478/java-2d-drawing-optimal-performance
		
		// Get system graphical configuration
		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		// Get image size
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		
		// Create optimized buffered image
		this.image = config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		
		// Draw original image in buffer
		Graphics2D graphics = this.image.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
	}
    
    /**
     * Creates an image from specified image input stream.
     * @param stream valid image input stream, not null
     * @throws IOException if an error occurs during reading
     */
    public SwingImage(InputStream stream) throws IOException {
        this(ImageIO.read(stream));
    }
    
    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }
    
}
