package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoadActivity extends AppCompatActivity {

    /**
     * The log tag of this class.
     */
    private static final String TAG = "LoadActivity_SlidingTiles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String game = intent.getStringExtra("game");

        threeByThreeListener(name, game);
        fourByFourListener(name, game);
        fiveByFiveListener(name, game);
    }

    /**
     * Activate the 3x3 button.
     *
     * @param name the username of the current active user
     * @param game the currently selected game
     */
    private void threeByThreeListener(final String name, final String game) {
        Button threeByThree = findViewById(R.id.three);
        threeByThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareSavedGame(3, name, game);
            }
        });
    }

    /**
     * Activate the 4x4 button.
     *
     * @param name the username of the current active user
     * @param game the currently selected game
     */
    private void fourByFourListener(final String name, final String game) {
        Button fourByFour = findViewById(R.id.four);
        fourByFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareSavedGame(4, name, game);
            }
        });
    }

    /**
     * Activate the 5x5 button.
     *
     * @param name the username of the current active user
     * @param game the currently selected game
     */
    private void fiveByFiveListener(final String name, final String game) {
        Button fiveByFive = findViewById(R.id.five);
        fiveByFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareSavedGame(5, name, game);
            }
        });
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private BoardManager loadFromFile(String fileName) {

        try {
            BoardManager boardManager;
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
                return boardManager;
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

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(BoardManager boardManager, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that there's no existing save of selected board configuration.
     */
    private void makeToastNoSave() {
        Toast.makeText(this, "No existing save", Toast.LENGTH_SHORT).show();
    }

    /**
     * Load the saved game state for a given game complexity if such save file exists.
     *
     * @param complexity the complexity (i.e. the board size) of the game.
     * @param name       the username of the current active user
     * @param game       the currently selected game
     */
    private void prepareSavedGame(int complexity, String name, String game) {
        String saveFileName = name + "_" + game + "_" + Integer.toString(complexity) + ".ser";
        BoardManager boardManager = loadFromFile(saveFileName);
        if (boardManager == null) {
            makeToastNoSave();
        } else {
            Board.setGameSize(complexity);
            saveToFile(boardManager, StartingActivity.TEMP_SAVE_FILENAME);
            makeToastLoadedText();
            switchToGame(boardManager, name, game);
        }
    }

    /**
     * Switch to the GameActivity view to play the loaded game.
     *
     * @param boardManager the game state loaded from a selected file
     * @param name         the username of the current active user
     * @param game         the currently selected game
     */
    private void switchToGame(BoardManager boardManager, String name, String game) {
        Intent tmp = new Intent(LoadActivity.this, GameActivity.class);
        tmp.putExtra("name", name);
        tmp.putExtra("game", game);
        saveToFile(boardManager, StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }
}