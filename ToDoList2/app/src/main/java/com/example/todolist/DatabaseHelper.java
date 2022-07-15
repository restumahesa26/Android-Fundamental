package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Daftar_Barang";
    private static final String COL1 = "ID";
    private static final String COL2 = "Nama";
    private static final String COL3 = "HargaBeli";
    private static final String COL4 = "Satuan";
    private static final String COL5 = "HargaJual";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("                + COL1 + " integer primary key, "                + COL2 + " TEXT, "                + COL3 + " INTEGER, "                + COL4 + " TEXT, "                + COL5 + " INTEGER" + ")";
        Log.d(TAG, "Creating table " + createTable);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nama_barang, Integer harga_beli, String satuan, Integer harga_jual) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, nama_barang);
        contentValues.put(COL3, harga_beli);
        contentValues.put(COL4, satuan);
        contentValues.put(COL5, harga_jual);
        Log.d(TAG, "insertData: Inserting " + nama_barang + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL1 + "=" + id, null);
    }

    public ArrayList<ModelData> getAllData() {
        ArrayList<ModelData> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nama_barang = cursor.getString(1);
            Integer harga_beli = cursor.getInt(2);
            String satuan = cursor.getString(3);
            Integer harga_jual = cursor.getInt(4);
            ModelData modelData = new ModelData(id, nama_barang, harga_beli, satuan, harga_jual);
            arrayList.add(modelData);
        }
        db.close();
        return arrayList;
    }
}
