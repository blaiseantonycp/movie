package com.qburst.blaise.movie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fav_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "favourite";
    private static final String ID = "film_id";
    private static final String IS_FAVOURITE = "is_favourite";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"("+ID+" varchar(10) primary key,"
                +IS_FAVOURITE+" int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert or ignore into "+TABLE_NAME+" values("+id+",1)");
    }

    public void remove(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME+" where "+ID+"="+id);
    }
    public List<String> getFavoriteMovies(){
        List<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select * from "+TABLE_NAME+" where "+IS_FAVOURITE+"=1";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(cursor.getColumnIndex(ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ids;
    }
    public boolean isFavourite(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                +TABLE_NAME+" where "+ID+"="+id,null);
        cursor.moveToFirst();
        try {
            return cursor.getInt(cursor.getColumnIndex(IS_FAVOURITE)) == 1;
        }
        catch(Exception e) {
            return false;
        }
    }
}
