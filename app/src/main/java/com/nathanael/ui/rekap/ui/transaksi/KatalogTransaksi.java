package com.nathanael.ui.transaksi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.nathanael.ui.R;
import com.nathanael.ui.database.DataHelper;

public class KatalogTransaksi extends AppCompatActivity
{
    String[] daftar, daftarID;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static com.nathanael.ui.transaksi.KatalogTransaksi ma;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ftransaksi);

        Button btnTambahProduk=(Button)findViewById(R.id.butonAddTransaction);

        final Bundle b = getIntent().getExtras();

        btnTambahProduk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                Intent inte = new Intent(com.nathanael.ui.transaksi.KatalogTransaksi.this, TambahTransaksi.class);
                inte.putExtra("email", b.getString("email"));
                startActivity(inte);
            }
        });


        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();
    }

    public void RefreshList()
    {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        bundle = getIntent().getExtras();
        cursor = db.rawQuery("SELECT * FROM transaksi WHERE email='"+bundle.getString("email")+"'",null);
        daftar = new String[cursor.getCount()];
        daftarID = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++)
        {
            cursor.moveToPosition(cc);
            daftarID[cc] = cursor.getString(0).toString();
            daftar[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView)findViewById(R.id.listTransaksi);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3)
            {
                final String selection = daftarID[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat Transaksi", "Update Transaksi", "Hapus Transaksi"};
                AlertDialog.Builder builder = new AlertDialog.Builder(com.nathanael.ui.transaksi.KatalogTransaksi.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        switch(item)
                        {
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), DetilTransaksi.class);
                                i.putExtra("id", selection);
                                i.putExtra("email", bundle.getString("email"));
                                startActivity(i);
                                break;
                            case 1 :
                                Intent in = new Intent(getApplicationContext(), EditTransaksi.class);
                                in.putExtra("id", selection);
                                in.putExtra("email", bundle.getString("email"));
                                startActivity(in);
                                break;
                            case 2 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM transaksi WHERE id = '"+selection+"' AND email = '"+bundle.getString("email")+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}