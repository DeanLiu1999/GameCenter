package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    public int p = 0; // This keeps track of the blank tile's position

    static int undo = 3;

    private int[] allMoves = new int[1000];

    private int current_state;

    private int max_state_reached = 0;

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
        allMoves[getCurrent_state()] = p;
        current_state = getCurrent_state() + 1;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
        allMoves[getCurrent_state()] = p;
        current_state = getCurrent_state() + 1;
        if (getCurrent_state() > max_state_reached)
            max_state_reached++;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        Board b = getBoard();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                if (b.getTile(row, col).getId() != row * Board.NUM_COLS + col + 1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Refresh the board manager.
     */
    void refresh_board_manager(){
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        return generateCase(row, col) > 0;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        p = position;
        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int situation = generateCase(row, col);
        if (situation == 1) {
            board.swapTiles(row, col, row - 1, col);
            p -= Board.NUM_ROWS;
        }
        else if (situation == 2) {
            board.swapTiles(row, col, row + 1, col);
            p += Board.NUM_ROWS;
        }
        else if (situation == 3) {
            board.swapTiles(row, col, row, col - 1);
            p--;
        }
        else if (situation == 4) {
            board.swapTiles(row, col, row, col + 1);
            p++;
        }
        else
            return;
        allMoves[getCurrent_state()] = p;
        current_state = getCurrent_state() + 1;
        if (getCurrent_state() > max_state_reached)
            max_state_reached++;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param row the row of tile to check
     * @param col the col of tile to check
     * @return an integer between 0 and 4 representing the situation
     */
    private int generateCase(int row, int col) {
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        //1 for above, 2 for below, 3 for left, 4 for right, and 0 for default
        Tile[] neighbours = {above, below, left, right};
        for (int i = 0; i < 4; i++) {
            if (neighbours[i] != null && neighbours[i].getId() == board.numTiles())
                return i + 1;
        }
        return 0;
    }

    /**
     * Set the size of the game to n by n.
     *
     * @param n the size of the game
     */
    void setGameSize(int n) {
        // n should be between 3 and 5
        if (n > 5 || n < 3)
            Board.NUM_COLS = Board.NUM_ROWS = 4;
        else
            Board.NUM_COLS = Board.NUM_ROWS = n;
    }

    /**
     * Set the maximum steps of undo to n.
     *
     * @param n the maximum steps of undo set by the user
     */
    public void setUndo(int n) {
        if (n < 0)
            n = 0 - n;
        undo = n;
    }

    /**
     * Return the current score for this player.
     *
     * @return the score in the current stage
     */
    int reportScore() {
        return board.getScore();
    }

    private int returnRemainingUndo(){
        return undo - (max_state_reached - getCurrent_state());
    }

    /**
     * Undo by 1 step.
     */
    void undoStep() {
        touchBackMove();
    }

    /**
     * Process an undo request.
     */
    private void touchBackMove() {
        if (returnRemainingUndo() == 0)
            return;
        if (getCurrent_state() > 0)
            current_state = getCurrent_state() - 1;
        p = allMoves[getCurrent_state()];
        int row = p / Board.NUM_COLS;
        int col = p % Board.NUM_COLS;
        int situation = generateCase(row, col);
        if (situation == 1) {
            board.swapTiles(row, col, row - 1, col);
        }
        else if (situation == 2) {
            board.swapTiles(row, col, row + 1, col);
        }
        else if (situation == 3) {
            board.swapTiles(row, col, row, col - 1);
        }
        else if (situation == 4) {
            board.swapTiles(row, col, row, col + 1);
        }
    }

    /**
     * Return the current state number.
     */
    int getCurrent_state() {
        return current_state;
    }
}