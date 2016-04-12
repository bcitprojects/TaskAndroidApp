package ca.bcit.comp3717.test2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Pika on 2016-04-11.
 */
public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            Log.d("err", "Running Thread");
            DataDbHelper db = new DataDbHelper(context);
            ArrayList<Task> tasks = db.getTasks();
            NotificationGenerator.generateWithList(context, tasks, 5);

            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

}
