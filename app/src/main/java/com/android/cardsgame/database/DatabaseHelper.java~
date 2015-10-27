package com.android.cardsgame.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Salman on 10/27/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "colour_memory_game";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "user";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String SCORE = "score";

    public static final String CREATE_USERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ( "
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + NAME + " VARCHAR, " + SCORE + " INTEGER )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
