package ca.bcit.comp3717.test2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kevin on 2/6/2016.
 */
public class CreateFragment extends Fragment {

    private String array_spinner[];
    EditText    txtDate;
    EditText    titleEdit;
    EditText    descEdit;
    Spinner     spinner;
    String      priority;

    Integer mYear, mMonth, mDay;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.create_fragment, container, false);

        txtDate     = (EditText) rootView.findViewById(R.id.txtdate);

        Button confirm = (Button) rootView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirm(v);
            }
        });

        array_spinner=new String[3];
        array_spinner[0] ="Low";
        array_spinner[1]="Medium";
        array_spinner[2]="High";
        Spinner s = (Spinner) rootView.findViewById(R.id.taskDifficultySpinner);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            txtDate.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                    + "/" + String.valueOf(year));
        }
    };

    public void onConfirm(final View view) {

        txtDate     = (EditText) getView().findViewById(R.id.txtdate);
        titleEdit   = (EditText)getView().findViewById(R.id.titleEditText);
        descEdit    = (EditText)getView().findViewById(R.id.descriptionEditText);
        spinner     = (Spinner)getView().findViewById(R.id.taskDifficultySpinner);
        priority    = Integer.toString(spinner.getSelectedItemPosition());
        DataDbHelper    db          = new DataDbHelper(getContext());

        String s1 = txtDate.getText().toString();
        String s2 = titleEdit.getText().toString();
        String s3 = descEdit.getText().toString();


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dateDue = Calendar.getInstance();
        try {
            dateDue.setTime(sdf.parse(s1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // get today into Calendar object
        Calendar today = Calendar.getInstance();

        int res = dateDue.compareTo(today); // returns < 0 if datedue is before today

        if(res >= 0) {
            if (s1.equals("") || s2.equals("") || s3.equals("")) {
                Toast.makeText(getContext(), "Please input all fields", Toast.LENGTH_LONG).show();
            } else {
                //save to db
                db.insert(titleEdit.getText().toString(), descEdit.getText().toString(), priority, txtDate.getText().toString());

                Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_LONG).show();
                txtDate.setText("");
                titleEdit.setText("");
                descEdit.setText("");
                ((MainActivity) getActivity()).getViewPager().getAdapter().notifyDataSetChanged();
            }
        }else{
            Toast.makeText(getContext(), "Please put in a valid date!", Toast.LENGTH_SHORT).show();
        }
    }

}
