package com.example.electricitygrrapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "ElectricityBill.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Bill(month TEXT, units INTEGER, total REAL, rebate REAL, finalCost REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS Bill");
        onCreate(db);
    }

    // Insert new data
    public boolean insertData(String month, int units, double total, double rebate, double finalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("month", month);
        cv.put("units", units);
        cv.put("total", total);
        cv.put("rebate", rebate);
        cv.put("finalCost", finalCost);
        long result = db.insert("Bill", null, cv);
        return result != -1;
    }

    // 1. Get all data (for ListView)
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT rowid AS _id, * FROM Bill", null);
    }

    // 2. Get single record by ID
    public Cursor getSingleRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Bill WHERE rowid = ?", new String[]{String.valueOf(id)});
    }


}

