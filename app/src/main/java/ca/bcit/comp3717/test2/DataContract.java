package ca.bcit.comp3717.test2;

import android.provider.BaseColumns;

/**
 * Created by rbs on 2016-03-22.
 */
public class DataContract {
    private DataContract()
    {
    }

    public static abstract class DataEntry
            implements BaseColumns
    {
        public static final String TABLE_NAME = "tasks";
        public static final String TITLE_NAME = "title";
        public static final String DESCRIPTION_NAME = "description";
        public static final String PRIORITY_NAME = "priority";
        public static final String DUE_NAME = "due";
    }
}
