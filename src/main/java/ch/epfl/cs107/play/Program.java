package ch.epfl.cs107.play;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.bike.BikeGame;
import ch.epfl.cs107.play.game.actor.crate.CrateGame;
import ch.epfl.cs107.play.game.tutorial.*;
import ch.epfl.cs107.play.io.DefaultFileSystem;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.FolderFileSystem;
import ch.epfl.cs107.play.io.ResourceFileSystem;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.window.swing.SwingWindow;

/**
 * Main entry point.
 */
public class Program {

	/** Maximal time step allowed for a single frame. */
	public static final float MAX_DELTA_TIME = 0.1f;

	/**
	 * Main entry point.
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// Define cascading file system
		FileSystem fileSystem = new FolderFileSystem(new ResourceFileSystem(DefaultFileSystem.INSTANCE));

		// Use Swing display
		Window window = new SwingWindow("Play", fileSystem);
		try {

			// Create a demo game
			Game game = new BikeGame();
			if (game.begin(window, fileSystem)) {

				// Use system clock to keep track of time progression
				long before;
				long now = System.nanoTime();

				// Run until the user try to close the window
				while (!window.isCloseRequested()) {

					// Compute time interval
					before = now;
					now = System.nanoTime();
					float deltaTime = (now - before);

					try {
						int timeDiff = Math.max(0, (int) (1E9/300 - deltaTime));
						Thread.sleep((int) (timeDiff / 1E6), (int) (timeDiff % 1E6));
					} catch (InterruptedException e) {
					}

					now = System.nanoTime();
					deltaTime = (now - before) / 1E9f;

					// Clip time interval
					if (deltaTime > MAX_DELTA_TIME) {
						deltaTime = MAX_DELTA_TIME;
						System.out.println("Can't keep up!");
					}

					// Let the game do its stuff
					game.update(deltaTime);

					// Render and update input
					window.update();
				}

			}
			game.end();

		} finally {

			// Release resources
			window.dispose();

		}
	}

}
