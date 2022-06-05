package com.example.hastanerandevu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "hastane_randevu";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String TABLE_RANDEVU = "users_randevu";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "adsoyad";
    private static final String KEY_PHONE = "telefon";
    private static final String KEY_PASSWORD = "sifre";
    private static final String KEY_IL = "il";
    private static final String KEY_ILCE = "ilce";
    private static final String KEY_HASTANE = "hastane";
    private static final String KEY_BOLUM = "bolum";
    private static final String KEY_DOKTOR = "doktor";
    private static final String KEY_TARIH = "tarih";
    private static final String KEY_HASTA = "hasta";

    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT ," + KEY_PASSWORD + " TEXT);";

    private static final String CREATE_TABLE_RANDEVU = "CREATE TABLE "
            + TABLE_RANDEVU + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IL + " TEXT," + KEY_ILCE + " TEXT ," + KEY_HASTANE + " TEXT," + KEY_BOLUM + " TEXT," + KEY_DOKTOR + " TEXT," + KEY_TARIH + " TEXT," + KEY_HASTA + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_RANDEVU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_RANDEVU + "'");
        onCreate(db);
    }

    //KAYIT İŞLEMİ YAPAR
    public boolean registerUser(String username , String phone , String password){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME , username);
        values.put(KEY_PHONE , phone);
        values.put(KEY_PASSWORD , password);

        long result = db.insert(TABLE_USER , null , values);
        if(result == -1)
            return false;
        else
            return true;
    }

    //ARAMA İŞLEMİ YAPAR
    public boolean checkUser(String username , String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = { KEY_ID };
        String selection = KEY_NAME + "=?" + " and " + KEY_PASSWORD + "=?";
        String [] selectionargs = { username , password};
        Cursor cursor = db.query(TABLE_USER , columns , selection ,selectionargs , null , null , null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if (count > 0)
            return true;
        else
            return false;
    }

    //KAYIT İŞLEMİ YAPAR
    public boolean RandevuAl(String sehir,String ilce,String hastane,String bolum,String doktor,String tarih,String hasta){
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_IL , sehir);
        values.put(KEY_ILCE , ilce);
        values.put(KEY_HASTANE, hastane);
        values.put(KEY_BOLUM, bolum);
        values.put(KEY_DOKTOR , doktor);
        values.put(KEY_TARIH , tarih);
        values.put(KEY_HASTA , hasta);

        long result=db.insert(TABLE_RANDEVU,null,values);
        if(result == -1)
            return false;
        else
            return true;
    }

    //ARAMA VE LİSTELEME İŞLEMİ YAPAR
    public String[] RandevuListele(TextView kisiBilgi, String name, Spinner randevuNoSpinner) {
        //ARAMA
        Cursor cursor=this.getReadableDatabase().rawQuery("select * from "+TABLE_RANDEVU+" where "+KEY_HASTA+"='"+name+"'",null);

        String[] randevuNolar=new String[cursor.getCount()];

        kisiBilgi.setText("");
        int i=0;
        while (cursor.moveToNext()) {
            randevuNolar[i]=cursor.getString(0);
            i++;
            //LİSTELEME
            kisiBilgi.append("\nRANDEVU NO: "+ cursor.getString(0) +"\nHASTANE: "+ cursor.getString(3) + "  " + "\nBÖLÜM: " +  cursor.getString(4) + "  " + "\nDOKTOR: " + cursor.getString(5) +"\nTARİH: "+cursor.getString(6)+ "\n\n");
        }
        return  randevuNolar;
    }

    //SİLME İŞLEMİ YAPAR
    public void RandevuSil(int id){
        this.getWritableDatabase().delete(TABLE_RANDEVU,KEY_ID+"="+id,null);
    }
}