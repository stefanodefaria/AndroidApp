package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import com.unifei.stefano.lab_ead_app.Controller;
import com.unifei.stefano.lab_ead_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Operation {

    private String endpoint;
    private String path;
    private JSONObject request;

    private Activity telaExpedidora;
    private String responseMessage;

    public Operation(String path,  Activity actv){
        this.path = path;
        this.responseMessage = null;
        this.telaExpedidora = actv;

        this.endpoint = "http://" + Controller.getIp() +
                ":" + telaExpedidora.getString(R.string.port);
    }
   // telaExpedidora.getString(R.string.ip_address)
    //Controller.getIp(R.string.ip_address);
    //Controller.getIp(R.array.ip_itens);
    //Controller.getIp()

    public void setResponse(String response) throws JSONException {
        JSONObject json = new JSONObject(response);

        if(json.has("message"))
            this.responseMessage = json.getString("message");
    }

    public void resetOperation(){
        this.responseMessage = null;
        this.request = null;
    }

    protected void setRequest(JSONObject request) {
        this.request = request;
    }

    public Activity getTelaExpedidora() {
        return telaExpedidora;
    }

    public String getName() { return path.replace("/", ""); } // remove a barra

    public String getUrl() { return endpoint + path; }

    public JSONObject getRequest() { return request; }

    public String getResponseMessage() { return responseMessage; }

    public boolean hasReceivedValidResponse(){ return (responseMessage!=null); }
}
