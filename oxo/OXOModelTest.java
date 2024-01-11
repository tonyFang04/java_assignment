import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OXOModelTest {

    @Test
    void addPlayer() {
        OXOModel testModel = new OXOModel(3,3,3);
        assertEquals(testModel.getNumberOfPlayers(),0);
        testModel.addPlayer(new OXOPlayer('K'));
        assertEquals(testModel.getNumberOfPlayers(),1);
        testModel.addPlayer(new OXOPlayer('O'));
        assertEquals(testModel.getNumberOfPlayers(),2);
        testModel.addPlayer(new OXOPlayer('C'));
        assertEquals(testModel.getNumberOfPlayers(),3);
    }

    @Test
    void getPlayerByNumber() {
        OXOModel testModel = new OXOModel(3,3,3);
        assertEquals(testModel.getNumberOfPlayers(),0);
        testModel.addPlayer(new OXOPlayer('K'));
        testModel.addPlayer(new OXOPlayer('Q'));
        testModel.addPlayer(new OXOPlayer('L'));
        assertEquals(testModel.getPlayerByNumber(0).getPlayingLetter(),'K');
        assertNotEquals(testModel.getPlayerByNumber(0).getPlayingLetter(),'Q');
        assertEquals(testModel.getPlayerByNumber(1).getPlayingLetter(),'Q');
        assertEquals(testModel.getPlayerByNumber(2).getPlayingLetter(),'L');
    }

    @Test
    void getNumberOfRows() {
        OXOModel testModel;
        for (int i = 0; i < 10; i++) {
            testModel = new OXOModel(i,3,3);
            assertEquals(testModel.getNumberOfRows(),i);
        }
    }

    @Test
    void getNumberOfColumns() {
        OXOModel testModel;
        for (int i = 0; i < 10; i++) {
            testModel = new OXOModel(3,i,3);
            assertEquals(testModel.getNumberOfColumns(),i);
        }
    }

    @Test
    void setCellOwner() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.setCellOwner(1,2,new OXOPlayer('X'));
        testModel.setCellOwner(2,0,new OXOPlayer('O'));
        assertEquals(testModel.getCellOwner(1,2).getPlayingLetter(),'X');
        assertEquals(testModel.getCellOwner(2,0).getPlayingLetter(),'O');
        assertEquals(testModel.getCellOwner(0,0),null);
        testModel.setCellOwner(2,3,new OXOPlayer('K'));
        assertEquals(testModel.getCellOwner(2,3).getPlayingLetter(),'K');
        testModel.setCellOwner(3,3,new OXOPlayer('K'));
        assertEquals(testModel.getCellOwner(3,3).getPlayingLetter(),'K');
        testModel.setCellOwner(8,8,new OXOPlayer('K'));
        assertEquals(testModel.getCellOwner(8,8).getPlayingLetter(),'K');
    }
}