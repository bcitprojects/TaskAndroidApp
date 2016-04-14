package ca.bcit.comp3717.test2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pika on 2016-04-11.
 */
public class NotificationGenerator {

    public static final int HIGH_PRIORITY = 7;
    public static final int MED_PRIORITY = 3;
    public static final int LOW_PRIORITY = 1;
    public static final int ZERO_DAY = 0;

    public static final int DAYS_IN_MILLISECONDS = 24*60*60*1000;
    public static final String TASK_DATE_FORMAT = "dd/MM/yyyy";
    public static final String TODAY_DATE_FORMAT = "dd/MM/yyyy";

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

        if((tasks != null) && (AboutFragment.notifications == 1)) {
            for(Task a: tasks) {
                if(isValidTask(a)){
                    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context, AlarmReceiver.class);
                    intent.putExtra("message", a.getDescription());
                    intent.putExtra("title", a.getTitle());
                    intent.putExtra("priority", a.getPriority());
                    intent.putExtra("id", notificationID);
                    intent.putExtra("IntentType", "Notification");

                    Log.d("Displaying", "Title: " + a.getTitle() + " Description: " + a.getDescription() + " Date Due: " + a.getDue() + " Priority: " + a.getPriority());

                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, notificationID, intent, 0);
                    notificationID++;

                    alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + inSeconds, alarmIntent);
                }
            }
        }else {
            System.out.println("Task List is null.");
        }
    }

    //checks if tasks will send a notification
    public static boolean isValidTask(Task task) {

        int difference = getNumberOfDayDifference(task);

        if(task.getPriority().equals("2")) {
            if(difference > ZERO_DAY && difference <= HIGH_PRIORITY) {
                return true;
            }
        }else if(task.getPriority().equals("1")) {
            if(difference > ZERO_DAY && difference <= MED_PRIORITY) {
                return true;
            }
        }else if(task.getPriority().equals("0")) {
            if(difference == LOW_PRIORITY) {
                return true;
            }
        }

        return false;
    }

    //gets number of days between task due date and today
    public static int getNumberOfDayDifference(Task task) {

        SimpleDateFormat sdf = new SimpleDateFormat(TASK_DATE_FORMAT);
        Date taskDate = new Date();
        DateFormat formatter = new SimpleDateFormat(TODAY_DATE_FORMAT);

        Date today = new Date();
        Date todayWithZeroTime = new Date();

        try {
            taskDate = sdf.parse(task.getDue());
            todayWithZeroTime = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (int) ((taskDate.getTime() / DAYS_IN_MILLISECONDS) - (int)((todayWithZeroTime.getTime() / DAYS_IN_MILLISECONDS)));
    }
}
