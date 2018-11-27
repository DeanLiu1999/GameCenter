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

import static group0642.csc207.fall18.gamecenter.ScoreBoard.updateScoreBoard;

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
        buttonListActions(entries, alphabet);
        if (load) {
            loadGame();
            load = false;
        }

    }

    public void setSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void setShowScoreboard_1Listener() {
        showScoreboard_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getHealth() == 0) {
                    switchToScore(name, game, score);
                } else {
                    switchToStart(name, game);
                }
            }
        });
    }

    public void switchToScore(String s, String t, int x) {
        Intent goToScore = new Intent(HangmanActivity.this, EndingScore.class);
        goToScore.putExtra("name", s);
        goToScore.putExtra("game", t);
        goToScore.putExtra("score", x);
        HangmanActivity.this.startActivity(goToScore);
    }

    public void switchToStart(String s, String t) {
        Intent startOver = new Intent(HangmanActivity.this, StartingActivity.class);
        startOver.putExtra("name", s);
        startOver.putExtra("game", t);
        HangmanActivity.this.startActivity(startOver);
    }

    public void buttonListActions(Button[] lst, String strings) {
        int i = 0;
        while (i < lst.length) {

            buttonListListener(lst[i], lst, strings.charAt(i));
            i++;
        }
    }

    public void buttonListListener(final Button b, final Button[] lst, final char letter) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterEntryListener(lst, b, letter);
            }
        });
    }

    public void disableAllButton(Button[] lst, boolean enabled) {
        for (Button aLst : lst) {
            aLst.setEnabled(enabled);
        }

    }

    public String chooseTheAnswer() {
        int index = randomGenerator.nextInt(wordList.size());
        return wordList.get(index);
    }

    void update() {
        view.setText(answer.getDisplay());
        healthBar.setText(String.valueOf(answer.getHealth()));
    }

    public void letterEntryListener(Button[] lst, Button button, char letter) {

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
            disableAllButton(lst, true);
            entered = new ArrayList();
            save();
        } else if (answer.getHealth() == 0) {
            makeToastEntryText("Game Over");
            updateScoreBoard(game, name, score);
            saveButton.setEnabled(false);
            showScoreboard_1.setText("Show scoreboard");
        }
    }


    private void makeToastEntryText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

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

    private void loadGame() {
        String FileName = name + "_" + game + "Infinity" + ".ser";
        loadFromFile("answer" + FileName, "score" + FileName, "entered" + FileName);

        update();
        int index;
        for (int i = 0; i < entered.size(); i++) {
            index = alphabet.indexOf((char) entered.get(i));
            entries[index].setEnabled(false);
        }

    }

    private void save() {
        String saveFileName = name + "_" + game + "Infinity" + ".ser";
        saveToFile(answer, "answer" + saveFileName);
        saveToFile(score, "score" + saveFileName);
        saveToFile(entered, "entered" + saveFileName);
        makeToastEntryText("Game Saved");
    }

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

    private void loadFromFile(String fileName1, String fileName2, String fileName3) {

        try {
            InputStream inputStream = this.openFileInput(fileName1);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                answer = (Word) input.readObject();
                inputStream.close();
            }
            InputStream inputStream2 = this.openFileInput(fileName2);
            if (inputStream2 != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream2);
                score = (int) input.readObject();
                inputStream2.close();
            }
            InputStream inputStream3 = this.openFileInput(fileName3);
            if (inputStream3 != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream3);
                entered = (ArrayList) input.readObject();
                inputStream3.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}
