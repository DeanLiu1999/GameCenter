package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class ScoreDisplay extends AppCompatActivity {
    private int counter = 0;
    // 1 for displaying score per-game, and 2 for displaying score per-person.
    public static int scoreboardMode = 1;
    private ArrayList scoreList;
    private ArrayList<ArrayList> pages;
    private int limit;
    private Button nextTen, prevTen;
    private ArrayToString format = new ArrayToString();


    /**
     * @param savedInstanceState Implemented the functionality of the displays
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);

        Intent intent = getIntent();
        String ID = intent.getStringExtra("userId");
        String gameName = intent.getStringExtra("gameName_1");
        TextView title = findViewById(R.id.title_2);
        if (scoreboardMode == 1) {
            scoreList = new ScoreBoard().getScorePerGame(gameName);
            title.setText(String.format("%s top scores", gameName));
        } else {
            scoreList = new ScoreBoard().getScoreGameUser(gameName, ID);
            title.setText(String.format("Your score in %s", gameName));
        }

        pages = new ArrayListPartitioner().partitionByLengthTen(scoreList);
        limit = pages.size();
        nextTen = findViewById(R.id.next_10);
        prevTen = findViewById(R.id.prev_10);
        // the initial page display
        prevTen.setEnabled(false);
        if (limit == 0) {
            nextTen.setEnabled(false);
        } else if (counter == limit - 1) {
            display_score(pages.get(counter));
            nextTen.setEnabled(false);
        } else {
            display_score(pages.get(counter));
        }

        setNextTenListener();
        setPrevTenListener();

    }

    private void setNextTenListener() {
        // functionality for the next page(10 scores each page)
        nextTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                prevTen.setEnabled(true);
                if (counter < limit - 1) {
                    display_score(pages.get(counter));
                } else if (counter == limit - 1) {
                    display_score(pages.get(counter));
                    nextTen.setEnabled(false);
                } else {
                    counter = limit - 1;
                    nextTen.setEnabled(false);
                }

            }
        });
    }

    private void setPrevTenListener() {
        // functionality for the previous page button
        prevTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                nextTen.setEnabled(true);
                if (counter > 0) {
                    display_score(pages.get(counter));

                } else if (counter == 0) {
                    display_score(pages.get(counter));
                    prevTen.setEnabled(false);
                } else {
                    counter = 0;
                    prevTen.setEnabled(false);
                }
            }
        });
    }

    /**
     * @param lst an ArrayList of Objects
     *            take in an ArrayList and display them on the score board.
     */
    private void display_score(ArrayList lst) {
        // The size of the ArrayList should be less than or equal to 10
        final TextView slot_1 = findViewById(R.id.textView5);
        final TextView slot_2 = findViewById(R.id.textView6);
        final TextView slot_3 = findViewById(R.id.textView7);
        final TextView slot_4 = findViewById(R.id.textView8);
        final TextView slot_5 = findViewById(R.id.textView9);
        final TextView slot_6 = findViewById(R.id.textView10);
        final TextView slot_7 = findViewById(R.id.textView11);
        final TextView slot_8 = findViewById(R.id.textView12);
        final TextView slot_9 = findViewById(R.id.textView13);
        final TextView slot_10 = findViewById(R.id.textView14);
        final TextView[] slots = {slot_1, slot_2, slot_3, slot_4,
                slot_5, slot_6, slot_7, slot_8, slot_9, slot_10};
        // empty the view before filling them
        int j = 0;
        while (j < slots.length) {
            slots[j].setText("");
            j++;
        }
        // filling the TextView
        int i = 0;
        while (i < lst.size()) {
            if (scoreboardMode == 1) {
                Object[] arr = (Object[]) lst.get(i);
                slots[i].setText(format.getString(arr));
            } else {
                String msg = lst.get(i).toString();
                slots[i].setText(String.format("  %s", msg));
            }
            i++;
        }

    }

}
