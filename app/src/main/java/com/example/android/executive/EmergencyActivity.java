package com.example.android.executive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.android.executive.R.id.emergencyMessageListView;


public class EmergencyActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private ListView mMessageListView;
    private EmergenciesAdapter mEmergenciesAdapter;
    int flagdup=0;


    List<Emergencies> emergencies = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        String u=settings.getString("lusername","");
        final String username;

        if(hasLoggedIn)
        {
        }
        else{
            Intent intent=new Intent(EmergencyActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        username=settings.getString("lusername","nil");

        if(!u.equals("")) {
            Toast.makeText(EmergencyActivity.this, username, Toast.LENGTH_LONG).show();
        }


//        Intent s= new Intent(EmergencyActivity.this, LocService.class);
//        startService(s);

        startService(new Intent(this, LocService.class));




        setContentView(R.layout.activity_emergency);

        mMessageListView = (ListView) findViewById(emergencyMessageListView);



        myRef = database.getReference("Emergencies");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                emergencies.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Emergencies user = postSnapshot.getValue(Emergencies.class);
                    Log.v("emer", "" + postSnapshot.getValue(Emergencies.class));
                    flagdup = 0;
                    for (int i = 0; i < emergencies.size(); i++)
                        if (emergencies.get(i).emergencyDetails.getUsername().equals(user.emergencyDetails.getUsername()))
                        {
                            flagdup = 1;
                            break;
                        }

                        int h=Integer.valueOf(user.emergencyDetails.getSi());
                        if(flagdup==0)
                            if(h==3) {
                                emergencies.add(user);
                            }

                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Emergencies user = postSnapshot.getValue(Emergencies.class);
                    Log.v("emer", "" + postSnapshot.getValue(Emergencies.class));
                    flagdup = 0;
                    for (int i = 0; i < emergencies.size(); i++)
                        if (emergencies.get(i).emergencyDetails.getUsername().equals(user.emergencyDetails.getUsername()))
                        {
                            flagdup = 1;
                            break;
                        }

                    int h=Integer.valueOf(user.emergencyDetails.getSi());
                    if(flagdup==0)
                        if(h==2) {
                            emergencies.add(user);
                        }

                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Emergencies user = postSnapshot.getValue(Emergencies.class);
                    Log.v("emer", "" + postSnapshot.getValue(Emergencies.class));
                    flagdup = 0;
                    for (int i = 0; i < emergencies.size(); i++)
                        if (emergencies.get(i).emergencyDetails.getUsername().equals(user.emergencyDetails.getUsername()))
                        {
                            flagdup = 1;
                            break;
                        }

                    int h=Integer.valueOf(user.emergencyDetails.getSi());
                    if(flagdup==0)
                        if(h==1) {
                            emergencies.add(user);
                        }

                }
                setAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            int i;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                i++;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                i--;
                if(i==0) {
                    emergencies.clear();
                    mEmergenciesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public void setAdapter(){


        mEmergenciesAdapter = new EmergenciesAdapter(this, R.layout.item_emergency_message, emergencies);

        mMessageListView.setAdapter(mEmergenciesAdapter);

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EmergencyActivity.this,Locate.class);
                TextView username =(TextView)view.findViewById(R.id.EmergenciesUsername);
                intent.putExtra("user",username.getText().toString());
                startActivity(intent);

//                mEmergenciesAdapter.remove(mEmergenciesAdapter);

            }
        });
    }
    int k=0;

    @Override
    public void onBackPressed()
    {
        if(k==0) {
            Toast.makeText(getApplicationContext(), "Press Back again to log out", Toast.LENGTH_LONG).show();
            k++;
        }
        else{
            Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            Intent f= new Intent(this, LocService.class);
            stopService(f);
            SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
            SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
            editor.putBoolean("hasLoggedIn", false);
            editor.putString("lusername","");

// Commit the edits!
            editor.commit();
            // code here to show dialog
            super.onBackPressed();  // optional depending on your needs
        }new Timer().schedule(new TimerTask() {
        @Override
        public void run() {
            k=0;
        }
    }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Intent i= new Intent(this, LocService.class);
//        stopService(i);
    }

    @Override
    public void onPause() {
        super.onPause();
//        Intent i= new Intent(this, LocService.class);
//        startService(i);
    }

    @Override
    public void onDestroy() {
        /*Intent i= new Intent(this, LocService.class);
        stopService(i);*/
        super.onDestroy();
    }
}
