package com.unifei.stefano.lab_ead_app.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;
import com.unifei.stefano.lab_ead_app.operations.OperationGetReports;

public class ActivityReportForm extends Activity {

    private OperationGetReports.Report mReport;
    private VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);

        //get report from ActivityReportList
        int reportIndex = getIntent().getExtras().getInt("index");
        mReport = Controller.getmTelaReportLista().getReport(reportIndex);

        //fills custom listview
        ListView listView = (ListView) findViewById(R.id.campos_list);
        listView.setAdapter(new MyListAdapter());
        justifyListViewHeightBasedOnChildren(listView);

        videoView = (VideoView) findViewById(R.id.reportVideoView);

        if(mReport.videoFilePath != null){
            videoView.setVideoPath(mReport.videoFilePath);
        }
        else{
            videoView.setVisibility(View.GONE);
            findViewById(R.id.playButton).setVisibility(View.GONE);
        }

        //prevents keyboard to show up automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void startVideo(View v) {
        videoView.start();
    }

    private void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
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
            if(mReport.fieldNames!=null){
                return mReport.fieldNames.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {

            return mReport.fieldNames.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = ActivityReportForm.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.itens, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                holder.editText1 = (EditText) convertView.findViewById(R.id.editText1);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.textView1.setText(mReport.fieldNames.get(position));
            holder.editText1.setText(mReport.values.get(position));
            holder.editText1.setFocusable(false);
//            holder.editText1.setEnabled(false);
            holder.editText1.setCursorVisible(false);
            holder.editText1.setKeyListener(null);
            holder.editText1.setBackgroundColor(Color.TRANSPARENT);

            return convertView;
        }

        private class ViewHolder {
            TextView textView1;
            EditText editText1;
            int ref;
        }
    }
}
