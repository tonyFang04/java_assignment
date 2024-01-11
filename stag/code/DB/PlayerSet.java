package DB;

import Entities.Location;
import Entities.Player;

import java.util.Hashtable;

public class PlayerSet {
    public Hashtable<String, Player> players;
    public PlayerSet() {
        players = new Hashtable<>();
    }

    public Player queryPlayer(String playerName, String startLocationName, Location startLocation) {
        if (players.containsKey(playerName)) {
            return players.get(playerName);
        }
        else {
            Player current = new Player();
            current.health = 3;
            current.currentLocationName = startLocationName;
            startLocation.playerNames.add(playerName);
            players.put(playerName,current);
            return current;
        }
    }
}
