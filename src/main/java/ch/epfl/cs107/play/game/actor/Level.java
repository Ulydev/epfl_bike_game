package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.game.actor.bike.levels.InvalidLevelException;

import java.util.List;

public abstract class Level {

    /**
     * Initializes the level by creating all actors in the provided game
     * @param game : the game to create all actors in
     * @return the list of actors in the level
     * @throws InvalidLevelException if the level could not be loaded
     */
    public abstract List<Actor> createAllActors(ActorGame game) throws InvalidLevelException;

}
