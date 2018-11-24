package group0642.csc207.fall18.gamecenter;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class Word {
    private String word;
    private int length;
    private ArrayList<String> entered;
    private String display;
    private int health;

    Word(String word){
        this.word = word;
        this.length = word.length();
        this.entered = new ArrayList<>();
        this.display = StringUtils.repeat("_", this.length);
        this.health = 10;
    }

    private String validEnter(String letter){
        if(letter.length() != 1){
            return "Length must be 1!";
        } //Check if letter is length one
        char c = letter.charAt(0);
        int i = (int)c;
        if(i < 97 || i > 122){
            return "Must be letter!";
        }
        if(this.entered.contains(letter)){
            return "Already entered";
        }
        this.entered.add(letter);
        return "Pass"; //Check if letter is Capital Letter
    }


    private boolean check(int position, String letter){
        if (this.word.length() >= position)
            return String.valueOf(this.word.charAt(position)).equals(letter);
        return false;
    }

    ArrayList<String> getEntered(){
        return entered;
    }

    void setHealth(int health){
        this.health = health;
    }

    int getHealth(){
        return health;
    }

    boolean win(){
        return word.equals(display);
    }

    String getDisplay(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < display.length(); i++){
            sb.append(display.charAt(i));
            sb.append(" ");
        }
        return sb.toString();
    }

    String enter(String entered){
        entered = entered.toLowerCase();
        String good = validEnter(entered);
        if(good.equals("Pass")){
            boolean found = false;
            for(int i = 0; i < this.length; i++){
                if(this.check(i, entered)){
                    display = display.substring(0, i) + entered + display.substring(i + 1);
                    found = true;
                }
            }
            if(!found){
                health--;
            }
        }
        return good;
    }

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
}