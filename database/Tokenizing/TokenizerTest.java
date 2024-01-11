package Tokenizing;

import DatabaseException.BNFSyntaxException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void isDelimiter() {
        Tokenizer tokenizer = new Tokenizer();
        assertTrue(tokenizer.isDelimiter(')'));
        assertTrue(tokenizer.isDelimiter('('));
        assertTrue(tokenizer.isDelimiter(' '));
        assertTrue(tokenizer.isDelimiter('\''));
        assertTrue(tokenizer.isDelimiter(';'));
        assertTrue(tokenizer.isDelimiter(','));
        int j = 0;
        for (char i = 0; i < 256; i++) {
            if (tokenizer.isDelimiter(i)) {
                j++;
            }
        }
        assertEquals(j,6);
    }

    @Test
    void addDelimiter() {
        Tokenizer tokenizer = new Tokenizer();
        assertEquals(tokenizer.addDelimiter(" ("),"(");
        assertThrows(ArrayIndexOutOfBoundsException.class,()->tokenizer.getTokenAt(0));

        Tokenizer tokenizer2 = new Tokenizer();
        assertEquals(tokenizer2.addDelimiter("( ")," ");
        assertEquals(tokenizer2.getTokenAt(0),"(");

        tokenizer2 = new Tokenizer();
        assertEquals(tokenizer2.addDelimiter("'8"),"8");
        assertEquals(tokenizer2.getTokenAt(0),"'");
    }

    @Test
    void addWord() {
        Tokenizer tokenizer = new Tokenizer();
        assertEquals(tokenizer.addWord("USE ")," ");
        assertEquals(tokenizer.getTokenAt(0),"USE");

        tokenizer = new Tokenizer();
        assertEquals(tokenizer.addWord("USE;"),";");
        assertEquals(tokenizer.getTokenAt(0),"USE");
    }

    @Test
    void checkInput() {
        Tokenizer tokenizer = new Tokenizer();
        assertThrows(BNFSyntaxException.class,()->tokenizer.checkInput(null),"[ERROR]: Command cannot be null");
        assertThrows(BNFSyntaxException.class,()->tokenizer.checkInput("USE"),"[ERROR]: Semi colon missing at end of line");
        assertThrows(BNFSyntaxException.class,()->tokenizer.checkInput("USE "),"[ERROR]: Semi colon missing at end of line");
        assertDoesNotThrow(()->tokenizer.checkInput("USE;"));
    }

    @Test
    void tokenizeIncomingCommands() {
        Tokenizer tokenizer = new Tokenizer();
        assertThrows(BNFSyntaxException.class,()->tokenizer.tokenizeIncomingCommands(null),"[ERROR]: Command cannot be null");
        assertDoesNotThrow(()->tokenizer.tokenizeIncomingCommands("CREATE DATABASE markbook;"));
        assertEquals(tokenizer.getTokenAt(0),"CREATE");
        assertEquals(tokenizer.getTokenAt(1),"DATABASE");
        assertEquals(tokenizer.getTokenAt(2),"markbook");
        assertEquals(tokenizer.getTokenAt(3),";");

        Tokenizer tokenizer2 = new Tokenizer();
        assertDoesNotThrow(()->tokenizer2.tokenizeIncomingCommands("CREATE TABLE marks (name, mark, pass);"));
        assertEquals(tokenizer2.getTokenAt(0),"CREATE");
        assertEquals(tokenizer2.getTokenAt(1),"TABLE");
        assertEquals(tokenizer2.getTokenAt(2),"marks");
        assertEquals(tokenizer2.getTokenAt(3),"(");
        assertEquals(tokenizer2.getTokenAt(4),"name");
        assertEquals(tokenizer2.getTokenAt(5),",");
        assertEquals(tokenizer2.getTokenAt(6),"mark");
        assertEquals(tokenizer2.getTokenAt(7),",");
        assertEquals(tokenizer2.getTokenAt(8),"pass");
        assertEquals(tokenizer2.getTokenAt(9),")");
        assertEquals(tokenizer2.getTokenAt(10),";");

        Tokenizer tokenizer3 = new Tokenizer();
        assertDoesNotThrow(()->tokenizer3.tokenizeIncomingCommands("INSERT INTO marks VALUES ('Bob', 35, false);"));
        assertEquals(tokenizer3.getTokenAt(0),"INSERT");
        assertEquals(tokenizer3.getTokenAt(1),"INTO");
        assertEquals(tokenizer3.getTokenAt(2),"marks");
        assertEquals(tokenizer3.getTokenAt(3),"VALUES");
        assertEquals(tokenizer3.getTokenAt(4),"(");
        assertEquals(tokenizer3.getTokenAt(5),"'");
        assertEquals(tokenizer3.getTokenAt(6),"Bob");
        assertEquals(tokenizer3.getTokenAt(7),"'");
        assertEquals(tokenizer3.getTokenAt(8),",");
        assertEquals(tokenizer3.getTokenAt(9),"35");
        assertEquals(tokenizer3.getTokenAt(10),",");
        assertEquals(tokenizer3.getTokenAt(11),"false");
        assertEquals(tokenizer3.getTokenAt(12),")");
        assertEquals(tokenizer3.getTokenAt(13),";");

        Tokenizer tokenizer4 = new Tokenizer();
        assertDoesNotThrow(()->tokenizer4.tokenizeIncomingCommands("SELECT * FROM marks;"));
        assertEquals(tokenizer4.getNoOfTokens(),5);

        Tokenizer tokenizer5 = new Tokenizer();
        assertDoesNotThrow(()->tokenizer5.tokenizeIncomingCommands("SELECT * FROM marks WHERE name != 'Dave';"));
        assertEquals(tokenizer5.getNoOfTokens(),11);

        Tokenizer tokenizer6 = new Tokenizer();
        assertDoesNotThrow(()->tokenizer6.tokenizeIncomingCommands("UPDATE marks SET mark = 38 WHERE name == 'Clive';"));
        assertEquals(tokenizer6.getNoOfTokens(),13);

        Tokenizer tokenizer7 = new Tokenizer();
        assertDoesNotThrow(()->tokenizer7.tokenizeIncomingCommands("DELETE FROM marks WHERE mark < 40;"));
        assertEquals(tokenizer7.getNoOfTokens(),8);

        Tokenizer tokenizer8 = new Tokenizer();
        assertDoesNotThrow(()->tokenizer8.tokenizeIncomingCommands(";;"));
        assertEquals(tokenizer8.getNoOfTokens(),2);
        assertEquals(tokenizer8.getTokenAt(0),";");
        assertEquals(tokenizer8.getTokenAt(1),";");
    }
}