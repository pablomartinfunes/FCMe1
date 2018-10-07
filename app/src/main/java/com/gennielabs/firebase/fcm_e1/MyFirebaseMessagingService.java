package com.gennielabs.firebase.fcm_e1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "onMessageReceived: FROM " + remoteMessage.getFrom() );

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "onMessageReceived: DATA: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG, "onMessageReceived: Message Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }


    }

    private void sendNotification(String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                                .setContentText(body)
                                                                .setContentTitle("Firebase Cloud Messaging")
                                                                .setAutoCancel(true)
                                                                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }
}
