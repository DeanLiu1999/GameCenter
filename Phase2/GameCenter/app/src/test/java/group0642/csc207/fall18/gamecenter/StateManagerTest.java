package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class StateManagerTest {

    private StateManager s;

    @Test
    public void testInitialization() {
        s = new StateManager();
        assertEquals(s.getStageC(), 0);
        assertEquals(s.getStageP(), 1);
        assertTrue(s.initialStage());
        assertFalse(s.gameEnd());
    }

    @Test
    public void testHitAndStand() {
        s = new StateManager();
        s.hit();
        assertEquals(s.getStageC(), 0);
        assertEquals(s.getStageP(), 2);
        s.stand();
        assertEquals(s.getStageC(), 1);
        assertEquals(s.getStageP(), 2);
    }

    @Test
    public void testInternalStructures() {
        s = new StateManager();
        Card c = new Card(s.getComputerCardsStr()[0]);
        assertEquals(s.getComputerScore(), c.getInfo());
        Card c1 = new Card(s.getPlayerCardsStr()[0]);
        Card c2 = new Card(s.getPlayerCardsStr()[1]);
        assertTrue(s.getPlayerScore() == c1.getInfo() + c2.getInfo() ||
        s.getPlayerScore() == 12);
        assertEquals(s.gameWin(), (s.getPlayerScore() >= s.getComputerScore()));
    }
}

