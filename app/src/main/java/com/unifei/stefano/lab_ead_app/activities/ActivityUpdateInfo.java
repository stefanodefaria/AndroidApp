package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.OperationLogin;

public class ActivityUpdateInfo extends Activity {

    private EditText mPasswordView;
    private EditText mNewPasswordView;
    private EditText mPasswordConfirmationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_update_info);

        EditText mPasswordView = (EditText) findViewById(R.id.senhaAntiga);
        EditText mNewPasswordView = (EditText) findViewById(R.id.newPassword);
        EditText mPasswordConfirmationView = (EditText) findViewById(R.id.newPassword2);

        Button mUpdateButton = (Button) findViewById(R.id.update_info);
        mUpdateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //attemptLogin();
                        Toast.makeText(ActivityUpdateInfo.this, "Teste", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
        );

    }
 //TODO
    public void attemptUpdate() {

        String oldPassword = mPasswordView.getText().toString();
        String newPassword = mNewPasswordView.getText().toString();
        String newPassword2 = mPasswordConfirmationView.getText().toString();
        IniciarOperacao.iniciar(OperationLogin.class, new Object[]{oldPassword, newPassword, newPassword2, this});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity_update_info, menu);
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
