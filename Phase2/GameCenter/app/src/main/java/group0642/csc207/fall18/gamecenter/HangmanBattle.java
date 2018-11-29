package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class HangmanBattle extends AppCompatActivity {


    /**
     * Name of current game, username of current active user, and score.
     */
    private static String game;
    private static String name;
    private TextView view;
    private TextView monsterHealth;
    private TextView characterHealth;
    private TextView monsterDamage;
    private TextView characterDamage;
    private int score = 0;
    private Word answer;
    private Random randomGenerator;
    private ArrayList<String> wordList;
    private Button showScoreboard_1;
    private Battle battle = new Battle();
    private int[] info = battle.getInfo();
    private int charHlth;
    private int monHlth;
    private int monDmg;
    private int charDmg;
    private int level = 0;
    private ImageView monster;
    private ImageView character;
    private int[] monsters;
    private boolean result = false;


    private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_battle);

        monsters = new int[]{R.drawable.level1, R.drawable.level2,
                R.drawable.level3, R.drawable.level4, R.drawable.level5, R.drawable.level6};

        Button[] entries = new Button[]{findViewById(R.id.a), findViewById(R.id.b), findViewById(R.id.c),
                findViewById(R.id.d), findViewById(R.id.e), findViewById(R.id.f), findViewById(R.id.g),
                findViewById(R.id.h), findViewById(R.id.i), findViewById(R.id.j), findViewById(R.id.k),
                findViewById(R.id.l), findViewById(R.id.m), findViewById(R.id.n), findViewById(R.id.o),
                findViewById(R.id.p), findViewById(R.id.q), findViewById(R.id.r), findViewById(R.id.s),
                findViewById(R.id.t), findViewById(R.id.u), findViewById(R.id.v), findViewById(R.id.w),
                findViewById(R.id.x), findViewById(R.id.y), findViewById(R.id.z)};

        showScoreboard_1 = findViewById(R.id.showScoreboard_1);

        monster = findViewById(R.id.monster);
        character = findViewById(R.id.character);

        randomGenerator = new Random();
        wordList = readFromFile("vocabulary_list.txt");
        answer = new Word(chooseTheAnswer());

        view = findViewById(R.id.wordDisplay);
        monsterHealth = findViewById(R.id.monsterHealth);
        monsterDamage = findViewById(R.id.monsterDamage);
        characterHealth = findViewById(R.id.charaterHealth);
        characterDamage = findViewById(R.id.characterDamage);

        update();
        updatePicture(level, monsters);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        game = intent.getStringExtra("game");
        buttonListActions(entries, alphabet);
        setShowScoreboard_1Listener();
    }

    void setShowScoreboard_1Listener() {
        showScoreboard_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!result) {
                    switchToStart(name, game);
                } else {

                    switchToScore(name, game, score);
                }
            }
        });

    }

    public void switchToScore(String s, String t, int x) {
        Intent goToScore = new Intent(HangmanBattle.this, EndingScore.class);
        goToScore.putExtra("name", s);
        goToScore.putExtra("game", t);
        goToScore.putExtra("score", x);
        HangmanBattle.this.startActivity(goToScore);
    }

    public void switchToStart(String s, String t) {
        Intent goToScore = new Intent(HangmanBattle.this, StartingActivity.class);
        goToScore.putExtra("name", s);
        goToScore.putExtra("game", t);
        HangmanBattle.this.startActivity(goToScore);
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
            aLst.setEnabled(enabled);
        }

    }


    public String chooseTheAnswer() {
        int index = randomGenerator.nextInt(wordList.size());
        return wordList.get(index);
    }

    void updatePicture(int i, int[] lst) {
        monster.setImageResource(lst[i]);
        character.setImageResource(R.drawable.hero);

    }

    void update() {
        info = battle.getInfo();
        charHlth = info[2];
        monHlth = info[0];
        monDmg = info[1];
        charDmg = info[3];

        view.setText(answer.getDisplay());
        monsterDamage.setText(String.valueOf(monDmg));
        characterHealth.setText(String.valueOf(charHlth));
        characterDamage.setText(String.valueOf(charDmg));
        monsterHealth.setText(String.valueOf(monHlth));
    }


    public void letterEntryListener(Button[] lst, Button button, String str) {
        boolean correctness = answer.enter(str);
        button.setEnabled(false);
        boolean notOver = battle.makeMove(correctness);
        if (answer.win()) {
            notOver = false;
        }
        update();
        String s;
        if (correctness) {
            s = "You attacked the monster";
            makeToastEntryText(s);
        } else {
            s = "The monster attacked you";
            makeToastEntryText(s);
        }
        if (!notOver) {
            endingDetermination(lst);
        }

    }

    private void endingDetermination(Button[] lst) {
        answer.setFinalDisplay();
        if (charHlth > 0) {
            if (level <= 4) {
                level++;
                makeToastEntryText("Level " + level);
                battle = new Battle(level, level);
                answer = new Word(chooseTheAnswer());
                disableAllButton(lst, true);
                update();
                updatePicture(level, monsters);
            } else {
                makeToastEntryText("You win");
                score = level;
                new ScoreBoard().updateScoreBoard(game, name, score);
                disableAllButton(lst, false);
                result = true;
                showScoreboard_1.setText("Show scoreboard");
            }
        } else {
            makeToastEntryText("You lose ");
            disableAllButton(lst, false);

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
}
