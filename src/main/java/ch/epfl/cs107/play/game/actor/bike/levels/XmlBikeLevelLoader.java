package ch.epfl.cs107.play.game.actor.bike.levels;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.*;
import ch.epfl.cs107.play.game.actor.general.Text;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlBikeLevelLoader {

    private FileSystem fileSystem;
    private String filePath;

    /**
     * Creates a new XmlBikeLevelLoader, which can later be loaded with loadActors(ActorGame)
     * @param fileSystem : the FileSystem instance to use
     * @param filePath : the path of the file to load
     */
    public XmlBikeLevelLoader(FileSystem fileSystem, String filePath) {
        this.fileSystem = fileSystem;
        this.filePath = filePath;
    }

    /**
     * Load and parse the provided XML file (given in the constructor)
     * @return the normalized Document instance of the file
     */
    private Document loadFile() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        InputStream file;
        try {
            documentBuilder = dbFactory.newDocumentBuilder();
            file = fileSystem.read(filePath);
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            return document;
        } catch (SAXException | ParserConfigurationException | IOException error) {
            error.printStackTrace();
            return null;
        }
    }

    /**
     * Parse and load all actors from the XML/Tiled document
     * @param game : the ActorGame instance to put actors in
     * @return all loaded actors in a list
     * @throws InvalidLevelException
     * @see #loadFile()
     * @see #loadActorFromNode(ActorGame, Node)
     */
    public List<Actor> loadActors(ActorGame game) {
        Document document = loadFile();
        if (document == null)
            throw new InvalidLevelException();

        NodeList nodeList = document.getElementsByTagName("object");

        List<Actor> actors = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Actor actor = loadActorFromNode(game, nodeList.item(i));
            actors.add(actor);
        }
        return actors;
    }

    /**
     * Loads an actor by parsing the specified node
     * @param game : the instance of the ActorGame to create the actor in
     * @param node : the current Node instance to parse
     * @return the loaded Actor
     * @throws EmptyObjectTypeException
     * @throws NoSuchObjectTypeException
     */
    private Actor loadActorFromNode(ActorGame game, Node node) {
        NamedNodeMap attributes = node.getAttributes();
        Node typeNode = attributes.getNamedItem("type");
        if (typeNode == null)
            throw new EmptyObjectTypeException();
        String type = typeNode.getNodeValue();

        float
                x = Float.parseFloat(getItem(attributes, "x")),
                y = Float.parseFloat(getItem(attributes, "y")) * -1;

        Vector position = new Vector(x, y);

        switch(type) {
            case "Crate":
            case "Bike":
            case "Finish":
                float
                        width = Float.parseFloat(getItem(attributes, "width")),
                        height = Float.parseFloat(getItem(attributes, "height"));
                switch(type) {
                    case "Crate":
                        return new Crate(game, position, width, height);
                    case "Bike":
                        return new Bike(game, position.add(width / 2, height / 2), 1);
                    case "Finish":
                        return new Finish(game, position.add(width, -height));
                }
            case "Terrain":
            case "SlippyTerrain":
            case "Trampoline":
            case "Decoration":
                Polyline polyline = new Polyline(getPoints(getItem(
                        node.getChildNodes().item(1).getAttributes(), "points"
                ), position));
                switch(type) {
                    case "Terrain":
                        return new Terrain(game, polyline);
                    case "SlippyTerrain":
                        return new SlippyTerrain(game, polyline);
                    case "Trampoline":
                        return new Trampoline(game, polyline);
                    case "Decoration":
                        return new Decoration(game, polyline);
                }
            case "DynamicObject":
                return new DynamicObject(game, new Polygon(getPoints(getItem(
                        node.getChildNodes().item(1).getAttributes(), "points"
                ), position)));
            case "Text":
                return new Text(game, position.add(0, -1), node.getChildNodes().item(1).getTextContent());
            case "AnchoredObject":
                Node propertiesNode = node.getChildNodes().item(1);
                Vector anchor1 = Vector.ZERO, anchor2 = Vector.ZERO;
                for (int i = 0; i < 4; i++) {
                    float value = Float.parseFloat(
                            propertiesNode.getChildNodes().item(2*i + 1).getAttributes()
                                    .getNamedItem("value").getNodeValue()
                    );
                    switch(i) {
                        case 0:
                            anchor1 = anchor1.add(value, 0);
                            break;
                        case 1:
                            anchor1 = anchor1.add(0, -value);
                            break;
                        case 2:
                            anchor2 = anchor2.add(value, 0);
                            break;
                        case 3:
                            anchor2 = anchor2.add(0, -value);
                            break;
                    }
                }
                return new AnchoredObject(game, new Polygon(getPoints(getItem(
                        node.getChildNodes().item(3).getAttributes(), "points"
                ), position)), anchor1, anchor2);
            default:
                throw new NoSuchObjectTypeException(type);
        }
    }

    /**
     * Retrieves the value of a named item from the specified map
     * @param map : the NamedNodeMap instance to get the item from
     * @param itemName
     * @return the value of the item
     */
    private String getItem(NamedNodeMap map, String itemName) {
        Node node = map.getNamedItem(itemName);
        if (node == null)
            throw new InvalidLevelException();
        return node.getNodeValue();
    }

    /**
     * Unserializes a string containing points into an ArrayList
     * @param serializedPoints : the serialized points in the form "x,x y,y z,z..."
     * @param origin : the offset to apply to the points
     * @return the list of points
     */
    private List<Vector> getPoints(String serializedPoints, Vector origin) {
        List<Vector> points = new ArrayList<>();
        for (String serializedPoint : serializedPoints.split(" ")) {
            String[] coords = serializedPoint.split(",");
            Vector point = new Vector(Float.parseFloat(coords[0]), -Float.parseFloat(coords[1]));
            points.add(point.add(origin));
        }
        return points;
    }

}
