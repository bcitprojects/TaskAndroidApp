package ca.bcit.comp3717.test2;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //hello
    TabLayout tabLayout;
    ViewPager viewPager;
    CustomAdapter custAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }

    private class CustomAdapter extends FragmentStatePagerAdapter {

        private String fragments [] = {"Tasks","Calendar", "Settings"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new TaskFragment();
                case 1:
                    return new CalendarFragment();
                case 2:
                    return new SettingsFragment();
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
            // TODO Auto-generated method stub
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


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*super.onActivityResult(requestCode,resultCode,data);
        String title = data.getStringExtra("title");
        //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        TaskFragment fragobj = new TaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "SUPPP");
        fragobj.setArguments(bundle);
        custAdapter.notifyDataSetChanged();
        if (requestCode == TaskFragment.TASK_ADD_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

            }
        }
    }*/


}
