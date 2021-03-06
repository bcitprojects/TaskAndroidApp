package ca.bcit.comp3717.test2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    CustomAdapter custAdapter;
    public static final int NOTIFICATION_HOUR = 8;
    public static final int NOTIFICATION_MINUTE = 0;
    public static final int NOTIFICATION_SECOND = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TaskBounty");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        custAdapter = new CustomAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(custAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        setDailyReminder(NOTIFICATION_HOUR, NOTIFICATION_MINUTE, NOTIFICATION_SECOND);
    }

    private class CustomAdapter extends FragmentStatePagerAdapter {

        private String fragments[] = {"Tasks", "Create", "About"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TaskFragment();
                case 1:
                    return new CreateFragment();
                case 2:
                    return new AboutFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Task> getTaskList() {
        DataDbHelper db = new DataDbHelper(this);
        return db.getTasks();
    }

    public void deleteAllTasks() {
        DataDbHelper db = new DataDbHelper(this);
        db.deleteAllTasks();
    }

    public void setDailyReminder(int hour, int minute, int second) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        Intent DailyReminderIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        DailyReminderIntent.putExtra("IntentType", "DailyReminder");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, DailyReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);

        try {
            am.cancel(pendingIntent);
        } catch (Exception e) {
            Log.e("Err", "AlarmManager update was not canceled. " + e.toString());
        }

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
