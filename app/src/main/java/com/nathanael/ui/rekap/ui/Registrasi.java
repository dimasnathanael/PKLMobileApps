package com.nathanael.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Calendar;

/**
 * Created by nathanael on 09-Feb-17.
 */

public class Registrasi extends ActionBarActivity
{
    String NAMESPACE = "http://schemas.xmlsoap.org/wsdl";
    String URL = "http://webtest.unpar.ac.id/pklws/pkl.php?wsdl";

    String SOAP_ACTION = "regpkl";
    String METHOD_NAME = "regpkl";

    Button btnCreate;
    EditText email, nama, alamat, nomorHp, produkUnggulan;
    String tglLahir;
//    String gender;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.fregistrasi);

        //GENDER
//        Spinner spinnerGender = (Spinner) findViewById(R.id.genderSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
//        spinnerGender.setAdapter(adapter);
//        final Spinner tempGender = spinnerGender;



        //DATEPICKER
        dateView = (TextView) findViewById(R.id.textViewSelectDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        String DAY = "";
        String MONTH = "";
        String YEAR= "";
        if((month+1) < 10)
        {
            MONTH = "0" + (month+1);
        }
        if(day < 10)
        {
            DAY = "0" + day ;
        }
        tglLahir = YEAR+MONTH+DAY;
        showDate(year, (month+1), day);


        email = (EditText) findViewById(R.id.editTextEmail);
        nama = (EditText) findViewById(R.id.editTextFullName);
        alamat = (EditText) findViewById(R.id.editTextPlaceAddress);
        nomorHp = (EditText) findViewById(R.id.editTextPhoneNumber);
//        gender = (String) ((Spinner) findViewById(R.id.genderSpinner)).getSelectedItem();
        produkUnggulan = (EditText) findViewById(R.id.editTextBestProduct);
        btnCreate=(Button)findViewById(R.id.buttonCreate);

        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {

                // TODO Auto-generated method stub
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("user", email.getText().toString());
                request.addProperty("nama", nama.getText().toString());
                request.addProperty("alamat", alamat.getText().toString());
                request.addProperty("nohp", nomorHp.getText().toString());
                request.addProperty("tgllahir", tglLahir);
                request.addProperty("produkunggulan", produkUnggulan.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try
                {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    SoapObject resultRequestSOAP = (SoapObject) envelope.bodyIn;
                    String result = resultRequestSOAP.toString();

                    if(!result.substring(23, 25).equalsIgnoreCase("OK"))
                    {
//                        regpklResponse{return=("sukses","bb","didaftarkan");}

                        Toast.makeText(getApplicationContext(), "Berhasil menambahkan PKL!", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(com.nathanael.ui.Registrasi.this, Login.class);
//                        Bundle b = new Bundle ();
//                        b.putString("SID", email.getText().toString());
//                        i.putExtras(b);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Maaf Anda tidak berhasil menambahkan pkl baru!",Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finish();
            }
        });

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
            String DAY = "";
            String MONTH = "";
            String YEAR=arg1+"";
            if((arg2+1) < 10)
            {
                MONTH = "0" + (arg2+1);
            }
            if(arg3 < 10)
            {
                DAY = "0" + arg3 ;
            }
            tglLahir = YEAR+MONTH+DAY;
            showDate(arg1, (arg2+1), arg3);
        }
    };

    private void showDate(int year, int month, int day)
    {
        String DAY = "";
        String MONTH = "";
        String YEAR = year+"";
        if(month < 10)
        {
            MONTH = "0" + month;
        }
        if(day < 10)
        {
            DAY = "0" + day ;
        }
        dateView.setText(new StringBuilder().append(YEAR).append("/").append(MONTH).append("/").append(DAY));
    }
}
