package ch.epfl.cs107.play.game.actor.bike.levels;

public class InvalidLevelIndexException extends Exception {
    public InvalidLevelIndexException() {
        super("Invalid level index");
    }
}
