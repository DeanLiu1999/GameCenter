package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class EndingScore extends AppCompatActivity {
    private String user, gameCall;

    /**
     * @param savedInstanceState is given
     *                           The display of the user's score after finishing the game
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_score);
        Intent intent = getIntent();
        user = intent.getStringExtra("name");
        gameCall = intent.getStringExtra("game");
        int score = intent.getIntExtra("score", 0);

        ArrayList<Object[]> userRanks = new ScoreBoard().getAfterGameScore(gameCall, user, score);
        int mark = marker(userRanks, score, user);

        anotherGameButtonListener();
        showButtonListener();
        exitButtonListener();
        TextView title = findViewById(R.id.title_4);
        title.setText(String.format("%s\'s Score", user));
        displayScore(userRanks, mark);
    }

    /**
     * Show the PerGameRecord page.
     */
    private void showButtonListener() {
        Button showScore = findViewById(R.id.showScoreboard);
        showScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moreScore = new Intent(EndingScore.this, ScoreDisplay.class);
                moreScore.putExtra("gameName_1", gameCall);
                moreScore.putExtra("userId", user);
                ScoreDisplay.scoreboardMode = 1;
                EndingScore.this.startActivity(moreScore);
            }
        });
    }

    /**
     * The button that exit the game and log out the user
     */
    private void exitButtonListener() {
        Button exit = findViewById(R.id.quit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quit = new Intent(EndingScore.this, LoginActivity.class);
                EndingScore.this.startActivity(quit);
            }
        });
    }

    /**
     *
     */
    private void anotherGameButtonListener() {

        final Button anotherGame = findViewById(R.id.newGame);
        anotherGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(EndingScore.this, Preface.class);
                back.putExtra("name", user);
                EndingScore.this.startActivity(back);
            }
        });
    }

    /**
     * @param lst    is a list of scores
     * @param points is the score of the user's last game
     * @param name   is the name of the user
     * @return an integer that marks a user's score in the recent game
     */
    private int marker(ArrayList lst, int points, String name) {
        int i = 0;
        while (i < lst.size()) {

            Object[] arr = (Object[]) lst.get(i);
            String username = (String) arr[1];
            int Score = (int) arr[2];
            if (Score == points && name.equals(username)) {
                return i;
            }
            i++;
        }
        return i;
    }


    /**
     * @param lst      is an ArrayList of Object arrays of size 2
     * @param position is a marker of where is user position in this list
     *                 This is the method that displays a segment of the scoreboard that contains tht user's last
     *                 game's score and the this score's rank in the scoreboard for the game.
     */
    private void displayScore(ArrayList lst, int position) {
        // The size of the ArrayList should be less than 10
        final TextView slot_1 = findViewById(R.id.one);
        final TextView slot_2 = findViewById(R.id.two);
        final TextView slot_3 = findViewById(R.id.three);
        final TextView slot_4 = findViewById(R.id.four);
        final TextView slot_5 = findViewById(R.id.five);
        final TextView slot_6 = findViewById(R.id.six);
        final TextView slot_7 = findViewById(R.id.seven);
        final TextView slot_8 = findViewById(R.id.eight);
        final TextView slot_9 = findViewById(R.id.nine);

        final TextView[] slots = {slot_1, slot_2, slot_3, slot_4,
                slot_5, slot_6, slot_7, slot_8, slot_9};
        int j = 0;
        while (j < slots.length) {
            slots[j].setText("");
            j++;
        }
        int i = 0;
        while (i < lst.size()) {
            Object[] arr = (Object[]) lst.get(i);
            String username = (String) arr[1];
            String Score = arr[2].toString();
            String rank = arr[0].toString();
            if (i == position) {
                String tmp = "Your Score:";
                slots[i].setText(String.format("%-4s%-20s  %-10s", rank + ".", tmp, Score));
            } else {
                slots[i].setText(String.format("%-4s%-30s  %-10s", rank + ".", username, Score));
            }
            i++;
        }

    }

}


