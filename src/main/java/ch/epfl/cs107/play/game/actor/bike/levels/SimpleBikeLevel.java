package ch.epfl.cs107.play.game.actor.bike.levels;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Crate;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

import java.util.Arrays;
import java.util.List;

public class SimpleBikeLevel extends Level {
    public List<Actor> createAllActors(ActorGame game) {
        return Arrays.asList(
                new Terrain(game, new Polyline(
                        -1000.0f, -1000.0f,
                        -1000.0f, 0.0f,
                        0.0f, 0.0f,
                        3.0f, 1.0f,
                        8.0f, 1.0f,
                        15.0f, 3.0f,
                        16.0f, 3.0f,
                        25.0f, 0.0f,
                        35.0f, -5.0f,
                        50.0f, -5.0f,
                        55.0f, -4.0f,
                        65.0f, 0.0f,
                        6500.0f, -1000.0f
                )),
                new Crate(game, new Vector(0.2f, 7.0f), 1, 1),
                new Crate(game, new Vector(2.0f, 6.0f), 1, 1),
                new Bike(game, new Vector(4.0f, 6.0f), 1),
                new Finish(game, new Vector(16.0f, 3.0f))
        );
    }
}
