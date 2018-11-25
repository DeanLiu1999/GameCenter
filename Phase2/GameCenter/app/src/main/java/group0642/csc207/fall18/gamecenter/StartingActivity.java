package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_temp.ser";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExternalFilesDir(null);//create the directory for file writing and reading
        setContentView(R.layout.activity_starting_);
        Intent inherit = getIntent();
        String name = inherit.getStringExtra("name");
        String game = inherit.getStringExtra("game");
        TextView title = findViewById(R.id.GameText);
        title.setText(String.format("Welcome to %s", game));

        addStartButtonListener(name, game);
        addLoadButtonListener(name, game);
    }

    /**
     * Activate the start button.
     *
     * @param nm the username of the current active user
     * @param gm the currently selected game
     */
    private void addStartButtonListener(final String nm, final String gm) {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gm.equals("Blackjack")) {
                    switchToBlackjack(nm, gm);

                } else if (gm.equals("Sliding Tiles")) {

                    switchToSlidingTilesSetting(nm, gm);
                } else if (gm.equals("Hangman")) {
                    switchToHangman(nm, gm);
                }
            }
        });
    }


    /**
     * Activate the load button.
     *
     * @param user the username of the current active user
     * @param game the currently selected game
     */
    private void addLoadButtonListener(final String user, final String game) {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.equals("Sliding Tiles")) {
                    LoadSlidingTiles(user, game);
                } else if (game.equals("Blackjack")) {
                    LoadBlackjack(user, game);
                } else if (game.equals("Hangman")) {
                    LoadHangman(user, game);
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
     *
     * @param s the username of the current active user
     * @param t the currently selected game
     */
    private void switchToSlidingTilesSetting(String s, String t) {
        Intent GameSettingIntent = new Intent(this, BackgroundSetting.class);
        GameSettingIntent.putExtra("name", s);
        GameSettingIntent.putExtra("game", t);

        StartingActivity.this.startActivity(GameSettingIntent);
    }

    private void switchToBlackjack(String s, String t) {
        Intent BlackjackGameIntent = new Intent(this, BlackjackGameActivity.class);
        BlackjackGameIntent.putExtra("name", s);
        BlackjackGameIntent.putExtra("game", t);
        StartingActivity.this.startActivity(BlackjackGameIntent);

    }

    private void switchToHangman(String s, String t) {
        Intent HangmanGameIntent = new Intent(this, HangmanActivity.class);
        HangmanGameIntent.putExtra("name", s);
        HangmanGameIntent.putExtra("game", t);
        StartingActivity.this.startActivity(HangmanGameIntent);
    }

    private void LoadSlidingTiles(String s, String t) {
        Intent goToSaved = new Intent(StartingActivity.this, LoadActivity.class);
        goToSaved.putExtra("name", s);
        goToSaved.putExtra("game", t);
        StartingActivity.this.startActivity(goToSaved);

    }

    private void LoadHangman(String s, String t) {
        Intent HangmanLoadIntent = new Intent(this, LoadHangman.class);
        HangmanLoadIntent.putExtra("name", s);
        HangmanLoadIntent.putExtra("game", t);
        StartingActivity.this.startActivity(HangmanLoadIntent);
    }

    private void LoadBlackjack(String s, String t) {
        Intent BlackjackLoadIntent = new Intent(this, LoadBlackjack.class);
        BlackjackLoadIntent.putExtra("name", s);
        BlackjackLoadIntent.putExtra("game", t);
        StartingActivity.this.startActivity(BlackjackLoadIntent);
    }


}
