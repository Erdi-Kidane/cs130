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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText UserEmail, UserPassword;
    private EditText userName;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ProgressDialog loadingBar;
    public Person newp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //currentUserID = mAuth.getCurrentUser().getUid();
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateNewAccount();
            }
        });


    }

    private void CreateNewAccount()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        final String setUserName = userName.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(setUserName))
        {
            Toast.makeText(this, "Please write your user name first....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we wre creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                //String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                //String currentUserID = mAuth.getCurrentUser().getUid();
                                //RootRef.child("Users").child(currentUserID).setValue("");
                                UpdateSettings(setUserName);

                                //RootRef.child("Users").child(currentUserID).child("device_token")
                                        //.setValue(deviceToken);


                                loadingBar.dismiss();
                                //mAuth.signOut();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
    private void UpdateSettings(String setUserName)
    {
        //String setUserName = userName.getText().toString();
        String currentUserID = mAuth.getCurrentUser().getUid();

        //HashMap<String, Object> profileMap = new HashMap<>();
        //profileMap.put("uid", currentUserID);
        //profileMap.put("name", setUserName);

        /*⬇ADD A PERSON OJBECT TO FIREBASE WHEN REGISTER ⬇*/
        Person mPerson = new Person(currentUserID);
        RootRef.child("Users").child(currentUserID).child("Person").setValue(mPerson);
        SendUserToMainActivity();

        /*⬆ADD A PERSON OJBECT TO FIREBASE WHEN REGISTER ⬆*/

        //profileMap.put('class',)

        /*
        RootRef.child("Users").child(currentUserID).setValue(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                            SendUserToMainActivity();
                            Toast.makeText(RegisterActivity.this, "Account Created Successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

         */

        /*ADD THE PERSON OBJECT INTO THE FIREBAZE   BELOW  */
        /*
        RootRef.child("Users").child(currentUserID).child("Person").setValue(mPerson)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Log.d("tag", "I am in successful adding a Person ojbect...");

                            SendUserToMainActivity();
                            Toast.makeText(RegisterActivity.this, "Account Created Successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
        /*ADD THE PERSON OBJECT INTO THE FIREBAZE  ABOVE */
    }

    private void InitializeFields() {
        CreateAccountButton = (Button) findViewById(R.id.register_button);
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        userName = (EditText) findViewById(R.id.set_user_name);

        loadingBar = new ProgressDialog(this);
    }


    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(RegisterActivity.this, MainPageActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    // Copy to you guys code

   // private void useValue(Person yourValue) {

     //   Log.d(TAG, "countryNameCode: " + yourValue);

    //}
}
