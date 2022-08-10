package edu.cseju.example.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context ) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table Userdetails(name text primary key, contact text, dob text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists Userdetails");
    }

    public boolean insertUserData(String name, String contact, String dob)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1)
            return false;
        else return true;
    }


    public boolean updateUserData(String name, String contact, String dob)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

        Cursor cursor = DB.rawQuery("select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount()>0)
        {
            long result = DB.update("Userdetails", contentValues, "name=?", new String[] {name});
            if (result == -1)
                return false;
            else return true;
        }
        return false;
    }

    public boolean deleteUserData(String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount()>0)
        {
            long result = DB.delete("Userdetails","name=?",new String[]{name});
            if (result == -1)
                return false;
            else return true;
        }
        else return false;
    }

    public Cursor getData()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Userdetails", null);
        return cursor;
    }

}
