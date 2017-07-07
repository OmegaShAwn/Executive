package com.example.android.executive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class logList extends AppCompatActivity {

    ArrayList<String> emerlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);

        final Calendar c= Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("UserCategories/AmbulanceDrivers");

        final ArrayAdapter adapter = new ArrayAdapter<>(logList.this, R.layout.listview, R.id.label, emerlist);
        final ListView listView = (ListView) findViewById(R.id.logs);
        listView.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            int no;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);
                final String string = user.getname();
                DatabaseReference myref = database.getReference("Log/Ambulance/"+string);
                myref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                timeEnded tE = dataSnapshot.getValue(timeEnded.class);
                if(c.get(Calendar.YEAR)<=tE.years &&  c.get(Calendar.MONTH)<=tE.months && c.get(Calendar.DAY_OF_MONTH)<=tE.dates){
                    no++;
                    String string1="Not Specified";
                    if(tE.ti==1)
                    {
                        string1="Neural";
                    }
                    if(tE.ti==2)
                    {
                        string1="pregnancy";
                    }
                    if(tE.ti==3)
                    {
                        string1="heart";
                    }
                    if(tE.ti==4)
                    {
                        string1="accident";
                    }
                    if(tE.ti==5)
                    {
                        string1="head injury";
                    }
                    if(tE.ti==6)
                    {
                        string1="other";
                    }
                    emerlist.add(string+" | "+string1+" | "+Integer.toString(tE.dates) + " | " + Integer.toString(tE.months) + " | " + Integer.toString(tE.years) + " || " + Integer.toString(tE.hours) + " : " + Integer.toString(tE.minutes));
                    adapter.notifyDataSetChanged();
                }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), details.class);
                String s = (String)listView.getItemAtPosition(position);
                String username = s.substring(0,s.indexOf(" | "));
                int no=0;
                for(int i = 0; i<=position; i++){
                    if((listView.getItemAtPosition(i)).equals(username)){
                        no++;
                    }
                }
                intent.putExtra("username",username);
                intent.putExtra("no", no+1);
                startActivity(intent);
            }
        });
    }
}
