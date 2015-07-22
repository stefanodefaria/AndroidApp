package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.OperationGetExpInfo;
import com.unifei.stefano.lab_ead_app.operations.OperationGetExpList;
import com.unifei.stefano.lab_ead_app.operations.OperationLogout;

import java.util.ArrayList;


public class ActivityExpList extends Activity {

    private ListView mlistView;
    private ArrayList<String> mExpNamesList;
    private ArrayList<String> mExpIDsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_list);
        Controller.setmTelaLista(this);

        final Activity thisActv = this;

        mlistView = (ListView) findViewById(R.id.listView);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String expKey = mExpIDsList.get(i);
                IniciarOperacao.iniciar(OperationGetExpInfo.class, new Object[]{expKey, thisActv});
            }
        });

        IniciarOperacao.iniciar(OperationGetExpList.class, new Object[]{this});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void setExpList(ArrayList<String> expNames, ArrayList<String> expIDs){
        this.mExpNamesList = expNames;
        this.mExpIDsList = expIDs;

        atualizaListView();
    }



    private void atualizaListView(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                mExpNamesList );

        mlistView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            IniciarOperacao.iniciar(OperationLogout.class, new Object[]{this});

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
