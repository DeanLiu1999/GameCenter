package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HangmanModes extends AppCompatActivity {
    private boolean loadOrNot;
    private String name;
    private String game;
    /**
     * The save manager.
     */
    private SaveManager saveManager;
    /**
     * The name of the save file for Battle mode.
     */
    private final String saveFileNameBat = "save_file_Battle.ser";
    /**
     * The name of the save file for Infinity mode.
     */
    private final String saveFileNameInf = "save_file_Infinity.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_modes);

        Intent inherit = getIntent();
        name = inherit.getStringExtra("name");
        game = inherit.getStringExtra("game");

        saveManager = new SaveManager.Builder()
                .context(this)
                .saveDirectory(name, game)
                .build();

        loadOrNot = inherit.getBooleanExtra("load", false);
        launchBattleMode();
        launchInfinityMode();
    }

    /**
     * launch the battle mode of hangman
     */
    private void launchBattleMode() {
        Button launchBattle = findViewById(R.id.normalButton);
        launchBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadOrNot) {
                    loadBattle();
                } else {
                    switchToBattle();
                }
            }
        });
    }

    /**
     * launch the infinity mode of hangman
     */
    private void launchInfinityMode() {
        Button launchInfinity = findViewById(R.id.infinityButton);
        launchInfinity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadOrNot) {
                    loadInfinity();
                } else {
                    switchToInfinity();
                }
            }
        });
    }

    /**
     * load the saved game for infinity mode of hangman. Show the user if there is no existing file.
     */
    private void loadInfinity() {
        if (saveManager.loadFromFile("answer_" + saveFileNameInf) != null &&
                saveManager.loadFromFile("entered_" + saveFileNameInf) != null &&
                saveManager.loadFromFile("score_" + saveFileNameInf) != null) {
            switchToInfinity();
        } else {
            Toast.makeText(this, "No existing file", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * load the saved game for battle mode of hangman. Show the user if there is no existing file.
     */
    private void loadBattle() {
        if (saveManager.loadFromFile("answer_" + saveFileNameBat) != null &&
                saveManager.loadFromFile("battle_" + saveFileNameBat) != null &&
                saveManager.loadFromFile("entered_" + saveFileNameBat) != null &&
                saveManager.loadFromFile("score_" + saveFileNameBat) != null) {
            switchToBattle();
        } else {
            Toast.makeText(this, "No existing file", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This is the intent to go to battle mode
     */
    private void switchToBattle() {
        Intent goToNormal = new Intent(this, HangmanBattle.class);
        goToNormal.putExtra("name", name);
        goToNormal.putExtra("game", game);
        HangmanBattle.load = loadOrNot;
        HangmanModes.this.startActivity(goToNormal);
    }

    /**
     * This is the intent to go to infinity mode
     */
    private void switchToInfinity() {
        Intent goToInfinity = new Intent(this, HangmanActivity.class);
        goToInfinity.putExtra("name", name);
        goToInfinity.putExtra("game", game);
        HangmanActivity.load = loadOrNot;
        HangmanModes.this.startActivity(goToInfinity);
    }
}