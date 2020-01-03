package com.smoothswitch.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smoothswitch.model.Place;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "smoothSwitch";
    private static final String TABLE_PLACES = "places";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_RINGERMODE = "ringerMode";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONG = "long";
    private static final String KEY_RADIUS = "radius";
    private static final String KEY_IS_ENABLED = "isEnabled";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_NAME + " TEXT,"
                + KEY_RINGERMODE + " TEXT,"
                + KEY_LAT + " REAL,"
                + KEY_LONG + " REAL,"
                + KEY_RADIUS + " TEXT,"
                + KEY_IS_ENABLED + " INTEGER"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

// Adding new place
    public void addPlace(Place place) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = mapPlaceToValues(place);
        db.insertOrThrow(TABLE_PLACES, null, values);
        db.close();
    }

    // Getting single place
    public Place getPlace(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[]{KEY_ID, KEY_NAME, KEY_RINGERMODE, KEY_LAT,
                        KEY_LONG, KEY_RADIUS, KEY_RADIUS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor == null)
            return null;
        cursor.moveToFirst();
        // return place
        return mapCursorToPlace(cursor);
    }

    // Getting All Places
    public List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLACES;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                places.add(mapCursorToPlace(cursor));
            } while (cursor.moveToNext());
        }
        return places;
    }

    // Updating single place
    public int updatePlace(Place place) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = mapPlaceToValues(place);
        return db.update(TABLE_PLACES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(place.getId())});
    }

    // Deleting single place
    public void deletePlace(Place place) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PLACES, KEY_ID + " = ?",
                new String[]{String.valueOf(place.getId())});
        db.close();
    }

    // Getting places Count
    public int getPlacesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLACES;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    private ContentValues mapPlaceToValues(Place place) {
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, place.getName());
        values.put(KEY_RINGERMODE, place.getRingerMode());
        values.put(KEY_LAT, place.getLatitude());
        values.put(KEY_LONG, place.getLongitude());
        values.put(KEY_RADIUS, place.getRadius());
        values.put(KEY_IS_ENABLED, place.isEnabled() ? 1 : 0);
        return values;
    }

    private Place mapCursorToPlace(Cursor cursor) {
        return new Place(cursor.getInt(0), cursor.getString(1),  cursor.getString(2),
                cursor.getDouble(3), cursor.getDouble(4),
                cursor.getDouble(5), cursor.getInt(6) == 1);
    }

    public void addPlaceAll(List<Place> places) {
        for (Place place : places) {
            addPlace(place);
        }
    }
}