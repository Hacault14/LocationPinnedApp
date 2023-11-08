package com.example.locationpinned;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateLocationActivity extends AppCompatActivity {
    private EditText currentAddressEditText;
    private EditText newLatitudeEditText;
    private EditText newLongitudeEditText;
    private Button updateLocationButton;
    private TextView resultTextView;
    private Button doneButton;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        currentAddressEditText = findViewById(R.id.currentAddressEditText);
        newLatitudeEditText = findViewById(R.id.newLatitudeEditText);
        newLongitudeEditText = findViewById(R.id.newLongitudeEditText);
        updateLocationButton = findViewById(R.id.updateLocationButton);
        resultTextView = findViewById(R.id.resultTextView);
        doneButton = findViewById(R.id.doneButton);
        databaseHelper = new LocationDatabaseHelper(this);

        updateLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentAddress = currentAddressEditText.getText().toString();
                String newLatitudeStr = newLatitudeEditText.getText().toString();
                String newLongitudeStr = newLongitudeEditText.getText().toString();

                try {
                    double newLatitude = Double.parseDouble(newLatitudeStr);
                    double newLongitude = Double.parseDouble(newLongitudeStr);

                    int rowsAffected = databaseHelper.updateLocationByAddress(currentAddress, newLatitude, newLongitude);

                    if (rowsAffected > 0) {
                        // Location updated successfully
                        resultTextView.setText("Location updated successfully.");
                    } else {
                        // Location not found or update failed
                        resultTextView.setText("Location not found or update failed.");
                    }
                } catch (NumberFormatException e) {
                    resultTextView.setText("Invalid latitude or longitude values.");
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMainMenu(); // Return to main menu
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
