package com.nathanael.ui.rekap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nathanael.ui.R;
import com.nathanael.ui.database.DataHelper;

public class Rekap extends AppCompatActivity
{
    String[] daftar, arrProduk;
    Menu menu;
    protected Cursor cursorTransaksi, cursorProduk;
    DataHelper dbcenter;
    public static com.nathanael.ui.rekap.Rekap ma;
    Bundle bundle;
    TextView tvSumTotal;
    int sum=0;
    int totalSum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frekap);

        final Bundle b = getIntent().getExtras();

        ma = this;
        dbcenter = new DataHelper(this);
        this.init();
    }

    public void init()
    {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        bundle = getIntent().getExtras();
        cursorTransaksi = db.rawQuery("SELECT * FROM transaksi WHERE email='"+bundle.getString("email")+"'",null);
        daftar = new String[cursorTransaksi.getCount()];

        //BIKIN TABEL
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableList);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.GRAY);


        TextView tv0 = new TextView(this);
        tv0.setText("Tanggal");
        tv0.setTextColor(Color.WHITE);
        tv0.setTextSize(16);
//        tv0.setGravity(Gravity.CENTER);
        tv0.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText("Nama Produk");
        tv1.setTextColor(Color.WHITE);
        tv1.setTextSize(16);
//        tv1.setGravity(Gravity.CENTER);
        tv1.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("Kuantitas");
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(16);
//        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText("Harga Jual");
        tv3.setTextColor(Color.WHITE);
        tv3.setTextSize(16);
//        tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText("Pendapatan");
        tv4.setTextColor(Color.WHITE);
        tv4.setTextSize(16);
//        tv4.setGravity(Gravity.CENTER);
        tv4.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv4);

        tableLayout.addView(tbrow0);


        cursorTransaksi.moveToFirst();
        for (int cc=0; cc < cursorTransaksi.getCount(); cc++)
        {
            cursorTransaksi.moveToPosition(cc);

            TableRow tbrow = new TableRow(this);

            TextView tanggalRow = new TextView(this);
            tanggalRow.setText(cursorTransaksi.getString(3).toString());
            tanggalRow.setTextSize(16);
            tanggalRow.setPadding(20, 10, 20, 10);
            tbrow.addView(tanggalRow);

            TextView namaProdukRow = new TextView(this);
            namaProdukRow.setText(cursorTransaksi.getString(1).toString());
            namaProdukRow.setTextSize(16);
            namaProdukRow.setPadding(20, 10, 20, 10);
            tbrow.addView(namaProdukRow);

            TextView kuantitasRow = new TextView(this);
            kuantitasRow.setText(cursorTransaksi.getString(2).toString());
            kuantitasRow.setTextSize(16);
            kuantitasRow.setPadding(20, 10, 20, 10);
            tbrow.addView(kuantitasRow);

            TextView hargaJualRow = new TextView(this);
            hargaJualRow.setText(cursorTransaksi.getString(6).toString());
            hargaJualRow.setTextSize(16);
            hargaJualRow.setPadding(20, 10, 20, 10);
            tbrow.addView(hargaJualRow);

            TextView pendapatanRow = new TextView(this);
            sum = (Integer.parseInt(cursorTransaksi.getString(2).toString())*Integer.parseInt(cursorTransaksi.getString(6).toString()));
            pendapatanRow.setTextSize(16);
            pendapatanRow.setPadding(20, 10, 20, 10);
            totalSum+=sum;
            pendapatanRow.setText(sum + "");
            tbrow.addView(pendapatanRow);

            tableLayout.addView(tbrow);
        }

        tvSumTotal = (TextView) findViewById(R.id.textViewSumTotal);
        tvSumTotal.setText(totalSum + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}