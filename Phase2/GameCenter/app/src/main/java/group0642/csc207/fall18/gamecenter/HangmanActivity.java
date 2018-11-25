package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private int mode = 2; /// TODO: 2018/11/25 Need to pass a value when player chose mode, 1 is infinitely
    private TextView view;
    private TextView healthBar;
    private int score = 0;
    private Word answer;
    private Random randomGenerator;
    private ArrayList<String> wordList;
    private Button showScoreboard_1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        randomGenerator = new Random();
        wordList = readFromFile("vocabulary_list.txt");

        answer = new Word(chooseTheAnswer());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        view = findViewById(R.id.wordDisplay);
        healthBar = findViewById(R.id.healthBar);
        update();
        showScoreboard_1 = findViewById(R.id.showScoreboard_1);
        enterButtonListener();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        game = intent.getStringExtra("game");

        showScoreboard_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToScore(name, game, score);
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


    public String chooseTheAnswer() {
        int index = randomGenerator.nextInt(wordList.size());
        return wordList.get(index);
    }

    void update(){
        view.setText(answer.getDisplay());
        healthBar.setText(String.valueOf(answer.getHealth()));
    }

    public void enterButtonListener() {
        final Button enter = findViewById(R.id.enterButton);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textEntry = findViewById(R.id.guess);
                String s = answer.enter(textEntry.getText().toString());
                if (!s.equals("Pass")) {
                    makeToastEntryText(s);
                } else {
                    update();
                }

                if (answer.win()) {
                    if (mode == 1){
                        int health = answer.getHealth();
                        answer = new Word(chooseTheAnswer());
                        answer.setHealth(health + 1);
                        update();
                        score++;
                    }
                    else {
                        makeToastEntryText("You win");
                        enter.setEnabled(false);
                        score = answer.getHealth();
                        showScoreboard_1.setEnabled(true);
                    }
                } else if (answer.getHealth() == 0) {
                    enter.setEnabled(false);
                    if (mode == 1){
                        makeToastEntryText("Game Over");
                        showScoreboard_1.setEnabled(true);
                    }
                    else {
                        makeToastEntryText("You lose");
                    }
                }
            }
        });
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
