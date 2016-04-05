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
public class TaskFragment extends ListFragment implements View.OnClickListener {

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

        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Resources resources = getResources();
        DataDbHelper    db  = new DataDbHelper(this.getActivity());
        taskList = new ArrayList<TaskListViewItem>();
        for(Task t: db.getTasks()){
            String s = t.getTitle() + " " + t.getDescription() + " " + t.getPriority();
            taskList.add(new TaskListViewItem(resources.getDrawable(R.drawable.test), t.getTitle(), t.getDescription()));
        }

        //taskList = getTaskList();

        adapter = new TaskListViewAdapter(getActivity(), taskList);
        /** Setting the array adapter to the list view */
        setListAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                //                int count = taskList.size() + 1;
//                Resources resources = getResources();
//                taskList.add(new TaskListViewItem(resources.getDrawable(R.drawable.test), "test Title " + count, "test description"));
//                adapter.notifyDataSetChanged();


                Intent intent = new Intent(getActivity(), TaskAddActivity.class);
                startActivityForResult(intent, TASK_ADD_REQUEST);
                break;
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        DataDbHelper    db          = new DataDbHelper(this.getActivity());

        for(Task t: db.getTasks()){
            String s = t.getTitle() + " " + t.getDescription() + " " + t.getPriority();
            Log.d("Test~~~~~~~~~~~~~~~~~~", s);
        }


        String   title        = data.getStringExtra("title");
        String   description  = data.getStringExtra("description");
        String   priority     = data.getStringExtra("priority");
        Drawable drawable     = getResources().getDrawable(R.drawable.test);

        switch(priority){
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

        //Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
        addListItem(drawable, title, description);
        adapter.notifyDataSetChanged();
    }

}
