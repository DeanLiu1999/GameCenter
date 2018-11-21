package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PersonalScoreActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState is given
     * Just to show the scores of this user in a chosen game
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_score);
        Intent intent = getIntent();
        final String nameId = intent.getStringExtra("name");
        String nameCopy = nameId;

        sTilesListener(nameCopy);
        hangmanListener(nameCopy);
        blackjackListener(nameCopy);

    }

    /**
     * @param s for name
     * Show the scores of the user in the game of Sliding Tiles
     */
    private void sTilesListener(final String s) {
        final Button sTiles = findViewById(R.id.slidingtiles);
        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seePersonal = new Intent(PersonalScoreActivity.this
                        , PerPersonRecord.class);
                seePersonal.putExtra("userId", s);
                seePersonal.putExtra("gameName_1", "Sliding Tiles");
                PersonalScoreActivity.this.startActivity(seePersonal);
            }
        });
    }

    private void hangmanListener(final String s) {
        final Button sTiles = findViewById(R.id.hangman);
        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seePersonal = new Intent(PersonalScoreActivity.this
                        , PerPersonRecord.class);
                seePersonal.putExtra("userId", s);
                seePersonal.putExtra("gameName_1", "Hangman");
                PersonalScoreActivity.this.startActivity(seePersonal);
            }
        });
    }

    private void blackjackListener(final String s){
        final Button sTiles = findViewById(R.id.blackjack);
        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seePersonal = new Intent(PersonalScoreActivity.this
                        , PerPersonRecord.class);
                seePersonal.putExtra("userId", s);
                seePersonal.putExtra("gameName_1", "Blackjack");
                PersonalScoreActivity.this.startActivity(seePersonal);
            }
        });
    }
}
