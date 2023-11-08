package com.example.locationpinned;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AddLocationActivity extends AppCompatActivity {
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private Button addLocationButton;
    private TextView resultTextView;
    private Button doneButton;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        addLocationButton = findViewById(R.id.addLocationButton);
        resultTextView = findViewById(R.id.resultTextView);
        doneButton = findViewById(R.id.doneButton);
        databaseHelper = new LocationDatabaseHelper(this);

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitudeStr = latitudeEditText.getText().toString();
                String longitudeStr = longitudeEditText.getText().toString();

                try {
                    double latitude = Double.parseDouble(latitudeStr);
                    double longitude = Double.parseDouble(longitudeStr);

                    // Use the existing geocodeAndSaveAddress() method to add the location to the database
                    // This method will add the location based on the provided latitude and longitude
                    GeocodingHelper geocodingHelper = new GeocodingHelper(AddLocationActivity.this);
                    geocodingHelper.geocodeAndSaveAddress(latitude, longitude);

                    resultTextView.setText("Location added successfully.");
                } catch (NumberFormatException e) {
                    resultTextView.setText("Invalid latitude or longitude values.");
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMainMenu(); //Return to the main menu
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
