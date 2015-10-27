package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.OperationGetReports;

import java.util.ArrayList;

public class ActivityReportList extends Activity {

    private ListView mlistView;
    private ArrayList<OperationGetReports.Report> mReports;
    private ArrayList<String> mExpNamesList = new ArrayList<>();

    public ActivityReportList() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        Controller.setmTelaReportLista(this);

        IniciarOperacao.iniciar(OperationGetReports.class, new Object[]{this});

        final Activity thisActv = this;

        mlistView = (ListView) findViewById(R.id.reportlistView);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Inicia ActivityReportForm com report selecionado
                Intent intent = new Intent(thisActv, ActivityReportForm.class);
                Bundle b = new Bundle();
                b.putInt("index",i);
                intent.putExtras(b);
                thisActv.startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        Controller.setmTelaReportLista(this);
    }

    private void atualizaListView(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                mExpNamesList );

        mlistView.setAdapter(arrayAdapter);
    }

    public void setReports(ArrayList<OperationGetReports.Report> reports){
        mReports = reports;

        for(int i=0; i<reports.size(); i++){
            mExpNamesList.add(reports.get(i).expName);
        }

        atualizaListView();
    }

    public OperationGetReports.Report getReport(int index){
        return mReports.get(index);
    }
}
