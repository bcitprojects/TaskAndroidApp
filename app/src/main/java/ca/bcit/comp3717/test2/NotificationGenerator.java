package ca.bcit.comp3717.test2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Pika on 2016-04-11.
 */
public class NotificationGenerator {

    public static void generateTimedNotification(Context context, String title, String message, int seconds) {

        int inSeconds = seconds * 1000;

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("message", message);
        intent.putExtra("title", title);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + inSeconds, alarmIntent);
        Toast.makeText(context, "Notification will be sent in " + seconds + " seconds.", Toast.LENGTH_LONG).show();
    }

    public static void generateWithList(Context context, ArrayList<Task> tasks, int seconds) {

        int inSeconds = seconds * 1000;
        int notificationID = 1;

        if(tasks != null) {
            for(Task a: tasks) {
                AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmReceiver.class);
                intent.putExtra("message", a.getDescription());
                intent.putExtra("title", a.getTitle());
                intent.putExtra("priority", a.getPriority());
                intent.putExtra("id", notificationID);
                intent.putExtra("IntentType", "Notification");

                Log.d("err", a.getTitle() + " " + a.getDescription() + " " + a.getPriority());

                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, notificationID, intent, 0);
                notificationID++;

                alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + inSeconds, alarmIntent);
            }
        }else {
            System.out.println("Task List is null.");
        }

    }
}
