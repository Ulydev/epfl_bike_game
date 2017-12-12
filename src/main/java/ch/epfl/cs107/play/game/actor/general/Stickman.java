package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Stickman extends GameEntity implements Actor {

    private Vector headLocation;
    private Vector shoulderLocation;
    private Vector elbowLocation;
    private Vector handLocation;
    private Vector waistLocation;
    private Vector leftFootLocation;
    private Vector rightFootLocation;
    private Vector leftKneeLocation;
    private Vector rightKneeLocation;

    /**
     * Creates a GameEntity representing a stickman that can be animated
     * @param game
     */
    public Stickman(ActorGame game) {
        super(game, true, Vector.ZERO);
    }

    public void draw(Canvas canvas, Transform transform, float alpha) {

        // Draw head
        canvas.drawShape(new Circle(0.2f, headLocation), transform, Color.WHITE, null, 0, alpha, 0);

        // Draw torso
        canvas.drawShape(new Polyline(
                shoulderLocation, waistLocation
        ), transform, null, Color.WHITE, 0.1f, alpha, 0);

        // Draw arm
        canvas.drawShape(new Polyline(
                shoulderLocation, elbowLocation, handLocation
        ), transform, null, Color.WHITE, 0.1f, alpha, 0);

        // Draw legs
        canvas.drawShape(new Polyline(
                waistLocation, leftKneeLocation, leftFootLocation
        ), transform, null, Color.WHITE, 0.1f, alpha, 0);
        canvas.drawShape(new Polyline(
                waistLocation, rightKneeLocation, rightFootLocation
        ), transform, null, Color.WHITE, 0.1f, alpha, 0);

    }
    public void draw(Canvas canvas, Transform transform) {
        draw(canvas, transform, 1);
    }
    @Override
    public void draw(Canvas canvas) {
        draw(canvas, getTransform(), 1);
    }

    /**
     * Body location setters
     */
    public void setHeadLocation(Vector location) {
        headLocation = location;
    }
    public void setShoulderLocation(Vector location) {
        shoulderLocation = location;
    }
    public void setElbowLocation(Vector location) {
        elbowLocation = location;
    }
    public void setHandLocation(Vector location) {
        handLocation = location;
    }
    public void setWaistLocation(Vector location) {
        waistLocation = location;
    }
    public void setLeftFootLocation(Vector location) {
        leftFootLocation = location;
    }
    public void setRightFootLocation(Vector location) {
        rightFootLocation = location;
    }
    public void setLeftKneeLocation(Vector location) {
        leftKneeLocation = location;
    }
    public void setRightKneeLocation(Vector location) {
        rightKneeLocation = location;
    }

}
