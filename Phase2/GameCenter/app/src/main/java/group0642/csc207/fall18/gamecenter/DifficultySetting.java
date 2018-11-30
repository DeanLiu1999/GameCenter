package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DifficultySetting extends AppCompatActivity {
    private Spinner spinner1;
    private BoardManager boardManager;
    // TODO
    private SaveManagerNew saveManager;
    private static final String[] paths = {"4 * 4", "3 * 3", "5 * 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        final String name = i.getStringExtra("name");
        final String game = i.getStringExtra("game");

        saveManager = new SaveManagerNew.Builder()
                .context(this)
                .saveDirectory(name, game)
                .build();

        boardManager = (BoardManager) saveManager.loadFromFile(StartingActivity.TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_difficulty);

        final EditText maxUndo = findViewById(R.id.maxundo);
        final Button go = findViewById(R.id.gobutton);
        go.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                String max = maxUndo.getText().toString();
                boardManager.setUndo(Integer.parseInt(max));
                boardManager.refresh_board_manager();
                saveManager.saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
                switchToGame(name, game);
            }
        });
        spinner1 = (Spinner) findViewById(R.id.spinnercomplexity);

        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paths);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Board.setGameSize(4);
                        break;
                    case 1:
                        Board.setGameSize(3);
                        break;
                    case 2:
                        Board.setGameSize(5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    // TODO
    /*
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
    */
    /*
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    /*
    // TODO
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    */
    private void switchToGame(String s, String t) {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("name", s);
        tmp.putExtra("game", t);
        startActivity(tmp);
    }
}
