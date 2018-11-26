package group0642.csc207.fall18.gamecenter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class Word implements Serializable {
    private String word;
    private int length;
    private String display;
    private int health;

    Word(String word) {
        this.word = word;
        this.length = word.length();
        this.display = StringUtils.repeat("_", this.length);
        this.health = 10;
    }

    private boolean check(int position, String letter) {
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
        for (int i = 0; i < display.length(); i++) {
            sb.append(display.charAt(i));
            sb.append(" ");
        }
        return sb.toString();
    }

    void enter(String entered) {
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
}

/*
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Word w = new Word("Whatever");
        while(w.getHealth() != 0 && !w.win()) {
            String message = w.enter(sc.nextLine());
            if(message.equals("Pass")){
                System.out.println("Display: " + w.getDisplay() + "Remaining health: " + w.getHealth());
            }
            else{
                System.out.println(message);
            }
        }
    }
    */
