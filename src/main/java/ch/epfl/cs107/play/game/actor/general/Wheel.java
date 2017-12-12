package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Wheel extends GameEntity implements Actor {

    private float radius;
    private WheelConstraint constraint;

    /**
     * Creates a Wheel that can be attached to an Entity
     * @param game
     * @param position
     * @param radius : the radius of the Wheel. Requirement: must be > 0
     */
    public Wheel(ActorGame game, Vector position, float radius) {
        super(game, position);

        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive");

        this.radius = radius;

        PartBuilder partBuilder = getEntity().createPartBuilder();
        partBuilder.setShape(new Circle(radius));
        partBuilder.setFriction(0.95f);
        partBuilder.build();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(new Circle(radius), getTransform(), null, Color.WHITE, 0.1f, 1.0f, 0);

        int lines = 6;
        for (int i = 0; i < lines; i++)
            canvas.drawShape(
                    new Polyline(0, 0, 0, radius),
                    getTransform().rotated((float)(Math.PI * 2 * i / lines), getPosition()),
                    null, Color.WHITE, 0.05f, 1.0f, 0
            );
    }

    @Override
    public void destroy() {
        detach();
        super.destroy();
    }

    /**
     * Attach the Wheel instance to another entity, using a WheelConstraint
     * @param vehicle : the entity to attach the Wheel instance to
     * @param anchor : the anchor of the vehicle
     * @param axis : the axis of the constraint
     */
    public void attach(Entity vehicle, Vector anchor, Vector axis) {
        WheelConstraintBuilder wheelConstraintBuilder =
                (WheelConstraintBuilder)(getOwner().createConstraintBuilder("WheelConstraintBuilder"));
        wheelConstraintBuilder.setFirstEntity(vehicle);
        wheelConstraintBuilder.setFirstAnchor(anchor);
        wheelConstraintBuilder.setSecondEntity(getEntity());
        wheelConstraintBuilder.setSecondAnchor(Vector.ZERO);
        wheelConstraintBuilder.setAxis(axis);
        wheelConstraintBuilder.setFrequency(6.0f);
        wheelConstraintBuilder.setDamping(0.5f);
        wheelConstraintBuilder.setMotorMaxTorque(10.0f);
        wheelConstraintBuilder.setMotorEnabled(false);
        constraint = wheelConstraintBuilder.build();
    }

    /**
     * Destroys the WheelConstraint, if there is any
     */
    public void detach() {
        if (constraint != null)
            constraint.destroy();
        constraint = null;
    }

    /**
     * Sets the constraint speed
     * @param speed
     */
    public void power(float speed) {
        if (constraint != null) {
            constraint.setMotorEnabled(true);
            constraint.setMotorSpeed(speed);
        }
    }

    /**
     * Disables the motor
     */
    public void relax() {
        if (constraint != null)
            constraint.setMotorEnabled(false);
    }

    /**
     * Computes the relative angular velocity of the Wheel instance, according to whether it's attached or not
     * @return the relative angular velocity of the Wheel instance
     */
    public float getSpeed() {
        if (constraint != null)
            return getEntity().getAngularVelocity() - constraint.getSecondBody().getAngularVelocity();
        else
            return getEntity().getAngularVelocity();
    }

}
