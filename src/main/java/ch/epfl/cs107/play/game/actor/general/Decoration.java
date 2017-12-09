package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Decoration extends GameEntity implements Actor {

    private Polyline shape;

    public Decoration(ActorGame game, Polyline polyline) {
        super(game, true, Vector.ZERO);
        shape = polyline;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(shape, getTransform(), null, Color.CYAN, 0.05f, 0.5f, 0);
    }
}
