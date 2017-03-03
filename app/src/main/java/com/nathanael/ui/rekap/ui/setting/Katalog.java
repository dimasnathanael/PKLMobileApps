package com.nathanael.ui.setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nathanael.ui.R;

/**
 * Created by nathanael on 09-Feb-17.
 */
public class Katalog extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fkatalog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
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
            Toast.makeText(com.nathanael.ui.setting.Katalog.this, "Profil Saya", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_settings)
        {
            Toast.makeText(com.nathanael.ui.setting.Katalog.this, "Setting", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_exit)
        {
            Toast.makeText(com.nathanael.ui.setting.Katalog.this, "Exit", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
