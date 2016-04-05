package ca.bcit.comp3717.test2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ca.bcit.comp3717.test2.DataContract.DataEntry;
/**
 * Created by rbs on 2016-03-22.
 */
public class DataDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Task.db";
    Context context;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataEntry.TABLE_NAME + " (" +
                    DataEntry._ID + " INTEGER PRIMARY KEY," +
                    DataEntry.TITLE_NAME + " TEXT NOT NULL" +
                    DataEntry.DESCRIPTION_NAME + " TEXT NOT NULL" +
                    DataEntry.PRIORITY_NAME + " TEXT NOT NULL" +
                    " )";
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

    public void insert(String title, String description, String priority){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO task values (title, description, priority);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
