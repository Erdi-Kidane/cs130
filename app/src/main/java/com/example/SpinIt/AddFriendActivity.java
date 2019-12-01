package com.example.SpinIt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;




public class AddFriendActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private NewAccessorAdapter myTabsAccessorAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private String currentGroupName;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend2);
        mToolbar =(Toolbar)findViewById(R.id.main_page_toolbar);
        myViewPager =(ViewPager)findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdapter = new NewAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);
        myTabLayout =(TabLayout)findViewById(R.id.main_tabs);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Friends");
        myTabLayout.setupWithViewPager(myViewPager);
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        currentGroupName = getIntent().getExtras().get("groupName").toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_people, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_option)
        {
            //updateUserStatus("offline");
            mAuth.signOut();
            SendUserToLoginActivity();
        }
        if (item.getItemId() == R.id.back_to_room_option)
        {

            Intent loginIntent = new Intent(AddFriendActivity.this,GroupChatActivity.class);
            loginIntent.putExtra("groupName" , currentGroupName);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        if (item.getItemId() == R.id.add)
        {

            Addfriend();
        }


        return true;
    }

    private void SendUserToLoginActivity(){
        Intent loginIntent = new Intent(AddFriendActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void Addfriend()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFriendActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Friend's Name :");

        final EditText groupNameField = new EditText(AddFriendActivity.this);

        groupNameField.setHint("e.g BestBuddy");
        builder.setView(groupNameField);


        builder.setPositiveButton("Invite", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                final String groupName = groupNameField.getText().toString();
                if (TextUtils.isEmpty(groupName))
                {
                    Toast.makeText(AddFriendActivity.this, "Please write Friend Name...", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                    mdatabaseReference.orderByChild("name").equalTo(groupName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    String uid = childSnapshot.getKey();
                                    CreateFriend(uid,groupName);
                                    break;
                                }
                            }
                            else{
                                Toast.makeText(AddFriendActivity.this, "Please Type the correct member name...", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { // ToDo: Do something for errors too
                        }
                    });
                    //CreateNewGroup(groupName);

                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void CreateFriend(final String memberName,final String m)
    {
        String currentUserID = mAuth.getCurrentUser().getUid();
        //UsersRef.child("Groups").child(groupName).child("Host").setValue(currentUserID);
        RootRef.child("Users").child(currentUserID).child("Friendlist").child(m).setValue(memberName)
                //UsersRef.child("Groups").child(groupName).child("Message").setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AddFriendActivity.this, m + " is adding successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
