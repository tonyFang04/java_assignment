package Entities;

public class Player extends ArtefactContainer {
    public int health;
    public String currentLocationName;

    //for inv/inventory command
    public String listArtefacts() {
        StringBuilder message = new StringBuilder("List of your artefacts:\n");
        for (String name : artefactNames) {
            message.append(name).append("\n");
        }
        return message.toString();
    }
}
