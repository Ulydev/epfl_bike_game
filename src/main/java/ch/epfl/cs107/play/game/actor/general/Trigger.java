package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;
import java.util.Set;

public class Trigger extends GameEntity implements Actor {

    private float radius;
    private BasicContactListener listener;

    public Trigger(ActorGame game, Vector position, float radius) {
        super(game, true, position);

        this.radius = radius;

        PartBuilder partBuilder = getEntity().createPartBuilder();
        partBuilder.setShape(new Circle(radius));
        partBuilder.setGhost(true);
        partBuilder.build();

        listener = new BasicContactListener();
        getEntity().addContactListener(listener);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(new Circle(radius), getTransform(), null, Color.WHITE, 0.05f, 1.0f, 0);
    }

    /**
     * Checks whether the provided GameEntity is touching the Trigger
     * @param gameEntity : the gameEntity to check collision with
     * @return a boolean, indicating whether there is a collision or not
     */
    public boolean isTouching(GameEntity gameEntity) {
        for (Entity entity : listener.getEntities()) {
            if (gameEntity.isEntity(entity))
                return true;
        }
        return false;
    }

}
