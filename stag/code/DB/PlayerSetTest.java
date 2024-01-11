package DB;

import static org.junit.jupiter.api.Assertions.*;

import Entities.Location;
import Entities.Player;
import org.junit.jupiter.api.*;
class PlayerSetTest {

    @Test
    void queryPlayer() {
        PlayerSet test = new PlayerSet();
        Location start1 = new Location();
        assertEquals(start1.playerNames.size(),0);
        Player current = test.queryPlayer("Tony","cabin",start1);
        assertEquals(start1.playerNames.size(),1);
        assertEquals(start1.playerNames.get(0),"Tony");
        assertEquals(current.currentLocationName,"cabin");
        assertTrue(test.players.containsKey("Tony"));
        assertTrue(test.players.contains(current));
        Player next = test.queryPlayer("Tony","cabin",start1);
        assertEquals(current,next);

        Location start2 = new Location();
        Player other = test.queryPlayer("Ashley","forest",start2);
        assertEquals(start2.playerNames.size(),1);
        assertEquals(start2.playerNames.get(0),"Ashley");
        assertEquals(other.currentLocationName,"forest");
        assertTrue(test.players.containsKey("Ashley"));
        assertTrue(test.players.contains(other));
        Player who = test.queryPlayer("Ashley","forest",start2);
        assertEquals(other,who);
    }
}