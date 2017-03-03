package com.nathanael.ui.transaksi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nathanael.ui.R;
import com.nathanael.ui.database.DataHelper;

import java.util.Calendar;

/**
 * Created by nathanael on 19-Feb-17.
 */

public class EditTransaksi extends AppCompatActivity
{
    protected Cursor cursor;
    DataHelper dbHelper;
    Button save, back;
    EditText namaProduk, qtyJual;
    TextView tglJual;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fedittransaksi);

        dbHelper = new DataHelper(this);
        namaProduk = (EditText) findViewById(R.id.editTextProductName);
        qtyJual = (EditText) findViewById(R.id.editTextQtySell);
        tglJual = (TextView) findViewById(R.id.textViewSelectDate);
        save = (Button) findViewById(R.id.buttonSaveTransaction);
        back = (Button) findViewById(R.id.buttonBack);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transaksi WHERE id = '" + getIntent().getStringExtra("id") + "' AND email = '"+getIntent().getStringExtra("email")+"'",null);
        cursor.moveToFirst();
        String tempID="";
        if (cursor.getCount()>0)
        {
            tempID = cursor.getString(0).toString();
            cursor.moveToPosition(0);
            namaProduk.setText(cursor.getString(1).toString());
            qtyJual.setText(cursor.getString(2).toString());
            tglJual.setText(cursor.getString(3).toString());
        }
        save = (Button) findViewById(R.id.buttonSaveTransaction);
        back = (Button) findViewById(R.id.buttonBack);
        // daftarkan even onClick pada btnSimpan
        final String finalID = tempID;
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE transaksi SET namaProduk='"+
                        namaProduk.getText().toString() +"', qtyJual='" +
                        qtyJual.getText().toString()+"', tglJual='"+
                        tglJual.getText().toString()+ "' WHERE id='" +
                        finalID+ "' AND email = '"+getIntent().getStringExtra("email")+"'");
                Toast.makeText(getApplicationContext(), "Berhasil mengedit transaksi!", Toast.LENGTH_LONG).show();
                KatalogTransaksi.ma.RefreshList();
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
