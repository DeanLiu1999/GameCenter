package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GameScoreActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState is given
     * Just to show the scores in this game
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);
        sTilesListener();

    }

    /**
     * Show the scores in the game of Sliding Tiles
     */
    private void sTilesListener() {
        Button sTiles = findViewById(R.id.game_1);

        sTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewGameScore = new Intent(GameScoreActivity.this,
                        PerGameRecord.class);
                viewGameScore.putExtra("gameName", "Sliding Tiles");
                GameScoreActivity.this.startActivity(viewGameScore);
            }
        });
    }
}
