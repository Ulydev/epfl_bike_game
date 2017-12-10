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

    private List<Actor> actors = new ArrayList<>();
    private List<Actor> addPool = new ArrayList<>();
    private List<Actor> removePool = new ArrayList<>();

    private World world;

    private Window window;
    private FileSystem fileSystem;

    private Vector viewCenter;
    private Vector viewTarget;
    private Positionable viewCandidate;
    private float viewShake = 0;
    private float viewScale = 1;
    private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.6f;
    private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.05f;
    private static final float BASE_VIEW_SCALE = 14.0f;

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
    public void update(float deltaTime) {
        updatePools();

        world.update(deltaTime);

        for (Actor actor : actors)
            actor.update(deltaTime);

        updateCamera(deltaTime);

        for (Actor actor : actors)
            actor.draw(window);
    }

    /**
     * Processes addPool and removePool, to avoid modifying the actors list while iterating through it
     */
    private void updatePools() {
        for (Actor actor : addPool)
            actors.add(actor);
        addPool.clear();
        for (Actor actor : removePool)
            actors.remove(actor);
        removePool.clear();
    }

    /**
     * Updates the shake and position of the camera according to its viewShake and viewCandidate
     * @param deltaTime
     */
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
    public void end() {
        for (Actor actor : actors) {
            actor.destroy();
        }
    }

    /**
     * Sets the camera target to follow
     * @param viewCandidate : the Positionable instance to follow
     */
    public void setViewCandidate(Positionable viewCandidate) {
        this.viewCandidate = viewCandidate;
    }
    public float getViewShake() {
        return viewShake;
    }
    public void setViewShake(float shake) {
        viewShake = shake;
    }

    /**
     * Creates a basic entity
     * @param fixed : whether the entity is dynamic or not
     * @param position : the initial position of the entity
     * @return the built entity
     */
    public Entity createEntity(boolean fixed, Vector position) {
        EntityBuilder entityBuilder = world.createEntityBuilder();
        entityBuilder.setFixed(fixed);
        entityBuilder.setPosition(position);
        return entityBuilder.build();
    }

    /**
     * Creates a ConstraintBuilder instance
     * @param type : the literal name of the ConstraintBuilder type
     * @return the corresponding ConstraintBuilder instance, or null otherwise
     */
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

    /**
     * Adds an actor to addPool (to be added later)
     * @param actor
     */
    public void addActor(Actor actor) {
        addPool.add(actor);
    }

    /**
     * Adds multiple actors to addPool
     * @param actors : a list of actors
     */
    public void addActors(List<Actor> actors) {
        addPool.addAll(actors);
    }

    /**
     * Adds an actor to removePool (to be removed later)
     * @param actor
     */
    public void removeActor(Actor actor) {
        removePool.add(actor);
    }

    public Canvas getCanvas() {
        return window;
    }
    protected FileSystem getFileSystem() {
        return fileSystem;
    }
    public Keyboard getKeyboard() {
        return window.getKeyboard();
    }

}
