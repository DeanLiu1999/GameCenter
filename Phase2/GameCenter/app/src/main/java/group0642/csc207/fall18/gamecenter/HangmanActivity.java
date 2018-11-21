package group0642.csc207.fall18.gamecenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HangmanActivity extends AppCompatActivity {


    private Word answer = new Word("hangman");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        TextView view = findViewById(R.id.wordDisplay);
        TextView healthBar = findViewById(R.id.healthBar);
        view.setText(answer.getDisplay());
        healthBar.setText(String.valueOf(answer.getHealth()));
        enterButtonListener(healthBar, view, answer);
    }

    public void enterButtonListener(final TextView health, final TextView display, final Word word) {
        final Button enter = findViewById(R.id.enterButton);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textEntry = findViewById(R.id.guess);
                word.enter(textEntry.getText().toString());
                health.setText(String.valueOf(word.getHealth()));
                display.setText(word.getDisplay());
                if (word.win()) {
                    makeToastWonText();
                    enter.setEnabled(false);
                } else if (word.getHealth() == 0) {
                    makeToastLostText();
                    enter.setEnabled(false);
                }
            }
        });
    }

    private void makeToastWonText() {
        Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show();
    }

    private void makeToastLostText() {
        Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show();
    }

}
