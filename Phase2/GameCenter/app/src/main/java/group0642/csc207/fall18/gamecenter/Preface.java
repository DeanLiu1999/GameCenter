package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Preface extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preface);


        Intent intent = getIntent();
        final String nameId = intent.getStringExtra("name");
        String nameCopy = nameId;

        game1ClickListener(nameCopy);
        perGameListener();
        perPerson(nameCopy);


    }

    /**
     * @param names for username
     * Proceed to game 1: Sliding Tiles
     */
    private void game1ClickListener(final String names) {
        Button game1 = findViewById(R.id.game1);
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame1 = new Intent(Preface.this, StartingActivity.class);
                playGame1.putExtra("name", names);
                playGame1.putExtra("game", "Sliding Tiles");
                Preface.this.startActivity(playGame1);
            }
        });
    }

    /**
     * Proceed to scoreboard per game
     */
    private void perGameListener() {
        final Button perGame = findViewById(R.id.perGame);
        perGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accessScorePerGame = new Intent(Preface.this,
                        GameScoreActivity.class);
                Preface.this.startActivity(accessScorePerGame);
            }
        });

    }

    /**
     * @param s for name
     * Proceed to scoreboard per person
     */
    private void perPerson(final String s) {
        final Button perPerson = findViewById(R.id.perPerson);
        perPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accessScorePerPerson = new Intent(Preface.this,
                        PersonalScoreActivity.class);
                accessScorePerPerson.putExtra("name", s);
                Preface.this.startActivity(accessScorePerPerson);
            }
        });
    }
}
