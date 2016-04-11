package ca.bcit.comp3717.test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ca.bcit.comp3717.test2.DataContract.DataEntry;
/**
 * Created by rbs on 2016-03-22.
 */
public class DataDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DB_VERSION = 7;
    public static final String DB_NAME = "Task.db";
    Context context;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataEntry.TABLE_NAME + "(" +
                    DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DataEntry.TITLE_NAME + " TEXT NOT NULL, " +
                    DataEntry.DESCRIPTION_NAME + " TEXT NOT NULL, " +
                    DataEntry.PRIORITY_NAME + " TEXT NOT NULL, " +
                    DataEntry.DUE_NAME + " TEXT NOT NULL" +
                    " );";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME;
    public DataDbHelper(final Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    //inserts new task to db
    public void insert(String title, String description, String priority, String due){
        //get data respository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //create new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataEntry.PRIORITY_NAME, priority);
        values.put(DataEntry.DESCRIPTION_NAME, description);
        values.put(DataEntry.TITLE_NAME, title);
        values.put(DataEntry.DUE_NAME, due);
        //insert the new row, returning the primary key value of the new row
        //insert into db
        db.insert(
                DataEntry.TABLE_NAME,
                null,
                values);
    }
    //returns list of tasks in db
    public ArrayList<Task> getTasks(){
        SQLiteDatabase    db     = this.getReadableDatabase();
        Cursor            c = db.rawQuery("SELECT * FROM tasks", null);
        ArrayList<Task> array  = new ArrayList<Task>(c.getCount());
        Cursor            cursor = db.rawQuery("SELECT * FROM tasks", null);

        int i = 0;
        if(c.moveToFirst())
        {
            do {
                Task task = new Task();
                task.setTitle(c.getString(c.getColumnIndex(DataEntry.TITLE_NAME)));
                task.setDescription(c.getString(c.getColumnIndex(DataEntry.DESCRIPTION_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DataEntry.PRIORITY_NAME)));
                task.setDue(c.getString(c.getColumnIndex(DataEntry.DUE_NAME)));
                array.add(task);
                i+=1;
            }while(c.moveToNext());
        }
        return array;
    }

    public void deleteAllTasks() {
        SQLiteDatabase    db     = this.getReadableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
