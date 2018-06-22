package com.example.blazk.twistyride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.net.URI;

public class MyDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ServiceHistory.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_SERVICE_HISTORY = "serviceHistory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMAGEPATH = "imagepath";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_DATATYPE = "datatype";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        String query1 = "CREATE TABLE " + TABLE_SERVICE_HISTORY + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                        COLUMN_IMAGEPATH + " TEXT " + ");";
*/
        String query = "CREATE TABLE " + TABLE_SERVICE_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATATYPE + " TEXT,"
                + COLUMN_IMAGEPATH + " TEXT,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE_HISTORY);
        onCreate(db);
    }

    //Add a new row to the database
    public void addImagePath(ServiceItem serviceItem) {
        if(serviceItem.get_dataType() == ServiceItem.DataType.IMAGE) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_IMAGEPATH, serviceItem.get_photoUri());
            values.put(COLUMN_DATATYPE, serviceItem.get_dataTypeString());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_SERVICE_HISTORY, null, values);
            db.close();
        }
    }

    //Delete a product from the database
    public void deleteImagePath(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SERVICE_HISTORY + " WHERE " + COLUMN_ID + "=\"" + id + "\";");
    }
/*
    public void addType(ServiceItem serviceItem) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATATYPE, serviceItem.get_dataTypeString());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SERVICE_HISTORY, null, values);
        db.close();
    }
*/
    //Print out the database as a string
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SERVICE_HISTORY + " WHERE 1";

        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("imagepath")) != null) {
                dbString += c.getString(c.getColumnIndex("imagepath"));
                dbString += "\n";
                c.moveToNext();
            }
        }
        db.close();
        return dbString;
    }

}












