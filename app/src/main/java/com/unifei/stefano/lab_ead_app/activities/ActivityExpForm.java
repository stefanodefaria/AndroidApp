package com.unifei.stefano.lab_ead_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
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
    private ArrayList<String> arrText;
    private ArrayList<String> arrHint;
    private String[] arrTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_form);

        Controller.setmTelaExpForm(this);

        TextView mExpNameViewForm = (TextView) findViewById(R.id.exp_nameForm);
        Bundle b = getIntent().getExtras();
        mExpNameViewForm.setText(b.getString("expName"));
        arrText = b.getStringArrayList("expFormCampos");
        arrHint = b.getStringArrayList("expFormHints");
        //TODO Colocar os hints na edittext
        arrTemp = new String[arrText.size()];

        MyListAdapter myListAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.campos_list);
        listView.setAdapter(myListAdapter);

        justifyListViewHeightBasedOnChildren(listView);

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
