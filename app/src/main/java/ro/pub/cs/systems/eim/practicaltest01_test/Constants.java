package ro.pub.cs.systems.eim.practicaltest01_test;

import android.content.Context;

public class Constants {
    final public static String PROCESSING_THREAD_TAG = "[Processing Thread]";
    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.arithmeticmean",
            "ro.pub.cs.systems.eim.practicaltest01.geometricmean"
    };
    final public static String BROADCAST_RECEIVER_EXTRA = "message";
    final public static String BROADCAST_RECEIVER_TAG = "[Message]";
    final public static String LEFT_COUNT = "left_count";
    final public static String RIGHT_COUNT = "right_count";
    final public static String NUMBER_OF_CLICKS = "number_of_clicks";
    final public static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    final public static int SUM_THRESHOLD = 5;
    final public static int SERVICE_STOPPED = 0;
    final public static int SERVICE_STARTED = 1;
    final public static String FIRST_NUMBER = "first_number";
    final public static String SECOND_NUMBER = "second_number";
}
