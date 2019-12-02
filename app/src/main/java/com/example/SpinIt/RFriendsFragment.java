package com.example.SpinIt;



import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class RFriendsFragment extends Fragment {
    private View groupFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String currentUserID,currentGroupName;
    public RFriendsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        groupFragmentView = inflater.inflate(R.layout.fragment_friends3, container, false);
        myRef = FirebaseDatabase.getInstance().getReference();
        currentGroupName = getActivity().getIntent().getExtras().getString("groupName");
        list_view = (ListView) groupFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);
        DisplayGroups();
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                final String cName = adapterView.getItemAtPosition(position).toString();
                DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                mdatabaseReference.orderByChild("name").equalTo(cName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String uid = childSnapshot.getKey();
                                CreateNewGroup(uid,cName);
                                break;
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { // ToDo: Do something for errors too
                    }
                });
            }
        });
        return groupFragmentView;
    }

    private void CreateNewGroup(final String memberName,final String c)
    {
        Log.d("tag","test value3: "+ currentGroupName);
        myRef.child("Groups").child(currentGroupName).child("Member").child(memberName).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(getActivity(), c + " People is removed Successfully...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void DisplayGroups() {
        myRef.child("Groups").child(currentGroupName).child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Set<String> set = new HashSet<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    set.add(ds.getValue().toString());
                }
                list_of_groups.clear();
                list_of_groups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
