package com.example.kesha.weddingcard1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by keshav on 01-05-2018.
 */

public class Databasehelper extends SQLiteOpenHelper {

    public static final String Database_name="Reminderfi40.db";
    public static final String Table_name="Reminder";
    public static final String COL_1="ID";
    public static final String COL_2="Category";
    public static final String COL_3="Event_name";
    public static final String COL_4="Longitude";
    public static final String COL_5="Latitude";
    public static final String COL_6="Start_Date";
    public static final String COL_7="End_Date";
    public static final String COL_8="start_time";
    public static final String COL_9="end_time";
    public static final String COL_10="cnt0";
    public Databasehelper(Context context) {
        super(context, Database_name,null,1 );

//        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("create table " + Table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Category TEXT,Event_name TEXT,Longitude TEXT,Latitude TEXT,Start_Date TEXT,End_Date TEXT,start_time TEXT,end_time TEXT,cnt0 INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    public boolean insertdata(String [] s)
    {
        SQLiteDatabase db=this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(COL_2, s[0]);
            cv.put(COL_3, s[1]);
            cv.put(COL_4, s[2]);
            cv.put(COL_5, s[3]);
            cv.put(COL_6, s[4]);
            cv.put(COL_7, s[5]);
            cv.put(COL_8, s[6]);
            cv.put(COL_9, s[7]);
            cv.put(COL_10,s[8]);
        long result1 = db.insert(Table_name, null, cv);

        if(result1==-1)
        {
            return false;
        }
        else
            return true;
    }

    public Cursor getAlldata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cr=db.rawQuery("select * from "+Table_name,null);
        return cr;
    }

    public Cursor getonedata(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor cr=db.rawQuery("select * from "+Table_name+" where ID="+id,null);

        return cr;
    }
    public Boolean updatecnt(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        int j=1;

        ContentValues cv = new ContentValues();
        cv.put(COL_10,1);
        int b=db.update(Table_name,cv,"ID="+id,null);
        //db.rawQuery("Update "+Table_name+" Set cnt0 = '" + j + "' where ID="+id,null);
        if(b==-1)
        {
            return false;
        }
        else
            return true;
    }
    public Cursor getdatetime() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("select ID,Start_Date,start_time from " + Table_name, null);
        return cr;
    }

    public Cursor getcnt(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("select * from " + Table_name+" where ID="+id, null);
        return cr;
    }

    public Boolean changecnt()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_10,0);

        //db.rawQuery("Update "+Table_name+" Set cnt0="+g, null);
        int b=db.update(Table_name,cv,null,null);
        if(b==-1)
        {
            return false;
        }
        else
            return true;
    }

}
