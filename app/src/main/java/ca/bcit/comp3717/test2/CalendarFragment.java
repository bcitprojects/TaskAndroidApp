package ca.bcit.comp3717.test2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Kevin on 2/6/2016.
 */
public class CalendarFragment extends Fragment {

    EditText txtDate;
    EditText txtDiff;
    Integer mYear, mMonth, mDay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);

        txtDate = (EditText) rootView.findViewById(R.id.txtdate);
        txtDiff = (EditText) rootView.findViewById(R.id.dateDiff);
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

            txtDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));

            // put date selection into Calendar object
            Calendar dateSet = Calendar.getInstance();
            dateSet.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateSet.set(Calendar.MONTH, monthOfYear);
            dateSet.set(Calendar.YEAR, year);

            // get today into Calendar object
            Calendar today = Calendar.getInstance();

            // get the time difference (millis)
            long difference = dateSet.getTimeInMillis() - today.getTimeInMillis();

            // get the days from the millis
            long days = difference / (24 * 60 * 60 * 1000);

            // set the text to display the days
            txtDiff.setText(Long.toString(days) + " days left");
        }
    };

}
