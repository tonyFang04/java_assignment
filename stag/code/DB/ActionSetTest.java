package DB;

import Parsers.ActionParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ActionSetTest {

    @Test
    void queryAction() {
        try {
            ActionSet actionSet = new ActionSet();
            ActionParser actionInput = new ActionParser("data/basic-actions.json");
            actionSet.actions = actionInput.buildActionSet();
            assertEquals(actionSet.queryAction(" fight elf"),actionSet.actions.get(3));
            assertEquals(actionSet.queryAction(" dropkick elf"),actionSet.actions.get(3));
            assertEquals(actionSet.queryAction(" fight elf puck"),actionSet.actions.get(3));
            assertEquals(actionSet.queryAction("upper cut mike"),null);
            assertEquals(actionSet.queryAction("please cut axe"),actionSet.actions.get(1));
            assertEquals(actionSet.queryAction("please cut down a green tree"),actionSet.actions.get(1));
            assertEquals(actionSet.queryAction("open key chop axe"),actionSet.actions.get(0));
            assertEquals(actionSet.queryAction("open key axe"),actionSet.actions.get(0));
            assertEquals(actionSet.queryAction("open drink potion"),actionSet.actions.get(2));
            assertEquals(actionSet.queryAction("open drink potion trapdoor"),actionSet.actions.get(0));
        } catch(ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}