package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;
import android.net.Uri;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.Toast;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.Definitions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Cinthya on 14/08/2015.
 */
public class OperationGetReports extends Operation{

    private String reqEmail;
    private String reqToken;

    private ArrayList<Report> reports = new ArrayList<>();

    public OperationGetReports(String email, String token, Activity actv) throws JSONException{
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

        if (!this.getResponseMessage().equals(Definitions.SUCCESS) || !json.has("reports")) {
            return;
        }

        JSONArray expReports = json.getJSONArray("reports");

        //iterate through every report
        for (int i = 0; i < expReports.length(); i++) {

            Report report = new Report();
            JSONObject jsonReport = expReports.getJSONObject(i);

            report.expName = jsonReport.getString("expName");
            report.expID = jsonReport.getString("expID");
            report.timestamp = jsonReport.getString("timestamp");

            JSONArray reportContent = jsonReport.getJSONArray("report");

            //iterate through every field in the report
            for (int j = 0; j < reportContent.length(); j++) {
                String fieldName = reportContent.getJSONObject(j).getString("fieldName");
                String value = reportContent.getJSONObject(j).getString("value");

                report.fieldNames.add(fieldName);
                report.values.add(value);
            }

            if(jsonReport.has("encodedVideo")){
                byte[] decodedString = Base64.decode(jsonReport.getString("encodedVideo"), Base64.DEFAULT);
                String filePath = this.getTelaExpedidora().getFilesDir().toString() + "/" +
                        reqEmail + "_" + report.expID + ".avi";

                try{
                    FileOutputStream os = new FileOutputStream(new File(filePath));
                    os.write(decodedString);
                    os.flush();
                    os.close();
                    report.videoFilePath = filePath;
                }
                catch (Exception e){
                    Controller.showErrorMessage(e);
                }
            }


            reports.add(report);

        }
    }

    public String getReqEmail() { return reqEmail; }
    public String getReqToken() { return reqToken; }
    public ArrayList<Report> getReports(){ return reports; }

    public class Report{
        public String expID, timestamp, videoFilePath, expName;
        public ArrayList<String> fieldNames, values;

        public Report(){
            fieldNames = new ArrayList<>();
            values = new ArrayList<>();
        }

    }
}
