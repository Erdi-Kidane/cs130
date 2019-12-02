package com.example.SpinIt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton MessageButton;
    private Button SpinButton;
    private EditText uMinput;
    private ScrollView mScrollView;
    private TextView TextMessage;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, GnRf, GmRF, myRef,newGnRf;

    private String currentGroupName, currentUserID, currentUserName, currentDate, currentTime;
    private Place winningPlace = null;
    private Person currentPerson;
    private Spin currentSpin;

    private boolean winnerFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        SpinButton = (Button) findViewById(R.id.littleSpin);
        currentGroupName = getIntent().getExtras().get("groupName").toString();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        myRef = FirebaseDatabase.getInstance().getReference();

        GnRf = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName).child("Message");
        newGnRf = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName).child("Member");
        Intent getPerson = getIntent();
        currentPerson = getPerson.getParcelableExtra("Person");
        mToolbar = (Toolbar) findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(currentGroupName);
        MessageButton = (ImageButton) findViewById(R.id.send_message_button);
        uMinput = (EditText) findViewById(R.id.input_group_message);
        TextMessage = (TextView) findViewById(R.id.group_chat_text_display);
        mScrollView = (ScrollView) findViewById(R.id.my_scroll_view);
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
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
        winningPlace = getIntent().getParcelableExtra("winningPlace");

        getPerson();
        getSpin();

        InitializeFields();
        GetUserInfo();
        newGnRf.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild(currentUserID))
                {

                }
                else{
                    Intent loginIntent = new Intent(GroupChatActivity.this,MainActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Indatabase();
                uMinput.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        SpinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(GroupChatActivity.this, Spinner.class);
                registerIntent.putExtra("groupName" , currentGroupName);
                registerIntent.putExtra("Person", currentPerson);
                registerIntent.putExtra("Spin", currentSpin);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(registerIntent);
            }
        });
        if(winningPlace != null){
            //populate
            userMessageInput.setText("This place was spun:\n" + winningPlace.getName() + ".\n" + "Here is the address:\n" + winningPlace.getAddress() + ".\n" + "Here is the URL: " + "\n" + winningPlace.getURL());
            winnerFlag = true;
            SaveMessageInfoToDatabase();
            userMessageInput.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GnRf.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                if (dataSnapshot.exists())
                {
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                if (dataSnapshot.exists())
                {
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void Indatabase()
    {
        String message = uMinput.getText().toString();
        String messagekEY = GnRf.push().getKey();

        if (TextUtils.isEmpty(message))
        {
            Toast.makeText(this, "Text Can not be Empty!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> groupMessageKey = new HashMap<>();

            GnRf.updateChildren(groupMessageKey);
            GmRF = GnRf.child(messagekEY);

           

        

            if(winnerFlag){
                currentUserName = "Spinner";
                winnerFlag = false;
            }

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("message", message);
            GmRF.updateChildren(messageInfoMap);
        }
    }
    private void DisplayMessages(DataSnapshot dataSnapshot)
    {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext())
        {
            String chatMessage = (String) ((DataSnapshot)iterator.next()).getValue();
            String chatName = (String) ((DataSnapshot)iterator.next()).getValue();
            TextMessage.append(chatName + ":\n" + chatMessage + "\n\n\n");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_option)
        {
            mAuth.signOut();
            Intent loginIntent = new Intent(GroupChatActivity.this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        if (item.getItemId() == R.id.main_invite_option)
        {

            Intent loginIntent = new Intent(GroupChatActivity.this,AddFriendActivity.class);
            loginIntent.putExtra("groupName" , currentGroupName);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        if (item.getItemId() == R.id.back_option)
        {
            Intent loginIntent = new Intent(GroupChatActivity.this,MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        if (item.getItemId() == R.id.make_public)
        {
            MakePublic();
        }
        if (item.getItemId() == R.id.quit)
        {
            DeleteUser();
            Intent loginIntent = new Intent(GroupChatActivity.this,MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
        return true;
    }
    private void DeleteUser(){
        String currentUserID = mAuth.getCurrentUser().getUid();
        myRef.child("Groups").child(currentGroupName).child("Member").child(currentUserID).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(GroupChatActivity.this, "You are removed from group Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void MakePublic()
    {
        myRef.child("Groups").child(currentGroupName).child("Public").setValue("1")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(GroupChatActivity.this, " group is Opening to public Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

                                  

    /*****************for testing***************************************************************/
    private void getPerson(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUID = user.getUid();
        Log.d("tag", "Now in Get(), after current ID: " + currentUID );

        DatabaseReference mPersonRef;
        mPersonRef = myRef.child("Users").child(currentUID).child("Person");
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
        mPersonRef = myRef.child("Users").child(currentUID).child("Spin");
        ValueEventListener personListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null){
                    currentSpin = new Spin(dataSnapshot);
                    Log.d("tag", "in ondatachange " + currentSpin.getPerson().getCurrentUID());

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
