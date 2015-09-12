package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OperationGetExpInfo extends Operation {
    private String reqEmail;
    private String reqToken;
    private String reqExpKey;

    private String expDescricao;
    private String expName;
    private ArrayList<String> expFormCampos;
    private ArrayList<String> expFormHints;


    public OperationGetExpInfo(String email, String token, String expKey, Activity actv) throws JSONException{
        super("/getExpInfo", actv);

        JSONObject request = new JSONObject();

        request.put("email", email);
        request.put("token", token);
        request.put("expKey", expKey);

        this.setRequest(request);
        this.reqEmail = email;
        this.reqToken = token;
        this.reqExpKey = expKey;
    }

    @Override
    public void setResponse(String response) throws JSONException {
        super.setResponse(response);
        JSONObject json = new JSONObject(response);

        if(json.has("expName"))
            this.expName = json.getString("expName");
        if(json.has("expInfo"))
            this.expDescricao = json.getString("expInfo");

        if(json.has("expReportInfo")){
            JSONArray expReportInfoArray = json.getJSONArray("expReportInfo");


            for(int i=0; i<expReportInfoArray.length(); i++){
                if (i==0) {
                    expFormCampos = new ArrayList<>();
                    expFormHints = new ArrayList<>();
                }
                JSONObject formItem = expReportInfoArray.getJSONObject(i);

                expFormCampos.add(formItem.getString("fieldName"));
                expFormHints.add(formItem.getString("hint"));
            }
        }
    }

    public String getReqEmail() { return reqEmail; }
    public String getReqToken() { return reqToken; }
    public String getReqExpKey() { return reqExpKey; }

    public String getExpDescricao() { return expDescricao; }
    public ArrayList<String> getExpFormCampos() { return expFormCampos; }
    public ArrayList<String> getExpFormHints() { return expFormHints; }
    public String getExpName() { return expName; }

}
