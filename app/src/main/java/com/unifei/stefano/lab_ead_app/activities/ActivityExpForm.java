package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.OperationSendReport;

import java.util.ArrayList;


public class ActivityExpForm extends Activity {

    private ListView mlistCampos;
    private ListView mlistHints;
    private ArrayList<String> mCamposList;
    private ArrayList<String> mHintsList;
    private ArrayList<String> arrText;
    private ArrayList<String> arrHint;
    private String[] arrTemp;
    private ArrayList<String> reportFieldNames;
    private ArrayList<String> reportValues;
    private String mExpKey;
    private String mEmail;
    private String mToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_form);

        Controller.setmTelaExpForm(this);

        TextView mExpNameViewForm = (TextView) findViewById(R.id.exp_nameForm);
        Bundle b = getIntent().getExtras();
        mExpNameViewForm.setText(b.getString("expName"));
        mExpKey = b.getString("expID");
       // mEmail = c.getString("email");
       // mToken = c.getString("token");
        arrText = b.getStringArrayList("expFormCampos");
        arrHint = b.getStringArrayList("expFormHints");
        arrTemp = new String[arrText.size()];

        MyListAdapter myListAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.campos_list);
        listView.setAdapter(myListAdapter);

        justifyListViewHeightBasedOnChildren(listView);

        Button mUpdateButton = (Button) findViewById(R.id.buttonReport);
        mUpdateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (validateInput()) {
                            attemptSend();
                             //Toast.makeText(ActivityExpForm.this, "Teste", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
        );


    }

    private boolean validateInput(){

        boolean cancel = false;
       // String focusView = null;


        for(int idx=0; idx<arrText.size(); idx++){
            if (TextUtils.isEmpty(arrTemp[idx])) {
                cancel = true;
            }
        }

      //  if (TextUtils.isEmpty(arrTemp[1])) {
     //       cancel = true;
       // }

        if (cancel){
            // There was an error; don't attempt loginRequest and focus the first
            // form field with an error.
            Toast.makeText(ActivityExpForm.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
       // return true;
        return !cancel;
    }

    public void attemptSend() {

        //ArrayList<String> reportFieldNames = arrText.get(position).toString();
       // ArrayList<String> reportFieldNames = arrText.;
        //String reportValues = arrTemp[position];
        reportFieldNames = new ArrayList<String> ();
        for(int idx=0; idx<arrText.size(); idx++){
            reportFieldNames.add(arrText.get(idx));
        }
        reportValues = new ArrayList<String> ();
        for(int idx=0; idx<arrText.size(); idx++){
            reportValues.add(arrTemp[idx]);
        }
           //  reportFieldNames = arrText;
           //  reportValues = arrTemp[idx];


        IniciarOperacao.iniciar(OperationSendReport.class, new Object[]{mExpKey, reportFieldNames, reportValues, this});
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


    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            if(arrText != null && arrText.size() != 0){
                return arrText.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {

            return arrText.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = ActivityExpForm.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.itens, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                holder.editText1 = (EditText) convertView.findViewById(R.id.editText1);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.textView1.setText(arrText.get(position));
            holder.editText1.setText(arrTemp[position]);
            holder.editText1.setHint(arrHint.get(position));
            holder.editText1.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {


                }

                @Override
                public void afterTextChanged(Editable arg0) {

                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView textView1;
            EditText editText1;
            int ref;
        }


    }


}
