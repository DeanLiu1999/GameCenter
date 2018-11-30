package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;


public class HangmanActivity extends AppCompatActivity {


    /**
     * Name of current game, username of current active user, and score.
     */
    private static String game;
    private static String name;
    private TextView view;
    private TextView healthBar;
    private int score = 0;
    private Word answer;
    private Random randomGenerator;
    private ArrayList<String> wordList;
    private Button showScoreboard_1;
    private Button saveButton;
    private Button[] entries;
    private ArrayList entered = new ArrayList();

    public static boolean load = false;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static final String TAG = "HangmanActivity";


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        entries = new Button[]{findViewById(R.id.a), findViewById(R.id.b), findViewById(R.id.c),
                findViewById(R.id.d), findViewById(R.id.e), findViewById(R.id.f), findViewById(R.id.g),
                findViewById(R.id.h), findViewById(R.id.i), findViewById(R.id.j), findViewById(R.id.k),
                findViewById(R.id.l), findViewById(R.id.m), findViewById(R.id.n), findViewById(R.id.o),
                findViewById(R.id.p), findViewById(R.id.q), findViewById(R.id.r), findViewById(R.id.s),
                findViewById(R.id.t), findViewById(R.id.u), findViewById(R.id.v), findViewById(R.id.w),
                findViewById(R.id.x), findViewById(R.id.y), findViewById(R.id.z)};

        randomGenerator = new Random();
        wordList = readFromFile("vocabulary_list.txt");
        answer = new Word(chooseTheAnswer());

        view = findViewById(R.id.wordDisplay);
        healthBar = findViewById(R.id.healthBar);
        update();
        showScoreboard_1 = findViewById(R.id.showScoreboard_1);
        saveButton = findViewById(R.id.saveButton_1);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        game = intent.getStringExtra("game");
        setSaveButton();
        setShowScoreboard_1Listener();
        buttonListActions();
        if (load) {
            loadGame();
            load = false;
        }

    }

    /**
     * The game is saved when the user presses the save button
     */
    public void setSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    /**
     * when the game has ended the user is able to see the scoreboard when it has not ended, the
     * user can leave this activity and proceed to the starting activity of this game
     */
    public void setShowScoreboard_1Listener() {
        showScoreboard_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getHealth() == 0) {
                    switchToScore();
                } else {
                    switchToStart();
                }
            }
        });
    }

    /**
     * This is the intent to switch to scoreboard
     */
    public void switchToScore() {
        Intent goToScore = new Intent(HangmanActivity.this, EndingScore.class);
        goToScore.putExtra("name", name);
        goToScore.putExtra("game", game);
        goToScore.putExtra("score", score);
        HangmanActivity.this.startActivity(goToScore);
    }

    /**
     * This is the intent to switch back to the starting activity
     * */
    public void switchToStart() {
        Intent startOver = new Intent(HangmanActivity.this, StartingActivity.class);
        startOver.putExtra("name", name);
        startOver.putExtra("game", game);
        HangmanActivity.this.startActivity(startOver);
    }

    /**
     * Each of the 26 letter button corresponds to the same letter in the alphabet. This method is
     * to make sure of when the button is pressed the correct letter is entered.
     */
    public void buttonListActions() {
        int i = 0;
        while (i < entries.length) {
            buttonListListener(entries[i], alphabet.charAt(i));
            i++;
        }
    }

    /**
     * @param b is the button corresponding to the letter
     * @param letter is the letter entered.
     *               this method enter the correct letter when the corresponding button is pressed
     */
    public void buttonListListener(final Button b, final char letter) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterEntryListener(b, letter);
            }
        });
    }

    /**
     * @param button is the button corresponding to the letter
     * @param letter is the letter entered.
     * this method determines what happens when the user entered a letter:
     *               if the user is incorrect, his health decreases; otherwise, the display will
     *               show his guess. Also, if the user completed the whole word, a new word will
     *               be generated, and the user enters the net round automatically. If the user
     *               loses the game, he will be able to see his score and the game would end there.
     */
    public void letterEntryListener(Button button, char letter) {
        entered.add(letter);
        String str = String.valueOf(letter);
        answer.enter(str);
        button.setEnabled(false);
        update();

        if (answer.win()) {
            int health = answer.getHealth();
            answer = new Word(chooseTheAnswer());
            answer.setHealth(health + 1);
            update();
            score++;
            disableAllButton(true);
            entered = new ArrayList();
            save();
            makeToastEntryText("Game Saved");
        } else if (answer.getHealth() == 0) {
            makeToastEntryText("Game Over");
            new ScoreBoard().updateScoreBoard(game, name, score);
            saveButton.setEnabled(false);
            disableAllButton(false);
            showScoreboard_1.setText("Show scoreboard");
        }
    }

    /**
     * @param enabled for whether the buttons are enabled or not
     *                this method enable or disable all the buttons used to enter letters.
     *                If the parameter is true, all buttons are enabled; otherwise, they are all
     *                disabled.
     */
    public void disableAllButton(boolean enabled) {
        for (Button aLst : entries) {
            aLst.setEnabled(enabled);
        }
    }

    /**
     * @return the word from the word list from vocabulary_list
     */
    public String chooseTheAnswer() {
        int index = randomGenerator.nextInt(wordList.size());
        return wordList.get(index);
    }

    /**
     * update the display of the health bar when the user entered an guess: it decreases when the
     * answer is wrong and it stays the same when the answer is the same
     * update the word display with user's guess if the guess is valid.
     */
    void update() {
        view.setText(answer.getDisplay());
        healthBar.setText(String.valueOf(answer.getHealth()));
    }


    /**
     * @param s is the message
     *          let the user know the when the game ended or saved, or he/she loses or wins
     */
    private void makeToastEntryText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param fileName is the file of the vocabulary list
     * @return an ArrayList of the word in that file
     */
    private ArrayList<String> readFromFile(String fileName) {
        BufferedReader reader;
        ArrayList<String> wordList = new ArrayList<>();
        try {
            final InputStream file = getAssets().open(fileName);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while (line != null) {
                Log.d("StackOverflow", line);
                wordList.add(line.toLowerCase());
                line = reader.readLine();
            }
            file.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
        return wordList;
    }

    /**
     * load the game the answer, score, and the letters the user entered so the user can resume
     * playing his saved state of game
     */
    private void loadGame() {
        String FileName = name + "_" + game + "Infinity" + ".ser";
        answer = (Word) loadFromFile("answer" + FileName);
        score = (int) loadFromFile("score" + FileName);
        entered = (ArrayList) loadFromFile("entered" + FileName);

        update();
        int index;
        for (int i = 0; i < entered.size(); i++) {
            index = alphabet.indexOf((char) entered.get(i));
            entries[index].setEnabled(false);
        }

    }

    /**
     * save the game by saving the answer, score, and the letters the user entered
     */
    private void save() {
        String saveFileName = name + "_" + game + "Infinity" + ".ser";
        saveToFile(answer, "answer" + saveFileName);
        saveToFile(score, "score" + saveFileName);
        saveToFile(entered, "entered" + saveFileName);

    }

    /**
     * @param object is the object we want to save: usually the score, answer of the game, and the
     *               letters the user has entered
     * @param fileName the name of the file we want to save over
     * save the object under the file name given
     */
    public void saveToFile(Object object, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(object);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * @param fileName the name of the file we want to load from
     * @return the object we read from the file: usually the score, answer of the game, and the
     * letters the user has entered
     */
    private Object loadFromFile(String fileName) {

        try {
            Object object;
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                object = input.readObject();
                inputStream.close();
                return object;
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "File contained unexpected data type: " + e.toString());
        }
        return null;
    }
}
