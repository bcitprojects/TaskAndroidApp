package ca.bcit.comp3717.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class TaskAddActivity extends AppCompatActivity {

    private String array_spinner[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        array_spinner=new String[3];
        array_spinner[0]="Low";
        array_spinner[1]="Medium";
        array_spinner[2]="High";
        Spinner s = (Spinner) findViewById(R.id.taskDifficultySpinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
    }

    public void onConfirm(final View view) {

        EditText        titleEdit   = (EditText)findViewById(R.id.titleEditText);
        EditText        descEdit    = (EditText)findViewById(R.id.descriptionEditText);
        String          title       = titleEdit.getText().toString();
        String          description = descEdit.getText().toString();
        Spinner         priority    = (Spinner)findViewById(R.id.taskDifficultySpinner);
        DataDbHelper    db          = new DataDbHelper(this);
        String          due         = "12-4-2016";
        //Toast.makeText(getApplicationContext(), Integer.toString(priority.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("priority", Integer.toString(priority.getSelectedItemPosition()));
        //save to db
        db.insert(title, description, Integer.toString(priority.getSelectedItemPosition()), due);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancel(final View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

//    public TaskFragment createFrag(Bundle bundle) {
//
//        TaskFragment fragobj = new TaskFragment();
//        fragobj.setArguments(bundle);
//        return fragobj;
//    }
}
