package com.example.spinit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser; FOR USER, NEXT STEP
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button   mButtonSignIn;
    private Button   mButtonSignUp;
    private TextView mErrorMessage;
    private Button   mButtonSignOut;

    private FirebaseAuth mAuth;

    private SignInButton       mButtonGoogle;
    private GoogleSignInClient mGoogleUser;

    final private int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailField    = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mButtonSignUp  = (Button)   findViewById(R.id.ButtonSignUp);
        mButtonSignIn  = (Button)   findViewById(R.id.ButtonErdi);
        mErrorMessage  = (TextView) findViewById(R.id.TextViewSignInError);
        mButtonGoogle  = (SignInButton) findViewById(R.id.ButtonGoogleSignIn);
        mButtonSignOut = (Button)   findViewById(R.id.ButtonSignOut);

        GoogleSignInOptions gid = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleUser = GoogleSignIn.getClient(this, gid);
        mAuth       = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mButtonSignIn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                loginToSystem(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        } );

        mButtonSignUp.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        } );

        mButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signWithGoogleAccount();
                goToDashBoard();
            }
        } );

        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signOutWithGoogleAccount();
                setContentView(R.layout.activity_main);
            }
        } );

        Log.d("tag", "I AM ONSTART...");
    }

    public boolean isValidForm() {
        boolean flag = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            flag = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            flag = false;
        } else {
            mPasswordField.setError(null);
        }

        return flag;
    }

    // 1. if the user is already log-in  - NEXT STEP.
    // 2. if valid input - from libary
    public void loginToSystem(String email, String password) {
        Log.d("tag", "in m in login now");

        if (!isValidForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Log.d("tag", "signInWithEmail:success");
                goToDashBoard();

            } else {
                Log.w("tag", "signInWithEmail:failure", task.getException());
                Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
            }

            if (!task.isSuccessful()) {
                mErrorMessage.setText(R.string.sign_error_message);
            }
        }
        });
    }

    // 1. need to see if account already exist - NEXT STEP: CURRENT USER.
    public void createAccount(String email, String password) {
        Log.d("tag", "i m in createAccount start");

        if ( !isValidForm() ) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("tag", "createUserWithEmail:success");
                        goToDashBoard();

                    } else {

                        Log.w("tag", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                    if (!task.isSuccessful()) {
                        mErrorMessage.setText(R.string.sign_error_message);
                    }
                }
            });
    }

    public void goToDashBoard(){
        Intent goToDash = new Intent(MainActivity.this, DashBoard.class);
        startActivity(goToDash);
    }

    //put the google account to firebase
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("tag", "i m in firebase Auth With Google");

        //has to get the credential, then signin by credential
        AuthCredential gid_cred = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(gid_cred)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d("tag", "i m signing In WithCredential: success");
                } else {
                    Log.w("tag", "i m signing In WithCredential:failure");
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

                if (!task.isSuccessful()) {
                    mErrorMessage.setText(R.string.sign_error_message);
                }
                }
            });
    }

    private void signWithGoogleAccount() {
        Intent signInIntent = mGoogleUser.getSignInIntent();
        startActivityForResult(signInIntent,5000);
    }

    @Override
    public void onActivityResult(int num_ask, int resultCode, Intent data) {
        super.onActivityResult(num_ask, resultCode, data);
        if (num_ask == 5000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Log.w("tag", "tell brian to fix you...", e); //debug
            }
        }
    }

    /*
    public void signOutWithGoogleAccount(){
        //do sth
        mAuth.signOut();

        Log.d("tag", "come on...............sign out!");
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag", "I AM IN RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag", "I AM IN PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag", "I AM IN ONSTOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "I AM IN DESTORY");
    }

}
