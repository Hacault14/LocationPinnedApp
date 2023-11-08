package com.example.locationpinned;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GetCoordinatesActivity extends AppCompatActivity {
    private EditText addressEditText;
    private Button getCoordinatesButton;
    private Button doneButton;
    private TextView resultTextView;
    private LocationDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coordinates);

        addressEditText = findViewById(R.id.addressEditText);
        getCoordinatesButton = findViewById(R.id.getCoordinatesButton);
        doneButton = findViewById(R.id.doneButton);
        resultTextView = findViewById(R.id.resultTextView);
        databaseHelper = new LocationDatabaseHelper(this);

        getCoordinatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = addressEditText.getText().toString();

                //Queries the database for a location based on the address
                Location location = databaseHelper.queryLocationByAddress(address);

                if (location != null) {
                    // Address exists in the database
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    resultTextView.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                } else {
                    // Address not found in the database
                    resultTextView.setText("Address does not exist in the database.");
                }
            }
        });

        // Handle "Done" button click to return to the main menu
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMainMenu();
            }
        });
    }

    private void navigateToMainMenu() {
        Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenuIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close(); // Close the database when the activity is destroyed.
    }
}
