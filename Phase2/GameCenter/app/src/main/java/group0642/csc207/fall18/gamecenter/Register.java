package group0642.csc207.fall18.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Register extends AppCompatActivity {

    /**
     * @param savedInstanceState given
     * Actions of register happens here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getExternalFilesDir(null);//create the directory for file writing and reading

        registerAction();
        cancelAction();
    }

    /**
     * Registration happens here
     */
    private void registerAction() {
        final EditText newPassword = findViewById(R.id.password);
        final EditText confirm = findViewById(R.id.passwordConfirmation);
        final EditText username = findViewById(R.id.newUser);
        final TextView notification = findViewById(R.id.notification);
        final Button create = findViewById(R.id.createAccount);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = username.getText().toString();
                String pass = newPassword.getText().toString();
                String confirmation = confirm.getText().toString();
                if (pass.equals(confirmation) && newName.length() <= 29) {
                    boolean reading = new UserAccounts().signUp(newName, pass);
                    if (reading) {
                        Intent loginIntent = new Intent(Register.this,
                                LoginActivity.class);
                        Register.this.startActivity(loginIntent);
                        notification.setText(R.string.success);
                    } else if (newName.length() > 29) {

                        notification.setText(R.string.toolong);
                    } else {
                        notification.setText(R.string.occupied);
                    }
                } else {

                    notification.setText(R.string.mismatch);
                }

            }
        });
    }

    /**
     * Cancellation of the registration happens here.
     */
    private void cancelAction() {

        Button cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(Register.this, LoginActivity.class);
                Register.this.startActivity(cancelIntent);
            }
        });
    }
}
