package com.unifei.stefano.lab_ead_app.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.OperationStartExp;

//import android.view.Menu;
//import android.view.MenuItem;

public class ActivityExpInfo extends Activity {

    private View mExpInfoScreenView;
    private View mProgressView;


    private String mExpKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_info);

        Controller.setmTelaExpInfo(this);

        this.mExpInfoScreenView = findViewById(R.id.exp_info_screen);
        this.mProgressView = findViewById(R.id.exp_progress_bar);
        TextView mExpDescriptionView = (TextView) findViewById(R.id.exp_description);
        TextView mExpNameView = (TextView) findViewById(R.id.exp_name);
        TextView mExpManualView = (TextView) findViewById(R.id.exp_guia_url_link);

        final Activity thisActv = this;

        Button buttonStartView = (Button) findViewById(R.id.button_start);
        Bundle b = getIntent().getExtras();
        this.mExpKey = b.getString("expKey");
        mExpNameView.setText(b.getString("expName"));
        mExpDescriptionView.setText(b.getString("expDescricao"));
        String url = getString(R.string.exp_manual_base_url) + mExpKey + ".pdf";
        String txt = getString(R.string.guia_lab_link_text);

        mExpManualView.setText(Html.fromHtml(
                "<a href=\"" + url + "\">" + txt + "</a> "));
        mExpManualView.setMovementMethod(LinkMovementMethod.getInstance());

        buttonStartView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showProgress(true);


                        IniciarOperacao.iniciar(OperationStartExp.class, new Object[]{mExpKey, thisActv});
                        // IniciarOperacao.iniciar(OperationStartExp.class, new Object[]{this});
//                    Controller.showErrorMessage(new Exception("Nao implementado ainda"));
                    }

                }
        );

        showProgress(false);


    }

    @Override
    protected void onDestroy()
    {
       // IniciarOperacao.iniciar(OperationGetExpList.class, new Object[]{mExpKey, this});
        super.onDestroy();

    }

    protected void onResume()
    {
        super.onResume();
        showProgress(false);
    }

    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mExpInfoScreenView.setVisibility(show ? View.GONE : View.VISIBLE);
        mExpInfoScreenView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mExpInfoScreenView.setVisibility(show ? View.GONE : View.VISIBLE);
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
