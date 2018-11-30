package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_temp.ser";
    // private final String tag = "StartingActivity";

    // TODO
    //private SaveManager saveManager;
    private static final String TAG = "StartingActivity";

    private String name;
    private String game;

    private boolean loadIndicator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExternalFilesDir(null);//create the directory for file writing and reading
        setContentView(R.layout.activity_starting_);
        Intent inherit = getIntent();
        name = inherit.getStringExtra("name");
        game = inherit.getStringExtra("game");
        /*
        saveManager = new SaveManager.Builder()
                .context(this)
                .build();
        */
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
                    switchToBlackjack();

                } else if (game.equals("Sliding Tiles")) {

                    switchToSlidingTilesSetting();
                } else if (game.equals("Hangman")) {
                    switchToHangman();
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
                    loadIndicator = true;
                    LoadBlackjack();
                } else if (game.equals("Hangman")) {
                    loadIndicator = true;
                    switchToHangman();
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
        GameSettingIntent.putExtra("game", game
        );

        StartingActivity.this.startActivity(GameSettingIntent);
    }

    private void LoadSlidingTiles() {
        Intent goToSaved = new Intent(StartingActivity.this, LoadActivity.class);
        goToSaved.putExtra("name", name);
        goToSaved.putExtra("game", game);
        StartingActivity.this.startActivity(goToSaved);

    }

    private void switchToBlackjack() {
        Intent BlackjackGameIntent = new Intent(this, BlackjackGameActivity.class);
        BlackjackGameIntent.putExtra("name", name);
        BlackjackGameIntent.putExtra("game", game);
        BlackjackGameActivity.load = loadIndicator;
        StartingActivity.this.startActivity(BlackjackGameIntent);

    }

    private void switchToHangman() {
        Intent HangmanGameIntent = new Intent(this, HangmanModes.class);
        HangmanGameIntent.putExtra("name", name);
        HangmanGameIntent.putExtra("game", game);
        HangmanGameIntent.putExtra("load", loadIndicator);
        StartingActivity.this.startActivity(HangmanGameIntent);
    }


    private void LoadBlackjack() {
        String FileName = name + "_" + game + ".ser";
        if (loadFromFile("stateManager" + FileName) != null &&
                loadFromFile("bankManager" + FileName) != null) {
            switchToBlackjack();
        } else {
            Toast.makeText(this, "No Existing Save", Toast.LENGTH_SHORT).show();
        }
    }

    private Object loadFromFile(String fileName) {

        try {
            Object object;
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                object = input.readObject();
                inputStream.close();
                return object;
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

}
