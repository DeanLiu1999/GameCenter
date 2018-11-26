package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {
    private Word w;

    @Test
    public void testInitializationAndGetterMethods() {
        w = new Word("apple");
        assertEquals(w.getHealth(), 10);
        assertFalse(w.win());
    }

    @Test
    public void testSetHealthMethod() {
        w = new Word("apple");
        w.setHealth(15);
        assertEquals(w.getHealth(), 15);
    }

    @Test
    public void testEnterAndGetDisplayMethodsAndWin() {
        w = new Word("apple");
        assertEquals(w.getDisplay(), "_ _ _ _ _");
        w.enter("a");
        assertEquals(w.getDisplay(), "a _ _ _ _");
        w.enter("p");
        w.enter("l");
        assertFalse(w.win());
        w.enter("e");
        assertEquals(w.getDisplay(), "a p p l e");
        assertTrue(w.win());
    }

    @Test
    public void testEnteredWrongLetterAndGetHealthMethod() {
        w = new Word("apple");
        w.setHealth(2);
        w.enter("t");
        assertEquals(w.getDisplay(), "_ _ _ _ _");
        assertEquals(w.getHealth(), 1);
        w.enter("v");
        assertEquals(w.getHealth(), 0);
    }

}
