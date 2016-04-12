package ca.bcit.comp3717.test2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

/**
 * Created by Pika on 2016-04-06.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Genrate random notification ID (won't keep track of notifications)

        String message = intent.getStringExtra("message");
        String title = intent.getStringExtra("title");
        String priority = intent.getStringExtra("priority");
        int id = intent.getIntExtra("id", -1);

        Intent resultIntent = new Intent(context, MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        switch(priority){
            case "0":
                mBuilder.setSmallIcon(R.drawable.test);
                break;
            case "1":
                mBuilder.setSmallIcon(R.drawable.med);
                break;
            case "2":
                mBuilder.setSmallIcon(R.drawable.high);
                break;
            default:
                mBuilder.setSmallIcon(R.drawable.test);
        }

        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        mBuilder.setAutoCancel(true);
        mBuilder.setTicker("You have task to complete!");
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, mBuilder.build());
    }
}
