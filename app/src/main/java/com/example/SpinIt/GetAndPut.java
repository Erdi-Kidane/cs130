package com.example.SpinIt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GetAndPut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_and_put);
    }
    //Copy the following code to your class
/*

    private void Put(Person p){

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        String mcurrentUserID = mAuth.getCurrentUser().getUid();
        mRootRef.child("Users").child(mcurrentUserID).child("Person").setValue(p);

    }

    //Copy the following code to your class then add the your custom implementation  to read the data from firebase
    //
        String currentUserID = mAuth.getCurrentUser().getUid();
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("Person");
        UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("tag","testValue: get");
            if (dataSnapshot.exists())
            {
                newp = dataSnapshot.getValue(Person.class);
                /*
                *
                * implement your own code
                *
                *
                *
                *
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    });

*/


}
