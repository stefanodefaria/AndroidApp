package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stefano on 10/07/15.
 */
public class OperationStartExp extends Operation{
    private String reqEmail;
    private String reqToken;
    private String reqExpId;


    public OperationStartExp(Activity sender, String email, String token, String expID) throws JSONException {
        super("/register", sender);

        JSONObject startExpJSON = new JSONObject();

        startExpJSON.put("email", email);
        startExpJSON.put("token", token);
        startExpJSON.put("expID", expID);

        this.setRequest(startExpJSON);

        this.reqEmail = email;
        this.reqToken = token;
        this.reqExpId = expID;
    }

    @Override
    public void resetOperation(){
        super.resetOperation();
        this.reqExpId = null;
        this.reqEmail = null;
        this.reqToken = null;
    }

    public String getReqToken() { return reqToken; }
    public String getReqEmail() { return reqEmail; }
    public String getReqExpId() { return reqExpId; }
}
