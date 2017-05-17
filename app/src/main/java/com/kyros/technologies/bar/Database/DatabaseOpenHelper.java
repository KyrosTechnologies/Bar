package com.kyros.technologies.bar.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by kyros on 02-05-2017.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper{
    private static final String DATABASE_NAME="liquor_list.db";
    private static final int DATABASE_VERSION=1;
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
