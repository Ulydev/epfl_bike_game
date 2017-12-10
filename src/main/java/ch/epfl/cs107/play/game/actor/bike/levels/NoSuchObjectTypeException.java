package ch.epfl.cs107.play.game.actor.bike.levels;

public class NoSuchObjectTypeException extends Exception {

    private String type;

    public NoSuchObjectTypeException(String type) {
        super("No such object type");
        this.type = type;
    }

    public String getObjectType() {
        return type;
    }

}
