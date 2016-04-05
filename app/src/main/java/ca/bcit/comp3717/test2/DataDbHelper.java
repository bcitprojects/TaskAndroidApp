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
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "Task.db";
    Context context;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataEntry.TABLE_NAME + "(" +
                    DataEntry._ID + " INTEGER PRIMARY KEY, " +
                    DataEntry.TITLE_NAME + " TEXT NOT NULL, " +
                    DataEntry.DESCRIPTION_NAME + " TEXT NOT NULL, " +
                    DataEntry.PRIORITY_NAME + " TEXT NOT NULL" +
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
    public void insert(int id, String title, String description, String priority){
        //get data respository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //create new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataEntry.PRIORITY_NAME, priority);
        values.put(DataEntry.DESCRIPTION_NAME, description);
        values.put(DataEntry.TITLE_NAME, title);
        values.put(DataEntry._ID, id);
        //insert the new row, returning the primary key value of the new row
        long newRowId;
        db.insert(
                DataEntry.TABLE_NAME,
                null,
                values);
    }
    //returns list of tasks in db
    public ArrayList<Task> getTasks(){
        SQLiteDatabase    db     = this.getReadableDatabase();
        Cursor            cursor = db.rawQuery("SELECT * FROM tasks", null);
        ArrayList<Task> array  = new ArrayList<Task>(cursor.getCount());
        int            i         = 0;
        //iterate through rows stored in cursor
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                Task task = new Task();
                task.setTitle(cursor.getString(cursor
                        .getColumnIndex(DataEntry.TITLE_NAME)));
                task.setTitle(cursor.getString(cursor
                        .getColumnIndex(DataEntry.DESCRIPTION_NAME)));
                task.setTitle(cursor.getString(cursor
                        .getColumnIndex(DataEntry.PRIORITY_NAME)));
                array.add(task);
                i++;
                cursor.moveToNext();
            }
        }
        return array; // change this later
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
