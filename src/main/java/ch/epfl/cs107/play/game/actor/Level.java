package ch.epfl.cs107.play.game.actor;

import java.util.List;

public abstract class Level {

    public abstract List<Actor> createAllActors(ActorGame game);

}
