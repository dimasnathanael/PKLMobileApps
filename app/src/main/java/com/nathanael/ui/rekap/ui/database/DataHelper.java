package com.nathanael.ui.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nathanael on 19-Feb-17.
 */

public class DataHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "PKLMobile.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub
        String sqlPKL = "CREATE TABLE pkl(email TEXT PRIMARY KEY, nama TEXT NULL, alamat TEXT NULL, nomorHp TEXT NULL, gender TEXT NULL, tglLahir TEXT NULL, produkUnggulan TEXT NULL);";
        String sqlProduk = "CREATE TABLE produk(namaProduk TEXT PRIMARY KEY, hargaPokok TEXT NULL, hargaJual TEXT NULL, email TEXT NULL);";
        String sqlTransaksi = "CREATE TABLE transaksi(id INTEGER PRIMARY KEY, namaProduk TEXT NULL, qtyJual TEXT NULL, tglJual TEXT NULL, email TEXT NULL, hargaPokok TEXT NULL, hargaJual TEXT NULL);";
        Log.d("Data", "onCreate: " + sqlPKL);
        Log.d("Data", "onCreate: " + sqlProduk);
        Log.d("Data", "onCreate: " + sqlTransaksi);
        db.execSQL(sqlPKL);
        db.execSQL(sqlProduk);
        db.execSQL(sqlTransaksi);
        sqlPKL = "INSERT INTO pkl (email, nama, alamat, nomorHp, gender, tglLahir, produkUnggulan) VALUES ('nathanael.silalahi@gmail.com', 'Dimas Nathanael', 'Jalan Bukit Resik 3 No.9, Ciumbuleuit, Bandung', '082120504611','Pria', '19951012', 'Sate Maranggi');";
        sqlProduk = "INSERT INTO produk (namaProduk, hargaPokok, hargaJual, email) VALUES ('Sate Maranggi', '12000', '17000','nathanael.silalahi@gmail.com');";
        sqlTransaksi = "INSERT INTO transaksi (id, namaProduk, qtyJual, tglJual, email, hargaPokok, hargaJual) VALUES (NULL, 'Sate Maranggi', '10', '20170221','nathanael.silalahi@gmail.com', '12000', '17000');";
        db.execSQL(sqlPKL);
        db.execSQL(sqlProduk);
        db.execSQL(sqlTransaksi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {
        // TODO Auto-generated method stub
    }
}