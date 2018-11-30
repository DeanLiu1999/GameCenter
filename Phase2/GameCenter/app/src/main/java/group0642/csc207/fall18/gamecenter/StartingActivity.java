package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_temp.ser";
    private String name;
    private String game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExternalFilesDir(null);//create the directory for file writing and reading
        setContentView(R.layout.activity_starting_);
        Intent inherit = getIntent();
        name = inherit.getStringExtra("name");
        game = inherit.getStringExtra("game");
        TextView title = findViewById(R.id.GameText);
        title.setText(String.format("Welcome to %s", game));

        addStartButtonListener();
        addLoadButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.equals("Blackjack")) {
                    switchToBlackjack(false);

                } else if (game.equals("Sliding Tiles")) {

                    switchToSlidingTilesSetting();
                } else if (game.equals("Hangman")) {
                    switchToHangman(false);
                }
            }
        });
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.equals("Sliding Tiles")) {
                    LoadSlidingTiles();
                } else if (game.equals("Blackjack")) {
                    LoadBlackjack();

                } else if (game.equals("Hangman")) {
                    switchToHangman(true);

                }

            }
        });
    }


    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * Switch to the BackgroundSetting to configure the tile background.
     */
    private void switchToSlidingTilesSetting() {
        Intent GameSettingIntent = new Intent(this, SlidingTileSetting.class);
        GameSettingIntent.putExtra("name", name);
        GameSettingIntent.putExtra("game", game);

        StartingActivity.this.startActivity(GameSettingIntent);
    }

    /**
     * go to the activity to load the sliding tile
     */
    private void LoadSlidingTiles() {
        Intent goToSaved = new Intent(StartingActivity.this, LoadActivity.class);
        goToSaved.putExtra("name", name);
        goToSaved.putExtra("game", game);
        StartingActivity.this.startActivity(goToSaved);

    }

    /**
     * @param loadOrNot is whether to load or not
     *                  this intent goes to the blackjack game activity: if loadOrNot is true, this
     *                  intent load the game otherwise it starts a new game
     */
    private void switchToBlackjack(boolean loadOrNot) {
        Intent BlackjackGameIntent = new Intent(this, BlackjackGameActivity.class);
        BlackjackGameIntent.putExtra("name", name);
        BlackjackGameIntent.putExtra("game", game);
        BlackjackGameIntent.putExtra("load", loadOrNot);
        StartingActivity.this.startActivity(BlackjackGameIntent);
    }


    /**
     * this method load the blackjack activity
     */
    private void LoadBlackjack() {
        SaveManager saveManager = new SaveManager.Builder()
                .context(this)
                .saveDirectory(name, game)
                .build();
        if (saveManager.loadFromFile("bankManager_save_file.ser") != null) {
            switchToBlackjack(true);
        } else {
            Toast.makeText(this, "No Existing Save", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param loadOrNot is whether to load or not
     *                  this intent goes to the hangman mode activity: if loadOrNot is true, this
     *                  intent load the game of either two modes in HangmanMode otherwise it means
     *                  to start a new game of either two modes in the mode selection
     */
    private void switchToHangman(boolean loadOrNot) {
        Intent HangmanGameIntent = new Intent(this, HangmanModes.class);
        HangmanGameIntent.putExtra("name", name);
        HangmanGameIntent.putExtra("game", game);
        HangmanGameIntent.putExtra("load", loadOrNot);
        StartingActivity.this.startActivity(HangmanGameIntent);
    }
}
