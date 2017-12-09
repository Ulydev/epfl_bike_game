package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Text extends GameEntity implements Actor {

    private String text;

    public Text(ActorGame game, Vector position, String text) {
        super(game, true, position);
        this.text = text;
    }

    public void draw(Canvas canvas) {
        canvas.drawText(text, 1, getTransform(), Color.GREEN, null, 0, false, false, Vector.ZERO, 0.5f, 0);
    }

}
