package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;

import java.util.ArrayList;


public class ActivityExpForm extends Activity {

    private ListView mlistCampos;
    private ListView mlistHints;

    private ArrayList<String> mCamposList;
    private ArrayList<String> mHintsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_form);

        Controller.setmTelaExpForm(this);


        TextView mExpNameViewForm = (TextView) findViewById(R.id.exp_nameForm);
        Bundle b = getIntent().getExtras();
        mExpNameViewForm.setText(b.getString("expName"));
        mlistCampos = (ListView) findViewById(R.id.campos_list);
        mlistHints = (ListView) findViewById(R.id.hints_list);

        //mCamposList = b.getStringArrayList("expFormCampos");
        // mHintsList = b.getStringArrayList("expFormHints");

       /* ArrayList<String> lista = new ArrayList<String>();
        for(int i=0 ; i<mCamposList.size() ; i++){
            lista.add(mCamposList.get(i));
            lista.add(mHintsList.get(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lista);

        mlistCampos.setAdapter(arrayAdapter);
        */
    //TODO Arrumar as listas(hints e sincronia entre elas)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                b.getStringArrayList("expFormCampos"));

        mlistCampos.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapterHints = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                b.getStringArrayList("expFormHints"));

        mlistHints.setAdapter(arrayAdapterHints);




//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setLogo(R.mipmap.ic_launcher);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_experimento_gravidade, menu);
        return true;
    }

    public void setExpCampos(ArrayList<String> expCampos){
        this.mCamposList = expCampos;

    }
 /*
    private void atualiza(){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                mCamposList );

        mlistView.setAdapter(arrayAdapter);
    }*/

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

    public void populateExpForm(String expKey, String expDescricao, String expName,
                           ArrayList<String> expFormCampos, ArrayList<String> expFormHints){



    }

    public void onClick(View v){
//        Controller.login(this);
    }

}
