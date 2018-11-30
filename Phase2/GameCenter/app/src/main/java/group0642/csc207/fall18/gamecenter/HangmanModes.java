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

public class HangmanModes extends AppCompatActivity {
    private boolean loadOrNot;
    private String name;
    private String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_modes);
        Intent inherit = getIntent();
        name = inherit.getStringExtra("name");
        game = inherit.getStringExtra("game");
        loadOrNot = inherit.getBooleanExtra("load", false);
        launchBattleMode();
        launchInfinityMode();
    }

    private void launchBattleMode() {
        Button launchNormal = findViewById(R.id.normalButton);
        launchNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadOrNot) {
                    loadBattle();
                } else {
                    switchToBattle();
                }
            }
        });
    }

    private void launchInfinityMode() {
        Button launchInfinity = findViewById(R.id.infinityButton);
        launchInfinity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadOrNot) {
                    loadInfinity();
                } else {
                    switchToInfinity();
                }
            }
        });
    }


    private void loadInfinity() {
        String FileName = name + "_" + game + "Infinity" + ".ser";
        if (loadFromFile("answer" + FileName) != null &&
                loadFromFile("score" + FileName) != null &&
                loadFromFile("entered" + FileName) != null) {
            switchToInfinity();
        } else {
            Toast.makeText(this, "No existing file", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadBattle() {
        String FileName = name + "_" + game + "Battle" + ".ser";
        if (loadFromFile("answer" + FileName) != null &&
                loadFromFile("score" + FileName) != null &&
                loadFromFile("entered" + FileName) != null &&
                loadFromFile("battle" + FileName) != null) {
            switchToBattle();
        } else {
            Toast.makeText(this, "No existing file", Toast.LENGTH_SHORT).show();
        }

    }

    private void switchToBattle() {
        Intent goToNormal = new Intent(this, HangmanBattle.class);
        goToNormal.putExtra("name", name);
        goToNormal.putExtra("game", game);
        HangmanBattle.load = loadOrNot;
        HangmanModes.this.startActivity(goToNormal);
    }

    private void switchToInfinity() {
        Intent goToInfinity = new Intent(this, HangmanActivity.class);
        goToInfinity.putExtra("name", name);
        goToInfinity.putExtra("game", game);
        HangmanActivity.load = loadOrNot;
        HangmanModes.this.startActivity(goToInfinity);

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
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Exception", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }
}