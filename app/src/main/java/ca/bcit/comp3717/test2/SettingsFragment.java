package ca.bcit.comp3717.test2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Kevin on 2/6/2016.
 */
public class SettingsFragment extends Fragment {

    private String array_spinner[];
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        root = rootView;

        array_spinner = new String[3];
        array_spinner[0] = "1";
        array_spinner[1] = "1.5";
        array_spinner[2] = "2";
        Spinner s = (Spinner) rootView.findViewById(R.id.notiFreqSpinner);
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

        Button notifyButton = (Button) rootView.findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NotificationGenerator.generateTimedNotification(rootView.getContext(), "Alert!", "You have task due!" , 5);
                NotificationGenerator.generateWithList(rootView.getContext(), ((MainActivity) getActivity()).getTaskList(), 5);
            }
        });

        Button deleteAllButton = (Button) rootView.findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).deleteAllTasks();
            }
        });

        return rootView;
    }


    public void sendNotification() {

        Intent resultIntent = new Intent(root.getContext(), MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        root.getContext(),
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(root.getContext());

        mBuilder.setSmallIcon(R.drawable.test);
        mBuilder.setContentTitle("Notification Alert!");
        mBuilder.setContentText("Manually generated notification.");
        mBuilder.setTicker("Notification Received!");
        mBuilder.setAutoCancel(true);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) root.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(9999, mBuilder.build());

    }

    public void delayedNotification() {

        //Calendar calendar = Calendar.getInstance();

        AlarmManager alarmMgr = (AlarmManager) root.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(root.getContext(), AlarmReceiver.class);
        intent.putExtra("message", "Hello World");
        intent.putExtra("title", "Alert!");

        PendingIntent alarmIntent = PendingIntent.getBroadcast(root.getContext(), 0, intent, 0);
        // set for 5 seconds later
        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15000, alarmIntent);
        Toast.makeText(root.getContext(), "Alarm set in 15 seconds", Toast.LENGTH_LONG).show();
    }

}
