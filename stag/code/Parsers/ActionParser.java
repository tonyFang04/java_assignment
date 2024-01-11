package Parsers;

import Models.Action;
import org.json.simple.parser.*;
import org.json.simple.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ActionParser {
    private JSONArray actionArray;
    public ActionParser(String filename) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));
        actionArray = (JSONArray) jsonObject.get("actions");
    }

    public ArrayList<Action> buildActionSet() {
        ArrayList<Action> actionSet = new ArrayList<>();
        for (Object jsonAction: actionArray) {
            Action action = new Action();
            JSONArray triggers = (JSONArray) ((JSONObject) jsonAction).get("triggers");
            for (Object trigger : triggers) {
                action.triggers.add(trigger.toString());
            }

            JSONArray subjects = (JSONArray) ((JSONObject) jsonAction).get("subjects");
            for (Object subject : subjects) {
                action.subjects.add(subject.toString());
            }

            JSONArray consumed = (JSONArray) ((JSONObject) jsonAction).get("consumed");
            for (Object consumedItem : consumed) {
                action.consumed.add(consumedItem.toString());
            }

            JSONArray produced = (JSONArray) ((JSONObject) jsonAction).get("produced");
            for (Object producedItem : produced) {
                action.produced.add(producedItem.toString());
            }

            action.narration = ((JSONObject) jsonAction).get("narration").toString();
            actionSet.add(action);
        }
        return actionSet;
    }
}
