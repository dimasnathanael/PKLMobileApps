package com.nathanael.ui.register;

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

public class DetilUser extends AppCompatActivity
{
    protected Cursor cursor;
    DataHelper dbHelper;
    Button back;
    TextView email, nama, alamat, nomorHp, gender, tglLahir, produkUnggulan;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fdetiluser);

        dbHelper = new DataHelper(this);
        email = (TextView) findViewById(R.id.textView1);
        nama = (TextView) findViewById(R.id.textView2);
        alamat = (TextView) findViewById(R.id.textView3);
        nomorHp = (TextView) findViewById(R.id.textView4);
        gender = (TextView) findViewById(R.id.textView5);
        tglLahir = (TextView) findViewById(R.id.textView6);
        produkUnggulan = (TextView) findViewById(R.id.textView7);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM pkl WHERE email = '" +
                getIntent().getStringExtra("email") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            email.setText(cursor.getString(0).toString());
            nama.setText(cursor.getString(1).toString());
            alamat.setText(cursor.getString(2).toString());
            nomorHp.setText(cursor.getString(3).toString());
            gender.setText(cursor.getString(4).toString());
            tglLahir.setText(cursor.getString(5).toString());
            produkUnggulan.setText(cursor.getString(6).toString());
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
