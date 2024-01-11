package Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtefactContainerTest {

    @Test
    void getArtefactName() {
        ArtefactContainer test = new ArtefactContainer();
        test.artefactNames.add("potion");
        test.artefactNames.add("magic wand");
        test.artefactNames.add("excalibur");
        test.artefactNames.add("gun");
        assertEquals(test.getArtefactName(" get gun"),"gun");
        assertEquals(test.getArtefactName(" get shotgun"),"gun");
        assertEquals(test.getArtefactName(" get g"),null);
        assertEquals(test.getArtefactName(" get gat"),null);
        assertEquals(test.getArtefactName("get gun"),"gun");
        assertEquals(test.getArtefactName("get potion"),"potion");
        assertEquals(test.getArtefactName("get magic wand"),"magic wand");
        assertEquals(test.getArtefactName("get excalibur"),"excalibur");
        assertEquals(test.getArtefactName("get excalibur gun"),"excalibur");

    }
}