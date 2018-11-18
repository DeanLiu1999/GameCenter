package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static group0642.csc207.fall18.gamecenter.ScoreBoard.getScorePerGame;

public class PerGameRecord extends AppCompatActivity {

    private int counter = 0;


    /**
     * @param savedInstanceState is given
     * Implemented the functionality of the displays
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_game_record);

        Intent intent = getIntent();
        String gameName = intent.getStringExtra("gameName");

        ArrayList<Object[]> lstScore = getScorePerGame(gameName);

        final ArrayList<ArrayList> pages_1 = divBy10(lstScore);
        final int limit = pages_1.size();
        final Button prev10 = findViewById(R.id.prev10);
        final Button next10 = findViewById(R.id.next10);

        // the initial page display
        prev10.setEnabled(false);
        if (pages_1.size() == 0) {
            next10.setEnabled(false);
        } else if (counter == limit - 1) {
            display_score(pages_1.get(counter));
            next10.setEnabled(false);
        } else {
            display_score(pages_1.get(counter));
        }
        // functionality for the previous page button
        prev10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                next10.setEnabled(true);
                if (counter > 0) {
                    display_score(pages_1.get(counter));

                } else if (counter == 0) {
                    display_score(pages_1.get(counter));
                    prev10.setEnabled(false);
                } else {
                    counter = 0;
                    prev10.setEnabled(false);
                }
            }
        });
        // functionality for the next page button
        next10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                prev10.setEnabled(true);
                if (counter < limit - 1) {
                    display_score(pages_1.get(counter));
                } else if (counter == limit - 1) {
                    display_score(pages_1.get(counter));
                    next10.setEnabled(false);
                } else {
                    counter = limit - 1;
                    next10.setEnabled(false);
                }

            }
        });
    }

    /**
     * @param lst an ArrayList
     * @return an ArrayList that contains ArrayLists each of size ten.
     */
    private ArrayList<ArrayList> divBy10(ArrayList lst) {
        if (lst == null) {
            return new ArrayList<>();
        } else if (lst.size() <= 10) {
            ArrayList<ArrayList> nest_1 = new ArrayList<ArrayList>();
            nest_1.add(lst);
            return nest_1;
        }
        ArrayList<ArrayList> nest = new ArrayList<ArrayList>();
        ArrayList<ArrayList> lst_1 = new ArrayList<ArrayList>();
        ArrayList<ArrayList> lst_2 = new ArrayList<ArrayList>();
        lst_1.addAll(lst.subList(0, 10));
        lst_2.addAll(lst.subList(10, lst.size()));
        nest.add(lst_1);
        nest.addAll(divBy10(lst_2));
        return nest;
    }


    /**
     * @param lst an ArrayList of object arrays with size 2
     *            take in an ArrayList and display them on the score board.
     */
    private void display_score(ArrayList lst) {
        // The size of the ArrayList should be less than or equal 10
        final TextView slot_1 = findViewById(R.id.first_1);
        final TextView slot_2 = findViewById(R.id.second_2);
        final TextView slot_3 = findViewById(R.id.third_3);
        final TextView slot_4 = findViewById(R.id.fourth_4);
        final TextView slot_5 = findViewById(R.id.five_5);
        final TextView slot_6 = findViewById(R.id.six_6);
        final TextView slot_7 = findViewById(R.id.seventh_7);
        final TextView slot_8 = findViewById(R.id.eight_8);
        final TextView slot_9 = findViewById(R.id.nine_9);
        final TextView slot_10 = findViewById(R.id.ten_10);
        final TextView[] slots = {slot_1, slot_2, slot_3, slot_4,
                slot_5, slot_6, slot_7, slot_8, slot_9, slot_10};
        int j = 0;
        // empty the view before filling them
        while (j < slots.length) {
            slots[j].setText("");
            j++;
        }
        int i = 0;
        // filling the TextView
        while (i < lst.size()) {
            Object[] arr = (Object[]) lst.get(i);
            String username = (String) arr[0];
            String Score = arr[1].toString();
            slots[i].setText(String.format("  %-30s         %30s", username, Score));
            i++;
        }
    }
}
