package Entities;

import Models.Action;

import java.util.ArrayList;

public class Location extends ArtefactContainer {
    public ArrayList<String> accessibleLocationNames;
    public ArrayList<String> characterNames;
    public ArrayList<String> furnitureNames;
    public ArrayList<String> playerNames;
    public Location() {
        accessibleLocationNames = new ArrayList<>();
        characterNames = new ArrayList<>();
        furnitureNames = new ArrayList<>();
        playerNames = new ArrayList<>();
    }

    public void removeEntity(String name) {
        accessibleLocationNames.remove(name);
        characterNames.remove(name);
        furnitureNames.remove(name);
    }

    public boolean contains(String item) {
        return characterNames.contains(item) ||
               furnitureNames.contains(item) ||
               accessibleLocationNames.contains(item);
    }

    public boolean unplacedContains(String item) {
        return characterNames.contains(item) ||
               furnitureNames.contains(item) ||
               artefactNames.contains(item);
    }
}
