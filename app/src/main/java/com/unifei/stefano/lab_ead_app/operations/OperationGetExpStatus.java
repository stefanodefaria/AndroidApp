/**
 * Created by stefano on 05/09/15.
 */

package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

public class OperationGetExpStatus extends Operation{

    private String reqEmail;
    private String reqToken;
    private String reqExpID;

    private int snapshotCount;
    private String encodedData;

    public OperationGetExpStatus(String email, String token, String expID, Activity actv) throws JSONException{
        super("/getExpStatus", actv);

        JSONObject request = new JSONObject();

        request.put("email", email);
        request.put("token", token);
        request.put("expID", expID);

        this.setRequest(request);

        this.reqEmail = email;
        this.reqToken = token;
        this.reqExpID = expID;
    }

    @Override
    public void setResponse(String response) throws JSONException {
        super.setResponse(response);
        JSONObject json = new JSONObject(response);

        if(json.has("snapshotCount"))
            snapshotCount = json.getInt("snapshotCount");
        if(json.has("data"))
            encodedData = json.getString("data");
    }
    @Override
    public void resetOperation(){
        super.resetOperation();
        this.reqToken = null;
        this.reqEmail = null;
        this.reqExpID = null;
    }

    public String getReqEmail() { return reqEmail; }
    public String getReqToken() { return reqToken; }
    public String getReqExpID() { return reqExpID; }
    public int getSnapshotCount() { return snapshotCount; }
    public String getEncodedData() { return encodedData; }
}
