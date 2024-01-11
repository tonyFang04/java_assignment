package Entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void listArtefacts() {
        Player test = new Player();
        test.artefactNames = new ArrayList<>();
        assertEquals(test.listArtefacts(),"List of your artefacts:\n");
        test.artefactNames.add("axe");
        assertEquals(test.listArtefacts(),"List of your artefacts:\naxe\n");
        test.artefactNames.add("coin");
        assertEquals(test.listArtefacts(),"List of your artefacts:\naxe\ncoin\n");
        test.artefactNames.add("potion");
        test.artefactNames.add("gun");
        test.artefactNames.add("knife");
        assertEquals(test.listArtefacts(),"List of your artefacts:\naxe\ncoin\npotion\ngun\nknife\n");
    }
}