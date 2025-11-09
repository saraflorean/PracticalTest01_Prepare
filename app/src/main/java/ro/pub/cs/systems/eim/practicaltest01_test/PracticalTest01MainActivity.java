package ro.pub.cs.systems.eim.practicaltest01_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class PracticalTest01MainActivity extends AppCompatActivity {
    private EditText pressMe1;
    private EditText pressMe2;
    private Button pressMe1Button;
    private Button pressMe2Button;
    private Button navigateButton;
    private int serviceStatus = Constants.SERVICE_STOPPED;

    private final IntentFilter intentFilter = new IntentFilter();

    private final NavigateButtonClickListener navigateButtonClickListener = new NavigateButtonClickListener();
    private class NavigateButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            int numberOfClicks = Integer.parseInt(pressMe1.getText().toString()) +
                                 Integer.parseInt(pressMe2.getText().toString());
            intent.putExtra(Constants.NUMBER_OF_CLICKS, numberOfClicks);
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
        }
    }
    private final ButtonPressClickListener buttonPressClickListener = new ButtonPressClickListener();
    private class ButtonPressClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            int leftNumberOfClicks = Integer.parseInt(pressMe1.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(pressMe2.getText().toString());

//            EditText currentCounterField = null;

            if (id == R.id.press_me_1_button){
                leftNumberOfClicks++;
                pressMe1.setText(String.valueOf(leftNumberOfClicks));
//                currentCounterField = pressMe1;
            } else if (id == R.id.press_me_2_button){
                rightNumberOfClicks++;
                pressMe2.setText(String.valueOf(rightNumberOfClicks));
//                currentCounterField = pressMe2;
            }

            // logica de incrementare
//            if (currentCounterField != null) {
//                currentCounterField.setText(String.valueOf(Integer.parseInt(currentCounterField.getText().toString()) + 1));
//                int currentValue = Integer.parseInt(currentCounterField.getText().toString());
//
//                // incrementeaza valoarea
//                currentValue++;
//
//                // scrie valoarea noua in campul text
//                currentCounterField.setText(String.valueOf(currentValue));
//            }
            if (leftNumberOfClicks + rightNumberOfClicks > Constants.SUM_THRESHOLD && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRST_NUMBER, leftNumberOfClicks);
                intent.putExtra(Constants.SECOND_NUMBER, rightNumberOfClicks);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private static class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, Objects.requireNonNull(intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_main);

        pressMe1Button = findViewById(R.id.press_me_1_button);
        pressMe2Button = findViewById(R.id.press_me_2_button);
        navigateButton = findViewById(R.id.navigate_button);

        pressMe1 = findViewById(R.id.press_me_1);
        pressMe2 = findViewById(R.id.press_me_2);

        pressMe1Button.setOnClickListener(buttonPressClickListener);
        pressMe2Button.setOnClickListener(buttonPressClickListener);
        navigateButton.setOnClickListener(navigateButtonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.LEFT_COUNT)) {
                pressMe1.setText(savedInstanceState.getString(Constants.LEFT_COUNT));
            } else {
                pressMe1.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey(Constants.RIGHT_COUNT)) {
                pressMe2.setText(savedInstanceState.getString(Constants.RIGHT_COUNT));
            } else {
                pressMe2.setText(String.valueOf(0));
            }
        } else {
            pressMe1.setText(String.valueOf(0));
            pressMe2.setText(String.valueOf(0));
        }

        for (int i = 0; i < Constants.actionTypes.length; i++) {
            intentFilter.addAction(Constants.actionTypes[i]);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(messageBroadcastReceiver, intentFilter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.LEFT_COUNT, pressMe1.getText().toString());
        outState.putString(Constants.RIGHT_COUNT, pressMe2.getText().toString());
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.LEFT_COUNT)) {
            pressMe1.setText(savedInstanceState.getString(Constants.LEFT_COUNT));
        } else {
            pressMe1.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey(Constants.RIGHT_COUNT)) {
            pressMe2.setText(savedInstanceState.getString(Constants.RIGHT_COUNT));
        } else {
            pressMe2.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }
}