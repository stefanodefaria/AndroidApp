package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by stefano on 09/08/15.
 */
public class OperationSendReport extends Operation {
    private String reqEmail;
    private String reqToken;
    private String reqExpKey;
    private JSONArray reqReport;

    public OperationSendReport(String email, String token, String expKey, JSONArray report, Activity actv) throws JSONException {
        super("/sendReport", actv);

        JSONObject request = new JSONObject();

        request.put("email", email);
        request.put("token", token);
        request.put("expKey", expKey);
        request.put("report", report);

        this.setRequest(request);
        this.reqEmail = email;
        this.reqToken = token;
        this.reqExpKey = expKey;
        this.reqReport = report;
    }

    public OperationSendReport(String email, String token, String expKey, ArrayList<String> reportFieldNames, ArrayList<String> reportValues, Activity actv) throws JSONException, Exception{
        super("/sendReport", actv);

        JSONObject request = new JSONObject();
        JSONArray report = new JSONArray();

        if(reportFieldNames.size() != reportValues.size()){
            throw new Exception("reportFieldNames array size is different than reportValues array size.");
        }

        for(int idx=0; idx<reportFieldNames.size(); idx++){
            JSONObject item = new JSONObject();
            item.put("fieldName", reportFieldNames.get(idx));
            item.put("value", reportValues.get(idx));
            report.put(item);
        }


        request.put("email", email);
        request.put("token", token);
        request.put("expKey", expKey);
        request.put("report", report);

        this.setRequest(request);
        this.reqEmail = email;
        this.reqToken = token;
        this.reqExpKey = expKey;
        this.reqReport = report;
    }

    public String getReqEmail() { return reqEmail; }
    public String getReqToken() { return reqToken; }
    public String getReqExpKey() { return reqExpKey; }
    public JSONArray getReqReport() { return reqReport; }
}
