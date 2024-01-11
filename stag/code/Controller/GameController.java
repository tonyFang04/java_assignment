package Controller;

import DB.*;
import Entities.Location;
import Entities.Player;
import Models.Action;
import Parsers.*;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class GameController {
    public GameMap gameState;
    public ActionSet actionSet;
    public PlayerSet playerSet;
    public GameController(String entityFilename, String actionFilename)
            throws IOException, ParseException, com.alexmerz.graphviz.ParseException {
        GraphParser parseDot = new GraphParser(entityFilename);
        gameState = parseDot.buildGameMap();
        ActionParser parseJSON = new ActionParser(actionFilename);
        actionSet = new ActionSet();
        actionSet.actions = parseJSON.buildActionSet();
        playerSet = new PlayerSet();
    }

    public String handleIncomingCommands(String fromCommandLine) {
        Tokenizer tokenizer = new Tokenizer(fromCommandLine);

        String playerName = tokenizer.getName();
        String command = tokenizer.getCommand();

        String startLocationName = gameState.startLocationName;
        Location startLocation = gameState.locations.get(startLocationName);

        Player currentPlayer = playerSet.queryPlayer(playerName,startLocationName,startLocation);

        if (command.contains("inv")) {
            return currentPlayer.listArtefacts();
        }

        if (command.contains("get")) {
            return gameState.getArtefact(currentPlayer,command);
        }

        if (command.contains("drop")) {
            return gameState.dropArtefact(currentPlayer,command);
        }

        if (command.contains("goto")) {
            return gameState.gotoLocation(currentPlayer,playerName,command);
        }

        if (command.contains("look")) {
            return gameState.look(currentPlayer.currentLocationName);
        }

        Action currentAction = actionSet.queryAction(command);
        if (currentAction == null) {
            return "[ERROR]: action does not exist";
        }

        if (gameState.canConsume(currentPlayer,currentAction) &&
            gameState.canProduce(currentAction)) {
            String message = gameState.consume(currentPlayer,currentAction,playerName);
            gameState.produce(currentPlayer,currentAction);
            return message;
        } else {
            return "[ERROR]: cannot execute action";
        }
    }
}
