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
}
