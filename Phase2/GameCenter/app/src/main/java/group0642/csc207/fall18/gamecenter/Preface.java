package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Preface extends AppCompatActivity {

    // the name of the user
    private String nameCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preface);


        Intent intent = getIntent();
        nameCopy = intent.getStringExtra("name");

        slideTilesClickListener();
        blackjackClickListener();
        hangmanClickListener();
        perGameListener();
        perPerson();
    }

    /**
     * the user switch to the game of blackjack by pressing this button
     */
    private void blackjackClickListener() {
        Button game1 = findViewById(R.id.blackjack);
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame1 = new Intent(Preface.this, StartingActivity.class);
                playGame1.putExtra("name", nameCopy);
                playGame1.putExtra("game", "Blackjack");
                Preface.this.startActivity(playGame1);
            }
        });
    }

    /**
     * the user switch to the game of hangman by pressing this button
     */
    private void hangmanClickListener() {
        Button game1 = findViewById(R.id.hangman);
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame1 = new Intent(Preface.this, StartingActivity.class);
                playGame1.putExtra("name", nameCopy);
                playGame1.putExtra("game", "Hangman");
                Preface.this.startActivity(playGame1);
            }
        });
    }

    /**
     * Proceed to game 1: Sliding Tiles
     */
    private void slideTilesClickListener() {
        Button game1 = findViewById(R.id.slidingtiles);
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame1 = new Intent(Preface.this, StartingActivity.class);
                playGame1.putExtra("name", nameCopy);
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
                switchToScore(1);
            }
        });

    }

    /**
     * Proceed to scoreboard per person
     */
    private void perPerson() {
        final Button perPerson = findViewById(R.id.perPerson);
        perPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToScore(2);
            }
        });
    }

    /**
     * @param i where 1 means score per game and 2 means score per person
     *          it switches to the activity that display all the scores
     */
    void switchToScore(final int i) {
        Intent accessScore = new Intent(Preface.this,
                ScorePerGame.class);
        accessScore.putExtra("name", nameCopy);
        accessScore.putExtra("mode", i);
        Preface.this.startActivity(accessScore);
    }
}