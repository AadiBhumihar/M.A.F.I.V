package com.example.bhumihar.mafiv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bhumihar on 18/11/16.
 */

public class Notification_db {

    private static final String database_name = "notification1" ;
    private static final String table_name = "noti_data1" ;
    private static final int version  = 1 ;


    private static final String key_tittle = "tittle" ;
    private static final String key_body = "body";
    private static final String key_check = "check_v";
    private static final String key_id = "id";


    private static String key_id_v = "id_v";
    private final static String db_create = "create table " + table_name + "("
            + key_id_v + " integer primary key );" ;

    private final Context dbcontext ;

    public Notification_db(Context dbcontext) {
        this.dbcontext = dbcontext;
    }

    private ndbhelper mhelper;
    private SQLiteDatabase rem_db;

   public long insert_data(int id ) {
        ContentValues cv = new ContentValues();
        cv.put(key_id_v,id);
        return rem_db.insert(table_name ,null ,cv);
    }


    public int[] get_id() throws SQLException {

        String[] string = new String[] {key_id_v};
        Cursor cursor = rem_db.query(table_name ,string ,null ,null ,null ,null ,null);
        int id_col = cursor.getColumnIndex(key_id_v);
       Log.e("row_count",String.valueOf(cursor.getCount()));

        int[] result = new int[cursor.getCount()];
        int i=0;
        Log.e("Cursor",String.valueOf(cursor.getCount()));
        try {
            for (cursor.moveToFirst() ;!cursor.isAfterLast() ;cursor.moveToNext())
            {

                result[i]=cursor.getInt(id_col);
                Log.e(String.valueOf(i),String.valueOf(result[i]));
                i++;
            }


        }catch (ArrayIndexOutOfBoundsException c)
        {
            c.printStackTrace();
        }

        return result ;
    }

    private class ndbhelper extends SQLiteOpenHelper
    {

        public ndbhelper(Context context) {
            super(context, database_name,null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(db_create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public Notification_db open()
    {
       mhelper = new ndbhelper(dbcontext);
        rem_db = mhelper.getReadableDatabase();
        return this ;
    }

    public void close() {
        mhelper.close();
    }



}
