package group0642.csc207.fall18.gamecenter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static group0642.csc207.fall18.gamecenter.StartingActivity.TEMP_SAVE_FILENAME;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /*
    Application of logcat for debugging based on information and examples adapted from
    https://developer.android.com/studio/debug/am-logcat
    Retrieved Oct. 27, 2018
     */

    /**
     * The log tag of this class.
     */
    private static final String TAG = "GameActivity_SlidingTiles";
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    /**
     * The buttons for undo and scoreboard.
     */
    private Button undo;
    private Button scoreboard;
    /**
     * The tile buttons to display.
     */
    private ArrayList<Button> tileButtons;
    /**
     * The names of all preset background image options.
     */
    private String[] back = {"flower", "deathwing", "illidan", "jaina",
            "leader", "malfurion", "medivh", "thrall", "tyrande", "velen", "arthas", "car", "elf"};
    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;
    /**
     * Tracker and preset interval for auto-saving.
     */
    private static int autoSaveTracker;
    private static final int AUTO_SAVE_INTERVAL = 5;
    /**
     * Name of current game, username of current active user, and score.
     */
    private static String game;
    private static String name;
    private static int s;

    /**
     * Returns the username of the current active user.
     *
     * @return the username of the current active user
     */
    private String getName() {
        return name;
    }

    /**
     * Returns the name of the current game.
     *
     * @return the name of the current game
     */
    private String getGame() {
        return game;
    }

    /**
     * Returns the reported score.
     *
     * @return the reported score.
     */
    private int getScore() {
        return s;
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        game = i.getStringExtra("game");

        undo = findViewById(R.id.undo);
        scoreboard = findViewById(R.id.scoreboard);
        final Button showImage = findViewById(R.id.fullbutton);

        addSaveButtonListener(name, game);
        // Give an initial value to begin tracking the interval for auto-saving.
        autoSaveTracker = 0;

        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bg = boardManager.getBoard().bg - 1;
                if (bg >= 0) {
                    Dialog builder = new Dialog(GameActivity.this);
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    builder.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            //nothing;
                        }
                    });
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + back[bg]);
                    ImageView imageView = new ImageView(GameActivity.this);
                    imageView.setImageURI(uri);
                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    builder.show();
                }
            }
        });

        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seeScore = new Intent(GameActivity.this, EndingScore.class);
                seeScore.putExtra("name", getName());
                seeScore.putExtra("game", getGame());
                seeScore.putExtra("score", getScore());
                GameActivity.this.startActivity(seeScore);
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Auto-save tracker is reset on undo.
                autoSaveTracker = -1;
                boardManager.undoStep();
            }
        });

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    private void addSaveButtonListener(final String user, final String game) {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saveFileName = user + "_" + game + "_" + Board.NUM_ROWS + ".ser";
                saveToFile(saveFileName);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(TEMP_SAVE_FILENAME);
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

    /**
     * Add a new score to the ScoreBoard.
     *
     * @param score the newly reported score
     */
    public static void addToScoreBoard(int score) {
        s = score;
        ScoreBoard.updateScoreBoard(GameActivity.game, GameActivity.name, score);
    }

    /**
     * Tick autoSaveTracker forward and save the game at predefined AUTO_SAVE_INTERVAL.
     */
    private void tickAutoSave() {
        Log.d(TAG, Integer.toString(autoSaveTracker));
        if (!(++autoSaveTracker < AUTO_SAVE_INTERVAL)) {
            Log.d(TAG, "Auto-saving");
            String saveFileName = name + "_" + game + "_" + Board.NUM_ROWS + ".ser";
            saveToFile(saveFileName);
            makeToastSavedText();
            autoSaveTracker = 0;
        }
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String saveFileName = name + "_" + game + "_" + Board.NUM_ROWS + ".ser";
        saveToFile(saveFileName);
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d(TAG, "State updated");
        if (boardManager.puzzleSolved()) {
            undo.setEnabled(false);
            scoreboard.setEnabled(true);
        }
        tickAutoSave();
        display();
    }
}