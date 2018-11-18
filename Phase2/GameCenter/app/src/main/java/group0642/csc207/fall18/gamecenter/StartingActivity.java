package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The log tag of this class.
     */
    private static final String TAG = "StartingActivity_SlidingTiles";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_temp.ser";
    /**
     * The board manager.
     */
    private BoardManager boardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager();

        Intent inherit = getIntent();
        String name = inherit.getStringExtra("name");
        String game = inherit.getStringExtra("game");

        // Default complexity (4x4) and background (no image) for a game of SlidingTiles.
        int size = 4;
        boardManager.setGameSize(size);
        int back = 0;
        Tile.setImages(back);

        getExternalFilesDir(null);//create the directory for file writing and reading

        saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_starting_);
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

                switchToSetting(nm, gm);
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
                Intent goToSaved = new Intent(StartingActivity.this, LoadActivity.class);
                goToSaved.putExtra("name", user);
                goToSaved.putExtra("game", game);
                StartingActivity.this.startActivity(goToSaved);
            }
        });
    }

    /**
     * Activate the save button.
     *
     * @param user the username of the current active user
     * @param game the currently selected game
     */


    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the BackgroundSetting to configure the tile background.
     *
     * @param s the username of the current active user
     * @param t the currently selected game
     */
    private void switchToSetting(String s, String t) {
        Intent GameSettingIntent = new Intent(this, BackgroundSetting.class);
        GameSettingIntent.putExtra("name", s);
        GameSettingIntent.putExtra("game", t);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        StartingActivity.this.startActivity(GameSettingIntent);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }
}
