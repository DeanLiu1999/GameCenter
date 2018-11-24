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

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        final Random randomGenerator = new Random();


        final ArrayList<String> wordList = readFromFile("vocabulary_list.txt");
        int index = randomGenerator.nextInt(wordList.size());
        String the_answer = wordList.get(index);
        final Word answer = new Word(the_answer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        TextView view = findViewById(R.id.wordDisplay);
        TextView healthBar = findViewById(R.id.healthBar);
        view.setText(answer.getDisplay());
        healthBar.setText(String.valueOf(answer.getHealth()));
        enterButtonListener(healthBar, view, answer);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        game = intent.getStringExtra("game");

    }


    public void enterButtonListener(final TextView health, final TextView display, final Word word) {
        final Button enter = findViewById(R.id.enterButton);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textEntry = findViewById(R.id.guess);
                String s = word.enter(textEntry.getText().toString());
                if (!s.equals("Pass")) {
                    makeToastEntryText(s);
                } else {
                    health.setText(String.valueOf(word.getHealth()));
                    display.setText(word.getDisplay());
                }

                if (word.win()) {
                    makeToastWonText();
                    enter.setEnabled(false);
                } else if (word.getHealth() == 0) {
                    makeToastLostText();
                    enter.setEnabled(false);
                }
            }
        });
    }

    private void makeToastWonText() {
        Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show();
    }

    private void makeToastEntryText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void makeToastLostText() {
        {
            Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show();
        }

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
