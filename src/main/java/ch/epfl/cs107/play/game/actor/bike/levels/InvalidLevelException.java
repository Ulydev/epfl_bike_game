package ch.epfl.cs107.play.game.actor.bike.levels;

public class InvalidLevelException extends RuntimeException {
    public InvalidLevelException() {
        super("Invalid level");
    }
}
