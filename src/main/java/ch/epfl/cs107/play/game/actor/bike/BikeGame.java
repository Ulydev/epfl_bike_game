package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.*;
import ch.epfl.cs107.play.game.actor.bike.levels.InvalidLevelException;
import ch.epfl.cs107.play.game.actor.bike.levels.InvalidLevelIndexException;
import ch.epfl.cs107.play.game.actor.bike.levels.XmlBikeLevel;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Window;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

enum GameState {
    PLAYING,
    WIN,
    LOSE
}

public class BikeGame extends ActorGame {

    private GameState state;

    private List<Level> levels;
    private int currentLevel;
    private float nextLevelTimer;

    private Bike bike;
    private Finish finish;

    private float textAnimation = 0;
    private TextGraphics text;
    private TextGraphics subText;

    protected List<Level> createLevelList() {
        return Arrays.asList(
                new XmlBikeLevel(getFileSystem(), "levels/tutorial.tmx"),
                new XmlBikeLevel(getFileSystem(), "levels/pillar.tmx"),
                new XmlBikeLevel(getFileSystem(), "levels/pendulums.tmx"),
                new XmlBikeLevel(getFileSystem(), "levels/slippy.tmx"),
                new XmlBikeLevel(getFileSystem(), "levels/fill.tmx"),
                new XmlBikeLevel(getFileSystem(), "levels/trampoline.tmx")
        );
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        levels = createLevelList();
        createText();
        startLevel(0);

        return true;
    }

    /**
     * Reset variables at the beginning of each level
     */
    private void init() {
        textAnimation = 0;
        nextLevelTimer = 0;
        bike = null;
        finish = null;
    }

    /**
     * @param level : the index of the level to start
     */
    private void startLevel(int level) {
        try {
            if (level < 0 || level >= levels.size())
                throw new InvalidLevelIndexException(level);

            end();
            init();

            currentLevel = level;
            List<Actor> actors = levels.get(currentLevel).createAllActors(this);
            for (Actor actor : actors) {
                if (actor instanceof Bike) {
                    bike = (Bike) actor;
                    setViewCandidate(bike);
                }
                if (actor instanceof Finish)
                    finish = (Finish) actor;
            }
            if (bike == null || finish == null)
                throw new InvalidLevelException();
            addActors(actors);

            state = GameState.PLAYING;
        } catch (InvalidLevelIndexException | InvalidLevelException error) {
            System.out.println("Could not load level: " + error.getMessage());
        }
    }

    private void nextLevel() {
        startLevel(currentLevel + 1);
    }

    private void resetLevel() {
        startLevel(currentLevel);
    }

    @Override
    public void update(float deltaTime) {
        if (state != GameState.WIN && getKeyboard().get(KeyEvent.VK_R).isPressed()) {
            resetLevel();
            return;
        }

        super.update(deltaTime);

        if (state == GameState.PLAYING) {
            if (bike.isDead()) {
                setViewShake(getViewShake() + 2.0f);
                setViewCandidate(null);
                lose();
            } else if (finish.isTouching(bike)) {
                bike.getDriver().cheer();
                bike.setStopped(true);
                setViewCandidate(null);
                win();
            }
        }

        if (state == GameState.WIN || state == GameState.LOSE) {
            updateText(deltaTime);
            drawText(getCanvas());
        }

        if (state == GameState.WIN) {
            nextLevelTimer -= deltaTime;
            if (nextLevelTimer <= 0)
                nextLevel();
        }

        if (!bike.isStopped() && !bike.isDead())
            setViewShake(bike.getVelocity().getLength() * 0.0035f);
    }

    /**
     * Initialize text graphics
     */
    private void createText() {
        text = new TextGraphics("", 1f, Color.WHITE, Color.BLACK, 0.02f, false, false, new Vector(0.5f, 0.5f), 1f, 10.0f);
        subText = new TextGraphics("", 1f, Color.LIGHT_GRAY, Color.BLACK, 0.02f, false, false, new Vector(0.5f, 0.5f), 1f, 10.0f);
        text.draw(getCanvas());
        subText.draw(getCanvas());
    }
    public void updateText(float deltaTime) {
        textAnimation = Math.min(1, textAnimation + deltaTime * 0.9f);
    }
    public void drawText(Canvas canvas) {
        text.setAlpha(textAnimation);
        text.setFontSize(1f + textAnimation * 0.8f);

        Transform transform = Transform.I.translated(canvas.getPosition());
        text.setRelativeTransform(
                transform.translated(0, (float)(-(1 + Math.cos(textAnimation * Math.PI))))
        );
        text.draw(canvas);

        subText.setAlpha(Math.max(0, (textAnimation - 0.5f) * 2));
        subText.setFontSize(1f + textAnimation * 0.2f);
        subText.setRelativeTransform(
                transform.translated(0, -1.8f + (float)(-(1 + Math.cos(textAnimation * Math.PI))) * 0.5f)
        );
        subText.draw(canvas);
    }

    /**
     * State management
     */
    public void win() {
        state = GameState.WIN;
        if (currentLevel == levels.size()-1) {
            text.setText("GAME-COMPLETE");
            subText.setText("THANKS-FOR-PLAYiNG");
            nextLevelTimer = Float.POSITIVE_INFINITY;
        } else {
            text.setText("LEVEL-COMPLETE");
            subText.setText("LOADiNG-NEXT-LEVEL");
            nextLevelTimer = 5;
        }
    }
    public void lose() {
        state = GameState.LOSE;
        text.setText("GAME-OVER");
        subText.setText("PRESS-R-TO-RESTART");
    }

}
