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

public class Terrain extends GameEntity implements Actor {

    Shape shape;

    public Terrain(ActorGame game, Vector position, Polyline polyline) {
        super(game, true, position);

        shape = polyline;

        PartBuilder partBuilder = getEntity().createPartBuilder();
        partBuilder.setShape(shape);
        partBuilder.setFriction(0.85f);
        partBuilder.build();
    }
    public Terrain(ActorGame game, Polyline polyline) {
        this(game, Vector.ZERO, polyline);
    }

    public void draw(Canvas canvas) {
        canvas.drawShape(shape, getTransform(), null, Color.LIGHT_GRAY, 0.1f, 1.0f, 0);
    }

}
