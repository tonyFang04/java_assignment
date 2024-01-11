package Controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void simpleTest() {
        Tokenizer tokenizer = new Tokenizer("Tony: goto forest.");
        assertEquals(tokenizer.getName(),"Tony");
        assertEquals(tokenizer.getCommand()," goto forest.");

        Tokenizer tokenizer1 = new Tokenizer("A:inv");
        assertEquals(tokenizer1.getName(),"A");
        assertEquals(tokenizer1.getCommand(),"inv");
    }
}