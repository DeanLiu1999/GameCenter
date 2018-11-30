package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * manage money during the game
 */
class BankManager implements Serializable {
    private Integer wager;
    private Integer bank;

    /**
     * set the initial value of bank to 800
     * set the initial value of wager to 0
     */
    BankManager() {
        bank = 800;
        wager = 0;
    }

    /**
     * return wager
     *
     * @return wager
     */
    Integer getWager() {
        return wager;
    }

    /**
     * return bank
     *
     * @return bank
     */
    Integer getBank() {
        return bank;
    }

    /**
     * add the input amount of money from bank to wager
     *
     * @param w the amount of money you want to add
     * @return true if input <= bank, false otherwise
     */
    boolean addWager(Integer w) {
        if (w <= bank) {
            bank -= w;
            wager += w;
            return true;
        }
        return false;
    }

    /**
     * add twice the wager to bank if game win, 0 otherwise
     * set wager to 0
     *
     * @param result game result
     */
    void checkOut(boolean result) {
        if (result) {
            bank += 2 * wager;
        }
        wager = 0;
    }

    /**
     * add all money in the bank to wager
     */
    void allIn() {
        addWager(bank);
    }

    /**
     * return whether the whole game is over
     *
     * @return whether the whole game is over
     */
    boolean gameOver() {
        return getBank().equals(0) && getWager().equals(0);
    }

    /**
     * return true if wager is zero
     *
     * @return whether wager is zero or not
     */
    boolean wagerIsZero() {
        return getWager().equals(0);
    }

    /**
     * put the wager back to the bank
     */
    void undo() {
        bank = bank + wager;
        wager = 0;
    }
}
