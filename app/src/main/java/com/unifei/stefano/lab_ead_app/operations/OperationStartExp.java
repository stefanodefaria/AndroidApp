package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by stefano on 10/07/15.
 */
public class OperationStartExp extends Operation{
    private String reqEmail;
    private String reqToken;
    private String reqExpId;

    private String expDescricao;
    private String expNameForm;
    private ArrayList<String> expFormCampos;
    private ArrayList<String> expFormHints;

    public OperationStartExp(String email, String token, String expID, Activity sender) throws JSONException {
        super("/startExp", sender);

        JSONObject startExpJSON = new JSONObject();

        startExpJSON.put("email", email);
        startExpJSON.put("token", token);
        startExpJSON.put("expID", expID);

        this.setRequest(startExpJSON);

        this.reqEmail = email;
        this.reqToken = token;
        this.reqExpId = expID;
    }

    /*
    public void setResponse(String response) throws JSONException {
        super.setResponse(response);
        JSONObject json = new JSONObject(response);

        this.expNameForm = json.getString("expName");
        this.expDescricao = json.getString("expInfo");
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
    */

    @Override
    public void resetOperation(){
        super.resetOperation();
        this.reqExpId = null;
        this.reqEmail = null;
        this.reqToken = null;
    }

    public String getExpDescricao() { return expDescricao; }
    public ArrayList<String> getExpFormCampos() { return expFormCampos; }
    public ArrayList<String> getExpFormHints() { return expFormHints; }
    public String getExpNameForm() { return expNameForm; }
    public String getReqToken() { return reqToken; }
    public String getReqEmail() { return reqEmail; }
    public String getReqExpId() { return reqExpId; }
}
