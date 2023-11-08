package com.example.locationpinned;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class GeocodingHelper {
    private Context context;

    public GeocodingHelper(Context context) {
        this.context = context;
    }

    //Reads a list of coordinates from a text file and saves them to the database
    public void readAndGeocodeCoordinates() {
        try {
            InputStream inputStream = context.getAssets().open("coordinates.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    double latitude = Double.parseDouble(parts[0]);
                    double longitude = Double.parseDouble(parts[1]);

                    geocodeAndSaveAddress(latitude, longitude);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Geocodes addresses based on latitude and longitude
    void geocodeAndSaveAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0);

                // Save the address to your database using the LocationDatabaseHelper
                LocationDatabaseHelper databaseHelper = new LocationDatabaseHelper(context);
                long newRowId = databaseHelper.addLocation(address, latitude, longitude);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
