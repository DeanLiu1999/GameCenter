package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class BankManagerTest {

    private BankManager b;

    @Test
    public void testGetMethodsAfterInitialization() {
        b = new BankManager();
        assertEquals((int) b.getBank(), 800);
        assertEquals((int) b.getWager(), 0);
    }

    @Test
    public void testAddWagerAndAllIn() {
        b = new BankManager();
        b.addWager(500);
        assertEquals((int) b.getBank(), 300);
        assertEquals((int) b.getWager(), 500);
        b.addWager(500);
        assertEquals((int) b.getBank(), 300);
        assertEquals((int) b.getWager(), 500);
        b.allIn();
        assertEquals((int) b.getBank(), 0);
        assertEquals((int) b.getWager(), 800);
    }

    @Test
    public void testCheckOut() {
        b = new BankManager();
        b.addWager(200);
        b.checkOut(false);
        assertEquals((int) b.getBank(), 600);
        assertEquals((int) b.getWager(), 0);
        b.addWager(300);
        b.checkOut(true);
        assertEquals((int) b.getBank(), 900);
        assertEquals((int) b.getWager(), 0);
    }
}

