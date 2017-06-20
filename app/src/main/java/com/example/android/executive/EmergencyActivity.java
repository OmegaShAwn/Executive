package com.example.android.executive;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import static com.example.android.executive.R.id.emergencyMessageListView;


public class EmergencyActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private ListView mMessageListView;
    private EmergenciesAdapter mEmergenciesAdapter;
    int flagdup=0;
    DatabaseReference ref = database.getReference("UserCategories/Doctors");

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

        if(!hasLoggedIn)
        {
            Intent intent=new Intent(EmergencyActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(!isNetworkAvailable())
            Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        username=settings.getString("lusername","");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(username)){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                    SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = settings.edit();

                    editor.putBoolean("hasLoggedIn",false);
                    editor.putString("lusername","");
                    editor.commit();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(!u.equals("")) {
            Toast.makeText(EmergencyActivity.this, username, Toast.LENGTH_SHORT).show();
        }


//        Intent s= new Intent(EmergencyActivity.this, LocService.class);
//        startService(s);

        if(hasLoggedIn) {
            Intent s = new Intent(EmergencyActivity.this, LocService.class);
            stopService(s);
            startService(new Intent(this, LocService.class));
        }

        setContentView(R.layout.activity_emergency);

        final Button B = (Button) findViewById(R.id.button3);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(B.getText().equals("STOP NOTIFICATION")){
                    Intent s = new Intent(EmergencyActivity.this, LocService.class);
                    stopService(s);
                    B.setText("START NOTIFICATION");
                }
                else{
                    Intent s = new Intent(EmergencyActivity.this, LocService.class);
                    startService(s);
                    B.setText("STOP NOTIFICATION");
                }
            }});

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

                        if(flagdup==0 && user.emergencyDetails!=null) {
                            int h = Integer.valueOf(user.emergencyDetails.getSi());
                            if (h == 3) {
                                emergencies.add(user);
                            }
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


                    if(flagdup==0 && user.emergencyDetails!=null) {
                        int h = Integer.valueOf(user.emergencyDetails.getSi());
                        if (h == 2) {
                            emergencies.add(user);
                        }
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

                    if(flagdup==0 && user.emergencyDetails!=null) {

                        int h = Integer.valueOf(user.emergencyDetails.getSi());
                        if (h == 1) {
                            emergencies.add(user);
                        }
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

                    if(flagdup==0 && user.emergencyDetails!=null) {

                        int h = Integer.valueOf(user.emergencyDetails.getSi());

                        if (h == 0) {
                            emergencies.add(user);
                        }
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
                finish();

//                mEmergenciesAdapter.remove(mEmergenciesAdapter);

            }
        });
    }
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
                        SharedPreferences.Editor editor = settings.edit();

                        editor.putBoolean("hasLoggedIn", false);
                        editor.putString("lusername","");

                        editor.commit();
                        startActivity(i);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.show();

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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
