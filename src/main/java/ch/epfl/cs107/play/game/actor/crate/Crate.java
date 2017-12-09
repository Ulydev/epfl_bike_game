package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate extends GameEntity implements Actor {

    private ImageGraphics image;

    public Crate(ActorGame game, boolean fixed, Vector position, String imageName, float width, float height) {
        super(game, fixed, position);

        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException();

        image = new ImageGraphics(imageName, width, height);
        image.setParent(getEntity());

        PartBuilder partBuilder = getEntity().createPartBuilder();
        Polygon polygon = new Polygon(
                new Vector(0.0f, 0.0f),
                new Vector(width, 0.0f),
                new Vector(width, height),
                new Vector(0.0f, height)
        );
        partBuilder.setShape(polygon);
        partBuilder.build();
    }

    public void update(float deltaTime) {

    }

    public void draw(Canvas canvas) {
        image.draw(canvas);
    }

}
