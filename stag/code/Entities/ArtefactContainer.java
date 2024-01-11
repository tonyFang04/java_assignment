package Entities;

import java.util.ArrayList;

public class ArtefactContainer extends Entity {
    public ArrayList<String> artefactNames;
    public ArtefactContainer() {
        artefactNames = new ArrayList<>();
    }

    public String getArtefactName(String command) {
        for (String pattern : artefactNames) {
            if (command.contains(pattern)) {
                return pattern;
            }
        }
        return null;
    }
}
