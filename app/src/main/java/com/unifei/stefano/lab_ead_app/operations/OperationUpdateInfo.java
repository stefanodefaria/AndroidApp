package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

public class OperationUpdateInfo extends Operation {

    private String reqEmail;
    private String reqToken;
    private String reqNewName;
    private String reqNewPass;

    public OperationUpdateInfo(String email, String token, String newPassword, String newName, Activity sender) throws JSONException {
        super("/updateInfo", sender);

        JSONObject updateJSON = new JSONObject();

        updateJSON.put("email", email);
        updateJSON.put("token", token);
        updateJSON.put("newPassword", newPassword);
        updateJSON.put("newName", newName);

        this.setRequest(updateJSON);


        this.reqEmail = email;
        this.reqToken = token;
        this.reqNewName = newName;
        this.reqNewPass = newPassword;
    }

    public void resetOperation(){
        super.resetOperation();
        this.reqToken = null;
        this.reqEmail = null;
        this.reqNewName = null;
        this.reqNewPass = null;
    }

    public String getReqEmail() { return reqEmail;}
    public String getReqToken() {return reqToken; }
    public String getReqNewName() { return reqNewName; }
    public String getReqNewPass() { return reqNewPass; }
}
