package group0642.csc207.fall18.gamecenter;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

class Word implements Serializable {

    private String word;

    private int length;

    private String display;

    private int health;

    /**
     * Manage a single hangman word.
     */
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

    /**
     * Return the letter user chose is at position or not.
     *
     * @param position the position in the word current checking
     * @param letter the letter user chose
     * @return equal
     */
    boolean check(int position, String letter) {
        if (this.word.length() >= position)
            return String.valueOf(this.word.charAt(position)).equals(letter);
        return false;
    }

    /**
     * Set health to health.
     *
     * @param health the position in the word current checking
     */
    void setHealth(int health) {
        this.health = health;
    }

    /**
     * Return health.
     *
     * @return health
     */
    int getHealth() {
        return health;
    }

    /**
     * Return win or not.
     *
     * @return win
     */
    boolean win() {
        return word.equals(display);
    }

    /**
     * Return display that each letter separated with one space character.
     *
     * @return display
     */
    String getDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < display.length() - 1; i++) {
            sb.append(display.charAt(i));
            sb.append(" ");
        }
        return sb.toString() + display.charAt(display.length() - 1);
    }

    /**
     * User chose the letter "entered", so we need to use some methods to change some variable.
     *
     * @param entered the letter use chose
     * @return found
     */
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

    /**
     * Set display to word. (This method is for Battle Mode, sometimes the player defeated the monster
     * but the word is not displayed fully)
     */
    void setFinalDisplay() {
        display = word;
    }
}

