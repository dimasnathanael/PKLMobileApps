package com.nathanael.ui.transaksi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nathanael.ui.R;
import com.nathanael.ui.database.DataHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nathanael on 19-Feb-17.
 */

public class TambahTransaksi extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    protected Cursor cursor;
    DataHelper dbHelper;
    Button save, back;
//    EditText namaProduk, qtyJual;
    EditText qtyJual;
    Spinner spinnerProduct;
    String tglJual;


    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ftambahtransaksi);

        dbHelper = new DataHelper(this);
        spinnerProduct = (Spinner) findViewById(R.id.spinnerProductItem);
        qtyJual = (EditText) findViewById(R.id.editTextQtySell);
        save = (Button) findViewById(R.id.buttonSaveTransaction);
        back = (Button) findViewById(R.id.buttonBack);

        spinnerProduct.setOnItemSelectedListener(com.nathanael.ui.transaksi.TambahTransaksi.this);
        // Loading spinner data from database
        loadSpinnerData();

        //DATEPICKER
        dateView = (TextView) findViewById(R.id.textViewSelectDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        save.setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                String hargaPokok = "";
                String hargaJual = "";

                SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
                cursor = dbRead.rawQuery("SELECT hargaPokok, hargaJual FROM produk WHERE namaProduk = '"+spinnerProduct.getSelectedItem()+"' AND email = '"+getIntent().getStringExtra("email")+"'",null);
                cursor.moveToFirst();
                for (int cc=0 ; cc < cursor.getCount() ; cc++)
                {
                    cursor.moveToPosition(0);
                    hargaPokok=cursor.getString(0).toString();
                    hargaJual=cursor.getString(1).toString();
                }

                // TODO Auto-generated method stub
                SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
                Bundle b = getIntent().getExtras();

                dbWrite.execSQL("INSERT INTO transaksi(id, namaProduk, qtyJual, tglJual, email, hargaPokok, hargaJual) VALUES(NULL, '" +
                        spinnerProduct.getSelectedItem()+"','"+
                        qtyJual.getText().toString() +"','" +
                        tglJual+"','" +
                        b.getString("email")+"','" +
                        hargaPokok+"','" +
                        hargaJual+ "')");
                Toast.makeText(getApplicationContext(), "Berhasil menambahkan transaksi!", Toast.LENGTH_LONG).show();
                KatalogTransaksi.ma.RefreshList();
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

    @SuppressWarnings("deprecation")
    public void setDate(View view)
    {
        showDialog(999);
        //akan menampilkan teks ketika kalendar muncul setelah menekan tombol
        Toast.makeText(getApplicationContext(), "Pilih Tangal", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        // TODO Auto-generated method stub
        if (id == 999)
        {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3)
        {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            tglJual=arg1+""+(arg2+1)+""+arg3;
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day)
    {
        dateView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    private void loadSpinnerData()
    {
        // database handler
        DataHelper db = new DataHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = this.getAllProductLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerProduct.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Anda memilih: " + label, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO Auto-generated method stub
    }

    public List<String> getAllProductLabels()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Bundle bundle = getIntent().getExtras();

        List<String> labels;
        Cursor cursor;

        cursor = db.rawQuery("SELECT namaProduk FROM produk WHERE email = '"+bundle.getString("email")+"'",null);

        cursor.moveToFirst();
        labels = new ArrayList<String>();
        for (int cc=0; cc < cursor.getCount(); cc++)
        {
            cursor.moveToPosition(cc);
            labels.add(cursor.getString(0).toString());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}