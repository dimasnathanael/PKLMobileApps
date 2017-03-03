package com.nathanael.ui.produk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nathanael.ui.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by nathanael on 19-Feb-17.
 */

public class SyncProduk extends AppCompatActivity
{
    String NAMESPACE = "http://schemas.xmlsoap.org/wsdl";
    String URL = "http://webtest.unpar.ac.id/pklws/pkl.php?wsdl";

    String SOAP_ACTION = "regproduk";
    String METHOD_NAME = "regproduk";

    Button save, back;
    EditText namaProduk, hargaPokok, hargaJual;

    String SID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ftambahproduk);

        Bundle b = getIntent().getExtras();
        SID = b.getString("SID");

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
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("sid", SID);
                request.addProperty("namaproduk", namaProduk.getText().toString());
                request.addProperty("hargapokok", hargaPokok.getText().toString());
                request.addProperty("hargajual", hargaJual.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try
                {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    SoapObject resultRequestSOAP = (SoapObject) envelope.bodyIn;
                    String temp = resultRequestSOAP.toString();

//                    regprodukResponse{return=("Bakso Malang","diregistrasi");}

                    String result = temp.substring(27, temp.length()-5);
                    String[] arr = result.split("\",\"");

                    if(arr[1].equalsIgnoreCase("diregistrasi"))
                    {
                        Toast.makeText(getApplicationContext(), "Produk berhasil ditambahkan!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Maaf produk tidak berhasil ditambahkan!",Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
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