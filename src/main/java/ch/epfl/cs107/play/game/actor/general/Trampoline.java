package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Trampoline extends Terrain {

    public Trampoline(ActorGame game, Vector position, Polyline polyline) {
        super(game, position, polyline);
        getEntity().getParts().get(0).setRestitution(1.6f);
    }
    public Trampoline(ActorGame game, Polyline polyline) {
        this(game, Vector.ZERO, polyline);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(shape, getTransform(), null, Color.PINK.darker(), 0.1f, 1.0f, 0);
    }

}
