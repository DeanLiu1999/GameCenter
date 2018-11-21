package group0642.csc207.fall18.gamecenter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class Word {
    private String word;
    private int length;
    private ArrayList<String> entered;
    private String display;
    private int health;

    public Word(String word) {
        this.word = word;
        this.length = word.length();
        this.entered = new ArrayList<>();
        this.display = StringUtils.repeat("_", this.length);
        this.health = 10;
    }

    public String validEnter(String letter) {
        if (letter.length() != 1) {
            return "Length must be 1!";
        } //Check if letter is length one
        char c = letter.charAt(0);
        int i = (int) c;
        if (i < 97 || i > 122) {
            return "Must be letter!";
        }
        if (this.entered.contains(letter)) {
            return "Already entered";
        }
        this.entered.add(letter);
        return "Pass"; //Check if letter is Capital Letter
    }

    public boolean check(int position, String letter) {
        return String.valueOf(this.word.charAt(position)).equals(letter);
    }

    public ArrayList<String> getEntered() {
        return entered;
    }

    public int getLength() {
        return length;
    }

    public int getHealth() {
        return this.health;
    }

    public boolean win() {
        return word.equals(display);
    }

    public String getDisplay() {
        String s = "";
        for (int i = 0; i < display.length(); i++) {
            s = s + display.charAt(i) + " ";
        }
        return s;
    }

    public String enter(String entered) {
        entered = entered.toLowerCase();
        String good = validEnter(entered);
        if (good.equals("Pass")) {
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
        }
        return good;
    }


}