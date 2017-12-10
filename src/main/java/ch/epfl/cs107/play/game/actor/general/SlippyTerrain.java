package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class SlippyTerrain extends Terrain {

    public SlippyTerrain(ActorGame game, Vector position, Polyline polyline) {
        super(game, position, polyline);

        for (Part part : getEntity().getParts())
            part.setFriction(0.1f);
    }
    public SlippyTerrain(ActorGame game, Polyline polyline) {
        this(game, Vector.ZERO, polyline);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(shape, getTransform(), null, Color.CYAN.brighter(), 0.1f, 1.0f, 0);
    }

}
