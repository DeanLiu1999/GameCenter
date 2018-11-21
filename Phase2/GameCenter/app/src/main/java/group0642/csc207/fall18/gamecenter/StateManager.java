package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.*;

public class StateManager implements Serializable {

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
        stageC = 2;
        while (getComputerScore() < 16 && 0 < getComputerScore() && stageC < 5)
            ++stageC;
    }

    public int getStageC() {
        return stageC;
    }

    public int getStageP() {
        return stageP;
    }

    boolean gameEnd() {
        return stageC >= 1 || getPlayerScore() == 0;
    }

    public String[] getComputerCardsStr() {
        String[] s = new String[6];
        for (int i = 0; i < 6; i++) {
            s[i] = computerCards[i].getData();
        }
        return s;
    }

    public String[] getPlayerCardsStr() {
        String[] s = new String[6];
        for (int i = 0; i < 6; i++) {
            s[i] = playerCards[i].getData();
        }
        return s;
    }
}

