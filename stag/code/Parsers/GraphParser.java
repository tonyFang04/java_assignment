package Parsers;
import DB.GameMap;
import Entities.Artefact;
import Entities.Character;
import Entities.Furniture;
import Entities.Location;
import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class GraphParser {
    private final ArrayList<Graph> subGraphs;
    public GraphParser(String filename) throws FileNotFoundException, ParseException {
        Parser parser = new Parser();
        FileReader reader = new FileReader(filename);
        parser.parse(reader);
        ArrayList<Graph> graphs = parser.getGraphs();
        subGraphs = graphs.get(0).getSubgraphs();
    }

    public GameMap buildGameMap() {
        GameMap gameState = new GameMap();
        addLocations(gameState);
        addEdges(gameState);
        return gameState;
    }

    void addLocations(GameMap gameState) {
        ArrayList<Graph> subGraphs1 = subGraphs.get(0).getSubgraphs();
        int i = 0;
        for (Graph g1 : subGraphs1){
            ArrayList<Node> nodesLoc = g1.getNodes(false);
            Node nLoc = nodesLoc.get(0);
            Location location = new Location();
            location.description = nLoc.getAttribute("description");
            selectItemType(g1,gameState,location);
            gameState.locations.put(nLoc.getId().getId(),location);
            if (i == 0) {
                gameState.startLocationName = nLoc.getId().getId();
            }
            i++;
        }
    }

    void selectItemType(Graph g, GameMap gameState, Location location) {
        ArrayList<Graph> subGraphs2 = g.getSubgraphs();
        for (Graph g2 : subGraphs2) {
            addItems(g2,gameState,location);
        }
    }

    void addItems(Graph g, GameMap gameState, Location location) {
        ArrayList<Node> nodesEnt = g.getNodes(false);
        for (Node nEnt : nodesEnt) {
            if (g.getId().getId().equals("furniture")) {
                Furniture furn = new Furniture();
                furn.description = nEnt.getAttribute("description");
                gameState.furniture.put(nEnt.getId().getId(), furn);
                location.furnitureNames.add(nEnt.getId().getId());
            }

            if (g.getId().getId().equals("characters")) {
                Character character = new Character();
                character.description = nEnt.getAttribute("description");
                gameState.characters.put(nEnt.getId().getId(), character);
                location.characterNames.add(nEnt.getId().getId());
            }

            if (g.getId().getId().equals("artefacts")) {
                Artefact artefact = new Artefact();
                artefact.description = nEnt.getAttribute("description");
                gameState.artefacts.put(nEnt.getId().getId(), artefact);
                location.artefactNames.add(nEnt.getId().getId());
            }
        }
    }

    void addEdges(GameMap gameState) {
        ArrayList<Edge> edges = subGraphs.get(1).getEdges();
        for (Edge e : edges){
            String sourceName = e.getSource().getNode().getId().getId();
            String targetName = e.getTarget().getNode().getId().getId();
            Location location = gameState.locations.get(sourceName);
            location.accessibleLocationNames.add(targetName);
        }
    }
}
