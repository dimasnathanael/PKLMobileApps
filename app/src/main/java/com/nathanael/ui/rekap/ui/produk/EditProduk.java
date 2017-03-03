package com.nathanael.ui.produk;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nathanael.ui.R;
import com.nathanael.ui.database.DataHelper;

/**
 * Created by nathanael on 19-Feb-17.
 */

public class EditProduk extends AppCompatActivity
{
    protected Cursor cursor;
    DataHelper dbHelper;
    Button save, back;
    EditText namaProduk, hargaPokok, hargaJual;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feditproduk);

        dbHelper = new DataHelper(this);
        namaProduk = (EditText) findViewById(R.id.editTextProductName);
        hargaPokok = (EditText) findViewById(R.id.editTextProductPrice);
        hargaJual = (EditText) findViewById(R.id.editProductSellPrice);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Bundle bundle = getIntent().getExtras();
        cursor = db.rawQuery("SELECT * FROM produk WHERE namaProduk = '" + getIntent().getStringExtra("namaProduk") + "' AND email = '"+bundle.getString("email")+"'",null);
        cursor.moveToFirst();
        String tempNama="";
        if (cursor.getCount()>0)
        {
            tempNama = cursor.getString(0).toString();
            cursor.moveToPosition(0);
            namaProduk.setText(cursor.getString(0).toString());
            hargaPokok.setText(cursor.getString(1).toString());
            hargaJual.setText(cursor.getString(2).toString());
        }
        save = (Button) findViewById(R.id.buttonSaveProduct);
        back = (Button) findViewById(R.id.buttonBack);
        // daftarkan even onClick pada btnSimpan
        final String finalNama=tempNama;
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE produk SET namaProduk='"+
                        namaProduk.getText().toString() +"', hargaPokok='" +
                        hargaPokok.getText().toString()+"', hargaJual='"+
                        hargaJual.getText().toString()+ "' where namaProduk='" +
                        finalNama+ "' AND email = '"+bundle.getString("email")+"'");
                Toast.makeText(getApplicationContext(), "Berhasil mengedit produk!", Toast.LENGTH_LONG).show();
                KatalogProduk.ma.RefreshList();
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
