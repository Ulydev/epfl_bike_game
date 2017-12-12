package ch.epfl.cs107.play.game.actor.bike.levels;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.io.FileSystem;

import java.util.List;

public class XmlBikeLevel extends Level {

    private XmlBikeLevelLoader levelLoader;

    /**
     * Creates a new Level loaded by an XmlBikeLevelLoader instance
     * @param fileSystem : the FileSystem instance used to read the level file
     * @param filePath : the path of the level file
     */
    public XmlBikeLevel(FileSystem fileSystem, String filePath) {
        levelLoader = new XmlBikeLevelLoader(fileSystem, filePath);
    }

    @Override
    public List<Actor> createAllActors(ActorGame game) {
        return levelLoader.loadActors(game);
    }

}
