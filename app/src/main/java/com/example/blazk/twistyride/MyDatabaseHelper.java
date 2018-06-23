package com.example.blazk.twistyride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ServiceHistory.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_SERVICE_HISTORY = "serviceHistory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMAGEPATH = "imagepath";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_DATATYPE = "datatype";
    private static final String COLUMN_SERVICEINFO = "serviceinfo";
    private static String COLUMN_DATE = "date";

    public static final int COLUMN_DATATYPE_INDEX = 0;
    public static final int COLUMN_SERVICEINFO_INDEX = 1;
    public static final int COLUMN_IMAGEPATH_INDEX = 2;
    public static final int COLUMN_DATE_INDEX = 3;
    public static final int COLUMN_TIMESTAMP_INDEX = 4;

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
                + COLUMN_SERVICEINFO + " TEXT,"
                + COLUMN_IMAGEPATH + " TEXT,"
                + COLUMN_DATE + " TEXT,"
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

    //Add a new row to the database
    public void addTextServiceInfo(ServiceItem serviceItem) {
        if(serviceItem.get_dataType() == ServiceItem.DataType.TEXT) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, serviceItem.get_date());
            values.put(COLUMN_DATATYPE, serviceItem.get_dataTypeString());
            values.put(COLUMN_SERVICEINFO, serviceItem.get_service());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_SERVICE_HISTORY, null, values);
            db.close();
        }
    }

    public List<List<String>> getColumnServiceinfo() {
        String selectQuery = "SELECT * FROM " + TABLE_SERVICE_HISTORY;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);

        //NUMBER_OF_COLUMNS = 5;
        int array_cnt = 0;
        List<List<String>> list = new ArrayList<List<String>>();

        while (cursor.moveToNext()) {
            List<String> data = new ArrayList<String>();
            if(cursor.getString(cursor.getColumnIndex(COLUMN_DATATYPE)).contains("TEXT")) {
                data.add(cursor.getString(cursor.getColumnIndex(COLUMN_DATATYPE)));
                data.add(cursor.getString(cursor.getColumnIndex(COLUMN_SERVICEINFO)));
                data.add("");
                data.add(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                data.add(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                list.add(data);
            }
            else if(cursor.getString(cursor.getColumnIndex(COLUMN_DATATYPE)).contains("IMAGE")){
                data.add(cursor.getString(cursor.getColumnIndex(COLUMN_DATATYPE)));
                data.add("");
                data.add(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEPATH)));
                data.add("");
                data.add(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                list.add(data);
            }
            array_cnt++;
        }
        cursor.close();
        return list;
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












