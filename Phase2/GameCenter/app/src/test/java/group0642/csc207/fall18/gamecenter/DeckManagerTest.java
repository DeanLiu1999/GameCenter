package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeckManagerTest {

    private DeckManager d;

    private boolean contain(String c, String[] cards) {
        for (String s: cards) {
            if (s.equals(c))
                return true;
        }
        return false;
    }

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

    @Test
    public void testGetCardsTopAndInitialization() {
        d = new DeckManager();
        Card[] cards = d.getCardsTop();
        assertTrue(contain(cards[0].getData(), DeckManager.deck));
        assertTrue(contain(cards[2].getData(), DeckManager.deck));
        assertTrue(contain(cards[6].getData(), DeckManager.deck));
        assertTrue(contain(cards[11].getData(), DeckManager.deck));
    }
}
