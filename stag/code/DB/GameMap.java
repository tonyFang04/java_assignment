package DB;

import Entities.*;
import Entities.Character;
import Models.Action;

import java.util.ArrayList;
import java.util.Hashtable;

public class GameMap {
    public Hashtable<String, Location> locations;
    public Hashtable<String, Artefact> artefacts;
    public Hashtable<String, Furniture> furniture;
    public Hashtable<String, Character> characters;
    public String startLocationName;
    public GameMap() {
        locations = new Hashtable<>();
        artefacts = new Hashtable<>();
        furniture = new Hashtable<>();
        characters = new Hashtable<>();
    }

    //built-in look command (could add self-exclusion feature in terms of player names)
    public String look(String locationName) {
        Location location = locations.get(locationName);
        StringBuilder message = new StringBuilder("You are in " + location.description + ". You can see:\n");
        for (String name : location.artefactNames) {
            message.append(artefacts.get(name).description).append("\n");
        }

        for (String name : location.furnitureNames) {
            message.append(furniture.get(name).description).append("\n");
        }

        for (String name : location.characterNames) {
            message.append(characters.get(name).description).append("\n");
        }

        for (String name: location.playerNames) {
            message.append(name).append("\n");
        }

        message.append("You can access from here:\n");

        for (String name: location.accessibleLocationNames) {
            message.append(name).append("\n");
        }
        return message.substring(0, message.length()-1);
    }

    //built-in get command
    public String getArtefact(Player currentPlayer, String command) {
        Location currentLocation = locations.get(currentPlayer.currentLocationName);
        String toAdd = currentLocation.getArtefactName(command);
        if (toAdd == null) {
            return "[ERROR]: cannot get item. Item does not exist";
        } else {
            currentLocation.artefactNames.remove(toAdd);
            currentPlayer.artefactNames.add(toAdd);
            return "You added " + toAdd;
        }
    }

    //built-in drop command
    public String dropArtefact(Player currentPlayer, String command) {
        String toDrop = currentPlayer.getArtefactName(command);
        if (toDrop == null) {
            return "[ERROR]: cannot drop item. Item does not exist";
        } else {
            Location currentLocation = locations.get(currentPlayer.currentLocationName);
            currentLocation.artefactNames.add(toDrop);
            currentPlayer.artefactNames.remove(toDrop);
            return "You dropped " + toDrop;
        }
    }

    //built-in goto command
    public String gotoLocation(Player currentPlayer, String playerName, String command) {
        Location currentLocation = locations.get(currentPlayer.currentLocationName);
        for (String accessible : currentLocation.accessibleLocationNames) {
            if (command.contains(accessible)) {
                currentPlayer.currentLocationName = accessible;
                //delete player's name from old location
                currentLocation.playerNames.remove(playerName);
                //add player's name to new location
                currentLocation = locations.get(accessible);
                currentLocation.playerNames.add(playerName);
                return look(currentPlayer.currentLocationName);
            }
        }
        return "[ERROR]: location does not exist";
    }

    public String consume(Player currentPlayer, Action currentAction, String playerName) {
        String message = "";
        Location currentLocation = locations.get(currentPlayer.currentLocationName);
        for (String item : currentAction.consumed) {
            currentLocation.removeEntity(item);
            currentPlayer.artefactNames.remove(item);
            if (item.equals("health")) {
                currentPlayer.health--;
                if (currentPlayer.health <= 0) {
                    currentLocation.artefactNames.addAll(currentPlayer.artefactNames);
                    currentPlayer.artefactNames = new ArrayList<>();
                    currentPlayer.health = 3;
                    currentPlayer.currentLocationName = startLocationName;
                    currentLocation.playerNames.remove(playerName);
                    Location start = locations.get(startLocationName);
                    start.playerNames.add(playerName);
                    message = "\nYou are dead\n" + look(currentPlayer.currentLocationName);
                }
            }
        }
        return currentAction.narration + message;
    }

    public void produce(Player currentPlayer, Action currentAction) {
        Location currentLocation = locations.get(currentPlayer.currentLocationName);
        Location unplaced = locations.get("unplaced");
        for (String item : currentAction.produced) {
            if (item.equals("health") && currentPlayer.health < 3) {
                currentPlayer.health++;
            }

            if (locations.containsKey(item) &&
                !currentLocation.accessibleLocationNames.contains(item)) {
                currentLocation.accessibleLocationNames.add(item);
            }

            if (unplaced.artefactNames.contains(item)) {
                currentLocation.artefactNames.add(item);
                unplaced.artefactNames.remove(item);
            }

            if (unplaced.characterNames.contains(item)) {
                currentLocation.characterNames.add(item);
                unplaced.characterNames.remove(item);
            }

            if(unplaced.furnitureNames.contains(item)) {
                currentLocation.furnitureNames.add(item);
                unplaced.furnitureNames.remove(item);
            }
        }
    }

    public boolean canConsume(Player currentPlayer, Action currentAction) {
        Location currentLocation = locations.get(currentPlayer.currentLocationName);
        for (String subject: currentAction.subjects) {
            if (!(currentPlayer.artefactNames.contains(subject)) &&
                !(currentLocation.contains(subject))) {
                return false;
            }
        }
        return true;
    }

    public boolean canProduce(Action currentAction) {
        Location unplaced = locations.get("unplaced");
        for (String product : currentAction.produced) {
            if (!(locations.containsKey(product)) &&
                !(product.equals("health")) &&
                !(unplaced.unplacedContains(product)) ||
                //can't go to unplaced
                (product.equals("unplaced"))) {
                return false;
            }
        }
        return true;
    }


}