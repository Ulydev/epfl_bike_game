package ch.epfl.cs107.play.game.actor.bike.levels;

public class NoSuchObjectTypeException extends Exception {

    private String objectType;

    public NoSuchObjectTypeException(String type) {
        super("No such object type");
        objectType = type;
    }

    public String getObjectType() {
        return objectType;
    }

}
