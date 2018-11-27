package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.*;

class StateManager implements Serializable {

    private int stageP;

    private int stageC;

    private Card[] playerCards;

    private Card[] computerCards;

    private DeckManager d = new DeckManager();

    StateManager() {
        stageP = 1;
        stageC = 0;
        this.playerCards = Arrays.copyOfRange(d.getCardsTop(), 0, 6);
        this.computerCards = Arrays.copyOfRange(d.getCardsTop(), 6, 12);
    }

    int getPlayerScore() {
        return d.calculateScore(stageP, playerCards);
    }

    int getComputerScore() {
        return d.calculateScore(stageC, computerCards);
    }

    void hit() {
        if (stageP < 5)
            stageP += 1;
    }

    void stand() {
        ++stageC;
    }

    int getStageC() {
        return stageC;
    }

    int getStageP() {
        return stageP;
    }

    boolean gameEnd() {
        return stageC >= 1 || getPlayerScore() == 0;
    }

    boolean gameWin() {
        return getPlayerScore() >= getComputerScore();
    }

    String[] getComputerCardsStr() {
        String[] s = new String[6];
        for (int i = 0; i < 6; i++) {
            s[i] = computerCards[i].getData();
        }
        return s;
    }

    String[] getPlayerCardsStr() {
        String[] s = new String[6];
        for (int i = 0; i < 6; i++) {
            s[i] = playerCards[i].getData();
        }
        return s;
    }

    boolean initialStage() {
        return getStageP() == 1 && getStageC() == 0;
    }

    private Integer[] strToCardsId(String[] cardStr) {
        Integer[] cardsId = {R.drawable.club_a, R.drawable.club_2, R.drawable.club_3, R.drawable.club_4,
                R.drawable.club_5, R.drawable.club_6, R.drawable.club_7, R.drawable.club_8,
                R.drawable.club_9, R.drawable.club_10, R.drawable.club_j, R.drawable.club_q,
                R.drawable.club_k,};
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

    Integer[] getComputerCardsId() {
        return strToCardsId(getComputerCardsStr());
    }

    Integer[] getPlayerCardsId() {
        return strToCardsId(getPlayerCardsStr());
    }
}

