package ch.epfl.cs107.play.game;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

/**
 * Represents the external interface of a game, as seen by the main game loop.
 */
public interface Game {
    
	/**
     * Initialises game state.
     * @param window context to use, not null
     * @param fileSystem file system to use, not null
     * @return whether the game was successfully started
     */
    boolean begin(Window window, FileSystem fileSystem);
    
    /**
     * Simulates a single time step.
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    void update(float deltaTime);
    
    /** Cleans up things, called even if initialisation failed. */
    void end();
    
}
