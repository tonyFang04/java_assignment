import OXOExceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class OXOControllerTest {

    @Test
    void getRowIndex() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        OXOController testController = new OXOController(testModel);
        for (char c = 'a'; c <= 'z'; c++) {
            assertEquals(testController.getRowIndex(c),c-'a');
        }
    }

    @Test
    void getColIndex() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        OXOController testController = new OXOController(testModel);
        for (char c = '0'; c <= '9'; c++) {
            assertEquals(testController.getColIndex(c),c-'0'-1);
        }
    }

    @Test
    void setCurrentPlayerIndex() {
        OXOModel testModel = new OXOModel(3,3,3);
        for (char c = 'A'; c <= 'G'; c++) {
            testModel.addPlayer(new OXOPlayer(c));
        }
        OXOController testController = new OXOController(testModel);
        for (int i = 0; i < 47; i++) {
            testController.setCurrentPlayerIndex();
        }
        assertEquals(testController.getCurrentPlayerIndex(),47%testModel.getNumberOfPlayers());
    }

    @Test
    void userInteraction() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        testModel.addPlayer(new OXOPlayer('O'));
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("A1");
        testController.userInteraction("i9");
        assertEquals(testModel.getCellOwner(0,0).getPlayingLetter(),'X');
        assertEquals(testModel.getCellOwner(8,8).getPlayingLetter(),'O');
    }

    @Test
    void checkCommandLength() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        OXOController testController = new OXOController(testModel);
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkCommandLength(""));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkCommandLength("1"));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkCommandLength("123"));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkCommandLength("123456"));
        assertDoesNotThrow(()->testController.checkCommandLength("a1"));
        assertDoesNotThrow(()->testController.checkCommandLength("A1"));
        assertDoesNotThrow(()->testController.checkCommandLength("i9"));
        assertDoesNotThrow(()->testController.checkCommandLength("I9"));
    }

    @Test
    void checkCharacter() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        OXOController testController = new OXOController(testModel);
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkCharacter("`1"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkCharacter("J1"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkCharacter("j1"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkCharacter("a0"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkCharacter("a:"));
        assertDoesNotThrow(()->testController.checkCharacter("a1"));
        assertDoesNotThrow(()->testController.checkCharacter("A1"));
        assertDoesNotThrow(()->testController.checkCharacter("i9"));
        assertDoesNotThrow(()->testController.checkCharacter("I9"));
    }

    @Test
    void checkCellBound() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        OXOController testController = new OXOController(testModel);
        assertThrows(OutsideCellRangeException.class, ()->testController.checkCellBound("a4"));
        assertThrows(OutsideCellRangeException.class, ()->testController.checkCellBound("d1"));
        assertDoesNotThrow(()->testController.checkCellBound("a1"));
        assertDoesNotThrow(()->testController.checkCellBound("A1"));
        assertDoesNotThrow(()->testController.checkCellBound("c3"));
        assertDoesNotThrow(()->testController.checkCellBound("C3"));
        testModel.setCellOwner(5,5,null);
        assertDoesNotThrow(()->testController.checkCellBound("f6"));
        assertDoesNotThrow(()->testController.checkCellBound("F6"));
    }

    @Test
    void checkCellAvailability() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        testModel.addPlayer(new OXOPlayer('O'));
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a1");
        assertThrows(CellAlreadyTakenException.class, ()->testController.checkCellAvailability("a1"));
        assertDoesNotThrow(()->testController.checkCellAvailability("b1"));
    }

    @Test
    void checkIncomingCommand() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        testModel.addPlayer(new OXOPlayer('O'));
        OXOController testController = new OXOController(testModel);


        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkIncomingCommand(""));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkIncomingCommand("1"));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkIncomingCommand("123"));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.checkIncomingCommand("123456"));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand(""));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand("1"));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand("123"));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand("123456"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand(""));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("1"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("123"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("123456"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand(""));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("1"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("123"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("123456"));


        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkIncomingCommand("J1"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkIncomingCommand("j1"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkIncomingCommand("a0"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.checkIncomingCommand("a*"));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand("J1"));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand("j1"));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand("a0"));
        assertThrows(InvalidIdentifierException.class, ()->testController.checkIncomingCommand("a*"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("J1"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("j1"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("a0"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("a*"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("J1"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("j1"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("a0"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("a*"));


        assertThrows(OutsideCellRangeException.class, ()->testController.checkIncomingCommand("a4"));
        assertThrows(OutsideCellRangeException.class, ()->testController.checkIncomingCommand("d1"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("a4"));
        assertThrows(CellDoesNotExistException.class, ()->testController.checkIncomingCommand("d1"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("a4"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("d1"));

        assertDoesNotThrow(()->testController.checkIncomingCommand("a1"));
        assertDoesNotThrow(()->testController.checkIncomingCommand("A1"));
        assertDoesNotThrow(()->testController.checkIncomingCommand("c3"));
        assertDoesNotThrow(()->testController.checkIncomingCommand("C3"));


        testController.userInteraction("a1");
        assertThrows(CellAlreadyTakenException.class, ()->testController.checkIncomingCommand("a1"));
        assertThrows(OXOMoveException.class, ()->testController.checkIncomingCommand("a1"));
        assertDoesNotThrow(()->testController.checkIncomingCommand("b1"));

    }

    @Test
    void compareTwoCells() {
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(new OXOPlayer('X'));
        testModel.addPlayer(new OXOPlayer('O'));
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a1");
        testController.userInteraction("b1");
        testController.userInteraction("a2");
        assertTrue(testController.compareTwoCells(0,0,0,1));
        assertFalse(testController.compareTwoCells(0,0,1,0));
        assertFalse(testController.compareTwoCells(2,0,2,1));
        assertFalse(testController.compareTwoCells(1,1,2,2));
    }

    @Test
    void winSubRow() {
        OXOModel testModel = new OXOModel(4,4,4);
        testModel.addPlayer(new OXOPlayer('X'));
        testModel.addPlayer(new OXOPlayer('O'));
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a1");
        testController.userInteraction("b1");
        testController.userInteraction("a2");
        testController.userInteraction("b2");
        testController.userInteraction("a3");
        testController.userInteraction("c1");
        assertFalse(testController.winSubRow(0,0));
        assertFalse(testController.winSubRow(0,1));
        assertFalse(testController.winSubRow(1,0));
        assertFalse(testController.winSubRow(2,0));
        assertFalse(testController.winSubRow(3,0));
        testModel.setWinThreshold(0);
        assertTrue(testController.winSubRow(0,0));
        assertTrue(testController.winSubRow(0,1));
        assertTrue(testController.winSubRow(1,0));
        assertTrue(testController.winSubRow(2,0));
        assertTrue(testController.winSubRow(3,0));
        testModel.setWinThreshold(1);
        assertTrue(testController.winSubRow(0,0));
        assertTrue(testController.winSubRow(0,1));
        assertTrue(testController.winSubRow(1,0));
        assertTrue(testController.winSubRow(2,0));
        assertFalse(testController.winSubRow(3,0));
        testModel.setWinThreshold(2);
        assertTrue(testController.winSubRow(0,0));
        assertTrue(testController.winSubRow(0,1));
        assertTrue(testController.winSubRow(1,0));
        assertFalse(testController.winSubRow(2,0));
        assertFalse(testController.winSubRow(3,0));
        testModel.setWinThreshold(3);
        assertTrue(testController.winSubRow(0,0));
        assertFalse(testController.winSubRow(0,1));
        assertFalse(testController.winSubRow(1,0));
        assertFalse(testController.winSubRow(2,0));
        assertFalse(testController.winSubRow(3,0));
    }

    @Test
    void winByRow() {
        OXOPlayer player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(4,4,5);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a1");
        testController.userInteraction("b2");
        testController.userInteraction("a2");
        testController.userInteraction("b3");
        testController.userInteraction("a3");
        testController.userInteraction("b4");
        testController.userInteraction("a4");
        assertFalse(testController.winByRow(0));
        assertFalse(testController.winByRow(1));
        testModel.setWinThreshold(4);
        assertTrue(testController.winByRow(0));
        assertEquals(testModel.getWinner(),player1);
        assertFalse(testController.winByRow(1));
        testModel.setWinThreshold(3);
        assertTrue(testController.winByRow(0));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByRow(1));
        assertEquals(testModel.getWinner(),player2);
        testModel.setCellOwner(4,4,null);
        assertTrue(testController.winByRow(0));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByRow(1));
        assertEquals(testModel.getWinner(),player2);
        OXOPlayer player3 = new OXOPlayer('S');
        testModel.addPlayer(player3);
        testController.userInteraction("c1");
        testController.userInteraction("c2");
        testController.userInteraction("c3");
        testController.userInteraction("d3");
        testController.userInteraction("e3");
        testController.userInteraction("c4");
        testController.userInteraction("d4");
        testController.userInteraction("e4");
        testController.userInteraction("c5");
        assertTrue(testController.winByRow(2));
        assertEquals(testModel.getWinner(),player1);
        testModel.setWinThreshold(2);
        assertTrue(testController.winByRow(0));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByRow(1));
        assertEquals(testModel.getWinner(),player2);
        assertTrue(testController.winByRow(2));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByRow(3));
        assertEquals(testModel.getWinner(),player2);
        assertTrue(testController.winByRow(4));
        assertEquals(testModel.getWinner(),player3);
    }

    @Test
    void checkWinInAllRows() {
        OXOPlayer player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(4,4,5);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("d1");
        testController.userInteraction("c1");
        testController.userInteraction("d2");
        testController.userInteraction("c2");
        testController.userInteraction("d3");
        testController.userInteraction("c3");
        testController.userInteraction("d4");
        OXOPlayer player3 = new OXOPlayer('S');
        testModel.addPlayer(player3);
        testController.userInteraction("b1");
        testController.userInteraction("a1");
        testController.userInteraction("c4");
        testController.userInteraction("b2");
        assertFalse(testController.checkWinInAllRows());
        testModel.setCellOwner(4,4,null);
        assertFalse(testController.checkWinInAllRows());
        testModel.setWinThreshold(4);
        assertTrue(testController.checkWinInAllRows());
        assertEquals(testModel.getWinner(),player1);
        testModel.setWinThreshold(3);
        assertTrue(testController.checkWinInAllRows());
        assertEquals(testModel.getWinner(),player2);
        testModel.setWinThreshold(1);
        assertTrue(testController.checkWinInAllRows());
        assertEquals(testModel.getWinner(),player3);
        OXOPlayer player4 = new OXOPlayer('K');
        testModel.setCellOwner(1,0,player4);
        testModel.setCellOwner(1,1,player4);
        testModel.setWinThreshold(2);
        assertTrue(testController.checkWinInAllRows());
        assertEquals(testModel.getWinner(),player4);
        testModel.setCellOwner(1,2,player2);
        assertTrue(testController.checkWinInAllRows());
        assertEquals(testModel.getWinner(),player4);
        for (int i = 0; i < 6; i++) {
            testModel.setCellOwner(4,i,player4);
        }
        testModel.setWinThreshold(6);
        assertTrue(testController.checkWinInAllRows());
        assertEquals(testModel.getWinner(),player4);
    }

    @Test
    void winSubCol() {
        OXOModel testModel = new OXOModel(4,4,4);
        testModel.addPlayer(new OXOPlayer('X'));
        testModel.addPlayer(new OXOPlayer('O'));
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a1");
        testController.userInteraction("a2");
        testController.userInteraction("b1");
        testController.userInteraction("b2");
        testController.userInteraction("c1");
        testController.userInteraction("a3");
        assertFalse(testController.winSubCol(0,0));
        assertFalse(testController.winSubCol(1,0));
        assertFalse(testController.winSubCol(0,1));
        assertFalse(testController.winSubCol(0,2));
        assertFalse(testController.winSubCol(0,3));
        testModel.setWinThreshold(0);
        assertTrue(testController.winSubCol(0,0));
        assertTrue(testController.winSubCol(1,0));
        assertTrue(testController.winSubCol(0,1));
        assertTrue(testController.winSubCol(0,2));
        assertTrue(testController.winSubCol(0,3));
        testModel.setWinThreshold(1);
        assertTrue(testController.winSubCol(0,0));
        assertTrue(testController.winSubCol(1,0));
        assertTrue(testController.winSubCol(0,1));
        assertTrue(testController.winSubCol(0,2));
        assertFalse(testController.winSubCol(0,3));
        testModel.setWinThreshold(2);
        assertTrue(testController.winSubCol(0,0));
        assertTrue(testController.winSubCol(1,0));
        assertTrue(testController.winSubCol(0,1));
        assertFalse(testController.winSubCol(0,2));
        assertFalse(testController.winSubCol(0,3));
        testModel.setWinThreshold(3);
        assertTrue(testController.winSubCol(0,0));
        assertFalse(testController.winSubCol(1,0));
        assertFalse(testController.winSubCol(0,1));
        assertFalse(testController.winSubCol(0,2));
        assertFalse(testController.winSubCol(0,3));
    }

    @Test
    void winByCol() {
        OXOPlayer player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(4,4,5);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a1");
        testController.userInteraction("b2");
        testController.userInteraction("b1");
        testController.userInteraction("c2");
        testController.userInteraction("c1");
        testController.userInteraction("d2");
        testController.userInteraction("d1");
        assertFalse(testController.winByCol(0));
        assertFalse(testController.winByCol(1));
        assertFalse(testController.winByCol(2));
        assertFalse(testController.winByCol(3));
        testModel.setWinThreshold(4);
        assertTrue(testController.winByCol(0));
        assertEquals(testModel.getWinner(),player1);
        assertFalse(testController.winByCol(1));
        testModel.setWinThreshold(3);
        assertTrue(testController.winByCol(0));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByCol(1));
        assertEquals(testModel.getWinner(),player2);
        testModel.setCellOwner(4,4,null);
        assertTrue(testController.winByCol(0));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByCol(1));
        assertEquals(testModel.getWinner(),player2);
        OXOPlayer player3 = new OXOPlayer('S');
        testModel.addPlayer(player3);
        testController.userInteraction("a3");
        testController.userInteraction("b3");
        testController.userInteraction("c3");
        testController.userInteraction("c4");
        testController.userInteraction("c5");
        testController.userInteraction("d3");
        testController.userInteraction("d4");
        testController.userInteraction("d5");
        testController.userInteraction("e3");
        assertTrue(testController.winByCol(2));
        assertEquals(testModel.getWinner(),player1);
        testModel.setWinThreshold(2);
        assertTrue(testController.winByCol(0));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByCol(1));
        assertEquals(testModel.getWinner(),player2);
        assertTrue(testController.winByCol(2));
        assertEquals(testModel.getWinner(),player1);
        assertTrue(testController.winByCol(3));
        assertEquals(testModel.getWinner(),player2);
        assertTrue(testController.winByCol(4));
        assertEquals(testModel.getWinner(),player3);
    }

    @Test
    void checkWinInAllCols() {
        OXOPlayer player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(4,4,5);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a4");
        testController.userInteraction("a3");
        testController.userInteraction("b4");
        testController.userInteraction("b3");
        testController.userInteraction("c4");
        testController.userInteraction("c3");
        testController.userInteraction("d4");
        OXOPlayer player3 = new OXOPlayer('S');
        testModel.addPlayer(player3);
        testController.userInteraction("a2");
        testController.userInteraction("a1");
        testController.userInteraction("d3");
        testController.userInteraction("b2");
        assertFalse(testController.checkWinInAllCols());
        testModel.setCellOwner(4,4,null);
        assertFalse(testController.checkWinInAllCols());
        testModel.setWinThreshold(4);
        assertTrue(testController.checkWinInAllCols());
        assertEquals(testModel.getWinner(),player1);
        testModel.setWinThreshold(3);
        assertTrue(testController.checkWinInAllCols());
        assertEquals(testModel.getWinner(),player2);
        testModel.setWinThreshold(1);
        assertTrue(testController.checkWinInAllCols());
        assertEquals(testModel.getWinner(),player3);
        OXOPlayer player4 = new OXOPlayer('K');
        testModel.setCellOwner(0,1,player4);
        testModel.setCellOwner(1,1,player4);
        testModel.setWinThreshold(2);
        assertTrue(testController.checkWinInAllCols());
        assertEquals(testModel.getWinner(),player4);
        testModel.setCellOwner(1,2,player2);
        assertTrue(testController.checkWinInAllCols());
        assertEquals(testModel.getWinner(),player4);
        for (int i = 0; i < 6; i++) {
            testModel.setCellOwner(i,4,player4);
        }
        testModel.setWinThreshold(6);
        assertTrue(testController.checkWinInAllCols());
        assertEquals(testModel.getWinner(),player4);
    }

    @Test
    void winSubTopLeftToBottomRight() {
        OXOPlayer player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(i,i,player1);
        }
        assertTrue(testController.winSubTopLeftToBottomRight(0,0));
        testModel.setCellOwner(4,4,player1);
        testModel.setWinThreshold(4);
        assertFalse(testController.winSubTopLeftToBottomRight(0,0));
        testModel.setWinThreshold(5);
        assertFalse(testController.winSubTopLeftToBottomRight(0,0));
        testModel.setCellOwner(3,3,player1);
        assertTrue(testController.winSubTopLeftToBottomRight(0,0));
        testModel.setWinThreshold(0);
        assertTrue(testController.winSubTopLeftToBottomRight(4,4));
        testModel.setWinThreshold(1);
        assertTrue(testController.winSubTopLeftToBottomRight(4,4));
        testModel.setWinThreshold(2);
        assertTrue(testController.winSubTopLeftToBottomRight(3,3));
        testModel.setWinThreshold(3);
        assertTrue(testController.winSubTopLeftToBottomRight(2,2));
        assertTrue(testController.winSubTopLeftToBottomRight(1,1));
        assertTrue(testController.winSubTopLeftToBottomRight(0,0));
        testModel.setWinThreshold(4);
        assertTrue(testController.winSubTopLeftToBottomRight(1,1));
        assertTrue(testController.winSubTopLeftToBottomRight(0,0));
        for (int i = 0; i < 4; i++) {
            testModel.setCellOwner(i,i + 1,player2);
        }
        assertTrue(testController.winSubTopLeftToBottomRight(0,1));
        OXOPlayer player3 = new OXOPlayer('K');
        for (int i = 0; i < 2; i++) {
            testModel.setCellOwner(i + 1,i + 3,player3);
        }
        assertEquals(testModel.getNumberOfColumns(),5);
        testModel.setWinThreshold(2);
        assertTrue(testController.winSubTopLeftToBottomRight(1,3));
        testModel.setCellOwner(0,2,player2);
        assertTrue(testController.winSubTopLeftToBottomRight(1,3));
        testModel.setCellOwner(1,0,player1);
        OXOPlayer player4 = new OXOPlayer('S');
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(i + 2,i + 1,player4);
        }
        testModel.setWinThreshold(3);
        assertTrue(testController.winSubTopLeftToBottomRight(2,1));
        assertFalse(testController.winSubTopLeftToBottomRight(1,0));
        testModel.setWinThreshold(4);
        assertFalse(testController.winSubTopLeftToBottomRight(1,0));
    }

    @Test
    void winSubBottomLeftToTopRight() {
        OXOPlayer player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(3,3,3);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(4 - i,i,player1);
        }
        assertTrue(testController.winSubBottomLeftToTopRight(4,0));
        testModel.setCellOwner(0,4,player1);
        testModel.setWinThreshold(4);
        assertFalse(testController.winSubBottomLeftToTopRight(4,0));
        testModel.setWinThreshold(5);
        assertFalse(testController.winSubBottomLeftToTopRight(4,0));
        testModel.setCellOwner(1,3,player1);
        assertTrue(testController.winSubBottomLeftToTopRight(4,0));
        testModel.setWinThreshold(0);
        assertTrue(testController.winSubBottomLeftToTopRight(0,4));
        testModel.setWinThreshold(1);
        assertTrue(testController.winSubBottomLeftToTopRight(0,4));
        testModel.setWinThreshold(2);
        assertTrue(testController.winSubBottomLeftToTopRight(1,3));
        testModel.setWinThreshold(3);
        assertTrue(testController.winSubBottomLeftToTopRight(2,2));
        assertTrue(testController.winSubBottomLeftToTopRight(3,1));
        assertTrue(testController.winSubBottomLeftToTopRight(4,0));
        testModel.setWinThreshold(4);
        assertTrue(testController.winSubBottomLeftToTopRight(3,1));
        assertTrue(testController.winSubBottomLeftToTopRight(4,0));
        for (int i = 0; i < 4; i++) {
            testModel.setCellOwner(3 - i,i + 1,player2);
        }
        assertTrue(testController.winSubBottomLeftToTopRight(3,1));
        OXOPlayer player3 = new OXOPlayer('K');
        for (int i = 0; i < 2; i++) {
            testModel.setCellOwner(1 - i,i + 1,player3);
        }
        assertEquals(testModel.getNumberOfColumns(),5);
        testModel.setWinThreshold(2);
        assertTrue(testController.winSubBottomLeftToTopRight(1,1));
        testModel.setCellOwner(2,0,player2);
        assertTrue(testController.winSubBottomLeftToTopRight(1,1));
        testModel.setCellOwner(4,1,player1);
        OXOPlayer player4 = new OXOPlayer('S');
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(3 - i,i + 2,player4);
        }
        testModel.setWinThreshold(3);
        assertTrue(testController.winSubBottomLeftToTopRight(3,2));
        assertFalse(testController.winSubBottomLeftToTopRight(4,1));
        testModel.setWinThreshold(4);
        assertFalse(testController.winSubBottomLeftToTopRight(4,1));
    }

    @Test
    void checkWinInAllDiagonals() {
        OXOPlayer player1 = new OXOPlayer('K');
        OXOModel testModel = new OXOModel(2,2,0);
        testModel.addPlayer(player1);
        OXOController testController = new OXOController(testModel);
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(),null);
        testModel.setCellOwner(0,0,player1);
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(),player1);
        testModel.setWinThreshold(1);
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(),player1);
        testModel.setCellOwner(0,0,null);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                testModel.setCellOwner(i,j,player1);
                assertTrue(testController.checkWinInAllDiagonals());
                assertEquals(testModel.getWinner(),player1);
                testModel.setCellOwner(i,j,null);
                testModel.setWinner(null);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                testModel.setCellOwner(i,j,player1);
                testModel.setCellOwner(i+1,j+1,player1);
                testModel.setWinThreshold(3);
                assertFalse(testController.checkWinInAllDiagonals());
                testModel.setWinThreshold(2);
                assertTrue(testController.checkWinInAllDiagonals());
                assertEquals(testModel.getWinner(),player1);
                testModel.setCellOwner(i,j,null);
                testModel.setCellOwner(i+1,j+1,null);
                testModel.setWinner(null);
                assertFalse(testController.checkWinInAllDiagonals());
            }
        }

        for (int l = 1; l < 9; l++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, player1);
                        testModel.setCellOwner(i + k, j + k, player1);
                    }
                    testModel.setWinThreshold(l);
                    assertTrue(testController.checkWinInAllDiagonals());
                    assertEquals(testModel.getWinner(), player1);
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, null);
                        testModel.setCellOwner(i + k, j + k, null);
                    }
                    testModel.setWinner(null);
                    assertFalse(testController.checkWinInAllDiagonals());
                }
            }
        }

        for (int l = 1; l < 9; l++) {
            for (int i = 15; i > 10; i--) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, player1);
                        testModel.setCellOwner(i - k, j + k, player1);
                    }
                    testModel.setWinThreshold(l);
                    assertTrue(testController.checkWinInAllDiagonals());
                    assertEquals(testModel.getWinner(), player1);
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, null);
                        testModel.setCellOwner(i - k, j + k, null);
                    }
                    testModel.setWinner(null);
                    assertFalse(testController.checkWinInAllDiagonals());
                }
            }
        }

        testModel = new OXOModel(3,3,2);
        testModel.addPlayer(player1);
        testController = new OXOController(testModel);
        OXOPlayer player2 = new OXOPlayer('O');
        OXOPlayer player3 = new OXOPlayer('X');
        OXOPlayer player4 = new OXOPlayer('S');
        testModel.setCellOwner(0,0,player1);
        testModel.setCellOwner(1,1,player1);
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(0 + i,1 + i,player2);
        }
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(0 + i,2 + i,player3);
        }
        for (int i = 0; i < 5; i++) {
            testModel.setCellOwner(1 + i,i,player4);
        }
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(), player1);
        testModel.setWinThreshold(3);
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(), player2);
        testModel.setCellOwner(0,1,player1);
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(), player3);
        testModel.setCellOwner(3,5,player1);
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(), player3);
        testModel.setWinThreshold(4);
        assertTrue(testController.checkWinInAllDiagonals());
        assertEquals(testModel.getWinner(), player4);
    }

    @Test
    void checkForWinner() {
        OXOPlayer player1 = new OXOPlayer('K');
        OXOModel testModel = new OXOModel(2,2,0);
        testModel.addPlayer(player1);
        OXOController testController = new OXOController(testModel);
        for (int l = 1; l < 9; l++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, player1);
                        testModel.setCellOwner(i + k, j + k, player1);
                    }
                    testModel.setWinThreshold(l);
                    assertTrue(testController.checkForWinner());
                    assertEquals(testModel.getWinner(), player1);
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, null);
                        testModel.setCellOwner(i + k, j + k, null);
                    }
                    testModel.setWinner(null);
                    assertFalse(testController.checkForWinner());
                }
            }
        }

        for (int l = 1; l < 9; l++) {
            for (int i = 15; i > 10; i--) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, player1);
                        testModel.setCellOwner(i - k, j + k, player1);
                    }
                    testModel.setWinThreshold(l);
                    assertTrue(testController.checkForWinner());
                    assertEquals(testModel.getWinner(), player1);
                    for (int k = 0; k < l; k++) {
                        testModel.setCellOwner(i, j, null);
                        testModel.setCellOwner(i - k, j + k, null);
                    }
                    testModel.setWinner(null);
                    assertFalse(testController.checkForWinner());
                }
            }
        }

        for (int l = 1; l < 9; l++) {
            for (int i = 0; i < 5; i++) {
                for (int k = 0; k < l; k++) {
                    testModel.setCellOwner(i, 0, player1);
                    testModel.setCellOwner(i + k, 0, player1);
                }
                testModel.setWinThreshold(l);
                assertTrue(testController.checkForWinner());
                assertEquals(testModel.getWinner(), player1);
                for (int k = 0; k < l; k++) {
                    testModel.setCellOwner(i, 0, null);
                    testModel.setCellOwner(i + k, 0, null);
                }
                testModel.setWinner(null);
                assertFalse(testController.checkForWinner());
            }
        }

        for (int l = 1; l < 9; l++) {
            for (int i = 0; i < 5; i++) {
                for (int k = 0; k < l; k++) {
                    testModel.setCellOwner(i, 0, player1);
                    testModel.setCellOwner(0, i + k, player1);
                }
                testModel.setWinThreshold(l);
                assertTrue(testController.checkForWinner());
                assertEquals(testModel.getWinner(), player1);
                for (int k = 0; k < l; k++) {
                    testModel.setCellOwner(i, 0, null);
                    testModel.setCellOwner(0, i + k, null);
                }
                testModel.setWinner(null);
                assertFalse(testController.checkForWinner());
            }
        }


        //rows
        player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        testModel = new OXOModel(4,4,5);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        testController = new OXOController(testModel);
        testController.userInteraction("d1");
        testController.userInteraction("c1");
        testController.userInteraction("d2");
        testController.userInteraction("c2");
        testController.userInteraction("d3");
        testController.userInteraction("c3");
        testController.userInteraction("d4");
        OXOPlayer player3 = new OXOPlayer('S');
        testModel.addPlayer(player3);
        testController.userInteraction("b1");
        testController.userInteraction("a1");
        testController.userInteraction("c4");
        testController.userInteraction("b2");
        assertFalse(testController.checkForWinner());
        testModel.setCellOwner(4,4,null);
        assertFalse(testController.checkForWinner());
        testModel.setWinThreshold(4);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player1);
        testModel.setWinThreshold(3);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player2);
        testModel.setWinThreshold(1);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player3);
        OXOPlayer player4 = new OXOPlayer('K');
        testModel.setCellOwner(1,0,player4);
        testModel.setCellOwner(1,1,player4);
        testModel.setWinThreshold(2);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player4);
        testModel.setCellOwner(1,2,player2);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player4);
        for (int i = 0; i < 6; i++) {
            testModel.setCellOwner(4,i,player4);
        }
        testModel.setWinThreshold(6);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player4);

        //top left to bottom right
        testModel = new OXOModel(3,3,2);
        testModel.addPlayer(player1);
        testController = new OXOController(testModel);
        player2 = new OXOPlayer('O');
        player3 = new OXOPlayer('X');
        player4 = new OXOPlayer('S');
        testModel.setCellOwner(0,0,player1);
        testModel.setCellOwner(1,1,player1);
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(0 + i,1 + i,player2);
        }
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(0 + i,2 + i,player3);
        }
        for (int i = 0; i < 5; i++) {
            testModel.setCellOwner(1 + i,i,player4);
        }
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player1);
        testModel.setWinThreshold(3);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player2);
        testModel.setCellOwner(0,1,player1);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player3);
        testModel.setCellOwner(3,5,player1);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player3);
        testModel.setWinThreshold(4);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player4);

        //bottom left to top right
        testModel = new OXOModel(3,3,2);
        testModel.addPlayer(player1);
        testController = new OXOController(testModel);
        player2 = new OXOPlayer('O');
        player3 = new OXOPlayer('X');
        player4 = new OXOPlayer('S');
        testModel.setCellOwner(1,0,player1);
        testModel.setCellOwner(0,1,player1);
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(2 - i,0 + i,player2);
        }
        for (int i = 0; i < 3; i++) {
            testModel.setCellOwner(2 - i,1 + i,player3);
        }
        for (int i = 0; i < 5; i++) {
            testModel.setCellOwner(4 - i,i,player4);
        }
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player1);
        testModel.setWinThreshold(3);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player2);
        testModel.setCellOwner(2,0,player1);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player3);
        testModel.setWinThreshold(4);
        testModel.setCellOwner(0,4,player1);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(), player4);
    }

    @Test
    void checkForWinnerRevisited() {
        //col
        OXOPlayer player1 = new OXOPlayer('X');
        OXOPlayer player2 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(3,3,2);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        testController.userInteraction("a4");
        testController.userInteraction("a3");
        testController.userInteraction("b4");
        testController.userInteraction("b3");
        testController.userInteraction("c4");
        testController.userInteraction("c3");
        testController.userInteraction("d4");
        OXOPlayer player3 = new OXOPlayer('S');
        testModel.addPlayer(player3);
        testController.userInteraction("a2");
        testController.userInteraction("a1");
        OXOPlayer player4 = new OXOPlayer('K');
        testModel.setCellOwner(0,1,player4);
        testModel.setCellOwner(1,1,player4);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player4);
        testModel.setWinThreshold(1);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player3);
        testModel.setWinThreshold(2);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player4);
        testModel.setWinThreshold(3);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player2);
        testModel.setWinThreshold(4);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player1);
        for (int i = 0; i < 6; i++) {
            testModel.setCellOwner(i,4,player4);
        }
        testModel.setWinThreshold(6);
        assertTrue(testController.checkForWinner());
        assertEquals(testModel.getWinner(),player4);
    }

    @Test
    void isBoardFull() {
        OXOPlayer player1 = new OXOPlayer('O');
        OXOModel testModel = new OXOModel(3,3,2);
        testModel.addPlayer(player1);
        OXOController testController = new OXOController(testModel);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                testModel.setCellOwner(row,col,player1);
            }
        }
        assertTrue(testController.isBoardFull());
        testModel.setCellOwner(2,2,null);
        assertFalse(testController.isBoardFull());
    }

    @Test
    void handleIncomingCommand() {
        OXOPlayer player1 = new OXOPlayer('O');
        OXOPlayer player2 = new OXOPlayer('X');
        OXOModel testModel = new OXOModel(3,3,2);
        testModel.addPlayer(player1);
        testModel.addPlayer(player2);
        OXOController testController = new OXOController(testModel);
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.handleIncomingCommand(""));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.handleIncomingCommand("1"));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.handleIncomingCommand("123"));
        assertThrows(InvalidIdentifierLengthException.class, ()->testController.handleIncomingCommand("123456"));
        assertThrows(OutsideCellRangeException.class, ()->testController.handleIncomingCommand("i9"));
        testModel.setCellOwner(8,8,null);
        assertDoesNotThrow(()->testController.handleIncomingCommand("i9"));
        assertThrows(CellAlreadyTakenException.class, ()->testController.handleIncomingCommand("i9"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.handleIncomingCommand("i0"));
        assertThrows(InvalidIdentifierCharacterException.class, ()->testController.handleIncomingCommand("j1"));
        assertEquals(testModel.getCellOwner(8,8),player1);
        assertEquals(testController.getCurrentPlayerIndex(),1);
        OXOPlayer player3 = new OXOPlayer('K');
        testModel.addPlayer(player3);
        assertDoesNotThrow(()->testController.handleIncomingCommand("c4"));
        assertEquals(testModel.getCellOwner(2,3),player2);
        assertEquals(testController.getCurrentPlayerIndex(),2);
    }

    @Test
    void handleIncomingCommandPart2() {
        OXOModel testModel = new OXOModel(3,3,2);
        OXOPlayer player[] = new OXOPlayer[9];
        for (int i = 0; i < 9; i++) {
            player[i] = new OXOPlayer((char)('A'+ i));
            testModel.addPlayer(player[i]);
        }
        OXOController testController = new OXOController(testModel);
        char command[] = new char[2];
        for (int i = 0; i < 3; i++) {
            command[0] = (char)('a' + i);
            for (int j = 0; j < 3; j++) {
                command[1] = (char)('1' + j);
                String input = new String(command);
                assertEquals(testController.getCurrentPlayerIndex(),i * 3 + j);
                assertDoesNotThrow(()->testController.handleIncomingCommand(input));
                if (i == 2 && j == 2) {
                    assertEquals(testModel.getWinner(),null);
                    assertEquals(testModel.isGameDrawn(),true);
                } else {
                    assertEquals(testModel.getWinner(),null);
                    assertEquals(testModel.isGameDrawn(),false);
                }
            }
        }
    }

    @Test
    void handleIncomingCommandPart3() {
        OXOModel testModel = new OXOModel(3,3,2);
        OXOPlayer player[] = new OXOPlayer[9];
        for (int i = 0; i < 9; i++) {
            player[i] = new OXOPlayer((char)('A'+ i));
            testModel.addPlayer(player[i]);
        }
        OXOController testController = new OXOController(testModel);
        char command[] = new char[2];
        testModel.setCellOwner(8,8,null);
        testModel.setWinThreshold(9);
        for (int i = 0; i < 9; i++) {
            command[0] = (char)('a' + i);
            for (int j = 0; j < 9; j++) {
                command[1] = (char)('1' + j);
                String input = new String(command);
                assertEquals(testController.getCurrentPlayerIndex(),j);
                assertDoesNotThrow(()->testController.handleIncomingCommand(input));
                if (i == 8 && j < 8) {
                    assertEquals(testModel.getWinner(),player[0]);
                    assertEquals(testModel.isGameDrawn(),false);
                }
            }
        }
        assertEquals(testController.isBoardFull(),true);
        assertEquals(testController.checkForWinner(),true);
        assertEquals(testModel.isGameDrawn(),false);
    }
}