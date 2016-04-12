package ca.bcit.comp3717.test2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

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
        taskList = new ArrayList<TaskListViewItem>();
        for(Task t: db.getTasks()){
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
            taskList.add(new TaskListViewItem(drawable, t.getTitle(), t.getDescription()));
        }

        //taskList = getTaskList();

        adapter = new TaskListViewAdapter(getActivity(), taskList);
        /** Setting the array adapter to the list view */
        setListAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


   private ArrayList<TaskListViewItem> getTaskList(){

        ArrayList<TaskListViewItem> test = new ArrayList<TaskListViewItem>();
        Resources resources = getResources();
       // test.add(new TaskListViewItem(resources.getDrawable(R.drawable.test), getString(R.string.test), getString(R.string.test_description)));
        test.add(new TaskListViewItem(resources.getDrawable(R.drawable.test), "test Title", "test description"));

        return test;
    }

    protected void addListItem(Drawable drawable, String title, String description) {

        Resources resources = getResources();
        taskList.add(new TaskListViewItem(drawable, title, description));
    }
}
