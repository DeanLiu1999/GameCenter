package group0642.csc207.fall18.gamecenter;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    private int score = 1000;

    /**
     * The number of rows.
     */
    static int NUM_ROWS;

    /**
     * The number of rows.
     */
    static int NUM_COLS;

    int bg;
    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iteration = tiles.iterator();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iteration.next();
            }
        }
        this.bg = Tile.bg;
    }

    static void setGameSize(int n) {
        // n should be between 3 and 5
        Board.NUM_COLS = Board.NUM_ROWS = n;
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /**
     * Return the current score
     *
     * @return score
     */
    int getScore() {
        return score;
    }

    /**
     * The TileIterator class inside class Board.
     */
    public class TileIterator implements Iterator<Tile> {

        /**
         * The current position.
         */
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < numTiles();
        }

        @Override
        public Tile next() {
            Tile tile;
            tile = getTile(current / Board.NUM_COLS, current % Board.NUM_COLS);
            current += 1;
            return tile;
        }
    }
    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        score = getScore() - 1;
        //Save the two tiles and then swap
        Tile t1 = getTile(row1, col1);
        Tile t2 = getTile(row2, col2);
        tiles[row1][col1] = t2;
        tiles[row2][col2] = t1;
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}
