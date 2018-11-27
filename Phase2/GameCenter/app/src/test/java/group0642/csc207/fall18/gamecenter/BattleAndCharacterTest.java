package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class BattleAndCharacterTest {

    private Battle b;

    @Test
    public void testGettersAndSettersAndRegularInitialization() {
        b = new Battle();
        int [] info = {1000, 200, 1400, 200};
        assertArrayEquals(b.getInfo(), info);
    }

    @Test
    public void testGettersAndSettersAndSpecialInitialization() {
        b = new Battle(2, 1);
        int [] info = {1200, 250, 1700, 300};
        assertArrayEquals(b.getInfo(), info);
    }

    @Test
    public void testMovements() {
        b = new Battle();
        assertTrue(b.makeMove(true));
        assertEquals(b.getInfo()[0], 800);
        assertTrue(b.makeMove(false));
        assertEquals(b.getInfo()[2], 1200);
    }

    @Test
    public void testUnreachableSituation() {
        b = new Battle(-10, -10);
        assertFalse(b.makeMove(true));
        assertEquals(b.getInfo().length, 1);
    }
}

