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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

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

        Log.d("tag", "email address: " + email);

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
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserID);
            profileMap.put("name", setUserName);

            //profileMap.put('class',)
            RootRef.child("Users").child(currentUserID).setValue(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                String title = "foo";
                                String startTime = "bar";
                                String endTime = "1";
                                String day = "2";
                                Person p = new Person(title,startTime,endTime,day);
                                Put(p);
                                //Person p2 = new Person(title,startTime,endTime,"3");
                                //Put(p);

                                Log.d("tag", "before get() .................... ");
                                Log.d("tag", "make sure what is p " + p);
                                //Get();

                                Log.d("tag", "After get() .................");
                                Person p2 = new Person("a","b","c","d");
                                //p2.title = "0";
                                //p2.startTime = "0";
                                //p2.endTime = "9";
                                //p2.day = "0";

                                Put(p2);

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
    private void Put(Person p){
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        String mcurrentUserID = mAuth.getCurrentUser().getUid();
        mRootRef.child("Users").child(mcurrentUserID).child("Person").setValue(p);
    }
    private void Get(){

        String rcurrentUserID = mAuth.getCurrentUser().getUid();
        DatabaseReference mReadreference = FirebaseDatabase.getInstance().getReference().child("Users").child(rcurrentUserID).child("Person");

        mReadreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newp = dataSnapshot.getValue(Person.class);
                Log.d("tag", "i m in Get(), the newp value is: " + newp);
                //useValue(newp);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
   // private void useValue(Person yourValue) {

     //   Log.d(TAG, "countryNameCode: " + yourValue);

    //}
}
