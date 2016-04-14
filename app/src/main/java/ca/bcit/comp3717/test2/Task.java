package ca.bcit.comp3717.test2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by rbs on 2016-04-04.
 */
public class Task implements Comparator<Task>{
    String title;
    String description;
    String priority;
    String due;
    public Task() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }


    public int compare(Task t1, Task t2) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dateSet1 = Calendar.getInstance();
        try {
            dateSet1.setTime(sdf1.parse(t1.getDue()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dateSet2 = Calendar.getInstance();
        try {
            dateSet2.setTime(sdf1.parse(t2.getDue()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateSet1.compareTo(dateSet2);

    }

}
