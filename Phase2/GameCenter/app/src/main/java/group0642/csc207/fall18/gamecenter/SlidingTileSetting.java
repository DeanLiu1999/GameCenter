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

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SlidingTileSetting extends AppCompatActivity {
    private BoardManager boardManager;
    private SaveManager saveManager;
    private static final String[] paths = {"4 * 4", "3 * 3", "5 * 5"};
    private static final String[] paths2 = {"number", "flower", "deathwing", "illidan", "jaina",
            "leader", "malfurion", "medivh", "thrall", "tyrande", "velen", "arthas", "car", "elf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile_setting);

        Intent i = getIntent();
        final String name = i.getStringExtra("name");
        final String game = i.getStringExtra("game");

        saveManager = new SaveManager.Builder()
                .context(this)
                .saveDirectory(name, game)
                .build();

        boardManager = new BoardManager();
        int size = 4;
        boardManager.setGameSize(size);
        int back = 0;
        Tile.setImages(back);

        final EditText maxUndo = findViewById(R.id.maxundo);
        final Button go = findViewById(R.id.gobutton);
        go.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                String max = maxUndo.getText().toString();
                boardManager.setUndo(Integer.parseInt(max));
                boardManager.refresh_board_manager();
                saveManager.newObject(boardManager);
                saveManager.saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
                switchToGame(name, game);
            }
        });
        Spinner spinner1 = findViewById(R.id.spinnercomplexity);
        Spinner spinner2 = findViewById(R.id.spinnerbackground);
        spinner1.setAdapter(createAdapter(paths));
        spinner2.setAdapter(createAdapter(paths2));

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

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
     * Return ArrayAdapter for spinner.
     *
     * @param path the choices on spinner
     * @return ArrayAdapter initialized with path
     */
    public ArrayAdapter<String> createAdapter(String[] path){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, path);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Switch to the GameActivity to play sliding tiles.
     *
     * @param s the username of the current active user
     * @param t the currently selected game
     */
    private void switchToGame(String s, String t) {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("name", s);
        tmp.putExtra("game", t);
        startActivity(tmp);
    }
}