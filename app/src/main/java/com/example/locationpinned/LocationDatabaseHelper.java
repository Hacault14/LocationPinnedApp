package com.example.locationpinned;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LocationDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LOCATION = "location";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_LATITUDE + " REAL, " +
            COLUMN_LONGITUDE + " REAL);";

    public LocationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }

    // Add a location to the database (avoid duplicates)
    public long addLocation(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if an entry with the same address already exists
        Cursor cursor = db.query(TABLE_LOCATION, new String[]{COLUMN_ID},
                COLUMN_ADDRESS + "=?", new String[]{address}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            // Duplicate entry found, skip insertion
            cursor.close();
            db.close();
            return -1; // Indicate that insertion was skipped due to duplication
        }

        // No duplicate found, insert the new location
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        long newRowId = db.insert(TABLE_LOCATION, null, values);

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return newRowId; // Returns the row ID of the newly inserted location
    }

    // Delete a location based on its address
    public int deleteLocationByAddress(String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = COLUMN_ADDRESS + "=?";
        String[] whereArgs = {address};

        int rowsAffected = db.delete(TABLE_LOCATION, whereClause, whereArgs);
        db.close();

        return rowsAffected;
    }

    // Update a location based on its address with new latitude and longitude
    public int updateLocationByAddress(String address, double newLatitude, double newLongitude) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, newLatitude);
        values.put(COLUMN_LONGITUDE, newLongitude);

        String whereClause = COLUMN_ADDRESS + "=?";
        String[] whereArgs = {address};

        int rowsAffected = db.update(TABLE_LOCATION, values, whereClause, whereArgs);
        db.close();

        return rowsAffected;
    }

    // Query the database for latitude and longitude based on the address
    public Location queryLocationByAddress(String address) {
        Location location = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMN_LATITUDE, COLUMN_LONGITUDE};
        String selection = COLUMN_ADDRESS + "=?";
        String[] selectionArgs = {address};

        Cursor cursor = db.query(
                TABLE_LOCATION,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            location = new Location(address, latitude, longitude);
        }

        cursor.close();
        db.close();

        return location;
    }

}
