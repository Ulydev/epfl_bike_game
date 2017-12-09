package ch.epfl.cs107.play.window.swing;

import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.window.Mouse;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Swing implementation of window context.
 */
public class SwingWindow extends Node implements Window {

	// Image stuff
	private FileSystem fileSystem;
	private Map<String, SwingImage> images;
	private List<Item> items;

	// Swing components
	private JFrame frame;
	private java.awt.Canvas canvas;
	private BufferStrategy strategy;

	// State information
	private volatile boolean closeRequested;
	private Button focus;
	private MouseProxy mouseProxy;
	private KeyboardProxy keyboardProxy;

	// Define mouse manager
	private class MouseProxy extends MouseAdapter implements Mouse {

		int previous = 0;
		int current = 0;
		int buffer = 0;
		Vector position = Vector.ZERO;

		@Override
		public void mousePressed(MouseEvent e) {
			synchronized (SwingWindow.this) {
				buffer |= 1 << e.getButton() - 1;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			synchronized (SwingWindow.this) {
				buffer &= ~(1 << e.getButton() - 1);
			}
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO mouse scroll?
		}

		@Override
		public Vector getPosition() {
			return position;
		}

        @Override
        public Vector getVelocity() {
            // TODO interpolate mouse velocity
            return Vector.ZERO;
        }
        
		@Override
		public Button getButton(int index) {
			int mask = 1 << index;
			return new Button((previous & mask) != 0, (current & mask) != 0);
		}

	}

	// Define keyboard manager
	private class KeyboardProxy extends KeyAdapter implements Keyboard {

		private Set<Integer> previous;
		private Set<Integer> current;
		private Set<Integer> buffer;

		public KeyboardProxy() {
			previous = new HashSet<>();
			current = new HashSet<>();
			buffer = new HashSet<>();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			synchronized (SwingWindow.this) {
				buffer.add(e.getKeyCode());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			synchronized (SwingWindow.this) {
				buffer.remove(e.getKeyCode());
			}
		}

		@Override
		public Button get(int code) {
			return new Button(previous.contains(code), current.contains(code));
		}

	}

	/**
	 * Creates a new window.
	 * 
	 * @param title
	 *            window caption
	 * @param fileSystem
	 *            source used to load images
	 */
	public SwingWindow(String title, FileSystem fileSystem) {

		// Prepare image loader
		this.fileSystem = fileSystem;
		images = new HashMap<>();
		items = new ArrayList<>();

		// Create Swing canvas
		canvas = new java.awt.Canvas();
		canvas.setFocusable(true);
		canvas.setFocusTraversalKeysEnabled(false);
		canvas.setIgnoreRepaint(true);
		canvas.setBackground(Color.BLACK);

		// Create Swing frame
		frame = new JFrame(title);
		frame.add(canvas);
		focus = new Button(false);

		// Handle close request
		WindowAdapter windowAdapter = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {
				closeRequested = true;
			}

		};
		frame.addWindowListener(windowAdapter);

		// Create mouse manager
		mouseProxy = new MouseProxy();
		canvas.addMouseListener(mouseProxy);
		canvas.addMouseWheelListener(mouseProxy);

		// Create keyboard manager
		keyboardProxy = new KeyboardProxy();
		canvas.addKeyListener(keyboardProxy);

		// Show frame
		frame.pack();
		frame.setSize(640, 480);
		frame.setVisible(true);
	}

	@Override
	public Button getFocus() {
		return focus;
	}

	@Override
	public Mouse getMouse() {
		return mouseProxy;
	}

	@Override
	public Keyboard getKeyboard() {
		return keyboardProxy;
	}

	@Override
	public boolean isCloseRequested() {
		return closeRequested;
	}

	@Override
	public void update() {

		// Compute viewport metrics
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		float halfX;
		float halfY;
		if (width > height) {
			halfX = 1.0f;
			halfY = (float) height / (float) width;
		} else {
			halfX = (float) width / (float) height;
			halfY = 1.0f;
		}
		Transform viewToWorld = getTransform();
		Transform worldToView = viewToWorld.inverted();
		Transform projection = new Transform(0.5f * width / halfX, 0.0f, 0.5f * width, 0.0f, -0.5f * height / halfY, 0.5f * height);
		Transform transform = worldToView.transformed(projection);

		// Setup double buffering if needed
		if (strategy == null) {
			canvas.createBufferStrategy(2);
			strategy = canvas.getBufferStrategy();
		}

		// Create graphic context
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();

		// Clear background
		graphics.setColor(canvas.getBackground());
		graphics.fillRect(0, 0, width, height);

		// Enable anti-aliasing
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Set view transform
		AffineTransform affine = new AffineTransform(transform.m00, transform.m10, transform.m01, transform.m11,
				transform.m02, transform.m12);
		graphics.transform(affine);

		// Render ordered drawables
		Collections.sort(items);
		for (Item item : items)
			item.render(graphics);

		// Finalize rendering
		graphics.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
		items.clear();

		// Update window state
		focus = focus.updated(canvas.hasFocus());

		// Get mouse pointer location
		float x = 0.0f;
		float y = 0.0f;
		PointerInfo pointer = MouseInfo.getPointerInfo();
		if (pointer != null) {
			Point point = pointer.getLocation();
			SwingUtilities.convertPointFromScreen(point, canvas);
			x = ((float) point.getX() - 0.5f * width) * 2.0f * halfX / width;
			y = ((float) point.getY() - 0.5f * height) * -2.0f * halfY / height;
		}
		mouseProxy.position = viewToWorld.onPoint(x, y);

		synchronized (this) {

			// Update mouse buttons
			mouseProxy.previous = mouseProxy.current;
			mouseProxy.current = mouseProxy.buffer;

			// Update keyboard buttons
			Set<Integer> tmp = keyboardProxy.previous;
			keyboardProxy.previous = keyboardProxy.current;
			keyboardProxy.current = keyboardProxy.buffer;
			keyboardProxy.buffer = tmp;
			keyboardProxy.buffer.clear();
			keyboardProxy.buffer.addAll(keyboardProxy.current);

		}
	}

	@Override
	public void dispose() {
		frame.dispose();
	}

	@Override
	public SwingImage getImage(String name) {
		SwingImage image = images.get(name);
		if (image == null) {
			InputStream input = null;
			try {
				input = fileSystem.read(name);
				image = new SwingImage(input);
			} catch (IOException e) {
				// Empty on purpose, will return null as an error
			} finally {
				try {
					if (input != null)
						input.close();
				} catch (IOException e) {
					// Empty on purpose
				}
			}
			images.put(name, image);
			// TODO maybe need to free memory at some point
		}
		return image;
	}

	/**
	 * Add specified item to current draw list.
	 * 
	 * @param item
	 *            any item, not null
	 */
	public void draw(Item item) {
		if (item == null)
			throw new NullPointerException();
		items.add(item);
	}

	@Override
	public void drawImage(Image image, Transform transform, float alpha, float depth) {
		if (transform == null)
			throw new NullPointerException();
		if (image == null || alpha <= 0.0f)
			return;
		items.add(new ImageItem(depth, alpha, transform, (SwingImage) image));
	}

    @Override
    public void drawShape(Shape shape, Transform transform, Color fillColor, Color outlineColor, float thickness, float alpha, float depth) {
        if (transform == null)
			throw new NullPointerException();
        if (shape == null || alpha <= 0.0f || (fillColor == null && (outlineColor == null || thickness <= 0.0f)))
			return;
        Path2D path = shape.toPath();
		path.transform(transform.getAffineTransform());
		items.add(new ShapeItem(path, fillColor, outlineColor, thickness, alpha, depth));
    }

    @Override
    public void drawText(String text, float fontSize, Transform transform, Color fillColor, Color outlineColor, float thickness, boolean bold, boolean italics, Vector anchor, float alpha, float depth) {
        if (transform == null)
			throw new NullPointerException();
        if (text == null || fontSize <= 0.0f || alpha <= 0.0f || (fillColor == null && (outlineColor == null || thickness <= 0.0f)))
            return;
        items.add(new TextItem(text, fontSize, transform, fillColor, outlineColor, thickness, bold, italics, anchor, depth, alpha));
    }
    
}
