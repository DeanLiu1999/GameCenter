package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BoardManagerTest {

    /**
     * The board manager for testing.
     */
    private BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpSolved() {
        Board.setGameSize(4);
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles);
        boardManager = new BoardManager(board);
    }

    /**
     * Make a shuffled Board.
     */
    private void setUpShuffled(int size) {
        boardManager = new BoardManager();
        boardManager.setGameSize(size);
        boardManager.refresh_board_manager();
    }

    /**
     * Swap the positions first two tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Returns a List of  all Tiles in a given Board in row-major order.
     *
     * @param board the Board
     * @return a List of all Tiles in the given Board in row-major order.
     */
    private List<Tile> boardToList(Board board) {
        List<Tile> tiles = new ArrayList<>();
        Iterator<Tile> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            Tile tile = boardIterator.next();
            if (tile.getId() != board.numTiles()) {
                tiles.add(tile);
            }
        }
        return tiles;
    }

    /**
     * Returns layout information about a given board as [board width, blank tile row],
     * where blank tile row is the number of rows that the blank tile is from the bottom.
     * N.B. the bottom row is 1.
     *
     * @param board the Board
     * @return the board layout [board width, blank tile row]
     */
    private int[] getBoardLayout(Board board) {
        int[] layout = new int[] {0, 0};
        int boardWidth = (int) Math.sqrt(board.numTiles());
        layout[0] = boardWidth;
        if (boardWidth % 2 == 0) {
            int blankId = board.numTiles();
            Iterator<Tile> boardIterator = board.iterator();
            int row = 0;
            int col = 0;
            while (boardIterator.hasNext() && layout[1] == 0) {
                Tile currentTile = boardIterator.next();
                if (currentTile.getId() == blankId) {
                    layout[1] = boardWidth - row;
                } else if (col == boardWidth - 1) {
                    col = 0;
                    row ++;
                } else {
                    col++;
                }
            }
        }
        return layout;
    }

    /**
     * Returns the number of inversions of a given tiles puzzle, represented by an
     * ArrayList of Tiles.
     *
     * @param tiles an ArrayList of Tiles
     * @return the number of inversions of this tiles puzzle
     */
    private int getInversionCount(List<Tile> tiles){
        int inversionCount = 0;
        int totalTiles = tiles.size();
        for (int current = 0; current < totalTiles - 1; current++) {
            for (int other = current + 1; other < totalTiles; other++) {
                Tile thisTile = tiles.get(current);
                Tile otherTile = tiles.get(other);
                if (thisTile.compareTo(otherTile) < 0) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    /**
     * Helper method to testSolvableAfterShuffle.
     * Asserts that the given board is solvable, and returns an ArrayList representation of
     * the given board.
     *
     * Tiles puzzle solvability formula from:
     * https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     * Retrieved Nov. 17, 2018
     *
     * @param board the Board
     * @return an ArrayList representation of the given board.
     */
    private List<Tile> isSolvable(Board board) {
        int[] boardLayout = getBoardLayout(board);
        List<Tile> tilesList = boardToList(board);
        int inversionCount = getInversionCount(tilesList);
        if (boardLayout[0] % 2 == 0) {
            assertEquals(boardLayout[1] % 2 != 0, inversionCount % 2 == 0);
        } else if (boardLayout[0] % 2 != 0) {
            assertEquals(inversionCount % 2, 0);
        }
        return tilesList;
    }

    /**
     * Test whether the game can distinguish between a solved and an unsolved board.
     */
    @Test
    public void testPuzzleIsSolved() {
        setUpSolved();
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test that the new shuffling algorithm can generate different boards that are all solvable.
     *
     * Tiles puzzle solvability formula from:
     * https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     * Retrieved Nov. 17, 2018
     */
    @Test
    public void testSolvableAfterShuffle() {

        setUpShuffled(3);
        List<Tile> tilesList1 = isSolvable(boardManager.getBoard());
        setUpShuffled(3);
        List<Tile> tilesList2 = isSolvable(boardManager.getBoard());
        assertNotEquals(tilesList1, tilesList2);

        setUpShuffled(4);
        tilesList1 = isSolvable(boardManager.getBoard());
        setUpShuffled(4);
        tilesList2 = isSolvable(boardManager.getBoard());
        assertNotEquals(tilesList1, tilesList2);

        setUpShuffled(5);
        tilesList1 = isSolvable(boardManager.getBoard());
        setUpShuffled(5);
        tilesList2 = isSolvable(boardManager.getBoard());
        assertNotEquals(tilesList1, tilesList2);
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpSolved();
        assertTrue(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(14));
        assertFalse(boardManager.isValidTap(10));
    }

    /**
     * Test that touchMove moves tiles correctly.
     */
    @Test
    public void testTouchMove_MovesCorrectTile(){
        Tile blank;
        setUpSolved();
        boardManager.getBoard().swapTiles(2, 2, 3, 3);

        boardManager.touchMove(0);
        blank = boardManager.getBoard().getTile(2,2);
        assertEquals(blank.getId(), 16);

        boardManager.touchMove(6);
        blank = boardManager.getBoard().getTile(1,2);
        assertEquals(blank.getId(), 16);

        boardManager.touchMove(5);
        blank = boardManager.getBoard().getTile(1,1);
        assertEquals(blank.getId(), 16);

        boardManager.touchMove(9);
        blank = boardManager.getBoard().getTile(2,1);
        assertEquals(blank.getId(), 16);

        boardManager.touchMove(10);
        blank = boardManager.getBoard().getTile(2,2);
        assertEquals(blank.getId(), 16);
    }

    /**
     * Test undo functionality whilst setting the undo count with a positive number.
     */
    @Test
    public void testUndo_UndoSetToPositive(){
        Tile blank;
        setUpSolved();
        boardManager.setUndo(2);
        boardManager.touchMove(14);
        boardManager.touchMove(10);
        boardManager.touchMove(9);
        boardManager.touchMove(8);
        blank = boardManager.getBoard().getTile(2,0);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        blank = boardManager.getBoard().getTile(2,1);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        blank = boardManager.getBoard().getTile(2,2);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        // TODO: blank tile should be at (2,2)
        blank = boardManager.getBoard().getTile(3,2);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        blank = boardManager.getBoard().getTile(3,2);
        assertEquals(blank.getId(), 16);
    }

    /**
     *  Test undo functionality whilst setting the undo count with a negative number.
     */
    @Test
    public void testUndo_UndoSetToNegative(){
        Tile blank;
        setUpSolved();
        boardManager.getBoard().swapTiles(0, 0, 3, 3);
        boardManager.setUndo(-2);
        boardManager.touchMove(1);
        boardManager.touchMove(5);
        boardManager.touchMove(6);
        boardManager.touchMove(7);
        blank = boardManager.getBoard().getTile(1,3);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        blank = boardManager.getBoard().getTile(1,2);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        blank = boardManager.getBoard().getTile(1,1);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        // TODO: blank tile should be at (1,1)
        blank = boardManager.getBoard().getTile(0,1);
        assertEquals(blank.getId(), 16);

        boardManager.undoStep();
        blank = boardManager.getBoard().getTile(0,1);
        assertEquals(blank.getId(), 16);
    }

    /**
     * Test that setGameSize correctly sets the game size.
     */
    @Test
    public void testSetGameSize_CanSet(){
        setUpSolved();
        boardManager.setGameSize(5);
        assertEquals(boardManager.getBoard().numTiles(), 25);
        boardManager.setGameSize(100);
        assertEquals(boardManager.getBoard().numTiles(), 16);
    }

    /**
     * Test that the game is correctly reporting the current score.
     */
    @Test
    public void testReportScore() {
        setUpSolved();
        assertEquals(boardManager.reportScore(), 1000);

        boardManager.touchMove(14);
        assertEquals(boardManager.reportScore(), 999);
    }
}
