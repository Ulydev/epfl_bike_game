package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class SimpleCrateGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block;
    private Entity crate;

    // Graphical representation of the bodies
    private ImageGraphics blockGraphics;
    private ImageGraphics crateGraphics;

    /**
     * This function generates a square polygon based on given dimensions
     * @param width
     * @param height
     * @return
     */
    private static Polygon createSquarePolygon(float width, float height) {
        return new Polygon(
                new Vector(0.0f, 0.0f),
                new Vector(width, 0.0f),
                new Vector(width, height),
                new Vector(0.0f, height)
        );
    }

    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));

        // Create block
        float blockWidth = 1.0f, blockHeight = 1.0f;
        EntityBuilder blockBuilder = world.createEntityBuilder();
        blockBuilder.setFixed(true);
        blockBuilder.setPosition(new Vector(1.0f, 0.5f));
        block = blockBuilder.build();

        // Create and attach block fixture
        PartBuilder blockPartBuilder = block.createPartBuilder();
        Polygon blockPolygon = createSquarePolygon(blockWidth, blockHeight);
        blockPartBuilder.setShape(blockPolygon);
        blockPartBuilder.build();

        // Create and attach graphics object to block
        blockGraphics = new ImageGraphics("stone.broken.4.png", blockWidth, blockHeight);
        blockGraphics.setParent(block);

        // Create crate
        float crateWidth = 1.0f, crateHeight = 1.0f;
        EntityBuilder crateBuilder = world.createEntityBuilder();
        crateBuilder.setFixed(false);
        crateBuilder.setPosition(new Vector(0.2f, 4.0f));
        crate = crateBuilder.build();

        // Create and attach crate fixture
        PartBuilder cratePartBuilder = crate.createPartBuilder();
        Polygon cratePolygon = createSquarePolygon(crateWidth, crateHeight);
        cratePartBuilder.setShape(cratePolygon);
        cratePartBuilder.build();

        // Create and attach graphics object to crate
        crateGraphics = new ImageGraphics("box.4.png", crateWidth, crateHeight);
        crateGraphics.setParent(crate);

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
        blockGraphics.draw(window);
        crateGraphics.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
