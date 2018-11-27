package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static group0642.csc207.fall18.gamecenter.ScoreBoard.getScoreGameUser;


public class PerPersonRecord extends AppCompatActivity {
    private int counter = 0;


    /**
     * @param savedInstanceState Implemented the functionality of the displays
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_person_score);

        Intent intent = getIntent();
        String ID = intent.getStringExtra("userId");
        String gameName = intent.getStringExtra("gameName_1");

        TextView title = findViewById(R.id.title_2);
        title.setText("Your score in " + gameName);

        ArrayList score_list = getScoreGameUser(gameName, ID);

        final ArrayList<ArrayList> pages = divideBy10(score_list);
        final int limit = pages.size();
        final Button next_10 = findViewById(R.id.next_10);
        final Button prev_10 = findViewById(R.id.prev_10);

        // the initial page display
        prev_10.setEnabled(false);
        if (pages.size() == 0) {
            next_10.setEnabled(false);

        } else if (counter == limit - 1) {
            display_score(pages.get(counter));
            next_10.setEnabled(false);
        } else {
            display_score(pages.get(counter));
        }

        // functionality for the next page(10 scores each page)
        next_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                prev_10.setEnabled(true);
                if (counter < limit - 1) {
                    display_score(pages.get(counter));
                } else if (counter == limit - 1) {
                    display_score(pages.get(counter));
                    next_10.setEnabled(false);
                } else {
                    counter = limit - 1;
                    next_10.setEnabled(false);
                }

            }
        });
        // functionality for the previous page button
        prev_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                next_10.setEnabled(true);
                if (counter > 0) {
                    display_score(pages.get(counter));

                } else if (counter == 0) {
                    display_score(pages.get(counter));
                    prev_10.setEnabled(false);
                } else {
                    counter = 0;
                    prev_10.setEnabled(false);
                }
            }
        });

    }


    /**
     * @param lst an ArrayList
     * @return an ArrayList that contains ArrayLists each of size ten.
     */
    private ArrayList<ArrayList> divideBy10(ArrayList lst) {
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
        nest.addAll(divideBy10(lst_2));
        return nest;
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
            String msg = lst.get(i).toString();
            slots[i].setText(String.format("  %s", msg));
            i++;
        }

    }

}
