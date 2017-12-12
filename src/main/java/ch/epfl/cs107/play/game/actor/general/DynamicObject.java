package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class DynamicObject extends GameEntity implements Actor {

    protected Polygon shape;

    /**
     * Creates a DynamicObject with the specified shape
     * @param game
     * @param position
     * @param polygon : the shape of the DynamicObject
     */
    public DynamicObject(ActorGame game, Vector position, Polygon polygon) {
        super(game, false, position);

        if (polygon == null)
            throw new NullPointerException("Polygon must not be null");

        shape = polygon;

        PartBuilder partBuilder = getEntity().createPartBuilder();
        partBuilder.setShape(shape);
        partBuilder.setFriction(0.8f);
        partBuilder.build();
    }
    public DynamicObject(ActorGame game, Polygon polygon) {
        this(game, Vector.ZERO, polygon);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(shape, getTransform(), null, Color.WHITE, 0.1f, 1.0f, 0);
    }

}
