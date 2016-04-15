package ca.bcit.comp3717.test2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Kevin on 2/6/2016.
 */
public class AboutFragment extends Fragment {

    private String array_spinner[];
    private View root;
    public static final String NOTIFICATION_STATE = "notificationKey";
    public static final String NOTIFREQ_VALUE = "notiFreqKey";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.about_fragment, container, false);
        root                = rootView;
       // sharedpreferences = rootView.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        editor = sharedpreferences.edit();

        //needed to get number of tasks COPY/PASTE THIS
        DataDbHelper db     = new DataDbHelper(getActivity());

        array_spinner = new String[3];
        array_spinner[0] = "1";
        array_spinner[1] = "2";
        array_spinner[2] = "3";
        Spinner s = (Spinner) rootView.findViewById(R.id.notiFreqSpinner);
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

        if(sharedpreferences.getInt(NOTIFREQ_VALUE, 1) == 3) {
            s.setSelection(2);
        }else if(sharedpreferences.getInt(NOTIFREQ_VALUE, 1) == 2) {
            s.setSelection(1);
        }else{
            s.setSelection(0);
        }

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos == 0) {
                    editor.putInt(NOTIFREQ_VALUE, pos + 1);
                    editor.commit();
                }else if(pos == 1) {
                    editor.putInt(NOTIFREQ_VALUE, pos + 1);
                    editor.commit();
                }else{
                    editor.putInt(NOTIFREQ_VALUE, pos + 1);
                    editor.commit();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

        Switch notificationSwitch = (Switch) rootView.findViewById(R.id.notiSwitch);

        if(sharedpreferences.getInt(NOTIFICATION_STATE, 1) == 0){
            notificationSwitch.toggle();
        }
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putInt(NOTIFICATION_STATE, 1);
                } else{
                    editor.putInt(NOTIFICATION_STATE, 0);
                }
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

    @Override
    public void onStop() {
        editor.commit();
        super.onStop();
    }

}
