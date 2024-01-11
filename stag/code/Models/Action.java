package Models;

import java.util.ArrayList;

public class Action {
    public ArrayList<String> triggers;
    public ArrayList<String> subjects;
    public ArrayList<String> consumed;
    public ArrayList<String> produced;
    public String narration;
    public Action() {
        triggers = new ArrayList<>();
        subjects = new ArrayList<>();
        consumed = new ArrayList<>();
        produced = new ArrayList<>();
    }

    public boolean containsTrigger(String command) {
        for (String trigger: triggers) {
            if (command.contains(trigger)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsSubject(String command) {
        for (String subject: subjects) {
            if (command.contains(subject)) {
                return true;
            }
        }
        return false;
    }
}
