package com.junliu.liuju.supportlibry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuju on 2018/1/27.
 */

public class MySqlHelper extends SQLiteOpenHelper {
    private volatile static MySqlHelper helper;
    public static MySqlHelper getInstance(Context context){
        if (helper == null){
            synchronized (MySqlHelper.helper){
                if (helper == null) helper = new MySqlHelper(context);
            }
        }
        return helper;
    }
    private MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private MySqlHelper(Context context){
        super(context,Constants.dataBaseName,null,Constants.dataBaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "creat table searchList(_id Integer primary key,content text,date text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
