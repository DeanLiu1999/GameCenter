package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PasswordReset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        getExternalFilesDir(null);//create the directory for file writing and reading

        resetListener();
        cancelListener();

    }

    /**
     * The reset action happens here
     */
    private void resetListener() {
        final EditText newPassword = findViewById(R.id.newPassword);
        final EditText matching = findViewById(R.id.passwordConfirmation);
        final EditText userId = findViewById(R.id.username);
        final TextView note = findViewById(R.id.notification);
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw = newPassword.getText().toString();
                String pw1 = matching.getText().toString();
                String nm = userId.getText().toString();
                if (pw.equals(pw1)) {
                    boolean running = new UserAccounts().resetPassword(nm, pw);
                    if (running) {
                        Intent loginIntent = new Intent(PasswordReset.this
                                , LoginActivity.class);
                        PasswordReset.this.startActivity(loginIntent);
                        note.setText(R.string.success_msg);
                        // reset successful
                    } else {
                        note.setText(R.string.userdne);
                        // if we can't find the user in our file
                    }
                } else {
                    note.setText(R.string.mismatch_msg);
                    // if the confirmation does not match the original password
                }
            }
        });
    }

    /**
     * The cancellation to password reset
     */
    private void cancelListener() {
        Button cancel = findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(PasswordReset.this,
                        LoginActivity.class);
                PasswordReset.this.startActivity(loginIntent);
            }
        });
    }
}
