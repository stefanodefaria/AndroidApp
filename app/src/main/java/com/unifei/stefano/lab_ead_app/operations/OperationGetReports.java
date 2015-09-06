package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Cinthya on 14/08/2015.
 */
public class OperationGetReports extends Operation{

    private String reqEmail;
    private String reqToken;

    private ArrayList<String> expID;
    private ArrayList<String> report;
    private ArrayList<String> expTime;
    private ArrayList<ArrayList<String>> listReport = new ArrayList<ArrayList<String>>();
    private ArrayList<String> xReport = new ArrayList<String>();

    public OperationGetReports (String email, String token, Activity actv) throws JSONException{
        super("/getReport", actv);

        JSONObject request = new JSONObject();

        request.put("email", email);
        request.put("token", token);

        this.setRequest(request);
        this.reqEmail = email;
        this.reqToken = token;
    }


    public void setResponse(String response) throws JSONException {
        super.setResponse(response);
        JSONObject json = new JSONObject(response);

        JSONArray expReport = json.getJSONArray("reports");

        for(int i=0; i<expReport.length(); i++){
            if (i==0) {
                expID = new ArrayList<>();
                expTime = new ArrayList<>();
                listReport = new ArrayList<>();
                xReport = new ArrayList<>();
            }
            JSONObject formItem = expReport.getJSONObject(i);
            expID.add(formItem.getString("expID"));
            expTime.add(formItem.getString("timestamp"));
            xReport.add(formItem.getString("report"));
            listReport.add(xReport);
        }

    }

    public String getReqEmail() { return reqEmail; }
    public String getReqToken() { return reqToken; }

    public ArrayList<String> getExpReports() { return getExpReports(); }


}
