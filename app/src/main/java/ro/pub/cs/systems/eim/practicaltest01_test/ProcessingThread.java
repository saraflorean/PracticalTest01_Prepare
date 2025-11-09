package ro.pub.cs.systems.eim.practicaltest01_test;

import static ro.pub.cs.systems.eim.practicaltest01_test.Constants.PROCESSING_THREAD_TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;
import android.os.Process;

public class ProcessingThread extends Thread {
    private final Context context;
    private boolean isRunning = true;

    private final Random random = new Random();

    private final double arithmeticMean;
    private final double geometricMean;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;
        arithmeticMean = (firstNumber + secondNumber) / 2.0;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        Log.d(PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());

        while (isRunning) {
            sendMessage();
            sleep10s();
        }
        Log.d(PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }
    private void sleep10s() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
