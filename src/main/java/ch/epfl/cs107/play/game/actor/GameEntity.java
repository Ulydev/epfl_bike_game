package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity {

    private Entity entity;
    private ActorGame game;

    public GameEntity(ActorGame game, boolean fixed, Vector position) {
        if (game == null || position == null)
            throw new NullPointerException();

        this.game = game;

        // Create entity
        entity = game.createEntity(fixed, position);
    }
    public GameEntity(ActorGame game, Vector position) {
        this(game, false, position);
    }

    public void destroy() {
        entity.destroy();
        game.removeActor((Actor)this);
    }

    protected Entity getEntity() {
        return entity;
    }
    protected ActorGame getOwner() {
        return game;
    }

    public boolean isEntity(Entity entity) {
        return getEntity() == entity;
    }

    public Transform getTransform() {
        return getEntity().getTransform();
    }
    public Vector getVelocity() {
        return getEntity().getVelocity();
    }
}
