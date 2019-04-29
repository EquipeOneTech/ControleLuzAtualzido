package com.example.medidor.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Controle.db";
    public static final String TABLE_HISTORICO = "Controle_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "MEDIDA_ANTERIOR";
    public static final String COL_3 = "VALOR_ESTIMADO";
    public static final String COL_4 = "DATA";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_HISTORICO + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, MEDIDA_ANTERIOR TEXT, VALOR_ESTIMADO TEXT, DATA TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORICO);
    }

    public boolean insertData(String medida, String valor, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_2,medida);
        contentValues.put(COL_3,valor);
        contentValues.put(COL_4,data);
        long result = db.insert(TABLE_HISTORICO,null,contentValues);
        db.close();

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase ();
        Cursor res = db.rawQuery ("Select * from "+TABLE_HISTORICO,null);
        return res;
    }
}
