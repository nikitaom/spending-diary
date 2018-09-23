package com.example.nikita.spendingdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 11;
    public static final String DB_NAME = "spendsDB";
    public static final String TABLE_SPENDS = "spends";
    public static final String TABLE_CATEGORY = "categoryTable";

    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String AMOUNT = "amount";
    public static final String CATEGORY = "category";
    public static final String TYPE = "type";
    public static final String FK_ID = "spends_id";
    public static final String ID_CAT = "_id_cat";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    String[] arr_category = {"Products","Funs","FastFood"};
    @Override
    public void onCreate(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        db.execSQL("create table " +TABLE_SPENDS + "(" +
                ID + " integer primary key," +
                DATE + " text," +
                TIME + " text," +
                AMOUNT + " text," +
                TYPE + " integer," +
                FK_ID + " integer," +
               " FOREIGN KEY ("+FK_ID+") REFERENCES "+TABLE_CATEGORY+"("+ID_CAT +"))");

        db.execSQL("create table " +TABLE_CATEGORY + "(" +
                ID_CAT + " integer primary key," +
                CATEGORY + " text" +")");

        for (int i =0;i<arr_category.length;i++){
            contentValues.put(CATEGORY,arr_category[i]);
            db.insert(TABLE_CATEGORY, null, contentValues);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_SPENDS);
        db.execSQL("drop table if exists " + TABLE_CATEGORY);
        onCreate(db);
    }
}
