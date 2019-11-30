package com.example.SpinIt;

import android.content.Intent;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainPageActivity extends AppCompatActivity {
    private Button mSpinButton, mGroupButton, mPrefenceButton;
    private Person currentPerson;
    /*****************for testing************************/
    private DatabaseReference mRootRef;
    /***************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mSpinButton = (Button) findViewById(R.id.spinButton);
        mGroupButton = (Button) findViewById(R.id.groupButton);
        mPrefenceButton = (Button) findViewById(R.id.pbutton);

        /*****************for testing************************/
        mRootRef = FirebaseDatabase.getInstance().getReference();
        /***************************************************/

        Log.d("tag", "before get() in MainPageActivity........");

        get();
        mSpinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(MainPageActivity.this, Spinner.class);
                registerIntent.putExtra("Person", currentPerson);
                startActivity(registerIntent);
            }
        });
        mPrefenceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(MainPageActivity.this, Profile.class);
                registerIntent.putExtra("Person", currentPerson);
                startActivity(registerIntent);
            }
        });
        mGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(MainPageActivity.this, MainActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    /*****************for testing***************************************************************/
    private void get(){
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
    /*****************for testing****************************************************************/
}
