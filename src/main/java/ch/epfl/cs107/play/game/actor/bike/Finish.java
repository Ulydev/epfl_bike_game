package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Finish extends Trigger {

    /**
     * Creates a Trigger representing a Finish line
     * @param game
     * @param position
     */
    public Finish(ActorGame game, Vector position) {
        super(game, position, 1.5f);
        getEntity().setPosition(getPosition().add(0, 1.0f));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(new Polyline(
                0, 0,
                0, 1.6f,
                1f, 1.6f,
                1f, 1.1f,
                0, 1.1f
        ), getTransform().translated(-0.5f, -1), null, Color.WHITE, 0.1f, 1.0f, 0);
    }

}
