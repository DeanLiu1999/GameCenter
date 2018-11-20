package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;

public class Card implements Serializable {
    private String data;

    private int info;

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

    public int getInfo() {
        return info;
    }

    public String getData() {
        return data;
    }
}
