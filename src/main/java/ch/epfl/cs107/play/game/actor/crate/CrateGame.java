package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame {

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);

        addActor(new Crate(this, false, new Vector(0.0f, 5.0f), "box.4.png", 1, 1));
        addActor(new Crate(this, false, new Vector(0.2f, 7.0f), "box.4.png", 1, 1));
        addActor(new Crate(this, false, new Vector(2.0f, 6.0f), "box.4.png", 1, 1));
        addActor(new Crate(this, true, new Vector(1.0f, 1.0f), "box.4.png", 1, 1));

        return true;
    }

}
