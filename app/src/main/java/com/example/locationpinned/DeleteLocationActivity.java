package com.example.locationpinned;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteLocationActivity extends AppCompatActivity {
    private EditText addressEditText;
    private Button deleteLocationButton;
    private TextView resultTextView;
    private Button doneButton;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_location);

        addressEditText = findViewById(R.id.addressEditText);
        deleteLocationButton = findViewById(R.id.deleteLocationButton);
        resultTextView = findViewById(R.id.resultTextView);
        doneButton = findViewById(R.id.doneButton);
        databaseHelper = new LocationDatabaseHelper(this);

        deleteLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = addressEditText.getText().toString();

                int rowsAffected = databaseHelper.deleteLocationByAddress(address);

                if (rowsAffected > 0) {
                    // Location deleted successfully
                    resultTextView.setText("Location deleted successfully.");
                } else {
                    // Location not found or deletion failed
                    resultTextView.setText("Location not found or deletion failed.");
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMainMenu(); //return to the main menu
            }
        });
    }

    private void navigateToMainMenu() {
        finish(); // Close the current activity and return to the previous one (Main Menu).
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
