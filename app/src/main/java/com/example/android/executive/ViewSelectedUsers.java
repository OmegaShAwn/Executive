package com.example.android.executive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewSelectedUsers extends AppCompatActivity {

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    List<User> users = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_users);

        Bundle bundle = getIntent().getExtras();
        final String userinfo=bundle.getString("user");

        mMessageListView = (ListView) findViewById(R.id.messageListView);




        if("ambulance".equals(userinfo)){

            myRef = database.getReference("UserCategories/AmbulanceDrivers");

        }
        else {
            assert userinfo != null;
            if(userinfo.equals("doctor")){
                myRef = database.getReference("UserCategories/Doctors");

            }
            else{
                myRef = database.getReference("UserCategories/Otheruser");
            }
        }


        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    Log.v("emer",""+postSnapshot.getValue(User.class));
                    users.add(user);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, users,userinfo);

        mMessageListView.setAdapter(mMessageAdapter);


    }

}
