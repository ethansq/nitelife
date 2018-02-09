package me.ethansq.nitelife.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.ethansq.nitelife.R;
import me.ethansq.nitelife.helpers.Utils;

public class ActivityLogin extends AppCompatActivity {
    private final String TAG = "ActivityLogin";

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private View mProgressBarWrapper;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBarWrapper = findViewById(R.id.progressBarWrapper);
        mEmailEditText = findViewById(R.id.emailInput);
        mPasswordEditText = findViewById(R.id.passwordInput);
        mAuth = FirebaseAuth.getInstance();
    }

    public void doAuthentication(View v) {
        Utils.crossfade(mProgressBarWrapper, null);

        mAuth.createUserWithEmailAndPassword(
                mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString())
                .addOnCompleteListener(ActivityLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Snackbar.make(findViewById(android.R.id.content), "Creating user failed.", Snackbar.LENGTH_LONG)
//                                    .show();

                            signIn();
                        }
                    }
                });
    }

    private void signIn() {
        mAuth.signInWithEmailAndPassword(
                mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Snackbar.make(findViewById(android.R.id.content), "Authentication failed.", Snackbar.LENGTH_LONG)
//                                    .show();

                            Utils.crossfade(null, mProgressBarWrapper);
                        }
                    }
                });
    }

    private void updateUI() {
        Utils.crossfade(null, mProgressBarWrapper);
        Intent in = new Intent(this, ActivityMain.class);
        startActivity(in);
    }
}
