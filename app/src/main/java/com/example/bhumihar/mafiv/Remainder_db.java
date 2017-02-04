package com.example.bhumihar.mafiv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by bhumihar on 12/11/16.
 */

public class Remainder_db {

    private static final String database_name = "task_remainder" ;
    private static final String table_name = "task_data" ;
    private static final int version  = 1 ;

    private static final String key_tittle = "tittle" ;
    private static final String key_body = "body";
    private static final String key_date_time = "date_time";
    private static final String key_id = "id";


    private final static String db_create = "create table " + table_name + "("
            + key_id + " integer primary key autoincrement, "
            + key_tittle + " text not null, "
            + key_body + " text not null, "
            + key_date_time + " text not null);" ;

    private final Context dbcontext ;

    public Remainder_db(Context context) {
        this.dbcontext = context ;
    }

    private dbhelper mhelper;
    private SQLiteDatabase rem_db;

    public long insert_data(String title, String body, String task_date_time) {
        ContentValues cv = new ContentValues();
        cv.put(key_tittle,title);
        cv.put(key_body ,body);
        cv.put(key_date_time ,task_date_time);

        return rem_db.insert(table_name ,null ,cv);
    }


    public ArrayList<String> get_id_info() throws SQLException {
         Cursor cursor = null ;
        ArrayList<String> result_str = new ArrayList<>() ;
        String result;
        int i=0;
        String[] string = new String[] {key_id ,key_tittle ,key_body ,key_date_time};

        try {
            cursor = rem_db.query(table_name ,string ,null ,null ,null ,null ,null);


        int id_col = cursor.getColumnIndex(key_id);
        int titl_col = cursor.getColumnIndex(key_tittle);


               result_str.add("ID"+"    "+"Tittle");

            for (cursor.moveToFirst() ;!cursor.isAfterLast() ;cursor.moveToNext())
            {
                result= cursor.getString(id_col) +"     " +cursor.getString(titl_col) +"  ";
                result_str.add(result);
            }

        } catch (CursorIndexOutOfBoundsException c)
        {
            c.printStackTrace();
        }

        return result_str ;
    }



     public String gettittle(long id_pos)   {

         Cursor cursor  = null;
         String Result = "";

         String[] string = new String[] {key_id ,key_tittle ,key_body ,key_date_time};
         try {
             cursor = rem_db.query(table_name, string, key_id + "=" + id_pos, null, null, null, null);
             if (cursor != null) {

                 int titl_col = cursor.getColumnIndex(key_tittle);
                 cursor.moveToFirst();
                 Result = cursor.getString(titl_col);
             }
         }catch (CursorIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }

        return Result ;

    }

    public String getbody(long id_pos) throws CursorIndexOutOfBoundsException  {
        String[] string = new String[] {key_id ,key_tittle ,key_body ,key_date_time};
        String Result = "";


        try {
            Cursor cursor = rem_db.query(table_name, string, key_id + "=" + id_pos, null, null, null, null);

            int body_col = cursor.getColumnIndex(key_body);
            if (cursor != null) {
                cursor.moveToFirst();
                Result = cursor.getString(body_col);
            }
        }catch (CursorIndexOutOfBoundsException c)
            {
                c.printStackTrace();
            }

        return Result ;

    }

    public String gettime(long id_pos) throws CursorIndexOutOfBoundsException {
        String[] string = new String[] {key_id ,key_tittle ,key_body ,key_date_time};
        Cursor cursor = null ;
        String Result = "";

        try {
            cursor = rem_db.query(table_name, string, key_id + "=" + id_pos, null, null, null, null);

            if (cursor != null) {

                int date_col = cursor.getColumnIndex(key_date_time);
                cursor.moveToFirst();
                Result = cursor.getString(date_col);
            }
        }catch (CursorIndexOutOfBoundsException c)
            {
                c.printStackTrace();
            }

        return Result ;

    }

    public int update_data(String title, String body, String task_date_time, long row_v) throws CursorIndexOutOfBoundsException {

        ContentValues cv = new ContentValues();
        cv.put(key_tittle,title);
        cv.put(key_body ,body);
        cv.put(key_date_time ,task_date_time);
       int id = 0;
        try {
            id = rem_db.update(table_name,cv,key_id + "=" + row_v,null);
        } catch (CursorIndexOutOfBoundsException c)
        {
            c.printStackTrace();
        }

         return id ;
    }

    public void delete_data(long id_pos2) throws CursorIndexOutOfBoundsException {


        try {
            rem_db.delete(table_name,key_id + "=" + id_pos2 ,null);
        } catch (CursorIndexOutOfBoundsException c)
        {
            c.printStackTrace();
        }


    }


    private class dbhelper extends SQLiteOpenHelper
    {

        public dbhelper(Context context) {
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

    public Remainder_db open() throws SQLException {

        mhelper = new dbhelper(dbcontext);
        rem_db = mhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mhelper.close();
    }


}
