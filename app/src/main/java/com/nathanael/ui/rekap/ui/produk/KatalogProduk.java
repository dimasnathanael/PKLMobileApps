package com.nathanael.ui.produk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nathanael.ui.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class KatalogProduk extends AppCompatActivity
{
    String NAMESPACE = "http://schemas.xmlsoap.org/wsdl";
    String URL = "http://webtest.unpar.ac.id/pklws/pkl.php?wsdl";

    String SOAP_ACTION = "getkatalog";
    String METHOD_NAME = "getkatalog";

    String[] daftar;
    ListView ListView01;
    Menu menu;
    public static com.nathanael.ui.produk.KatalogProduk ma;
    Bundle bundle;

    String SID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.fkatalog);

        Button btnTambahProduk=(Button)findViewById(R.id.butonAddProduct);

        final Bundle b = getIntent().getExtras();

        btnTambahProduk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                Intent inte = new Intent(com.nathanael.ui.produk.KatalogProduk.this, SyncProduk.class);
                inte.putExtra("SID", b.getString("SID"));
                startActivity(inte);
            }
        });

        ma = this;
        RefreshList();
    }

    public void RefreshList()
    {
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

            //            getkatalogResponse{return=("Bakso Babi"),("bakso goreng"),("Bakso Malang"),("Bakso Urat");}

            String result = temp.substring(28, temp.length()-5);
            daftar = result.split("\"\\),\\(\"");

//            Toast.makeText(getApplicationContext(), daftar[0], Toast.LENGTH_LONG).show();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        ListView01 = (ListView)findViewById(R.id.listProduk);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3)
            {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat Produk", "Update Produk", "Hapus Produk"};
                AlertDialog.Builder builder = new AlertDialog.Builder(com.nathanael.ui.produk.KatalogProduk.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        switch(item)
                        {
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), DetilProduk.class);
                                i.putExtra("namaProduk", selection);
                                i.putExtra("SID", SID);
                                startActivity(i);
                                break;
                            case 1 :
                                Intent in = new Intent(getApplicationContext(), EditProduk.class);
                                in.putExtra("namaProduk", selection);
                                in.putExtra("SID", SID);
                                startActivity(in);
                                break;
                            case 2 :
                                String SOAP_ACTION2 = "delproduk";
                                String METHOD_NAME2 = "delproduk";

                                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                                request.addProperty("ssid", SID);
                                request.addProperty("namaproduk", selection);

                                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                                envelope.setOutputSoapObject(request);
                                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                                try
                                {
                                    androidHttpTransport.call(SOAP_ACTION2, envelope);
                                    SoapObject resultRequestSOAP = (SoapObject) envelope.bodyIn;
                                    String temp = resultRequestSOAP.toString();

                                    //            delprodukResponse{return=("bakso goreng","dihapus");}

                                    String result = temp.substring(27, temp.length()-5);
                                    String[] arr = result.split("\",\"");

                                    if(arr[1].equalsIgnoreCase("dihapus"))
                                    {
                                        Toast.makeText(getApplicationContext(), "Produk berhasil dihapus!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Maaf produk tidak berhasil dihapus!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
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