package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeckManagerTest {

    private DeckManager d;

    @Test
    public void testCalculateScore() {
        d = new DeckManager();
        Card[] cards = {new Card("A"), new Card("J"), new Card("9"), new Card("Q"),
                new Card("2"), new Card("3")};
        assertEquals(d.calculateScore(0, cards), 11);
        assertEquals(d.calculateScore(1, cards), 21);
        assertEquals(d.calculateScore(2, cards), 20);
        assertEquals(d.calculateScore(3, cards), 0);
    }
}
