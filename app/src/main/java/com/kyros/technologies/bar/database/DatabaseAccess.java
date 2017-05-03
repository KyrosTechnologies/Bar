package com.kyros.technologies.bar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyros on 02-05-2017.
 */

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private static DatabaseAccess databaseAccess;
    private DatabaseAccess(Context mContext){
        this.sqLiteOpenHelper=new DatabaseOpenHelper(mContext);
    }
    public static DatabaseAccess getDatabaseAccess(Context mContext){
        if(databaseAccess==null){
            databaseAccess=new DatabaseAccess(mContext);
        }
        return databaseAccess;
    }
    public void openDatabase(){
        this.database=sqLiteOpenHelper.getWritableDatabase();
    }
    public void closeDatabase(){
        if(database!=null){
            this.database.close();
        }

    }
    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM type_table", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
