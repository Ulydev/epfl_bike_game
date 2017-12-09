package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity body;
    // Graphical representation of the body
    private ImageGraphics brokenStoneGraphics;
    private ImageGraphics bowGraphics;

    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));

        // Create Body
        EntityBuilder entityBuilder = world.createEntityBuilder();
        entityBuilder.setFixed(true);
        entityBuilder.setPosition(new Vector(1.0f, 1.5f));
        body = entityBuilder.build();

        // Create and attach stone graphics object to body
        brokenStoneGraphics = new ImageGraphics("stone.broken.4.png", 1, 1);
        brokenStoneGraphics.setAlpha(1.0f);
        brokenStoneGraphics.setDepth(0.0f);
        brokenStoneGraphics.setParent(body);

        // Create and attach bow graphics object to body
        bowGraphics = new ImageGraphics("bow.png", 1, 1);
        bowGraphics.setAlpha(1.0f);
        bowGraphics.setDepth(1.0f);
        //bowGraphics.setDepth(-1.0f); // Render bow on top of stone
        bowGraphics.setParent(body);

        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
        // Update physics
        world.update(deltaTime);

        // Set camera position
        window.setRelativeTransform(Transform.I.scaled(10.0f));

        // Render graphics objects
        brokenStoneGraphics.draw(window);
        bowGraphics.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
