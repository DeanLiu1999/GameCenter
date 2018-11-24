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
            hold();
    }

    void hold() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Time Delay issue...");
        }
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
}

