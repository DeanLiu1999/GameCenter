package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    private Card c;

    @Test
    public void testNumericCardData() {
        c = new Card("6");
        assertEquals(c.getData(), "6");
    }

    @Test
    public void testNumericCardInfo() {
        c = new Card("8");
        assertEquals(c.getInfo(), 8);
    }

    @Test
    public void testSpecialCardInfo() {
        c = new Card("A");
        assertEquals(c.getInfo(), 11);
    }

    @Test
    public void testSpecialCardData() {
        c = new Card("J");
        assertEquals(c.getData(), "J");
    }
}
