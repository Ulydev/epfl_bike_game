package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.window.Window;

import java.awt.*;

/**
 * Simple game, to show basic the basic architecture
 */
public class RopeGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block;
    private Entity ball;

    // Graphical representation of the bodies
    private ImageGraphics blockGraphics;
    private ShapeGraphics ballGraphics;

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
        Polygon blockPolygon = new Polygon(
                new Vector(0.0f, 0.0f),
                new Vector(blockWidth, 0.0f),
                new Vector(blockWidth, blockHeight),
                new Vector(0.0f, blockHeight)
        );
        blockPartBuilder.setShape(blockPolygon);
        blockPartBuilder.build();

        // Create and attach graphics object to block
        blockGraphics = new ImageGraphics("stone.broken.4.png", blockWidth, blockHeight);
        blockGraphics.setParent(block);

        // Create ball
        float ballRadius = 0.6f;
        EntityBuilder ballBuilder = world.createEntityBuilder();
        ballBuilder.setFixed(false);
        ballBuilder.setPosition(new Vector(0.6f, 4.0f));
        ball = ballBuilder.build();

        // Create and attach ball fixture
        PartBuilder ballPartBuilder = ball.createPartBuilder();
        Circle ballCircle = new Circle(ballRadius);
        ballPartBuilder.setShape(ballCircle);
        ballPartBuilder.build();

        // Create and attach graphics object to ball
        ballGraphics = new ShapeGraphics(ballCircle, Color.BLUE, Color.RED, 0.1f, 1.0f, 0);
        ballGraphics.setParent(ball);

        // Create rope constraint
        RopeConstraintBuilder ropeConstraintBuilder = world.createRopeConstraintBuilder();
        ropeConstraintBuilder.setFirstEntity(block);
        ropeConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, blockHeight/2));
        ropeConstraintBuilder.setSecondEntity(ball);
        ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
        ropeConstraintBuilder.setMaxLength(6.0f);
        ropeConstraintBuilder.setInternalCollision(true);
        ropeConstraintBuilder.build();

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
        ballGraphics.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
