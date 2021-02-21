package com.example.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Temperature.db";
    public static final String TABLE_NAME="temperature_table";
    public static final String rTABLE_NAME="reminder_table";
    public static final String COL_1="ID";
    public static final String COL_2="TEMPERATURE";
    public static final String COL_3="DATE";
    public static final String COL_4="LOCATION";
    public static final String rCOL_1 = "ID";
    public static final String rCOL_2 = "TITLE";
    public static final String rCOL_3 = "TIME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TEMPERATURE TEXT,DATE TEXT,LOCATION TEXT) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ rTABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,TIME TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ rTABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String temperature, String date, String location) {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,temperature);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,location);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertReminderData (String reminderTitle, String reminderTime) {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(rCOL_2, reminderTitle);
        contentValues.put(rCOL_3, reminderTime);
        long result = db.insert(rTABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getReminderData() {
        SQLiteDatabase db =  this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + rTABLE_NAME + " order by Id DESC LIMIT 1 ",null);
        return res;
    }

    public Cursor getAllData() {
        SQLiteDatabase db =  this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
