package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Text extends GameEntity implements Actor {

    private String text;

    /**
     * Creates a new Text that can be placed in the game
     * @param game
     * @param position
     * @param text : the string content
     */
    public Text(ActorGame game, Vector position, String text) {
        super(game, true, position);

        if (text == null)
            throw new NullPointerException("Text must not be null");

        this.text = text;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, 1, getTransform(), Color.GREEN, null, 0, false, false, Vector.ZERO, 0.5f, 0);
    }

}
