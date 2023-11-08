package com.example.locationpinned;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    private Button getCoordinatesButton;
    private Button addLocationButton;
    private Button deleteLocationButton;
    private Button updateLocationButton;
    private GeocodingHelper geocodingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getCoordinatesButton = findViewById(R.id.getCoordinatesButton);
        addLocationButton = findViewById(R.id.addLocationButton);
        deleteLocationButton = findViewById(R.id.deleteLocationButton);
        updateLocationButton = findViewById(R.id.updateLocationButton);
        geocodingHelper = new GeocodingHelper(this);

        geocodingHelper.readAndGeocodeCoordinates();

        getCoordinatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the Get Coordinates from Address functionality
                startActivity(new Intent(MainMenuActivity.this, GetCoordinatesActivity.class));
            }
        });

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the Add Location from Coordinates functionality
                startActivity(new Intent(MainMenuActivity.this, AddLocationActivity.class));
            }
        });

        deleteLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the Delete Location by Address functionality
                startActivity(new Intent(MainMenuActivity.this, DeleteLocationActivity.class));
            }
        });

        updateLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the Update Location by Coordinates functionality
                startActivity(new Intent(MainMenuActivity.this, UpdateLocationActivity.class));
            }
        });
    }
}
