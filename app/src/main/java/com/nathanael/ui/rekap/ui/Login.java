package com.nathanael.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by nathanael on 09-Feb-17.
 */

public class Login extends ActionBarActivity
{
    String NAMESPACE = "http://schemas.xmlsoap.org/wsdl";
    String URL = "http://webtest.unpar.ac.id/pklws/pkl.php?wsdl";

    String SOAP_ACTION = "login";
    String METHOD_NAME = "login";

    EditText edUserName, edTextPassword;
    Button btnLogin;
    String SID="";
    TextView prefUsername, prefPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.flogin);

        edUserName = (EditText)findViewById(R.id.editTextUserName);
        edTextPassword = (EditText)findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

        prefUsername = (TextView)findViewById(R.id.textViewPrefUsername);
        prefPassword = (TextView)findViewById(R.id.textViewPrefPassword);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("user", edUserName.getText().toString());
                request.addProperty("password", edTextPassword.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try
                {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    SoapObject resultRequestSOAP = (SoapObject) envelope.bodyIn;
                    String result = resultRequestSOAP.toString();
                    SID = result.substring(28, 60);

                    if(result.substring(23, 25).equalsIgnoreCase("OK"))
                    {
//                        loginResponse{return=("OK","6304e11edf175610fd862ab2e8881ce4");}

                        Toast.makeText(getApplicationContext(),"Login berhasil...",Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(com.nathanael.ui.Login.this, Menu.class);
                        Bundle b = new Bundle ();
                        b.putString("SID", SID);
                        i.putExtras(b);
                        startActivity(i);


                        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", edUserName.getText().toString());
                        editor.putString("password", edTextPassword.getText().toString());
                        editor.apply();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Maaf username/password Anda salah!",Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void displayData(View view)
    {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String getUsername = sharedPref.getString("username", "");
        String getPassword = sharedPref.getString("password", "");

        prefUsername.setText(getUsername);
        prefPassword.setText(getPassword);
    }

    public void doLogin(View view)
    {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void doRegister(View view)
    {
        Intent intent = new Intent(this, Registrasi.class);
        startActivity(intent);
    }
}
