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
    private int mode;
    private TextView view;
    private TextView healthBar;
    private int score = 0;
    private Word answer;
    private Random randomGenerator;
    private ArrayList<String> wordList;
    private Button showScoreboard_1;


    private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        Button[] entries = new Button[]{findViewById(R.id.a), findViewById(R.id.b), findViewById(R.id.c),
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

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        game = intent.getStringExtra("game");
        mode = intent.getIntExtra("mode", 2);

        showScoreboard_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToScore(name, game, score);
            }
        });
        buttonListActions(entries, alphabet);

    }

    public void buttonListActions(Button[] lst, String[] lstOfStrings) {
        int i = 0;
        while (i < lst.length) {

            buttonListListener(lst[i], lst, lstOfStrings[i]);
            i++;
        }
    }

    public void buttonListListener(final Button b, final Button[] lst, final String string) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterEntryListener(lst, b, string);
            }
        });
    }

    public void disableAllButton(Button[] lst, boolean enabled) {
        for (Button aLst : lst) {
            aLst.setEnabled(false);
        }

    }

    public void switchToScore(String s, String t, int x) {
        Intent goToScore = new Intent(HangmanActivity.this, EndingScore.class);
        goToScore.putExtra("name", s);
        goToScore.putExtra("game", t);
        goToScore.putExtra("score", x);
        HangmanActivity.this.startActivity(goToScore);
    }


    public String chooseTheAnswer() {
        int index = randomGenerator.nextInt(wordList.size());
        return wordList.get(index);
    }

    void update() {
        view.setText(answer.getDisplay());
        healthBar.setText(String.valueOf(answer.getHealth()));
    }


    public void letterEntryListener(Button[] lst, Button button, String str) {
        String s = answer.enter(str);
        button.setEnabled(false);
        if (!s.equals("Pass")) {
            makeToastEntryText(s);
        } else {
            update();
        }

        if (answer.win()) {
            if (mode == 1) {
                int health = answer.getHealth();
                answer = new Word(chooseTheAnswer());
                answer.setHealth(health + 1);
                update();
                score++;
                disableAllButton(lst, true);
            } else {
                makeToastEntryText("You win");
                score = answer.getHealth();
                showScoreboard_1.setEnabled(true);
                updateScoreBoard(game, name, score);
                disableAllButton(lst, false);
            }
        } else if (answer.getHealth() == 0) {
            if (mode == 1) {
                makeToastEntryText("Game Over");
                updateScoreBoard(game, name, score);
                showScoreboard_1.setEnabled(true);
            } else {
                makeToastEntryText("You lose ");
                disableAllButton(lst, true);
            }
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

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(answer);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                answer = (Word) input.readObject();
                inputStream.close();
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
