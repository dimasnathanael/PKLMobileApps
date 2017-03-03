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

public class TambahProduk extends AppCompatActivity
{
    protected Cursor cursor;
    DataHelper dbHelper;
    Button save, back;
    EditText namaProduk, hargaPokok, hargaJual;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ftambahproduk);

        dbHelper = new DataHelper(this);
        namaProduk = (EditText) findViewById(R.id.editTextProductName);
        hargaPokok = (EditText) findViewById(R.id.editTextProductPrice);
        hargaJual = (EditText) findViewById(R.id.editProductSellPrice);
        save = (Button) findViewById(R.id.buttonSaveProduct);
        back = (Button) findViewById(R.id.buttonBack);

        save.setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Bundle bundle = getIntent().getExtras();

                db.execSQL("INSERT INTO produk(namaProduk, hargaPokok, hargaJual, email) VALUES ('" +
                        namaProduk.getText().toString()+"','"+
                        hargaPokok.getText().toString() +"','" +
                        hargaJual.getText().toString() + "','" +
                        bundle.getString("email")+ "')");
                Toast.makeText(getApplicationContext(), "Berhasil menambahkan produk!", Toast.LENGTH_LONG).show();
                KatalogProduk.ma.RefreshList();
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
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