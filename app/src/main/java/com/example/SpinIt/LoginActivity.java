package com.example.SpinIt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    //private FirebaseUser currentuser;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private Button LoginButton;
    private EditText UserEmail, UserPassword;
    private Button NeedNewAccountLink;
    private DatabaseReference UsersRef;

    // "ButtonGoogleSignIn" in layout
    //for google signin
    /**************************************************************/
    private SignInButton mButtonGoogle;
    private Button       mButtonGoogleSignOut;
    //private GoogleSignInClient mGoogleUser;
    private GoogleSignInClient mGoogleSignInClient;
    /**************************************************************/

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("tag", "In onCreate().......");

        mAuth = FirebaseAuth.getInstance();
        //currentuser =mAuth.getCurrentUser();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        InitializeFields();

        /**************************************************************/
        mButtonGoogle  = (SignInButton) findViewById(R.id.ButtonGoogleSignIn);
        mButtonGoogleSignOut = findViewById(R.id.ButtonGoogleSignOut);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d("tag", "In setOnClickListener(), before call googleSignIn.......");
                googleSignIn();
            }
        });

        mButtonGoogleSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d("tag", "In click Signout()...");
                googleSignOut();
            }
        });

        //

        /**************************************************************/


        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendUserToRegisterActivity();
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AllowUserToLogin();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            Log.d("tag", "In onActivityResult(), see the task..." + data);
            Log.d("tag", "In onActivityResult(), see the task..." + requestCode);
            Log.d("tag", "In onActivityResult(), see the task..." + resultCode);

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            Log.d("tag", "In onActivityResult(), see the task..." + task);

            try {
                Log.d("tag", "In onActivityResult()..........");

                GoogleSignInAccount account = task.getResult(ApiException.class);



                Log.d("tag", "In onActivityResult(), the account" + account);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                Log.w("tag", "Google sign in failed", e);
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("tag", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        SendUserToMainActivity();
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("tag", "signInWithCredential:success");
                        //FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                    } else {
                        //failed message
                        Log.w("tag", "signInWithCredential:failure", task.getException());
                    }
                }
            });
    }

    private void googleSignIn(){
        Log.d("tag", "In googleSignIn().......");

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void googleSignOut(){

        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("tag", "In googleSignOut()...");
                    }
                });
    }

    private void AllowUserToLogin()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                /*String currentUserId = mAuth.getCurrentUser().getUid();
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                UsersRef.child(currentUserId).child("device_token")
                                        .setValue(deviceToken)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {*/
                                                //if (task.isSuccessful())
                                               //{
                                                    SendUserToMainActivity();
                                                    Toast.makeText(LoginActivity.this, "Logged in Successful...", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                //}
                                           // }
                                        //});
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void InitializeFields() {
        LoginButton = (Button) findViewById(R.id.login_button);

        UserEmail = (EditText) findViewById(R.id.login_email);
        UserPassword = (EditText) findViewById(R.id.login_password);
        NeedNewAccountLink = (Button) findViewById(R.id.need_new_account_link);

        loadingBar = new ProgressDialog(this);
    }

    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(LoginActivity.this, MainPageActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToRegisterActivity()
    {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}
