package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void containsTrigger() {
        Action test = new Action();
        test.triggers.add("chop");
        test.triggers.add("cut");
        test.triggers.add("cutdown");
        assertTrue(test.containsTrigger("chop wood"));
        assertTrue(test.containsTrigger("cut wood"));
        assertTrue(test.containsTrigger("cutdown wood"));
        assertTrue(test.containsTrigger("chopwood"));
        assertFalse(test.containsTrigger("fight wood"));
        assertTrue(test.containsTrigger("chop "));
        assertTrue(test.containsTrigger("chop dollar"));
    }

    @Test
    void containsSubject() {
        Action test = new Action();
        test.subjects.add("tree");
        test.subjects.add("axe");
        test.subjects.add("gun");
        assertTrue(test.containsSubject("chop tree"));
        assertTrue(test.containsSubject("chop axe"));
        assertTrue(test.containsSubject("chop gun"));
        assertFalse(test.containsSubject("chop tre"));
        assertFalse(test.containsSubject("chop ax"));
        assertFalse(test.containsSubject("chop cat"));
    }
}