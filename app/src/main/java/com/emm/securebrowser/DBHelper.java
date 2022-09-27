package com.emm.securebrowser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"FavDetails.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table FavDetails(url TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists FavDetails");
    }

    public Boolean insertuserdata(String url){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("url",url);
        long result=DB.insert("FavDetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean deletedata (String url) {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("FavDetails", "url=?", new String[]{url});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
    }
    public ArrayList<String> getdata(){
        ArrayList<String> dataArray = new ArrayList<>();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from FavDetails", null);
        while (cursor.moveToNext()){
            dataArray.add(cursor.getString(0));
        }


        return dataArray;
    }

}
