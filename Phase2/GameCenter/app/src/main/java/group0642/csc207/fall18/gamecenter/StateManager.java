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

    int[] getComputerScores() {
        int[] scores = new int[6];
        for (int i = 0; i < 6; i++)
            scores[i] = d.calculateScore(i, computerCards);
        return scores;
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
    boolean gameWin(){ return getPlayerScore() >= getComputerScore(); }

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
    
    boolean initialStage(){
        return getStageP() == 1 && getStageC() == 0;
    }
}

