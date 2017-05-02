package com.example.android.executive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ShAwn on 06-04-2017.
 */



public class LocService extends Service {



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    int q;

    public LocService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not Yet Implemented");
    }

    public void showNot() {
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), EmergencyActivity.class), 0);

        if (q != 0) {
            final Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setTicker("New Emergency")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("An emergency has arrived")
                    .setContentText("emergencies yet to arrive: " +q)
                    .setSound(alarmSound)
                    .setContentIntent(pi)
                    .setColor(getResources().getColor(R.color.colorred))
                    .setAutoCancel(true)
                    .build();


            notificationManager.notify(0, notification);

        }
        else
        {
            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setTicker("New Emergency")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("An emergency has arrived")
                    .setContentText("No more emergencies yet to arrive.")
                    .setSound(alarmSound)
                    .setContentIntent(pi)
                    .setColor(getResources().getColor(R.color.colorred))
                    .setAutoCancel(true)
                    .build();



            notificationManager.notify(0, notification);

        }
    }


    public void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), EmergencyActivity.class), 0);

            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setTicker("New Emergency")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("New Emergency!")
                    .setContentText("emergencies yet to arrive: " +q)
                    .setContentIntent(pi)
                    .setColor(getResources().getColor(R.color.colorred))
                    .setSound(alarmSound)
                    .setAutoCancel(true)
                    .build();


            notificationManager.notify(0, notification);

    }


    @Override
    public void onCreate() {
        super.onCreate();

        q=0;
        myRef = database.getReference("Emergencies");

        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                q++;
                showNotification();

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                q--;
                showNot();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }



    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
