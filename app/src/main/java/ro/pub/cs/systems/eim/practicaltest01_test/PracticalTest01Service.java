package ro.pub.cs.systems.eim.practicaltest01_test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.Objects;

public class PracticalTest01Service extends Service {

//    public PracticalTest01Service() {
//    }
    private ProcessingThread processingThread = null;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        String CHANNEL_ID = "my_channel_01";
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT);
//
//        ((NotificationManager) Objects.requireNonNull(getSystemService(Context.NOTIFICATION_SERVICE))).createNotificationChannel(channel);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("")
//                .setContentText("").build();
//
//        startForeground(1, notification);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int firstNumber = intent.getIntExtra(Constants.FIRST_NUMBER, -1);
        int secondNumber = intent.getIntExtra(Constants.SECOND_NUMBER, -1);

        processingThread = new ProcessingThread(this, firstNumber, secondNumber);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}