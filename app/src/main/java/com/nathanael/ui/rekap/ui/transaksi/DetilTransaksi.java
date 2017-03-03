package com.nathanael.ui.transaksi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nathanael.ui.R;
import com.nathanael.ui.database.DataHelper;

/**
 * Created by nathanael on 19-Feb-17.
 */

public class DetilTransaksi extends AppCompatActivity
{
    protected Cursor cursor;
    DataHelper dbHelper;
    Button back;
    TextView namaProduk, qtyJual, tglJual;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fdetiltransaksi);

        dbHelper = new DataHelper(this);
        namaProduk = (TextView) findViewById(R.id.textViewProductNameSelect);
        qtyJual = (TextView) findViewById(R.id.textViewQtySellSelect);
        tglJual = (TextView) findViewById(R.id.textViewSelectDate);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transaksi WHERE id = '" + getIntent().getStringExtra("id") + "' AND email = '"+getIntent().getStringExtra("email")+"'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            namaProduk.setText(cursor.getString(1).toString());
            qtyJual.setText(cursor.getString(2).toString());
            tglJual.setText(cursor.getString(3).toString());
        }
        back = (Button) findViewById(R.id.buttonBackToCatalog);
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
