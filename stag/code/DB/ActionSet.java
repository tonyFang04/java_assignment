package DB;

import Models.Action;

import java.util.ArrayList;

public class ActionSet {
    public ArrayList<Action> actions;

    public Action queryAction(String command) {
        for (Action action : actions) {
            if (action.containsTrigger(command)
                    && action.containsSubject(command)) {
                return action;
            }
        }
        return null;
    }
}
