package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class AnchoredObject extends DynamicObject {

    private RevoluteConstraint constraint;
    private float constraintRadius;

    private Entity anchorEntity;

    /**
     * Creates a DynamicObject with a RevoluteConstraint around the specified anchors
     * @param game
     * @param position
     * @param polygon
     * @param anchor1
     * @param anchor2
     */
    public AnchoredObject(ActorGame game, Vector position, Polygon polygon, Vector anchor1, Vector anchor2) {
        super(game, position, polygon);

        if (anchor1 == null || anchor2 == null)
            throw new NullPointerException("Anchors must not be null");

        anchorEntity = getOwner().createEntity(true, Vector.ZERO);

        RevoluteConstraintBuilder revoluteConstraintBuilder =
                (RevoluteConstraintBuilder) (getOwner().createConstraintBuilder("RevoluteConstraintBuilder"));

        revoluteConstraintBuilder.setFirstEntity(anchorEntity);
        revoluteConstraintBuilder.setFirstAnchor(anchor1);

        revoluteConstraintBuilder.setSecondEntity(getEntity());
        revoluteConstraintBuilder.setSecondAnchor(anchor1);

        revoluteConstraintBuilder.setInternalCollision(true);

        constraint = revoluteConstraintBuilder.build();

        constraintRadius = anchor1.sub(anchor2).getLength();
    }
    public AnchoredObject(ActorGame game, Polygon polygon, Vector anchor1, Vector anchor2) {
        this(game, Vector.ZERO, polygon, anchor1, anchor2);
    }
    public AnchoredObject(ActorGame game, Polygon polygon, Vector anchor) {
        this(game, polygon, anchor, Vector.ZERO);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Vector firstPosition = constraint.getFirstAnchor();
        float angle = -getTransform().getAngle();
        Vector secondPosition = Vector.Y.opposite().rotated(angle).mul(constraintRadius).add(firstPosition);

        canvas.drawShape(new Polyline(firstPosition, secondPosition), Transform.I, null, Color.GRAY, 0.1f, 1, 0);
        for (int i = 0; i < 2; i++)
            canvas.drawShape(new Circle(0.1f),
                    Transform.I.translated(i == 0 ? firstPosition : secondPosition),
                    null, Color.GRAY, 0.1f, 1, 0
            );
    }

    @Override
    public void destroy() {
        anchorEntity.destroy();
        super.destroy();
    }

}
