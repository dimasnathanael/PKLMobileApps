package com.nathanael.ui.produk;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nathanael.ui.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by nathanael on 19-Feb-17.
 */

public class DetilProduk extends AppCompatActivity
{
    String NAMESPACE = "http://schemas.xmlsoap.org/wsdl";
    String URL = "http://webtest.unpar.ac.id/pklws/pkl.php?wsdl";

    String SOAP_ACTION = "getproduk";
    String METHOD_NAME = "getproduk";

    String SID, NAMAPRODUK;

    Button back;
    TextView namaProduk, hargaPokok, hargaJual;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.fproduk);

        namaProduk = (TextView) findViewById(R.id.textView1);
        hargaPokok = (TextView) findViewById(R.id.textView2);
        hargaJual = (TextView) findViewById(R.id.textView3);

        Bundle b = getIntent().getExtras();
        SID = b.getString("SID");
        NAMAPRODUK = b.getString("namaProduk");

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("ssid", SID);
        request.addProperty("namaproduk", NAMAPRODUK);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject resultRequestSOAP = (SoapObject) envelope.bodyIn;
            String temp = resultRequestSOAP.toString();

            //            getprodukResponse{return=("Bakso Malang","15000","16000")}

            String result = temp.substring(27, temp.length()-5);
            String[] arr = result.split("\",\"");

            namaProduk.setText(arr[0]);
            hargaPokok.setText(arr[1]);
            hargaJual.setText(arr[2]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
