package ca.bcit.comp3717.test2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rbs on 2016-04-11.
 */
public class AboutFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.about_fragment, container, false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        int taskCount = prefs.getInt("com.example.app.count", 0);
        TextView t = (TextView) getView().findViewById(R.id.numberTasks);  //UPDATE
        t.setText("Current Number of Tasks: " + Integer.toString(taskCount));
        return rootView;
    }

}
