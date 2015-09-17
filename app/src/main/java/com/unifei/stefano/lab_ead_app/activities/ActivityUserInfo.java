package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;

public class ActivityUserInfo extends Activity {

   // private TextView mNameViewForm;
    private TextView mEmailViewForm;
    private String mName;
    private String email;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Controller.setmTelaUser(this);
        TextView mNameViewForm = (TextView) findViewById(R.id.nameView);
        mNameViewForm.setText(Controller.getmNameUser());
        TextView mAccType = (TextView) findViewById(R.id.accType);
        mAccType.setText(Controller.getAccType());
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(Controller.getEmail());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
