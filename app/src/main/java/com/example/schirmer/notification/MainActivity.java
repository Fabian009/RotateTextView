package com.example.schirmer.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public class NotificationReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }

        public void NotificationReceiver()
        {

        }
    }


    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";

    private NotificationReceiver mReceiver = new NotificationReceiver();




    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID=0;

    Button btnNotify;
    Button btnCancel;
    Button btnUpdate;

    private void SetNotificationButtonState (Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled)
    {
        btnNotify.setEnabled(isNotifyEnabled);
        btnCancel.setEnabled(isCancelEnabled);
        btnUpdate.setEnabled(isUpdateEnabled);
    }

    /**
     * Hier wird der NotificationChannnel initialisiert und NotificationManager zugewiesen
     */
    public void createNotificationChannel()
    {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder()
    {

        //Möglichkeit eröffnen bei Anklicken des Notification-bars zur betreffenden Activity zurück zukehren
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You have been notified")
                .setContentText("This is your notification text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_android_black_24dp);

        return notifyBuilder;
    }

    private void sendNotification()
    {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                updateIntent, PendingIntent.FLAG_ONE_SHOT);


        
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        notifyBuilder.addAction(R.drawable.ic_action_name, "Update Notification", updatePendingIntent);


        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());



    }



    private void cancelNotification()
    {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    private void updateNotification()
    {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
            .bigPicture(androidImage)
            .setBigContentTitle("Notification Updated"))
            .setContentText("Hello World ");

        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        btnNotify = (Button)findViewById(R.id.btnNotify);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);

        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        SetNotificationButtonState(true, false,false);

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
                SetNotificationButtonState(false, true, true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
                SetNotificationButtonState(true, false, false);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
                SetNotificationButtonState(false, false, true);
            }
        });

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


}
