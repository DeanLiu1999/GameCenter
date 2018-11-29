package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.*;

class StateManager implements Serializable {

    private int stageP;

    private int stageC;

    private Card[] playerCards;

    private Card[] computerCards;

    private DeckManager d = new DeckManager();

    static final Integer[] cardsId = {R.drawable.club_a, R.drawable.club_2, R.drawable.club_3,
            R.drawable.club_4, R.drawable.club_5, R.drawable.club_6, R.drawable.club_7,
            R.drawable.club_8, R.drawable.club_9, R.drawable.club_10, R.drawable.club_j,
            R.drawable.club_q, R.drawable.club_k,};

    /**
     * Manage a new black jack game state.
     */
    StateManager() {
        stageP = 1;
        stageC = 0;
        this.playerCards = Arrays.copyOfRange(d.getCardsTop(), 0, 6);
        this.computerCards = Arrays.copyOfRange(d.getCardsTop(), 6, 12);
    }

    /**
     * Return the current score for player.
     *
     * @return the player score
     */
    int getPlayerScore() {
        return d.calculateScore(stageP, playerCards);
    }

    /**
     * Return the current score for computer.
     *
     * @return the computer score
     */
    int getComputerScore() {
        return d.calculateScore(stageC, computerCards);
    }

    /**
     * Process a hit request.
     */
    void hit() {
        if (stageP < 5)
            stageP += 1;
    }

    /**
     * Process a stand request.
     */
    void stand() {
        ++stageC;
    }

    /**
     * Return stageC, 1 less than the number of cards displayed for computer.
     *
     * @return stageC
     */
    int getStageC() {
        return stageC;
    }

    /**
     * Return stageP, 1 less than the number of cards displayed for player.
     *
     * @return stageP
     */
    int getStageP() {
        return stageP;
    }

    /**
     * Return true iff the game ends
     *
     * @return gameEnd
     */
    boolean gameEnd() {
        return stageC >= 1 || getPlayerScore() == 0;
    }

    /**
     * Return true iff the player wins
     *
     * @return playerWin
     */
    boolean gameWin() {
        return getPlayerScore() >= getComputerScore();
    }

    /**
     * Return the computer cards type
     *
     * @return the computer cards in String
     */
    String[] getComputerCardsStr() {
        String[] s = new String[6];
        for (int i = 0; i < 6; i++) {
            s[i] = computerCards[i].getData();
        }
        return s;
    }

    /**
     * Return the player cards type
     *
     * @return the player cards in String
     */
    String[] getPlayerCardsStr() {
        String[] s = new String[6];
        for (int i = 0; i < 6; i++) {
            s[i] = playerCards[i].getData();
        }
        return s;
    }

    /**
     * Return true iff the game is at its initial stage
     *
     * @return true iff the game is at its initial stage
     */
    boolean initialStage() {
        return getStageP() == 1 && getStageC() == 0;
    }


    /**
     * Return the cards id  based on cards type
     *
     * @param cardStr the cards type in String
     * @return the cards id
     */
    private Integer[] strToCardsId(String[] cardStr) {
        Integer[] cards = new Integer[6];
        for (int i = 0; i < 6; i++) {
            switch (cardStr[i]) {
                case "A":
                    cards[i] = cardsId[0];
                    break;
                case "J":
                    cards[i] = cardsId[10];
                    break;
                case "Q":
                    cards[i] = cardsId[11];
                    break;
                case "K":
                    cards[i] = cardsId[12];
                    break;
                default:
                    cards[i] = cardsId[Integer.parseInt(cardStr[i]) - 1];
                    break;
            }
        }
        return cards;
    }

    /**
     * Return the computer cards id
     *
     * @return the computer cards id
     */
    Integer[] getComputerCardsId() {
        return strToCardsId(getComputerCardsStr());
    }

    /**
     * Return the player cards id
     *
     * @return the player cards id
     */
    Integer[] getPlayerCardsId() {
        return strToCardsId(getPlayerCardsStr());
    }
}

