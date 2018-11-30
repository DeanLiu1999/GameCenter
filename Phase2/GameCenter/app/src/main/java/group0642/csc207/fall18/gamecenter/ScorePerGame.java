package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ScorePerGame extends AppCompatActivity {
    //this is the indicator of whether to display score per-person or score per-game
    private int perGameOrPerson;
    // this is the variable for the username
    private String nameId;

    /**
     * @param savedInstanceState is given
     *                           Just to show the scores of this user in a chosen game
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_game);

        Intent intent = getIntent();
        nameId = intent.getStringExtra("name");
        perGameOrPerson = intent.getIntExtra("mode", 2);

        sTilesListener();
        hangmanListener();
        blackjackListener();

    }

    /**
     * Show the scores of the user in the game of Sliding Tiles
     */
    private void sTilesListener() {
        final Button sTiles = findViewById(R.id.slidingtiles);
        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSlidingTiles();

            }
        });
    }

    /**
     * Show the scores of the user in the game of hangman
     */
    private void hangmanListener() {
        final Button hMan = findViewById(R.id.hangman);
        hMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToHangman();

            }
        });
    }

    /**
     * Show the scores of the user in the game of blackjack
     */
    private void blackjackListener() {
        final Button bJack = findViewById(R.id.blackjack);
        bJack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switchToBlackjack();
            }
        });
    }

    /**
     * The intent to switch to Blackjack
     */
    private void switchToBlackjack() {
        Intent seePersonal = new Intent(ScorePerGame.this
                , ScoreDisplay.class);
        seePersonal.putExtra("userId", nameId);
        seePersonal.putExtra("gameName_1", "Blackjack");
        ScoreDisplay.scoreboardMode = perGameOrPerson;
        ScorePerGame.this.startActivity(seePersonal);
    }


    /**
     * The intent to switch to Hangman
     */
    private void switchToHangman() {
        Intent seePersonal = new Intent(ScorePerGame.this
                , ScoreDisplay.class);
        seePersonal.putExtra("userId", nameId);
        seePersonal.putExtra("gameName_1", "Hangman");
        ScoreDisplay.scoreboardMode = perGameOrPerson;
        ScorePerGame.this.startActivity(seePersonal);
    }

    /**
     * The intent to switch to Sliding Tiles
     */
    private void switchToSlidingTiles() {
        Intent seePersonal = new Intent(ScorePerGame.this
                , ScoreDisplay.class);
        seePersonal.putExtra("userId", nameId);
        seePersonal.putExtra("gameName_1", "Sliding Tiles");
        ScoreDisplay.scoreboardMode = perGameOrPerson;
        ScorePerGame.this.startActivity(seePersonal);
    }
}
