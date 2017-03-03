package com.nathanael.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nathanael.ui.produk.KatalogProduk;
import com.nathanael.ui.register.KatalogUser;
import com.nathanael.ui.rekap.Rekap;
import com.nathanael.ui.transaksi.KatalogTransaksi;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by nathanael on 09-Feb-17.
 */
public class Menu extends ActionBarActivity
{
    String NAMESPACE = "http://schemas.xmlsoap.org/wsdl";
    String URL = "http://webtest.unpar.ac.id/pklws/pkl.php?wsdl";

    String SOAP_ACTION = "getpkl";
    String METHOD_NAME = "getpkl";

    String SID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.fmenu);

        TextView textWelcome = (TextView) findViewById(R.id.textViewWelcome);

        Bundle b = getIntent().getExtras();
        SID = b.getString("SID");

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("ssid", SID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject resultRequestSOAP = (SoapObject) envelope.bodyIn;
            String temp = resultRequestSOAP.toString();

            //            getpklResponse{return=("nathanael.silalahi@gmail.com","Dimas","Ciumbuleuit","082120504611","19951012","Bakso")}

            String result = temp.substring(24, temp.length()-5);
            String[] arr = result.split("\",\"");
//            Toast.makeText(getApplicationContext(), arr[0], Toast.LENGTH_LONG).show();

            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("fullname", arr[1]);
            editor.putString("address", arr[2]);
            editor.putString("phone", arr[3]);
            editor.putString("birthdate", arr[4]);
            editor.putString("bestproduct", arr[5]);
            editor.apply();

            textWelcome.setText(arr[1]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Button btnTampilKatalog = (Button) findViewById(R.id.buttonShowCatalog);
        btnTampilKatalog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent intent = new Intent(com.nathanael.ui.Menu.this, KatalogProduk.class);
                Bundle bundl = new Bundle ();
                bundl.putString("SID", SID);
                intent.putExtras(bundl);
                startActivity(intent);
            }
        });

        Button btnTampilTransaksi = (Button) findViewById(R.id.buttonShowTransaction);
        btnTampilTransaksi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent intent = new Intent(com.nathanael.ui.Menu.this, KatalogTransaksi.class);
                Bundle bundl = new Bundle ();
                bundl.putString("SID", SID);
                intent.putExtras(bundl);
                startActivity(intent);
            }
        });

        Button btnTampilRekap = (Button) findViewById(R.id.buttonShowRekap);
        btnTampilRekap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent intent = new Intent(com.nathanael.ui.Menu.this, Rekap.class);
                Bundle bundl = new Bundle ();
                bundl.putString("SID", SID);
                intent.putExtras(bundl);
                startActivity(intent);
            }
        });

        Button btnTampilUser = (Button) findViewById(R.id.buttonShowUser);
        btnTampilUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent intent = new Intent(com.nathanael.ui.Menu.this, KatalogUser.class);
                startActivity(intent);
            }
        });

        Button btnExit = (Button) findViewById(R.id.buttonExit);
        btnExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent i = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        getMenuInflater().inflate(R.menu.katalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.action_profile)
        {
            Toast.makeText(com.nathanael.ui.Menu.this, "Profil Saya", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_settings)
        {
            Toast.makeText(com.nathanael.ui.Menu.this, "Setting", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_exit)
        {
            Toast.makeText(com.nathanael.ui.Menu.this, "Exit", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
