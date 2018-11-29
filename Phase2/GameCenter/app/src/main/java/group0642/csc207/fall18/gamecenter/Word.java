package group0642.csc207.fall18.gamecenter;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Word implements Serializable {
    private String word;
    private int length;
    private String display;
    private int health;

    Word(String word) {
        if (word != null) {
            this.word = word;
        } else {
            this.word = "young";
        }
        this.length = this.word.length();
        this.display = StringUtils.repeat("_", this.length);
        this.health = 10;
    }

    boolean check(int position, String letter) {
        if (this.word.length() >= position)
            return String.valueOf(this.word.charAt(position)).equals(letter);
        return false;
    }

    void setHealth(int health) {
        this.health = health;
    }

    int getHealth() {
        return health;
    }

    boolean win() {
        return word.equals(display);
    }

    String getDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < display.length() - 1; i++) {
            sb.append(display.charAt(i));
            sb.append(" ");
        }
        return sb.toString() + display.charAt(display.length() - 1);
    }

    boolean enter(String entered) {
        boolean found = false;
        for (int i = 0; i < this.length; i++) {
            if (this.check(i, entered)) {
                display = display.substring(0, i) + entered + display.substring(i + 1);
                found = true;
            }
        }
        if (!found) {
            health--;
        }
        return found;
    }

    void setFinalDisplay() {
        display = word;
    }
}

