package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class StateManagerTest {

    private StateManager s;

    private static final String [] reference = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "J", "Q", "K"};

    // I need to test randomness of the cards but I only get to test 12 cards each time. Therefore
    // I choose to test several times to enhance code coverage.

    @Test
    public void testInitializationAndBooleans() {
        s = new StateManager();
        assertTrue(s.initialStage());
        assertFalse(s.gameEnd());
        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getComputerCardsStr()[i]);
            assertEquals((int) s.getComputerCardsId()[i], (int) StateManager.cardsId[j]);
        }

        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getPlayerCardsStr()[i]);
            assertEquals((int) s.getPlayerCardsId()[i], (int) StateManager.cardsId[j]);
        }
    }

    @Test
    public void testInitializationAndGetters() {
        s = new StateManager();
        assertEquals(s.getStageC(), 0);
        assertEquals(s.getStageP(), 1);
        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getComputerCardsStr()[i]);
            assertEquals((int) s.getComputerCardsId()[i], (int) StateManager.cardsId[j]);
        }

        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getPlayerCardsStr()[i]);
            assertEquals((int) s.getPlayerCardsId()[i], (int) StateManager.cardsId[j]);
        }
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
        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getComputerCardsStr()[i]);
            assertEquals((int) s.getComputerCardsId()[i], (int) StateManager.cardsId[j]);
        }

        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getPlayerCardsStr()[i]);
            assertEquals((int) s.getPlayerCardsId()[i], (int) StateManager.cardsId[j]);
        }
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
        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getComputerCardsStr()[i]);
            assertEquals((int) s.getComputerCardsId()[i], (int) StateManager.cardsId[j]);
        }

        for (int i = 0; i < 6; i++) {
            List<String> referenceList = Arrays.asList(reference);
            int j = referenceList.indexOf(s.getPlayerCardsStr()[i]);
            assertEquals((int) s.getPlayerCardsId()[i], (int) StateManager.cardsId[j]);
        }
    }
}

