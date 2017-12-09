package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;
import java.util.List;

public class Crate extends DynamicObject {

    public Crate(ActorGame game, Vector position, float width, float height) {
        super(game, position, new Polygon(
                0, 0,
                width, 0,
                width, height,
                0, height
        ));

        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Dimensions must be non-negative");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        List<Vector> points = shape.getPoints();
        for (int i = 0; i < 2; i++)
            canvas.drawShape(new Polyline(
                    points.get(i),
                    points.get(i + 2)
            ), getTransform(), null, Color.WHITE, 0.1f, 1f, 0);
    }

}
