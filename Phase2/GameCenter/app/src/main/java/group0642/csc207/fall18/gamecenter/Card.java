package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;

public class Card implements Serializable {
    private String data;

    private int info;

    /**
     * A new card
     *
     * @param s the type of the card
     */
    Card(String s){
        this.data = s;
        if (s.equals("A")) {
            info = 11;
        }
        else if (s.equals("K") || s.equals("Q") || s.equals("J") || s.equals("10")) {
            info = 10;
        }
        else
            info = Integer.parseInt(s);
    }

    /**
     * Return the info under blackjack rule: 2, 3 .... to 11
     *
     * @return info
     */
    public int getInfo() {
        return info;
    }

    /**
     * Return the type of the card
     *
     * @return data
     */
    public String getData() {
        return data;
    }
}
