package ro.pub.cs.systems.eim.practicaltest01_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {
    private Button okButton;
    private Button cancelButton;
    private TextView numberOfClicksTextView;

    private final OkButtonClickListener okButtonClickListener = new OkButtonClickListener();
    private class OkButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // CE FACE BUTONUL
            setResult(RESULT_OK);
            finish();
        }
    }
    private final CancelButtonClickListener cancelButtonClickListener = new CancelButtonClickListener();
    private class CancelButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // CE FACE BUTONUL
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_secondary);

        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);
        numberOfClicksTextView = findViewById(R.id.number_of_clicks);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.NUMBER_OF_CLICKS)) {
            int numberOfClicks = intent.getExtras().getInt(Constants.NUMBER_OF_CLICKS, -1);
            numberOfClicksTextView.setText(String.valueOf(numberOfClicks));
        }

        okButton.setOnClickListener(okButtonClickListener);
        cancelButton.setOnClickListener(cancelButtonClickListener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}