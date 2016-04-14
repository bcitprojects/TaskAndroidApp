package ca.bcit.comp3717.test2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Kevin on 2/6/2016.
 */
public class TaskFragment extends ListFragment {

    private ArrayList<TaskListViewItem> taskList;
    private TaskListViewAdapter adapter;
    public Button  button;
    public static final int TASK_ADD_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.task_fragment, container, false);


        Resources resources = getResources();
        DataDbHelper db  = new DataDbHelper(this.getActivity());
        ArrayList<Task> tasks;
        tasks = db.getTasks();
        Log.d("tasks", tasks.toString());
        Collections.sort(tasks, new Task());
        Log.d("tasks", tasks.toString());

        taskList = new ArrayList<TaskListViewItem>();
        for(Task t: tasks){
            String dateDiff = "";
            Drawable drawable;
            switch(t.getPriority()){
                case "0":
                    drawable = getResources().getDrawable(R.drawable.test);
                    break;
                case "1":
                    drawable = getResources().getDrawable(R.drawable.med);
                    break;
                case "2":
                    drawable = getResources().getDrawable(R.drawable.high);
                    break;
                default:
                    drawable = getResources().getDrawable(R.drawable.test);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar dateSet = Calendar.getInstance();
            try {
                dateSet.setTime(sdf.parse(t.getDue()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // get today into Calendar object
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);

            // get the time difference (millis)
            long difference = dateSet.getTimeInMillis() - today.getTimeInMillis();

            // get the days from the millis
            long days = (difference / (24 * 60 * 60 * 1000)) + 1;

            // set the text to display the days
            dateDiff = Long.toString(days);

            String dayString = (dateDiff.equalsIgnoreCase("1"))? " day left":" days left";
            TaskListViewItem tlvi = new TaskListViewItem(t.getId(), drawable, dateDiff + dayString + '\n' + t.getTitle(), t.getDescription());
            tlvi.setDue(dateSet);
            taskList.add(tlvi);
        }

        //taskList = getTaskList();

        adapter = new TaskListViewAdapter(getActivity(), taskList);
        /** Setting the array adapter to the list view */
        setListAdapter(adapter);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        TaskListViewItem task = (TaskListViewItem)getListView().getItemAtPosition(position);
        Calendar cal = task.getDue();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, d MMM yyyy");
        String formatted = format1.format(cal.getTime());
        Toast.makeText(getContext(), "DUE ON: " + formatted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
