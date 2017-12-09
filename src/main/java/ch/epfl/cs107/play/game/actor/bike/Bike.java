package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Bike extends GameEntity implements Actor {

    private float pedalAngle = 0;
    private float scaleX;
    private int direction;
    private Wheel leftWheel;
    private Wheel rightWheel;

    private Driver driver;
    private float driverAlpha = 1;

    private boolean dead = false;
    private boolean stopped = false;

    private static float MAX_WHEEL_SPEED = 30.0f;
    private static float LEAN_SPEED = 20.0f;
    private static float WHEEL_OFFSET = 0.9f;

    public Bike(ActorGame game, Vector position, int direction) {
        super(game, position);

        this.direction = direction;
        scaleX = direction;

        PartBuilder partBuilder = getEntity().createPartBuilder();
        Polygon polygon = new Polygon(
                0.0f, 1.0f,
                0.3f, 1.2f,
                0.0f, 2.0f,
                -0.3f, 1.2f
        );
        partBuilder.setShape(polygon);
        partBuilder.setGhost(true);
        partBuilder.build();

        leftWheel = new Wheel(game, getPosition().add(new Vector(-WHEEL_OFFSET, 0)), 0.5f);
        rightWheel = new Wheel(game, getPosition().add(new Vector(WHEEL_OFFSET, 0)), 0.5f);

        leftWheel.attach(getEntity(), new Vector(-WHEEL_OFFSET, 0), new Vector(-WHEEL_OFFSET, -1.0f));
        rightWheel.attach(getEntity(), new Vector(WHEEL_OFFSET, 0), new Vector(WHEEL_OFFSET, -1.0f));

        ContactListener contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Part other = contact.getOther();
                if (other.isGhost())
                    return;
                if (leftWheel.isEntity(other.getEntity()) || rightWheel.isEntity(other.getEntity()))
                    return;
                if (!isStopped())
                    setDead(true);
            }
            @Override
            public void endContact(Contact contact) {}
        };
        getEntity().addContactListener(contactListener);

        driver = new Driver(game, this);
    }

    /**
     * Normalizes the provided angle between -PI and PI
     * @param angle : the angle to normalize
     * @return the normalized angle
     */
    private float normalizeAngle(float angle) {
        float PI = (float)Math.PI;
        angle =  angle % (PI * 2);
        angle = (angle + (PI * 2)) % (PI * 2);
        if (angle > PI)
            angle -= (PI * 2);
        return angle;
    }

    @Override
    public void update(float deltaTime) {
        if (stopped) {
            lean(normalizeAngle(getEntity().getAngularPosition()));
            brake();
        } else if (!isDead()) {
            updateMovement();
        }

        scaleX += (direction - scaleX) * 10 * deltaTime; // Lerp
        pedalAngle += getMotorWheel().getSpeed() * 0.5f * direction * deltaTime;

        if (isDead()) {
            driverAlpha = Math.max(driverAlpha - deltaTime * 5, 0);
            relax();
        }

        driver.update(deltaTime);
    }

    private void updateMovement() {
        Keyboard keyboard = getOwner().getKeyboard();

        if (keyboard.get(KeyEvent.VK_SPACE).isPressed())
            switchDirection();

        relax();

        if (keyboard.get(KeyEvent.VK_DOWN).isDown())
            brake();
        if (keyboard.get(KeyEvent.VK_UP).isDown())
            forward();

        if (keyboard.get(KeyEvent.VK_LEFT).isDown())
            lean(-1);
        else if (keyboard.get(KeyEvent.VK_RIGHT).isDown())
            lean(1);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(new Polyline(
                -WHEEL_OFFSET, 0,
                -0.5f, 0.5f,
                0.5f, 0.5f,
                WHEEL_OFFSET, 0),
                getTransform(), null, Color.WHITE, 0.1f, 1, 0
        );

        driver.draw(canvas, driverAlpha);
        leftWheel.draw(canvas);
        rightWheel.draw(canvas);
    }

    @Override
    public void destroy() {
        leftWheel.destroy();
        rightWheel.destroy();
        super.destroy();
    }

    /**
     * @return the motor wheel depending on the current direction of the bike
     */
    private Wheel getMotorWheel() {
        return (direction == 1) ? leftWheel : rightWheel;
    }

    public Driver getDriver() {
        return driver;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getPedalAngle() {
        return pedalAngle;
    }

    private void switchDirection() {
        direction *= -1;
    }

    private void relax() {
        leftWheel.relax();
        rightWheel.relax();
    }

    private void brake() {
        leftWheel.power(0);
        rightWheel.power(0);
    }

    private void forward() {
        Wheel motorWheel = getMotorWheel();

        boolean needsMotor = (
                (direction == -1 && motorWheel.getSpeed() < MAX_WHEEL_SPEED)
                        || (direction == 1 && motorWheel.getSpeed() > -MAX_WHEEL_SPEED)
        );
        if (needsMotor)
            motorWheel.power(-direction * MAX_WHEEL_SPEED);
        else
            motorWheel.relax();
    }

    private void lean(float direction) {
        getEntity().applyAngularForce(direction * -LEAN_SPEED);
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public boolean isDead() {
        return dead;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
    public boolean isStopped() {
        return stopped;
    }

}
