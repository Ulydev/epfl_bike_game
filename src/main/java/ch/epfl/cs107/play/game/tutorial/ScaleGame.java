package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Simple game, to show basic the basic architecture
 */
public class ScaleGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block;
    private Entity plank;
    private Entity ball;

    // Graphical representation of the bodies
    private ImageGraphics blockGraphics;
    private ImageGraphics plankGraphics;
    private ImageGraphics ballGraphics;

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
        float blockWidth = 10.0f, blockHeight = 1.0f;
        EntityBuilder blockBuilder = world.createEntityBuilder();
        blockBuilder.setFixed(true);
        blockBuilder.setPosition(new Vector(-5.0f, -1.0f));
        block = blockBuilder.build();

        // Create and attach block fixture
        PartBuilder blockPartBuilder = block.createPartBuilder();
        Polygon blockPolygon = createSquarePolygon(blockWidth, blockHeight);
        blockPartBuilder.setShape(blockPolygon);
        blockPartBuilder.build();

        // Create and attach graphics object to block
        blockGraphics = new ImageGraphics("stone.broken.4.png", blockWidth, blockHeight);
        blockGraphics.setParent(block);

        // Create plank
        float plankWidth = 5.0f, plankHeight = 0.2f;
        EntityBuilder plankBuilder = world.createEntityBuilder();
        plankBuilder.setFixed(false);
        plankBuilder.setPosition(new Vector(-2.5f, 0.8f));
        plank = plankBuilder.build();

        // Create and attach plank fixture
        PartBuilder plankPartBuilder = plank.createPartBuilder();
        Polygon plankPolygon = createSquarePolygon(plankWidth, plankHeight);
        plankPartBuilder.setShape(plankPolygon);
        plankPartBuilder.build();

        // Create and attach graphics object to plank
        plankGraphics = new ImageGraphics("wood.3.png", plankWidth, plankHeight);
        plankGraphics.setParent(plank);

        // Create ball
        float ballRadius = 0.5f;
        EntityBuilder ballBuilder = world.createEntityBuilder();
        ballBuilder.setFixed(false);
        ballBuilder.setPosition(new Vector(0.5f, 4.0f));
        ball = ballBuilder.build();

        // Create and attach ball fixture
        PartBuilder ballPartBuilder = ball.createPartBuilder();
        Circle ballCircle = new Circle(ballRadius);
        ballPartBuilder.setShape(ballCircle);
        ballPartBuilder.setFriction(0.8f);
        ballPartBuilder.build();

        // Create and attach graphics object to ball
        ballGraphics = new ImageGraphics("explosive.11.png", ballRadius*2.0f, ballRadius*2.0f, new Vector(0.5f, 0.5f));
        ballGraphics.setParent(ball);

        // Create revolute constraint between block and plank
        RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
        revoluteConstraintBuilder.setFirstEntity(block);
        revoluteConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, (blockHeight*7)/4));
        revoluteConstraintBuilder.setSecondEntity(plank);
        revoluteConstraintBuilder.setSecondAnchor(new Vector(plankWidth/2, plankHeight/2));
        revoluteConstraintBuilder.setInternalCollision(true);
        revoluteConstraintBuilder.build();

        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
        if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
            ball.applyAngularForce(1.0f);
        } else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
            ball.applyAngularForce(-1.0f);
        }

        // Update physics
        world.update(deltaTime);

        // Set camera position
        window.setRelativeTransform(Transform.I.scaled(10.0f));

        // Render graphics objects
        blockGraphics.draw(window);
        plankGraphics.draw(window);
        ballGraphics.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
