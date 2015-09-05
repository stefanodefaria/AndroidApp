package com.unifei.stefano.lab_ead_app.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.OperationLogin;
import com.unifei.stefano.lab_ead_app.operations.OperationRegister;


/**
 * A loginRequest screen that offers loginRequest via email/password.
 */
public class ActivityLogin extends Activity {

    // UI references.
    private EditText mEmailView;
    private EditText mIPView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox mRegisterCheckboxView;
    private EditText mNameView;
    private EditText mPasswordConfirmationView;
    private Button mSignInRegisterButton;
    private String ipNum;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Controller.setmTelaLogin(this);

        // Set up the loginRequest form.
        mEmailView = (EditText) findViewById(R.id.email);
       // mIPView = (EditText) findViewById(R.id.ip);
        mPasswordView = (EditText) findViewById(R.id.password);
        mNameView = (EditText) findViewById(R.id.name);
        mPasswordConfirmationView = (EditText) findViewById(R.id.passwordConfirmation);
        mRegisterCheckboxView = (CheckBox) findViewById(R.id.checkBoxRegister);
        mSignInRegisterButton = (Button) findViewById(R.id.sign_in_register_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

       // myNum = Integer.parseInt(mIPView.getText().toString());
//        ipNum = mIPView.getText().toString();


        mRegisterCheckboxView.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                     onChangedRegisterCheckBox(b);
                 }
            });

        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        String[] items = getResources().getStringArray(R.array.ip_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

       // dropdown.OnItemChanged(items){
       //     Controller.setIp(items.Text);
       // }

        mSignInRegisterButton.setOnClickListener(
                new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validateInput()){
                        showProgress(true);

                        Spinner sp =	(Spinner)findViewById(R.id.spinner);
                        String spinnerString = null;
                        spinnerString = sp.getSelectedItem().toString();
                        Controller.setIp(spinnerString);
                        if(mRegisterCheckboxView.isChecked()) attemptRegister();
                        else attemptLogin();

                        //mNameView.setText(null);
                        mPasswordView.setText(null);
                        mPasswordConfirmationView.setText(null);

                    }
                }
            }
        );
    }

    @Override
    protected void onResume(){
        super.onResume();
        showProgress(false);
    }


/*
    Spinner dropDown = (Spinner)findViewById(R.id.spinner1);
    dropDown.OnItemChanged(item){
        Controller.setIp(item.Text);
    }

*/

    private void onChangedRegisterCheckBox(boolean checked){
        if(checked){
            mNameView.setVisibility(View.VISIBLE);
            mPasswordConfirmationView.setVisibility(View.VISIBLE);
            mSignInRegisterButton.setText(getString(R.string.action_register));

            mEmailView.setNextFocusDownId(R.id.name);
            mNameView.setNextFocusDownId(R.id.password);
            mPasswordView.setNextFocusDownId(R.id.passwordConfirmation);
            mPasswordConfirmationView.setNextFocusDownId(R.id.sign_in_register_button);

        }
        else{
            mNameView.setVisibility(View.GONE);
            mPasswordConfirmationView.setVisibility(View.GONE);
            mSignInRegisterButton.setText(getString(R.string.action_login));

            mEmailView.setNextFocusDownId(R.id.password);
            mPasswordView.setNextFocusDownId(R.id.sign_in_register_button);
        }
    }


    /**
     * Attempts to sign in or register the account specified by the loginRequest form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual loginRequest attempt is made.
     */
    public void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        IniciarOperacao.iniciar(OperationLogin.class, new Object[]{email, password, this});
    }

    public void attemptRegister(){

        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        IniciarOperacao.iniciar(OperationRegister.class, new Object[]{email, password, name, this});
    }

    private boolean validateInput(){
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mNameView.setError(null);
        mPasswordConfirmationView.setError(null);

        boolean isRegistration = mRegisterCheckboxView.isChecked();

        // Store values at the time of the loginRequest attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mNameView.getText().toString();
//        String ip = mIPView.getText().toString();
        String passwordConfirmation = mPasswordConfirmationView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(isRegistration){
            if (TextUtils.isEmpty(passwordConfirmation)) {
                mPasswordConfirmationView.setError(getString(R.string.error_field_required));
                focusView = mPasswordConfirmationView;
                cancel = true;
            } else if (!passwordConfirmation.equals(password)) {
                mPasswordConfirmationView.setError(getString(R.string.error_incorrect_password_confirmation));
                focusView = mPasswordConfirmationView;
                cancel = true;
            }
            if (TextUtils.isEmpty(name)) {
                mNameView.setError(getString(R.string.error_field_required));
                focusView = mNameView;
                cancel = true;
            }
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

     //   if (TextUtils.isEmpty(ip)) {
      //      mIPView.setError(getString(R.string.error_field_required));
       //     focusView = mIPView;
       //     cancel = true;
      //  }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!email.contains("@")) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }



        if (cancel){
            // There was an error; don't attempt loginRequest and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return !cancel;
    }

    /**
     * Shows the progress UI and hides the loginRequest form.
     */
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

}