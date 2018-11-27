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
    private static final String TAG = "StartingActivity";

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
        Intent HangmanGameIntent = new Intent(this, HangmanModes.class);
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
        Intent HangmanLoadIntent = new Intent(this, HangmanModes.class);
        HangmanLoadIntent.putExtra("name", s);
        HangmanLoadIntent.putExtra("game", t);
        HangmanLoadIntent.putExtra("load", true);
        StartingActivity.this.startActivity(HangmanLoadIntent);
    }

    private void LoadBlackjack(String s, String t) {
        String FileName = name + "_" + game + ".ser";
        if(
                loadFromFile("bankManager" + FileName) != null) {
            Intent BlackjackLoadIntent = new Intent(this, BlackjackGameActivity.class);
            BlackjackLoadIntent.putExtra("name", s);
            BlackjackLoadIntent.putExtra("game", t);
            BlackjackGameActivity.load = true;
            StartingActivity.this.startActivity(BlackjackLoadIntent);
        }else{
            makeToastText("No Existing Save!");
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

    private void makeToastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
