package DB;

import Entities.Character;
import Entities.Location;
import Entities.Player;
import Models.Action;
import Parsers.ActionParser;
import Parsers.GraphParser;
import com.alexmerz.graphviz.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    @Test
    void look() {
        try {
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            testMap.locations.get("forest").playerNames.add("Tony");
            testMap.locations.get("forest").playerNames.add("Ragnar");
            testMap.locations.get("forest").playerNames.add("Maria");
            testMap.locations.get("forest").characterNames.add("Aqua");
            Character AquaSama = new Character();
            AquaSama.description = "An usel... divine goddess: Aqua Sama";
            testMap.characters.put("Aqua",AquaSama);
            assertEquals(testMap.look("forest"),"You are in A dark forest. You can see:\nBrass key\nAn usel... divine goddess: Aqua Sama\nTony\nRagnar\nMaria\nYou can access from here:\nstart");
        } catch (FileNotFoundException | ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void getArtefact() {

        try {
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            PlayerSet playerSet = new PlayerSet();
            Location currentLocation = testMap.locations.get("start");
            assertEquals(currentLocation.playerNames.size(),0);
            Player testPlayer = playerSet.queryPlayer("Tony","start",currentLocation);
            assertEquals(testPlayer.artefactNames.size(),0);
            assertEquals(currentLocation.artefactNames.size(),1);
            assertEquals(currentLocation.artefactNames.get(0),"potion");
            assertEquals(currentLocation.playerNames.size(),1);
            assertEquals(currentLocation.playerNames.get(0),"Tony");

            assertEquals(testMap.getArtefact(testPlayer,"get potion"),"You added potion");

            assertEquals(testPlayer.artefactNames.size(),1);
            assertEquals(testPlayer.artefactNames.get(0),"potion");
            assertEquals(currentLocation.artefactNames.size(),0);

            assertEquals(testMap.getArtefact(testPlayer,"get chunchunmaru"),"[ERROR]: cannot get item. Item does not exist");
        } catch (FileNotFoundException | ParseException exception) {
            exception.printStackTrace();
        }

    }

    @Test
    void dropArtefact() {

        try {
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            PlayerSet playerSet = new PlayerSet();
            Location currentLocation = testMap.locations.get("start");
            Player testPlayer = playerSet.queryPlayer("Tony","start",currentLocation);
            assertEquals(testMap.getArtefact(testPlayer,"get potion"),"You added potion");
            assertEquals(testMap.dropArtefact(testPlayer,"drop potion"),"You dropped potion");
            assertEquals(testPlayer.artefactNames.size(),0);
            assertEquals(currentLocation.artefactNames.size(),1);
            assertEquals(currentLocation.artefactNames.get(0),"potion");
            assertEquals(testMap.dropArtefact(testPlayer,"drop chunchunmaru"),"[ERROR]: cannot drop item. Item does not exist");
        } catch (FileNotFoundException | ParseException exception) {
            exception.printStackTrace();
        }

    }

    @Test
    void gotoLocation() {
        try {
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            PlayerSet playerSet = new PlayerSet();
            Location currentLocation = testMap.locations.get("start");
            Player testPlayer = playerSet.queryPlayer("Tony","start",currentLocation);
            assertEquals(currentLocation.playerNames.size(),1);
            assertEquals(currentLocation.playerNames.get(0),"Tony");
            assertEquals(testMap.gotoLocation(testPlayer,"Tony","start"),"[ERROR]: location does not exist");
            assertEquals(testMap.gotoLocation(testPlayer,"Tony","cellar"),"[ERROR]: location does not exist");
            assertEquals(testMap.gotoLocation(testPlayer,"Tony","forest"),"You are in A dark forest. You can see:\n" +
                    "Brass key\n" +
                    "Tony\n" +
                    "You can access from here:\n" +
                    "start");
            assertEquals(currentLocation.playerNames.size(),0);
            assertEquals(testPlayer.currentLocationName,"forest");
            currentLocation = testMap.locations.get("forest");
            assertEquals(currentLocation.playerNames.size(),1);
            assertEquals(currentLocation.playerNames.get(0),"Tony");
        } catch (FileNotFoundException | ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void consume() {
        try {
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            PlayerSet playerSet = new PlayerSet();
            assertEquals(testMap.startLocationName,"start");
            Location currentLocation = testMap.locations.get("cellar");
            currentLocation.accessibleLocationNames.add("forest");
            assertEquals(currentLocation.accessibleLocationNames.size(),2);
            currentLocation.accessibleLocationNames.remove("start");
            assertEquals(currentLocation.accessibleLocationNames.size(),1);
            Player testPlayer = playerSet.queryPlayer("Tony","cellar",currentLocation);
            testPlayer.artefactNames.add("gun");
            testPlayer.artefactNames.add("knife");
            testPlayer.artefactNames.add("chainsaw");
            ActionSet actionSet = new ActionSet();
            ActionParser actionInput = new ActionParser("data/basic-actions.json");
            actionSet.actions = actionInput.buildActionSet();
            assertEquals(testPlayer.artefactNames.size(),3);
            System.out.println(testMap.consume(testPlayer, actionSet.queryAction("fight elf"),"Tony"));
            assertEquals(testPlayer.artefactNames.size(),3);
            System.out.println(testMap.consume(testPlayer, actionSet.queryAction("fight elf"),"Tony"));
            assertEquals(testPlayer.artefactNames.size(),3);
            System.out.println(testMap.consume(testPlayer, actionSet.queryAction("fight elf"),"Tony"));
            assertEquals(testPlayer.artefactNames.size(),0);
            assertEquals(testPlayer.currentLocationName,"start");
            assertEquals(currentLocation.artefactNames.size(),3);
            assertEquals(currentLocation.accessibleLocationNames.size(),1);
            currentLocation.accessibleLocationNames.remove("forest");
            currentLocation.accessibleLocationNames.add("start");

            //test artefact removal
            testPlayer.artefactNames.add("potion");
            assertEquals(testPlayer.artefactNames.size(),1);
            testMap.consume(testPlayer, actionSet.queryAction("please drink blue potion"),"Tony");
            assertEquals(testPlayer.artefactNames.size(),0);
            testMap.consume(testPlayer, actionSet.queryAction("please drink blue potion"),"Tony");
            assertEquals(testPlayer.artefactNames.size(),0);

            //test furniture removal
            currentLocation = testMap.locations.get("start");
            assertEquals(currentLocation.playerNames.size(),1);
            assertEquals(currentLocation.playerNames.get(0),"Tony");
            currentLocation.furnitureNames.add("tree");
            assertEquals(currentLocation.furnitureNames.size(),2);
            System.out.println(testMap.consume(testPlayer, actionSet.queryAction("cut tree"),"Tony"));
            assertEquals(currentLocation.furnitureNames.size(),1);

            //test removing multiple elements
            Action currentAction = actionSet.queryAction("cut tree");
            currentAction.consumed.add("axe");
            currentAction.consumed.add("gun");
            currentLocation.furnitureNames.add("tree");
            currentLocation.furnitureNames.add("axe");
            currentLocation.furnitureNames.add("gun");
            assertEquals(currentLocation.furnitureNames.size(),4);
            System.out.println(testMap.consume(testPlayer, currentAction,"Tony"));
            assertEquals(currentLocation.furnitureNames.size(),1);

            //test 5 kinds of entities
            currentAction.consumed.remove("axe");
            currentAction.consumed.remove("gun");
            assertEquals(currentAction.consumed.size(),1);
            currentAction.consumed.add("axe");
            currentAction.consumed.add("potion");
            currentAction.consumed.add("cellar");
            currentAction.consumed.add("lumberjack");
            currentAction.consumed.add("tree");
            currentAction.consumed.add("health");

            testPlayer.artefactNames.add("axe");
            currentLocation.artefactNames.add("potion");
            currentLocation.accessibleLocationNames.add("cellar");
            currentLocation.furnitureNames.add("tree");
            currentLocation.characterNames.add("lumberjack");

            assertEquals(testPlayer.health,3);
            assertTrue(testPlayer.artefactNames.contains("axe"));
            assertTrue(currentLocation.artefactNames.contains("potion"));
            assertTrue(currentLocation.accessibleLocationNames.contains("cellar"));
            assertTrue(currentLocation.furnitureNames.contains("tree"));
            assertTrue(currentLocation.characterNames.contains("lumberjack"));

            testMap.consume(testPlayer, currentAction,"Tony");

            assertEquals(testPlayer.health,2);
            assertFalse(testPlayer.artefactNames.contains("axe"));
            assertTrue(currentLocation.artefactNames.contains("potion"));
            assertFalse(currentLocation.accessibleLocationNames.contains("cellar"));
            assertFalse(currentLocation.furnitureNames.contains("tree"));
            assertFalse(currentLocation.characterNames.contains("lumberjack"));
        } catch (ParseException | IOException | org.json.simple.parser.ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void canConsume() {
        try {
            //init
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            PlayerSet playerSet = new PlayerSet();
            Location currentLocation = testMap.locations.get("start");
            Player testPlayer = playerSet.queryPlayer("Tony","start",currentLocation);
            assertEquals(testPlayer.currentLocationName,"start");
            assertEquals(currentLocation.playerNames.size(),1);
            assertEquals(currentLocation.playerNames.get(0),"Tony");
            ActionSet actionSet = new ActionSet();
            ActionParser actionInput = new ActionParser("data/basic-actions.json");
            actionSet.actions = actionInput.buildActionSet();

            //test begins
            assertFalse(testMap.canConsume(testPlayer,actionSet.actions.get(0)));
            testPlayer.artefactNames.add("key");
            assertTrue(testMap.canConsume(testPlayer,actionSet.actions.get(0)));
            testPlayer.artefactNames.remove("key");

            assertFalse(testMap.canConsume(testPlayer,actionSet.actions.get(1)));
            testPlayer.artefactNames.add("axe");
            currentLocation.furnitureNames.add("tree");
            assertTrue(testMap.canConsume(testPlayer,actionSet.actions.get(1)));
            testPlayer.artefactNames.remove("axe");
            currentLocation.furnitureNames.remove("tree");

            assertFalse(testMap.canConsume(testPlayer,actionSet.actions.get(2)));
            testPlayer.artefactNames.add("potion");
            assertTrue(testMap.canConsume(testPlayer,actionSet.actions.get(2)));
            testPlayer.artefactNames.remove("potion");

            assertFalse(testMap.canConsume(testPlayer,actionSet.actions.get(3)));
            currentLocation.characterNames.add("elf");
            assertTrue(testMap.canConsume(testPlayer,actionSet.actions.get(3)));
            currentLocation.characterNames.remove("elf");
        } catch (ParseException | IOException | org.json.simple.parser.ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void canProduce() {
        try {
            //init
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            ActionSet actionSet = new ActionSet();
            ActionParser actionInput = new ActionParser("data/basic-actions.json");
            actionSet.actions = actionInput.buildActionSet();

            //test starts
            assertTrue(testMap.canProduce(actionSet.actions.get(0)));
            assertFalse(testMap.canProduce(actionSet.actions.get(1)));
            assertTrue(testMap.canProduce(actionSet.actions.get(2)));
            assertTrue(testMap.canProduce(actionSet.actions.get(3)));

            //more tests
            Action action = new Action();
            action.produced.add("cabin");
            assertFalse(testMap.canProduce(action));
            action.produced.remove("cabin");
            action.produced.add("lumberjack");
            assertFalse(testMap.canProduce(action));
            action.produced.remove("lumberjack");
            action.produced.add("health");
            assertTrue(testMap.canProduce(action));
            action.produced.remove("health");
            action.produced.add("tree");
            assertFalse(testMap.canProduce(action));
            action.produced.remove("tree");
            action.produced.add("gun");
            assertFalse(testMap.canProduce(action));
            action.produced.remove("gun");


            parser = new GraphParser("data/extended-entities.dot");
            testMap = parser.buildGameMap();
            assertTrue(testMap.canProduce(action));
            action.produced.add("cabin");
            assertTrue(testMap.canProduce(action));
            action.produced.add("riverbank");
            action.produced.add("log");
            action.produced.add("lumberjack");
            action.produced.add("health");
            action.produced.add("cabin");
            action.produced.add("hole");
            action.produced.add("gold");
            action.produced.add("shovel");
            assertTrue(testMap.canProduce(action));
            action.produced.add("unplaced");
            assertFalse(testMap.canProduce(action));
        } catch (ParseException | IOException | org.json.simple.parser.ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void produce() {
        try {
            //init
            GraphParser parser = new GraphParser("data/basic-entities.dot");
            GameMap testMap = parser.buildGameMap();
            PlayerSet playerSet = new PlayerSet();
            Location currentLocation = testMap.locations.get("start");
            Player testPlayer = playerSet.queryPlayer("Tony","start",currentLocation);
            assertEquals(testPlayer.currentLocationName,"start");
            assertEquals(currentLocation.playerNames.size(),1);
            assertEquals(currentLocation.playerNames.get(0),"Tony");

            //test health
            Action action = new Action();
            action.produced.add("health");
            testMap.produce(testPlayer,action);
            assertEquals(testPlayer.health,3);
            testPlayer.health = 0;
            testMap.produce(testPlayer,action);
            assertEquals(testPlayer.health,1);
            testMap.produce(testPlayer,action);
            assertEquals(testPlayer.health,2);
            testMap.produce(testPlayer,action);
            assertEquals(testPlayer.health,3);

            //test location
            action.produced.add("cellar");
            action.produced.add("forest");
            assertEquals(currentLocation.accessibleLocationNames.size(),1);
            testMap.produce(testPlayer,action);
            assertEquals(currentLocation.accessibleLocationNames.size(),2);
            assertEquals(currentLocation.accessibleLocationNames.get(0),"forest");
            assertEquals(currentLocation.accessibleLocationNames.get(1),"cellar");

            //test character, artefact and furniture
            Location unplaced = testMap.locations.get("unplaced");
            unplaced.furnitureNames.add("tree");
            unplaced.characterNames.add("aqua");
            unplaced.artefactNames.add("chunchunmaru");
            action.produced.add("tree");
            action.produced.add("aqua");
            action.produced.add("chunchunmaru");
            assertEquals(unplaced.artefactNames.size(),1);
            assertEquals(unplaced.characterNames.size(),1);
            assertEquals(unplaced.furnitureNames.size(),1);
            assertEquals(currentLocation.artefactNames.size(),1);
            assertEquals(currentLocation.characterNames.size(),0);
            assertEquals(currentLocation.furnitureNames.size(),1);
            testMap.produce(testPlayer,action);
            assertEquals(unplaced.artefactNames.size(),0);
            assertEquals(unplaced.characterNames.size(),0);
            assertEquals(unplaced.furnitureNames.size(),0);
            assertEquals(currentLocation.artefactNames.size(),2);
            assertEquals(currentLocation.characterNames.size(),1);
            assertEquals(currentLocation.furnitureNames.size(),2);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}