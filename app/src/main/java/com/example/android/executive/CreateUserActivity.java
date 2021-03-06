package com.example.android.executive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity {

    boolean flag = true;
    ArrayList<User> users = new ArrayList<>();

    EditText name ;
    EditText phoneno ;
    EditText username ;
    EditText password ;
    EditText specialization;

    User newuser = new User();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        name = (EditText) findViewById(R.id.name);
        phoneno = (EditText) findViewById(R.id.phoneno);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        specialization = (EditText)findViewById(R.id.special);



        Bundle bundle = getIntent().getExtras();
        final String userinfo=bundle.getString("user");

        if(userinfo.equals("ambulance")){
            specialization.setVisibility(View.GONE);
             myRef = database.getReference("UserCategories/AmbulanceDrivers");


        }
        else if(userinfo.equals("doctor")){
             myRef = database.getReference("UserCategories/Doctors");

        }
        else{
            myRef = database.getReference("UserCategories/Otheruser");
        }


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button create = (Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newuser.setPassword(password.getText().toString());
                newuser.setPhno(Long.parseLong(phoneno.getText().toString()));
                newuser.setUsername(username.getText().toString());
                newuser.setname(name.getText().toString());
                flag=true;

                if(userinfo.equals("doctor")||userinfo.equals("otheruser"))
                    newuser.setSpeciality(specialization.getText().toString());
                Log.v("Button",""+users.size());
                //Log.v("Button",""+users.get(0).getUsername());
                //Log.v("Button",""+users.get(1).getUsername());


                for(int i=0;i<users.size();i++){
                    if ((users.get(i).getUsername()).equals(newuser.getUsername())){
                        flag=false;
                        Toast.makeText(CreateUserActivity.this,"Username already exists",Toast.LENGTH_LONG).show();
                        break;
                    }
                }

                if(users.size()==0){
                    flag=true;
                    //Toast.makeText(CreateUserActivity.this,"Username already exists",Toast.LENGTH_LONG).show();

                }

                if(flag==true){
                    myRef.child(newuser.getUsername()).setValue(newuser);
                    Toast.makeText(CreateUserActivity.this,"Account Created",Toast.LENGTH_LONG).show();

                }
            }
        });





    }
}
