package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.*;

public class DeckManager implements Serializable {

    private Card[] cardsTop = new Card[12];

    public static final String[] deck = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2",
            "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2",
            "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2",
            "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

    /**
     * A new deck manager with randomly shuffled cards.
     */
    DeckManager() {
        Card[] cardsTemp = new Card[52];
        for (int i = 0; i < 52; i++) {
            cardsTemp[i] = new Card(deck[i]);
        }
        ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cardsTemp));
        Collections.shuffle(cards);
        for (int i = 0; i < 12; i++) {
            cardsTop[i] = cards.get(i);
        }
    }

    /**
     * Return the top 12 cards of the deck
     *
     * @return cardsTop
     */
    Card[] getCardsTop() {
        return cardsTop;
    }

    /**
     * Return the score following blackjack rule
     *
     * @param stage the state corresponded to the player
     * @param sixCards the potential cards
     * @return the tile at (row, col)
     */
    int calculateScore(int stage, Card[] sixCards) {
        int result = 0;
        int numAce = 0;
        for (int i = 0; i <= stage; i++) {
            result += sixCards[i].getInfo();
            if (sixCards[i].getInfo() == 11)
                numAce += 1;
        }
        while (result > 21 && numAce > 0) {
            result -= 10;
            numAce -= 1;
        }
        if (result <= 21)
            return result;
        return 0;
    }
}

