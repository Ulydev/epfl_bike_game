package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.general.Stickman;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Driver extends Stickman implements Actor {

    private Bike bike;

    private boolean isCheering = false;
    private float cheerTime = 0;
    private float cheerAnimation = 0;

    /**
     * Creates a Driver associated with a bike
     * @param game
     * @param bike : the Bike instance of the Driver
     */
    public Driver(ActorGame game, Bike bike) {
        super(game);

        if (bike == null)
            throw new NullPointerException("Bike must not be null");

        this.bike = bike;
    }

    @Override
    public void update(float deltaTime) {
        setHeadLocation(getHeadLocation());
        setShoulderLocation(getShoulderLocation());
        setElbowLocation(getElbowLocation().mixed(getCheeringElbowLocation(), cheerTime));
        setHandLocation(getHandLocation().mixed(getCheeringHandLocation(), cheerTime));
        setWaistLocation(getWaistLocation());
        setLeftFootLocation(getLeftFootLocation());
        setRightFootLocation(getRightFootLocation());
        setLeftKneeLocation(getLeftKneeLocation());
        setRightKneeLocation(getRightKneeLocation());

        if (isCheering) {
            cheerTime = Math.min(cheerTime + deltaTime * 3, 1);
            cheerAnimation = (cheerAnimation + deltaTime) % 1;
        }
    }

    /**
     * Overload for #draw(Canvas), adding a transparency parameter
     * @param canvas
     * @param alpha
     */
    public void draw(Canvas canvas, float alpha) {
        super.draw(canvas, bike.getScaledTransform(), alpha);
    }
    @Override
    public void draw(Canvas canvas) {
        draw(canvas, 1);
    }

    /**
     * Triggers the cheering animation
     */
    public void cheer() {
        isCheering = true;
        cheerTime = 0;
    }

    /**
     * Body location getters
     */
    private Vector getHeadLocation() {
        return new Vector(0.0f, 1.75f);
    }
    private Vector getShoulderLocation() {
        return getHeadLocation().add(new Vector(-0.15f, -0.15f));
    }
    private Vector getElbowLocation() {
        return new Vector(0.1f, 1.2f);
    }
    private Vector getHandLocation() {
        return new Vector(0.5f, 1.0f);
    }
    private Vector getCheeringElbowLocation() {
        return getShoulderLocation().add(getElbowLocation().sub(getShoulderLocation()).rotated(0.8 + Math.cos(cheerAnimation * Math.PI * 2) * 0.4));
    }
    private Vector getCheeringHandLocation() {
        return getCheeringElbowLocation().add(Vector.X.mul(getElbowLocation().sub(getHandLocation()).getLength()).rotated(1 + Math.sin(cheerAnimation * Math.PI) * 0.2));
    }
    private Vector getWaistLocation() {
        return new Vector(-0.5f, 0.9f);
    }
    private Vector getFootLocation(float angle) {
        return new Vector(0, 0.2f).add(new Vector((float)Math.cos(angle) * 0.2f, (float)Math.sin(angle) * 0.2f));
    }
    private Vector getLeftFootLocation() {
        return getFootLocation(bike.getPedalAngle() + (float)Math.PI);
    }
    private Vector getRightFootLocation() {
        return getFootLocation(bike.getPedalAngle());
    }
    private Vector getKneeLocation(float angle) {
        return new Vector(0.1f, 0.6f).add(new Vector((float)Math.cos(angle) * bike.getPedalRadius(), (float)Math.sin(angle) * bike.getPedalRadius()));
    }
    private Vector getLeftKneeLocation() {
        return getKneeLocation(bike.getPedalAngle() + (float)Math.PI);
    }
    private Vector getRightKneeLocation() {
        return getKneeLocation(bike.getPedalAngle());
    }

}
