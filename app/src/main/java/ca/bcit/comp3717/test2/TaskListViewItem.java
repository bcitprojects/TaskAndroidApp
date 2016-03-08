package ca.bcit.comp3717.test2;

import android.graphics.drawable.Drawable;

/**
 * Created by Kevin on 2/6/2016.
 */
public class TaskListViewItem {

    public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description

    public TaskListViewItem(Drawable icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }
}
