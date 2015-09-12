package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OperationGetExpList extends Operation {

    private String reqEmail;
    private String reqToken;
    private ArrayList<String> expKeys;
    private ArrayList<String> expNames;

    public OperationGetExpList(String email, String token, Activity actv) throws JSONException{
        super("/getExpList", actv);

        JSONObject request = new JSONObject();

        request.put("email", email);
        request.put("token", token);

        this.setRequest(request);

        this.reqEmail = email;
        this.reqToken = token;
    }

    @Override
    public void setResponse(String response) throws JSONException {
        super.setResponse(response);
        JSONObject json = new JSONObject(response);

        if(json.has("experiencesKeys") && json.has("experiencesNames")){
            JSONArray keysArr = json.getJSONArray("experiencesKeys");      // os 2 arrays devem ter
            JSONArray namesArr = json.getJSONArray("experiencesNames"); // o mesmo length

            for(int i=0; i<keysArr.length(); i++){
                if (i==0) {
                    expKeys = new ArrayList<>();
                    expNames = new ArrayList<>();
                }
                expKeys.add(keysArr.getString(i));
                expNames.add(namesArr.getString(i));
            }
        }
    }

    @Override
    public void resetOperation(){
        super.resetOperation();
        this.reqToken = null;
        this.reqEmail = null;
        this.expKeys = null;
        this.expNames = null;
    }

    public String getReqEmail() { return reqEmail; }
    public String getReqToken() { return reqToken; }
    public ArrayList<String> getExpKeys() { return expKeys; }
    public ArrayList<String> getExpNames() { return expNames; }

}
