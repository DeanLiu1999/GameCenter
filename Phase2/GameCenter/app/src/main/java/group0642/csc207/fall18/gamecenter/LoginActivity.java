package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static group0642.csc207.fall18.gamecenter.UserAccounts.signIn;

public class LoginActivity extends AppCompatActivity {


    /**
     * @param savedInstanceState given
     * Actions in the login activity happens here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getExternalFilesDir(null);//create the directory for file writing and reading

        loginClickListener();
        registerClickListener();
        resetClickListener();
    }


    /**
     * The actions of login button is implemented here
     */
    private void loginClickListener() {
        Button login = findViewById(R.id.login);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final TextView notify = findViewById(R.id.notification);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                boolean match = signIn(name, pass);
                if (match) {
                    Intent loginIntent = new Intent(LoginActivity.this, Preface.class);
                    loginIntent.putExtra("name", name);
                    LoginActivity.this.startActivity(loginIntent);
                    notify.setText(R.string.welcome_message);
                } else {
                    notify.setText(R.string.wrong_note);
                }
            }
        });
    }

    /**
     * The actions of reset button is implemented here(in case the user forgets his/her password)
     */
    private void resetClickListener() {
        Button reset = findViewById(R.id.passwordReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetIntent = new Intent(LoginActivity.this,
                        PasswordReset.class);
                LoginActivity.this.startActivity(resetIntent);
            }
        });
    }

    /**
     * The actions of register button is implemented here for new users
     */
    private void registerClickListener() {
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, Register.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }


}