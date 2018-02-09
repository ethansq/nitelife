package me.ethansq.nitelife.firebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.ethansq.nitelife.activities.ActivityLogin;
import me.ethansq.nitelife.helpers.SimpleCallback;

public class Firebase {
    private static Firebase ourInstance;

    private FirebaseAuth mAuth;
    private Firebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static Firebase getInstance() {
        if (ourInstance == null) {
            ourInstance = new Firebase();
        }

        return ourInstance;
    }

    public void authenticate(
            final String email, final String password, @Nullable final SimpleCallback callback) {

        createUser(email, password, callback);
    }

    private void createUser(
            final String email, final String password, @Nullable final SimpleCallback callback) {

        final String TAG = "Firebase.createUser";
        // First, try to create an account with the email and password
        // If successful, we're authenticated
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            callback.execute(SimpleCallback.SUCCESS);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Snackbar.make(findViewById(android.R.id.content), "Creating user failed.", Snackbar.LENGTH_LONG)
//                                    .show();

                            signInUser(email, password, callback);
                        }
                    }
                });
    }

    private void signInUser(String email, String password, @Nullable final SimpleCallback callback) {
        final String TAG = "Firebase.signInUser";

        mAuth.signInWithEmailAndPassword(
                email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            callback.execute(SimpleCallback.SUCCESS);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            callback.execute(SimpleCallback.FAILURE);
                        }
                    }
                });
    }
}