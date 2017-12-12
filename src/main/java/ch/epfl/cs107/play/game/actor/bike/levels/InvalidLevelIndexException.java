package ch.epfl.cs107.play.game.actor.bike.levels;

public class InvalidLevelIndexException extends RuntimeException {

    private int index;

    public InvalidLevelIndexException(int index) {
        super("Invalid level index: " + index);
        this.index = index;
    }

    public int getLevelIndex() {
        return index;
    }
}
