package ca.bcit.comp3717.test2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 2/6/2016.
 */
public class AboutFragment extends Fragment {

    private String array_spinner[];
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.about_fragment, container, false);
        root                = rootView;
        //needed to get number of tasks COPY/PASTE THIS
        DataDbHelper db     = new DataDbHelper(getActivity());

        array_spinner = new String[3];
        array_spinner[0] = "1";
        array_spinner[1] = "1.5";
        array_spinner[2] = "2";
        Spinner s = (Spinner) rootView.findViewById(R.id.notiFreqSpinner);
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

        //display number of tasks user currently has to complete COPY/PASTE THIS
        int taskCount = db.getCount();
        TextView t = (TextView) rootView.getRootView().findViewById(R.id.taskCount);  //UPDATE
        t.setText("Tasks to Complete: " + Integer.toString(taskCount));

        Button notifyButton = (Button) rootView.findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
