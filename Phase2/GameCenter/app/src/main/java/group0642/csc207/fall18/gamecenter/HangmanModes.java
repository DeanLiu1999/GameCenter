package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HangmanModes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_modes);
        Intent inherit = getIntent();
        String name = inherit.getStringExtra("name");
        String game = inherit.getStringExtra("game");
        launchNormalMode(name, game);
        launchInfinityMode(name, game);
    }

    private void launchNormalMode(final String name, final String game) {
        Button launchNormal = findViewById(R.id.normalButton);
        launchNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToNormal(name, game);
            }
        });
    }
    private void launchInfinityMode(final String name, final String game) {
        Button launchInfinity = findViewById(R.id.infinityButton);
        launchInfinity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToInfinity(name, game);
            }
        });
    }

    private void switchToNormal(String s, String t) {
        Intent goToNormal = new Intent(this, HangmanBattle.class);
        goToNormal.putExtra("name", s);
        goToNormal.putExtra("game", t);
        goToNormal.putExtra("mode", 2);
        HangmanModes.this.startActivity(goToNormal);
    }
    private void switchToInfinity(String s, String t) {
        Intent goToInfinity = new Intent(this, HangmanActivity.class);
        goToInfinity.putExtra("name", s);
        goToInfinity.putExtra("game", t);
        goToInfinity.putExtra("mode", 1);
        HangmanModes.this.startActivity(goToInfinity);
    }
}
