package com.example.android.executive;

import android.app.Notification;
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
    int white=0xfdfdfd;


    public LocService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not Yet Implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        q=0;
        myRef = database.getReference("Emergencies");


        showNotif();


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


        return START_STICKY;
    }

    public void showNot() {

        final PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), EmergencyActivity.class), 0);

        if (q != 0) {
            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setTicker("New Emergency")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("An emergency has arrived")
                    .setContentText("emergencies yet to arrive: " +q)
                    .setSound(alarmSound)
                    .setContentIntent(pi)
                    .setColor(white)
                    .setAutoCancel(true)
                    .build();



            startForeground(1337, notification);

        }
        else
        {
            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setTicker("New Emergency")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("An emergency has arrived")
                    .setContentText("No more emergencies yet to arrive.")
                    .setSound(alarmSound)
                    .setContentIntent(pi)
                    .setColor(white)
                    .setAutoCancel(true)
                    .build();




            startForeground(1337, notification);
        }

    }


    public void showNotification() {

        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), EmergencyActivity.class), 0);

            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setTicker("New Emergency")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("New Emergency!")
                    .setContentText("emergencies yet to arrive: " +q)
                    .setContentIntent(pi)
                    .setColor(white)
                    .setSound(alarmSound)
                    .setAutoCancel(true)
                    .build();


        startForeground(1337, notification);
    }


    public void showNotif() {

        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), EmergencyActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setTicker("New Emergency")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Rajagiri Hostpital")
                .setContentText("No emergencies")
                .setContentIntent(pi)
                .setColor(white)
                .setAutoCancel(true)
                .build();


        startForeground(1337, notification);
    }



  /*  @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceTask = new Intent(getApplicationContext(),this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent =PendingIntent.getService(getApplicationContext(), 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartPendingIntent);

        Handler mainHandler = new Handler(getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "I'm a toast!", Toast.LENGTH_SHORT).show();
            }
        });


        super.onTaskRemoved(rootIntent);
    }
*/


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
