package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.*;

public abstract class ActorGame implements Game {

    // List of game actors
    private List<Actor> actors = new ArrayList<>();
    private List<Actor> addPool = new ArrayList<>();
    private List<Actor> removePool = new ArrayList<>();

    // Physics world
    private World world;

    private Window window;
    private FileSystem fileSystem;

    // Viewport properties
    private Vector viewCenter;
    private Vector viewTarget;
    private Positionable viewCandidate;
    private float viewShake = 0;
    private float viewScale = 1;
    private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.6f;
    private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.05f;
    private static final float BASE_VIEW_SCALE = 14.0f;

    public Keyboard getKeyboard() {
        return window.getKeyboard();
    }

    public Canvas getCanvas() {
        return window;
    }

    // Main actor
    private Actor payload;

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        // Initialize window and file system
        this.window = window;
        this.fileSystem = fileSystem;

        // Create physics world
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));

        // Initialize camera position
        Vector initialPosition = Vector.ZERO;
        viewCenter = initialPosition;
        viewTarget = initialPosition;

        return true;
    }

    @Override
    public void end() {
        for (Actor actor : actors) {
            actor.destroy();
        }
    }

    private void updateCamera(float deltaTime) {
        viewShake *= 0.9;
        float
                shakeX = (float)((Math.random()-0.5) * 2 * viewShake),
                shakeY = (float)((Math.random()-0.5) * 2 * viewShake),
                shakeA = (float)((Math.random()-0.5) * 0.5 * viewShake);

        float targetScale = 1;
        if (viewCandidate != null) {
            viewTarget = viewCandidate
                    .getPosition()
                    .add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
            targetScale = viewCandidate.getVelocity().getLength() * VIEW_TARGET_VELOCITY_COMPENSATION;
        }
        float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
        viewCenter = viewCenter.mixed(viewTarget, 1.0f - ratio);
        viewScale = viewScale * ratio + targetScale * (1 - ratio);
        Transform viewTransform = Transform.I
                .scaled(BASE_VIEW_SCALE + viewScale)
                .translated(shakeX, shakeY)
                .rotated(shakeA)
                .translated(viewCenter);
        window.setRelativeTransform(viewTransform);
    }

    @Override
    public void update(float deltaTime) {
        // Process pools
        for (Actor actor : addPool)
            actors.add(actor);
        addPool.clear();
        for (Actor actor : removePool)
            actors.remove(actor);
        removePool.clear();

        // Update physics
        world.update(deltaTime);

        // Update all actors
        for (Actor actor : actors)
            actor.update(deltaTime);

        // Update camera position
        updateCamera(deltaTime);

        // Draw all actors
        for (Actor actor : actors)
            actor.draw(window);
    }

    public void setViewCandidate(Positionable viewCandidate) {
        this.viewCandidate = viewCandidate;
    }
    public float getViewShake() {
        return viewShake;
    }
    public void setViewShake(float shake) {
        viewShake = shake;
    }

    public Entity createEntity(boolean fixed, Vector position) {
        EntityBuilder entityBuilder = world.createEntityBuilder();
        entityBuilder.setFixed(fixed);
        entityBuilder.setPosition(position);
        return entityBuilder.build();
    }

    public ConstraintBuilder createConstraintBuilder(String type) {
        switch(type) {
            case "WheelConstraintBuilder":
                return world.createWheelConstraintBuilder();
            case "RevoluteConstraintBuilder":
                return world.createRevoluteConstraintBuilder();
            case "PointConstraintBuilder":
                return world.createPointConstraintBuilder();
            default:
                break;
        }
        return null;
    }

    public void addActor(Actor actor) {
        addPool.add(actor);
    }
    public void addActors(List<Actor> actors) {
        addPool.addAll(actors);
    }
    public void removeActor(Actor actor) {
        removePool.add(actor);
    }

    protected FileSystem getFileSystem() {
        return fileSystem;
    }

    public Actor getPayload() {
        return payload;
    }
    public void setPayload(Actor actor) {
        payload = actor;
    }

}
