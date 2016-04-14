package ca.bcit.comp3717.test2;

import android.graphics.drawable.Drawable;

import java.util.Calendar;

/**
 * Created by Kevin on 2/6/2016.
 */
public class TaskListViewItem {

    public final int id;              // the row id in database
    public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description
    public Calendar due;

    public TaskListViewItem(int id, Drawable icon, String title, String description) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public void setDue(Calendar due){
        this.due = due;
    }

    public Calendar getDue(){
        return due;
    }
}
