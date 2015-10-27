package com.android.cardsgame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.android.cardsgame.entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Salman on 10/27/2015.
 */
public class DBProcessor {

    private static DBProcessor instance = null;

    DatabaseHelper helper = null;
    SQLiteDatabase db;

    private DBProcessor(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static DBProcessor getInstance(Context context) {
        if (instance == null) {
            instance = new DBProcessor(context);
        }
        return instance;
    }

    public void open() {
        if (db == null || !db.isOpen()) {
            db = helper.getWritableDatabase();
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        open();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS + " ORDER BY " + DatabaseHelper.SCORE + " DESC ", null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(DatabaseHelper.USER_ID));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.NAME));
            int score = c.getInt(c.getColumnIndex(DatabaseHelper.SCORE));
            users.add(new User(id, name, score));
        }
        return users;
    }

    public int createNewUser(String name, int score) {
        open();
        if (isUserExist(name)) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.SCORE, score);
            int n = db.update(DatabaseHelper.TABLE_USERS, cv, DatabaseHelper.NAME + " = '" + name + "'", null);
            return n;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.NAME, name);
            cv.put(DatabaseHelper.SCORE, score);
            long n = db.insert(DatabaseHelper.TABLE_USERS, null, cv);
            return (int) n;
        }
    }

    public boolean isUserExist(String name) {
        open();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.NAME + " = '" + name + "'", null);
        return c.getCount() == 0 ? false : true;
    }

}
