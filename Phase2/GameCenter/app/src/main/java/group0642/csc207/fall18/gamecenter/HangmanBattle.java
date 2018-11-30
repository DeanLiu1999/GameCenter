package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class HangmanBattle extends AppCompatActivity {


    /**
     * Name of current game, username of current active user, and score.
     */
    private static String game;
    private static String name;
    public static boolean load = false;

    private TextView view;
    private TextView monsterHealth;
    private TextView characterHealth;
    private TextView monsterDamage;
    private TextView characterDamage;
    private Button[] entries;
    private Button saveButton;
    private Button showScoreboard_1;
    private ImageView monster;
    private ImageView character;

    private Battle battle = new Battle();
    private Word answer;
    private Random randomGenerator;
    private ArrayList<String> wordList;
    private ArrayList entered = new ArrayList();
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private int[] monsters;
    private int[] info = battle.getInfo();
    private int charHlth;
    //the health of the charater
    private int monHlth;
    // the health of the monster
    private int monDmg;
    // the attack damage of the monster
    private int charDmg;
    // the attack damage of the character
    private int level = 0;
    private int score = 0;
    private boolean result = false;

    /**
     * The save manager.
     */
    private SaveManager saveManager;
    /**
     *
     */
    private ArrayList<Object> objectsToSave;
    /**
     *
     */
    private String[] saveFilePrefixes = new String[]{"answer_", "battle_", "entered_", "score_"};
    /**
     * The name of the save file.
     */
    private String saveFileName = "save_file_Battle.ser";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_battle);

        monsters = new int[]{R.drawable.level1, R.drawable.level2,
                R.drawable.level3, R.drawable.level4, R.drawable.level5, R.drawable.level6};

        entries = new Button[]{findViewById(R.id.a), findViewById(R.id.b), findViewById(R.id.c),
                findViewById(R.id.d), findViewById(R.id.e), findViewById(R.id.f), findViewById(R.id.g),
                findViewById(R.id.h), findViewById(R.id.i), findViewById(R.id.j), findViewById(R.id.k),
                findViewById(R.id.l), findViewById(R.id.m), findViewById(R.id.n), findViewById(R.id.o),
                findViewById(R.id.p), findViewById(R.id.q), findViewById(R.id.r), findViewById(R.id.s),
                findViewById(R.id.t), findViewById(R.id.u), findViewById(R.id.v), findViewById(R.id.w),
                findViewById(R.id.x), findViewById(R.id.y), findViewById(R.id.z)};

        showScoreboard_1 = findViewById(R.id.showScoreboard_1);
        saveButton = findViewById(R.id.saveButton_1);

        monster = findViewById(R.id.monster);
        character = findViewById(R.id.character);

        view = findViewById(R.id.wordDisplay);
        monsterHealth = findViewById(R.id.monsterHealth);
        monsterDamage = findViewById(R.id.monsterDamage);
        characterHealth = findViewById(R.id.charaterHealth);
        characterDamage = findViewById(R.id.characterDamage);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        game = intent.getStringExtra("game");

        saveManager = new SaveManager.Builder()
                .context(this)
                .saveDirectory(name, game)
                .build();

        wordList = saveManager.readTextFromFile("vocabulary_list.txt");
        randomGenerator = new Random();
        answer = new Word(chooseTheAnswer());
        update();
        updatePicture(level, monsters);

        collectObjectsToSave();

        buttonListActions();
        setShowScoreboard_1Listener();
        setSaveButton();
        if (load) {
            loadGame();
            load = false;
        }
    }

    /**
     * the action of the save button happens here
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
    void setShowScoreboard_1Listener() {
        showScoreboard_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!result) {
                    switchToStart();
                } else {
                    switchToScore();
                }
            }
        });

    }

    /**
     * This is the intent to switch to scoreboard
     */
    public void switchToScore() {
        Intent goToScore = new Intent(HangmanBattle.this, EndingScore.class);
        goToScore.putExtra("name", name);
        goToScore.putExtra("game", game);
        goToScore.putExtra("score", score);
        HangmanBattle.this.startActivity(goToScore);
    }

    /**
     * This is the intent to switch back to the starting activity
     */
    public void switchToStart() {
        Intent goToScore = new Intent(HangmanBattle.this, StartingActivity.class);
        goToScore.putExtra("name", name);
        goToScore.putExtra("game", game);
        HangmanBattle.this.startActivity(goToScore);
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
     * @param b     is the button corresponding to the letter
     * @param entry is the letter entered.
     *              this method enter the correct letter when the corresponding button is pressed
     */
    public void buttonListListener(final Button b, final char entry) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterEntryListener(b, entry);
            }
        });
    }

    /**
     * @param button is the button corresponding to the letter
     * @param entry  is the letter entered.
     *               this method determines what happens when the user entered a letter:
     *               if the user is incorrect, the character's health decreases by the amount of the
     *               monster's attack damage; otherwise, the display will show his guess and the
     *               monster's health will decrease by the attack damage from the character. Also,
     *               if the user completed the whole word, a new word will be generated, and the
     *               user enters the next round automatically. If the user loses the game or
     *               completes the entire 6 levels of games, he/she will be able to see his score
     *               and the game would end there.
     */
    public void letterEntryListener(Button button, char entry) {
        entered.add(entry);
        String str = String.valueOf(entry);
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
            endingDetermination();
        } else {
            save();
        }

    }

    /**
     * This is used to determine the ending of each round and whether the ending of this round
     * results in the ending of the entire game
     */
    private void endingDetermination() {
        answer.setFinalDisplay();
        if (charHlth > 0) {
            if (level <= 4) {
                level++;
                score = level;
                entered = new ArrayList();
                makeToastEntryText("Level " + level);

                battle = new Battle(level, level);
                answer = new Word(chooseTheAnswer());

                disableAllButton(true);
                update();
                updatePicture(level, monsters);
                save();
                makeToastEntryText("Game Saved");
            } else {
                makeToastEntryText("You win");
                score = level;
                new ScoreBoard().updateScoreBoard(game, name, score);
                disableAllButton(false);
                result = true;
                showScoreboard_1.setText("Show scoreboard");
                saveButton.setEnabled(false);
            }
        } else {
            makeToastEntryText("You lose ");
            disableAllButton(false);
            score = level;
            new ScoreBoard().updateScoreBoard(game, name, score);
            result = true;
            showScoreboard_1.setText("Show scoreboard");
            saveButton.setEnabled(false);
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
     * @param i   is the current level
     * @param lst is the list of the pictures
     *            update the picture of the monster of current level with the corresponding picture
     *            and display the picture of the character as well
     */
    void updatePicture(int i, int[] lst) {
        monster.setImageResource(lst[i]);
        character.setImageResource(R.drawable.hero);

    }

    /**
     * update and display the character and monster's health and damage
     */
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

    /**
     * @param s is the message
     *          let the user know the when the game ended or saved, or he/she loses or wins
     */
    private void makeToastEntryText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * load the game the answer, score, battle state,  and the letters the user entered so the user
     * can resume playing his saved state of game
     */
    private void loadGame() {
        answer = (Word) saveManager.loadFromFile("answer_" + saveFileName);
        battle = (Battle) saveManager.loadFromFile("battle_" + saveFileName);
        entered = (ArrayList) saveManager.loadFromFile("entered_" + saveFileName);
        score = (int) saveManager.loadFromFile("score_" + saveFileName);

        update();
        updatePicture(score, monsters);
        int index;
        for (int i = 0; i < entered.size(); i++) {
            index = alphabet.indexOf((char) entered.get(i));
            entries[index].setEnabled(false);
        }
        collectObjectsToSave();
        makeToastEntryText("Game Loaded");
    }

    /**
     * save the game by saving the answer, score, the letters the user entered, and the battle state
     */
    private void save() {
        ArrayList<String> saveFileNames = new ArrayList<>();
        for (String prefix : saveFilePrefixes) {
            saveFileNames.add(prefix + saveFileName);
        }
        collectObjectsToSave();
        saveManager.newObjects(objectsToSave);
        saveManager.saveToFiles(saveFileNames);
    }

    /**
     * Collects all objects intended to be saved.
     */
    private void collectObjectsToSave() {
        objectsToSave = new ArrayList<>();
        objectsToSave.add(answer);
        objectsToSave.add(battle);
        objectsToSave.add(entered);
        objectsToSave.add(score);
    }
}
