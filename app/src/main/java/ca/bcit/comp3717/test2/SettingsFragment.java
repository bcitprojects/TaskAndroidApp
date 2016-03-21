package ca.bcit.comp3717.test2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Kevin on 2/6/2016.
 */
public class SettingsFragment extends Fragment {

    private String array_spinner[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        array_spinner=new String[3];
        array_spinner[0]="1";
        array_spinner[1]="1.5";
        array_spinner[2]="2";
        Spinner s = (Spinner) rootView.findViewById(R.id.notiFreqSpinner);
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

        return rootView;
    }

}
