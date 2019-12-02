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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;



public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabAccessorAdapter myTabsAccessorAdapter;
    private FirebaseUser currentuser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private Person currentPerson = null;
    private String currentUserName;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar =(Toolbar)findViewById(R.id.main_page_toolbar);
        myViewPager =(ViewPager)findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdapter = new TabAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);
        myTabLayout =(TabLayout)findViewById(R.id.main_tabs);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("SpinIt");
        myTabLayout.setupWithViewPager(myViewPager);
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();
        String currentUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    currentUserName = dataSnapshot.child("name").getValue().toString();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void RemoveNewGroup(final String groupName)
    {
        RootRef.child("Groups").child(groupName).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, groupName + " is removed Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Addfriend()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Friend's Name :");
        final EditText groupNameField = new EditText(MainActivity.this);
            groupNameField.setHint("e.g BestBuddy");
            builder.setView(groupNameField);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                final String groupName = groupNameField.getText().toString();
                if (TextUtils.isEmpty(groupName))
                {
                    Toast.makeText(MainActivity.this, "Please input correct Name...", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(MainActivity.this, "Name not in the system!...", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { // ToDo: Do something for errors too
                        }
                    });
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
        RootRef.child("Users").child(currentUserID).child("Friendlist").child(m).setValue(memberName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                                Toast.makeText(MainActivity.this, m + " is adding successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void RequestPrivateNewGroup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Room Name :");
        final EditText groupNameField = new EditText(MainActivity.this);
        groupNameField.setHint("e.g UCLA");
        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                final String groupName = groupNameField.getText().toString();
                if (TextUtils.isEmpty(groupName))
                {
                    Toast.makeText(MainActivity.this, "Please write Room Name...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String currentUserID = mAuth.getCurrentUser().getUid();
                    RootRef.child("Groups").child(groupName).child("Host").setValue(currentUserID);
                    RootRef.child("Groups").child(groupName).child("Member").child(currentUserID).setValue(currentUserName);
                    RootRef.child("Groups").child(groupName).child("Message").setValue("")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(MainActivity.this, groupName + " is Created Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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
    private void RequestPublicNewGroup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Room Name :");
        final EditText groupNameField = new EditText(MainActivity.this);
        groupNameField.setHint("e.g UCLA");
        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                final String groupName = groupNameField.getText().toString();

                if (TextUtils.isEmpty(groupName))
                {
                    Toast.makeText(MainActivity.this, "Please write Room Name...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String currentUserID = mAuth.getCurrentUser().getUid();
                    RootRef.child("Groups").child(groupName).child("Host").setValue(currentUserID);
                    RootRef.child("Groups").child(groupName).child("Member").child(currentUserID).setValue(currentUserName);
                    RootRef.child("Groups").child(groupName).child("Message").setValue("");
                    RootRef.child("Groups").child(groupName).child("Public").setValue("1")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(MainActivity.this, groupName + " is Created Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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


    private void RemoveGroup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Room Name :");
        final EditText groupNameField = new EditText(MainActivity.this);
        groupNameField.setHint("e.g UCLA");
        builder.setView(groupNameField);
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String groupName = groupNameField.getText().toString();
                if (TextUtils.isEmpty(groupName))
                {
                    Toast.makeText(MainActivity.this, " not exist in system!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RemoveNewGroup(groupName);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_option)
        {
            mAuth.signOut();
            Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        if (item.getItemId() == R.id.main_create_public_group_option)
        {
            RequestPublicNewGroup();
        }
        if (item.getItemId() == R.id.main_create_private_group_option)
        {
            RequestPrivateNewGroup();
        }
        if (item.getItemId() == R.id.main_remove_group_option)
        {
            RemoveGroup();
        }
        if (item.getItemId() == R.id.option1)
        {
            Intent settingsIntent = new Intent(MainActivity.this, MainPageActivity.class);
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(settingsIntent);
            finish();
        }
        if (item.getItemId() == R.id.add_friend)
        {
            Addfriend();
        }
        return true;
    }
}
