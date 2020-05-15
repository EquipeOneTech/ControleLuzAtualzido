package com.equipeonetech.apptest.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Controle.db";
    public static final String TABLE_HISTORICO = "Controle_table";
    public static final String TABLE_VALOR_RECOMENDADO = "Valor_recomendado";

    /**TABLE Controle_table*/
    public static final String COL_1 = "ID";
    public static final String COL_2 = "MEDIDA_ANTERIOR";
    public static final String COL_3 = "VALOR_ESTIMADO";
    public static final String COL_4 = "DATA";

    /**TABLE Valor_recomendado*/
    public static final String COL_1_ID_RECOMEND = "ID_RECOMEND";
    public static final String COL_2_RECOMEND = "VALOR_RECOMENDADO";
    public static final String COL_3_RECOMEND = "DATA";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_HISTORICO + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, MEDIDA_ANTERIOR TEXT, VALOR_ESTIMADO TEXT, DATA TEXT)");
        db.execSQL("CREATE TABLE "+ TABLE_VALOR_RECOMENDADO + "(ID_RECOMEND INTEGER PRIMARY KEY AUTOINCREMENT, VALOR_RECOMENDADO TEXT, DATA TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORICO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_VALOR_RECOMENDADO);
        onCreate(db);
    }
    public boolean insertDataRecomendTable(String valorRecommended, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_RECOMEND,valorRecommended);
        contentValues.put(COL_3_RECOMEND,data);
        long result = db.insert(TABLE_VALOR_RECOMENDADO, null, contentValues);
        db.close();

        if (result==-1){
            return false;
        }else {
            return true;
        }
    }
    public boolean insertDataControleTable(String medida, String valor, String data){
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



    public Cursor getAllDataControleTable(){
        SQLiteDatabase db = this.getWritableDatabase ();
        Cursor res = db.rawQuery ("Select * from "+TABLE_HISTORICO,null);
        return res;
    }

    public void deleteAllValueRecommend(){
        SQLiteDatabase db = this.getWritableDatabase ();
        db.delete(TABLE_VALOR_RECOMENDADO,null, null);
    }

    public void deleteCalculos(String value){
        SQLiteDatabase db = this.getWritableDatabase ();
        db.execSQL("DELETE FROM "+ TABLE_HISTORICO + " WHERE ID = (SELECT ID FROM "+ TABLE_HISTORICO +" WHERE "+ COL_2 +" LIKE " +value+")");

    }

    public Cursor getAllDataRecomendTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        //SELECT *FROM usuario WHERE _id = (SELECT MAX( _id ) FROM usuario);

        Cursor res = db.rawQuery("Select * from "+TABLE_VALOR_RECOMENDADO, null);
        return res;
    }
}

