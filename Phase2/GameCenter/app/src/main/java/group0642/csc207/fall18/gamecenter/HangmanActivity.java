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
    private Word answer;
    private Random randomGenerator;
    private ArrayList<String> wordList;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        randomGenerator = new Random();

        wordList = readFromFile("vocabulary_list.txt");
        int index = randomGenerator.nextInt(wordList.size());
        String the_answer = wordList.get(index);
        answer = new Word(the_answer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        view = findViewById(R.id.wordDisplay);
        healthBar = findViewById(R.id.healthBar);
        update();
        enterButtonListener(healthBar, view);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        game = intent.getStringExtra("game");

    }

    void update(){
        view.setText(answer.getDisplay());
        healthBar.setText(String.valueOf(answer.getHealth()));
    }

    public void enterButtonListener(final TextView health, final TextView display) {
        final Button enter = findViewById(R.id.enterButton);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textEntry = findViewById(R.id.guess);
                String s = answer.enter(textEntry.getText().toString());
                if (!s.equals("Pass")) {
                    makeToastEntryText(s);
                } else {
                    health.setText(String.valueOf(answer.getHealth()));
                    display.setText(answer.getDisplay());
                }

                if (answer.win()) {
                    if (true){ /// TODO: 2018/11/24 Change the if statement to a condition (some value means infinitely mode) 
                        int health = answer.getHealth();
                        int index = randomGenerator.nextInt(wordList.size());
                        String the_answer = wordList.get(index);
                        answer = new Word(the_answer);
                        answer.setHealth(health + 1);
                        update();
                    }
                    else {
                        makeToastEntryText("You Win");
                        enter.setEnabled(false);
                    }
                } else if (answer.getHealth() == 0) {
                    makeToastEntryText("You Lose");
                    enter.setEnabled(false);
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
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d("StackOverflow", line);
                line = reader.readLine();
                wordList.add(line);

            }
            file.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
        return wordList;
    }
}
