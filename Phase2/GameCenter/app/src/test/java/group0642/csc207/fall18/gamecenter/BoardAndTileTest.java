package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    Board b;

    @Test
    public void testSpecialTile() {
        Board.setGameSize(3);
        Tile.setImages(1);
        Tile t = new Tile(5);
        Tile t2 = new Tile(4);
        assertEquals(t.getBackground(), R.drawable.ftile_8);
        assertEquals(t.getId(), 6);
        assertEquals(t.compareTo(t2), -1);
    }

    @Test
    public void testGettersAndSettersAndRegularInitialization() {
        Tile.setImages(0);
        Tile t = new Tile(0);
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(t);
        Board.setGameSize(1);
        b = new Board(tiles);
        Tile.setImages(0);
        assertEquals(b.getScore(), 1000);
        assertEquals(b.getTile(0, 0), t);
        b.setScore(80);
        assertEquals(b.getScore(), 80);
        assertEquals(b.numTiles(), 1);
    }

    @Test
    public void testMakeMoveAndUndo() {
        Tile.setImages(0);
        Board.setGameSize(4);

    }


}