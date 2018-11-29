package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ScorePerGame extends AppCompatActivity {

    private int perGameOrPerson;
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
     *
     * Show the scores of the user in the game of Sliding Tiles
     */
    private void sTilesListener() {
        final Button sTiles = findViewById(R.id.slidingtiles);
        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seePersonal = new Intent(ScorePerGame.this
                        , ScoreDisplay.class);
                seePersonal.putExtra("userId", nameId);
                seePersonal.putExtra("gameName_1", "Sliding Tiles");
                ScoreDisplay.scoreboardMode = perGameOrPerson;
                ScorePerGame.this.startActivity(seePersonal);
            }
        });
    }

    private void hangmanListener() {
        final Button sTiles = findViewById(R.id.hangman);
        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seePersonal = new Intent(ScorePerGame.this
                        , ScoreDisplay.class);
                seePersonal.putExtra("userId", nameId);
                seePersonal.putExtra("gameName_1", "Hangman");
                ScoreDisplay.scoreboardMode = perGameOrPerson;
                ScorePerGame.this.startActivity(seePersonal);
            }
        });
    }

    private void blackjackListener() {
        final Button sTiles = findViewById(R.id.blackjack);
        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seePersonal = new Intent(ScorePerGame.this
                        , ScoreDisplay.class);
                seePersonal.putExtra("userId", nameId);
                seePersonal.putExtra("gameName_1", "Blackjack");
                ScoreDisplay.scoreboardMode = perGameOrPerson;
                ScorePerGame.this.startActivity(seePersonal);
            }
        });
    }
}
