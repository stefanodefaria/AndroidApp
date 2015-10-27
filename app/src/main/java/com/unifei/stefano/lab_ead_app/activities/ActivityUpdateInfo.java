package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.OperationUpdateInfo;

public class ActivityUpdateInfo extends Activity {

    private EditText mNewNameView;
    private EditText mNewPasswordView;
    private EditText mNewPasswordConfirmationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_update_info);

        Controller.setmTelaUpdate(this);

        mNewNameView = (EditText) findViewById(R.id.novoNome);
        mNewPasswordView = (EditText) findViewById(R.id.newPassword);
        mNewPasswordConfirmationView = (EditText) findViewById(R.id.newPassword2);

        Button mUpdateButton = (Button) findViewById(R.id.update_info);
        mUpdateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(validateInput()){
                            attemptUpdate();
                            Toast.makeText(ActivityUpdateInfo.this, "Informacoes Atualizadas", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
        );

    }

    @Override
    protected void onResume(){
        super.onResume();
        Controller.setmTelaUpdate(this);
    }

    public void attemptUpdate() {

        String newPassword = mNewPasswordView.getText().toString();
        String name = mNewNameView.getText().toString();

        IniciarOperacao.iniciar(OperationUpdateInfo.class, new Object[]{newPassword, name, this});
    }

    private boolean validateInput(){


        mNewNameView.setError(null);
        mNewPasswordView.setError(null);
        mNewPasswordConfirmationView.setError(null);

        String name = mNewNameView.getText().toString();
        String password = mNewPasswordView.getText().toString();
        String passwordConfirmation = mNewPasswordConfirmationView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            mNewNameView.setError(getString(R.string.error_field_required));
            focusView = mNewNameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(passwordConfirmation)) {
            mNewPasswordConfirmationView.setError(getString(R.string.error_field_required));
            focusView = mNewPasswordConfirmationView;
            cancel = true;
        } else if (!passwordConfirmation.equals(password)) {
            mNewPasswordConfirmationView.setError(getString(R.string.error_incorrect_password_confirmation));
            focusView = mNewPasswordConfirmationView;
            cancel = true;
        }

        if (cancel){
            // There was an error; don't attempt loginRequest and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return !cancel;
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
