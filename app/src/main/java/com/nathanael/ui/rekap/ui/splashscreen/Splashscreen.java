package com.nathanael.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.nathanael.ui.Login;
import com.nathanael.ui.R;

public class Splashscreen extends AppCompatActivity
{

    //Set waktu lama splashscreen
    private static int splashInterval = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fsplashawal);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(com.nathanael.ui.splashscreen.Splashscreen.this, Login.class);
                startActivity(i); // menghubungkan activity splashscren ke main activity dengan intent


                //jeda selesai Splashscreen
                this.finish();
            }

            private void finish()
            {
                // TODO Auto-generated method stub
            }
        }, splashInterval);
    };
}
