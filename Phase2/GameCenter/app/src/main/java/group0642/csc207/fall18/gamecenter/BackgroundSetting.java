package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BackgroundSetting extends AppCompatActivity {
    private BoardManager boardManager;
    private static final String[] paths = {"number", "flower", "deathwing", "illidan", "jaina",
            "leader", "malfurion", "medivh", "thrall", "tyrande", "velen", "arthas", "car", "elf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_choosing);
        Intent i = getIntent();
        final String name = i.getStringExtra("name");
        final String game = i.getStringExtra("game");

        // Default complexity (4x4) and background (no image) for a game of SlidingTiles.

        boardManager = new BoardManager();
        int size = 4;
        boardManager.setGameSize(size);
        int back = 0;
        Tile.setImages(back);

        final Button next = findViewById(R.id.nextbutton);
        next.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
                switchToDifficulty(name, game);
            }
        });
        Spinner spinner1 = findViewById(R.id.spinnerbackground);

        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paths);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Tile.setImages(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private void switchToDifficulty(String s, String t) {
        Intent tmp = new Intent(this, DifficultySetting.class);
        tmp.putExtra("name", s);
        tmp.putExtra("game", t);
        startActivity(tmp);
    }
}
