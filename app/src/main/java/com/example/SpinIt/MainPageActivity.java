package com.example.SpinIt;

import android.content.Intent;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainPageActivity extends AppCompatActivity {
    private ImageButton mSpinButton, mGroupButton, mPrefenceButton;
    private Person currentPerson;
    private Spin currentSpin;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    private DatabaseReference mRootRef;
    /*****************for testing************************/

    /***************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mSpinButton = (ImageButton) findViewById(R.id.spinButton);
        mGroupButton = (ImageButton) findViewById(R.id.groupButton);
        mPrefenceButton = (ImageButton) findViewById(R.id.pbutton);
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();


        /*****************for testing************************/
        mRootRef = FirebaseDatabase.getInstance().getReference();
        /***************************************************/

        Log.d("tag", "before get() in MainPageActivity........");

        getPerson();
        getSpin();
        mSpinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(currentSpin.getListOfPlaces() != null && !currentSpin.getListOfPlaces().isEmpty()) {
                    ArrayList<Place> tempPl = currentSpin.getRandomPlaces();
                    Intent registerIntent = new Intent(MainPageActivity.this, Spinner.class);
                    registerIntent.putExtra("groupName", "thisisaforbiddenchatnamedontuseitplease");
                    registerIntent.putExtra("Person", currentPerson);
                    registerIntent.putExtra("Spin", currentSpin);
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(registerIntent);
                }
                else{
                    //insert toast
                    String valOfSelection = "Please set your preferences and set your location";
                    Toast toast = Toast.makeText(MainPageActivity.this, " " + valOfSelection,2);
                    //toast.setGravity(49, 0, 50);
                    toast.show();


                    Log.d("tag", "The location + food preferences need to be set in preference");
                }
            }
        });
        mPrefenceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                Intent registerIntent = new Intent(MainPageActivity.this, Profile.class);
                registerIntent.putExtra("Person", currentPerson);
                registerIntent.putExtra("Spin", currentSpin);
                startActivity(registerIntent);
            }
        });
        mGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(currentSpin.getListOfPlaces() != null && !currentSpin.getListOfPlaces().isEmpty()) {
                    Intent registerIntent = new Intent(MainPageActivity.this, MainActivity.class);
                    registerIntent.putExtra("Person", currentPerson);
                    registerIntent.putExtra("Spin", currentSpin);
                    startActivity(registerIntent);
                }
                else
                {
                    String valOfSelection = "Please set your preferences and set your location";
                    Toast toast = Toast.makeText(MainPageActivity.this, " " + valOfSelection,2);
                    //toast.setGravity(49, 0, 50);
                    toast.show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        //GetUserInfo();

        if (currentuser == null){
            Intent loginIntent = new Intent(MainPageActivity.this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        else{
            VerifyUserExistance();
            //GetUserInfo();
        }
    }

    private void VerifyUserExistance() {
        String currentUserID = mAuth.getCurrentUser().getUid();
        mRootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.child("name").exists()))
                {
                    Toast.makeText(MainPageActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //SendUserToSettingsActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

///*****************for testing***************************************************************/
   private void getPerson(){
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       final String currentUID = user.getUid();
       Log.d("tag", "Now in Get(), after current ID: " + currentUID );

       DatabaseReference mPersonRef;
       mPersonRef = mRootRef.child("Users").child(currentUID).child("Person");
       ValueEventListener personListener = new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               if (dataSnapshot.getValue() != null){
                   currentPerson = new Person(dataSnapshot);

                   Log.d("tag", "In get(), Person SUCCESSFULLY GET: " + currentPerson.getCurrentUID());
               }
               Log.d("tag", "In get(), after get the arrList ");
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Log.w("tag", "loadPost:onCancelled", databaseError.toException());

           }
       };
       mPersonRef.addValueEventListener(personListener);
   }

   private void getSpin(){
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       final String currentUID = user.getUid();
       Log.d("tag", "Now in Get(), after current ID: " + currentUID );

       DatabaseReference mPersonRef;
       mPersonRef = mRootRef.child("Users").child(currentUID).child("Spin");
       ValueEventListener personListener = new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               if (dataSnapshot.getValue() != null){
                   currentSpin = new Spin(dataSnapshot);

               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Log.w("tag", "loadPost:onCancelled", databaseError.toException());

           }
       };
       mPersonRef.addValueEventListener(personListener);
   }
    /*****************for testing****************************************************************/
}
