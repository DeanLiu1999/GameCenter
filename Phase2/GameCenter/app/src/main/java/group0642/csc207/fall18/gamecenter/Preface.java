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

        slideTilesClickListener(nameCopy);
        blackjackClickListener(nameCopy);
        hangmanClickListener(nameCopy);
        perGameListener(nameCopy);
        perPerson(nameCopy);
    }

    private void blackjackClickListener(final String names) {
        Button game1 = findViewById(R.id.blackjack);
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame1 = new Intent(Preface.this, StartingActivity.class);
                playGame1.putExtra("name", names);
                playGame1.putExtra("game", "Blackjack");
                Preface.this.startActivity(playGame1);
            }
        });
    }

    private void hangmanClickListener(final String names) {
        Button game1 = findViewById(R.id.hangman);
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame1 = new Intent(Preface.this, StartingActivity.class);
                playGame1.putExtra("name", names);
                playGame1.putExtra("game", "Hangman");
                Preface.this.startActivity(playGame1);
            }
        });
    }

    /**
     * @param names for username
     *              Proceed to game 1: Sliding Tiles
     */
    private void slideTilesClickListener(final String names) {
        Button game1 = findViewById(R.id.slidingtiles);
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
    private void perGameListener(final String s) {
        final Button perGame = findViewById(R.id.perGame);
        perGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToScore(s, 1);
            }
        });

    }

    /**
     * @param s for name
     *          Proceed to scoreboard per person
     */
    private void perPerson(final String s) {
        final Button perPerson = findViewById(R.id.perPerson);
        perPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToScore(s, 2);
            }
        });
    }

    void switchToScore(final String s, final int i) {
        Intent accessScore = new Intent(Preface.this,
                ScorePerGame.class);
        accessScore.putExtra("name", s);
        accessScore.putExtra("mode", i);
        Preface.this.startActivity(accessScore);
    }
}