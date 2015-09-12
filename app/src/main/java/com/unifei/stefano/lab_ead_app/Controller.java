package com.unifei.stefano.lab_ead_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.unifei.stefano.lab_ead_app.activities.ActivityExpForm;
import com.unifei.stefano.lab_ead_app.activities.ActivityExpInfo;
import com.unifei.stefano.lab_ead_app.activities.ActivityExpList;
import com.unifei.stefano.lab_ead_app.activities.ActivityLogin;
import com.unifei.stefano.lab_ead_app.activities.ActivityReportList;
import com.unifei.stefano.lab_ead_app.activities.ActivityUserInfo;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.Operation;
import com.unifei.stefano.lab_ead_app.operations.OperationGetExpInfo;
import com.unifei.stefano.lab_ead_app.operations.OperationGetExpList;
import com.unifei.stefano.lab_ead_app.operations.OperationGetExpStatus;
import com.unifei.stefano.lab_ead_app.operations.OperationLogin;
import com.unifei.stefano.lab_ead_app.operations.OperationLogout;
import com.unifei.stefano.lab_ead_app.operations.OperationRegister;
import com.unifei.stefano.lab_ead_app.operations.OperationSendReport;
import com.unifei.stefano.lab_ead_app.operations.OperationStartExp;
import com.unifei.stefano.lab_ead_app.operations.OperationUpdateInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Controller {

    private static ActivityExpForm mTelaExpForm;
    private static ActivityLogin mTelaLogin;
    private static ActivityExpList mTelaLista;
    private static ActivityExpInfo mTelaExpInfo;
    private static ActivityReportList mTelaReportLista;
    private static ActivityUserInfo mTelaUser;

    private static Activity mTelaEmUso;

    private static ArrayList<String> mExpNamesList;
    private static ArrayList<String> mExpKeysList;
    private static ArrayList<String> mExpFormCampos;
    private static ArrayList<String> mExpFormHints;
    private static String mExpDescricao;
    private static String mExpName;
    private static String mExpID;
    private static String name;
    private static String email;
    //private static String mName;
    private static String newPassword;
    private static JSONArray reqReport;
    private static String ip;


    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Controller.ip = ip;
    }

    public static void setmTelaExpForm(ActivityExpForm mTelaExpForm) {
        Controller.mTelaExpForm = mTelaExpForm;
        mTelaEmUso = mTelaExpForm;
    }
    public static void setmTelaLogin(ActivityLogin mTelaLogin) {
        Controller.mTelaLogin = mTelaLogin;
        mTelaEmUso = mTelaLogin;
    }
    public static void setmTelaLista(ActivityExpList mTelaLista) {
        Controller.mTelaLista = mTelaLista;
        mTelaEmUso = mTelaLista;
    }
    public static void setmTelaExpInfo(ActivityExpInfo mTelaExpInfo) {
        Controller.mTelaExpInfo = mTelaExpInfo;
        mTelaEmUso = mTelaLista;
    }
    public static void setmTelaReportLista(ActivityReportList mTelaReportLista) {
        Controller.mTelaReportLista = mTelaReportLista;
        mTelaEmUso = mTelaReportLista;
    }

    public static void setmTelaUser(ActivityUserInfo mTelaUser) {
        Controller.mTelaUser = mTelaUser;
        mTelaEmUso = mTelaUser;
    }

    public static void receberResposta(Exception error, final Operation operation){
        if(error == null) {

            switch (operation.getName()){
                case "register":
                    handleRegisterResponse((OperationRegister) operation);
                    break;
                case "login":
                    handleLoginResponse((OperationLogin) operation);
                    break;
                case "logout":
                    handleLogoutResponse((OperationLogout) operation);
                    break;
                case "getExpList":
                    handleGetExpListResponse((OperationGetExpList) operation);
                    break;
                case "getExpInfo":
                    handleExpInfoResponse((OperationGetExpInfo) operation);
                    break;
                case "startExp":
                    handleStartExp((OperationStartExp) operation);
                    break;
                case "updateInfo":
                    handleUpdateInfo((OperationUpdateInfo) operation);
                    break;
                case "sendReport":
                    handleSendReport((OperationSendReport) operation);
                    break;
                case "getExpStatus":
                    handleGetExpStatus((OperationGetExpStatus) operation);
                    break;
                default:
                    showErrorMessage(new Exception("Operação <" + operation.getName() + "> nao implementada"));
            }
        }
        else {

            Callable<Void> callback = null;

            //goes back to Login Screen in case of no Internet connection / Server off
            if(error instanceof HttpsOperation.NoInternetException ||
                    error instanceof org.apache.http.conn.HttpHostConnectException){

                callback = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        backToLoginScreen(operation.getTelaExpedidora());
                        operation.getTelaExpedidora().finish();
                        return null;
                    }
                };
            }
            showErrorMessage(error, callback);
        }
    }

    private static void handleRegisterResponse(OperationRegister registerOp) {
        String responseMsg = registerOp.getResponseMessage();
        switch (responseMsg){
            case Definitions.SUCCESS:
                // Após registro, inicia login automaticamente
                String email = registerOp.getReqEmail();
                String password = registerOp.getReqPassword();
                name = registerOp.getReqName();
                Activity sender = registerOp.getTelaExpedidora();

                if(mTelaEmUso == mTelaUser){
                    Intent intent = new Intent(sender, ActivityUserInfo.class);

                     Bundle b = new Bundle();
                     b.putString("email", email);
                     b.putString("name", name);
                     intent.putExtras(b);
                    break;
                }
                //Intent intent = new Intent(sender, ActivityUserInfo.class);

               // Bundle b = new Bundle();
               // b.putString("email", email);
              //  b.putString("name", name);
               // intent.putExtras(b); //Insere parametros no intent

                IniciarOperacao.iniciar(OperationLogin.class, new Object[]{email, password, sender});
                break;
            case Definitions.EMAIL_NOT_UNIQUE:
                mTelaLogin.showProgress(false);
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_email_not_unique)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }
    }

    private static void handleLoginResponse(OperationLogin loginOp){
        String responseMsg = loginOp.getResponseMessage();
        switch (responseMsg){
            case Definitions.SUCCESS:
                IniciarOperacao.setToken(loginOp.getToken());
                IniciarOperacao.setEmail(loginOp.getReqEmail());
                IniciarOperacao.setTimeOutLimit(loginOp.getTimeoutLimit());
                IniciarOperacao.refreshTimeOutDate();
                Intent intent = new Intent(loginOp.getTelaExpedidora(), ActivityExpList.class);
                loginOp.getTelaExpedidora().startActivity(intent);

                break;
            case Definitions.BAD_CREDENTIALS:
                mTelaLogin.showProgress(false);
                showErrorMessage(new Exception(mTelaLogin.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }
    }

    private static void handleLogoutResponse(OperationLogout logoutOp){
        String responseMsg = logoutOp.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:
                IniciarOperacao.setToken(null);
                IniciarOperacao.setTimeOutLimit(-1);
                IniciarOperacao.setEmail(null);
                Intent intent = new Intent(logoutOp.getTelaExpedidora(), ActivityLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                logoutOp.getTelaExpedidora().startActivity(intent);

                mTelaEmUso.finish();
                break;
            case Definitions.BAD_CREDENTIALS:
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }
    }

    private static void handleUpdateInfo(OperationUpdateInfo updateInfoOp){
        String responseMsg = updateInfoOp.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:
                newPassword = updateInfoOp.getReqNewPass();
                name = updateInfoOp.getReqNewName();
                break;
            case Definitions.BAD_CREDENTIALS:
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }
    }

    private static void handleSendReport(OperationSendReport sendReportOp){
        String responseMsg = sendReportOp.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:
                String mExpID = sendReportOp.getReqExpKey();
             //   String email = sendReportOp.getReqEmail();
             //   String token = sendReportOp.getReqToken();
                reqReport = sendReportOp.getReqReport();

                Intent intent = new Intent(sendReportOp.getTelaExpedidora(), ActivityExpForm.class);
                Bundle c = new Bundle();
                c.putString("expID", mExpID);

               // c.putString("email", email);
               // c.putString("token", token);
                intent.putExtras(c);

                break;
            case Definitions.BAD_CREDENTIALS:
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }
    }

    private static void handleGetExpStatus(OperationGetExpStatus getExpStatusOp){
        String responseMsg = getExpStatusOp.getResponseMessage();

        switch (responseMsg){
            case Definitions.SUCCESS:

                // exp has finished, and encodedData is a video
                if(getExpStatusOp.getSnapshotCount() == -1){

                    String filePath = null;

                    //converts base64 video to file
                    if(getExpStatusOp.getEncodedData() != null){
                        byte[] decodedString = Base64.decode(getExpStatusOp.getEncodedData(), Base64.DEFAULT);
                        filePath = getExpStatusOp.getTelaExpedidora().getFilesDir().toString() + "/" +
                                getExpStatusOp.getReqEmail() + "_" + getExpStatusOp.getReqExpID() + ".avi";

                        try{
                            FileOutputStream os = new FileOutputStream(new File(filePath));
                            os.write(decodedString);
                            os.flush();
                            os.close();
                        }
                        catch (Exception e){
                            showErrorMessage(e);
                        }
                    }

                    mTelaExpForm.finishFakeStreaming(filePath);
                    return;
                }

                //updates snapshot
                if(getExpStatusOp.getEncodedData() != null){
                    byte[] decodedString = Base64.decode(getExpStatusOp.getEncodedData(), Base64.DEFAULT);
                    Bitmap snapshot = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    mTelaExpForm.updateSnapshot(snapshot);
                }

                //queries again for new snapshot
                Object[] params = {getExpStatusOp.getReqExpID(), getExpStatusOp.getSnapshotCount(), getExpStatusOp.getTelaExpedidora()};

                IniciarOperacao.iniciar(OperationGetExpStatus.class, params);

                break;
            default:
                showErrorMessage(new Exception(responseMsg));
                mTelaExpForm.finishFakeStreaming(null);
        }
    }

    private static void handleGetExpListResponse(OperationGetExpList getExpListOp){
        String responseMsg = getExpListOp.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:
                IniciarOperacao.refreshTimeOutDate();
                mExpNamesList = getExpListOp.getExpNames();
                mExpKeysList = getExpListOp.getExpKeys();

                if(mTelaEmUso == mTelaReportLista){
                    mTelaReportLista.setReportList(mExpNamesList, mExpKeysList);
                    break;
                }

                if(mTelaEmUso!= mTelaLista){
                    Intent intent = new Intent(getExpListOp.getTelaExpedidora(),ActivityExpList.class);
                    Bundle b = new Bundle();
                    b.putString("nameInfo", name);
                    getExpListOp.getTelaExpedidora().startActivity(intent);
                  }

                mTelaLista.setExpList(mExpNamesList, mExpKeysList);
                //mTelaReportLista.setReportList(mExpNamesList, mExpKeysList);

                break;
            case Definitions.BAD_CREDENTIALS:
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }
    }

    private static void handleExpInfoResponse(OperationGetExpInfo getExpInfoOp) {
        String responseMsg = getExpInfoOp.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:
                IniciarOperacao.refreshTimeOutDate();
                mExpName = getExpInfoOp.getExpName();
                mExpDescricao = getExpInfoOp.getExpDescricao();
                mExpFormCampos = getExpInfoOp.getExpFormCampos();
                mExpFormHints = getExpInfoOp.getExpFormHints();
                String expKey = getExpInfoOp.getReqExpKey();

                if (mTelaExpInfo != null) {
                    mTelaExpInfo.finish();
                    mTelaExpInfo = null;
                }

                Intent intent = new Intent(getExpInfoOp.getTelaExpedidora(), ActivityExpInfo.class);

                Bundle b = new Bundle();
                b.putString("expKey", expKey);
                b.putString("expName", mExpName);
                b.putString("expDescricao", mExpDescricao);
                b.putStringArrayList("expFormCampos", mExpFormCampos);
                b.putStringArrayList("expFormHints", mExpFormHints);
                intent.putExtras(b); //Insere parametros no intent
                getExpInfoOp.getTelaExpedidora().startActivity(intent);

                break;
            case Definitions.BAD_CREDENTIALS:
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }
    }

    private static void handleStartExp(OperationStartExp getExpForm) {
        String responseMsg = getExpForm.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:


                IniciarOperacao.refreshTimeOutDate();
                mExpID = getExpForm.getReqExpId();

                //mExpNameForm = expForm.getExpName();

                if(mTelaExpForm != null){
                    mTelaExpForm.finish();
                    mTelaExpInfo = null;
                }

                Intent intent = new Intent(getExpForm.getTelaExpedidora(),ActivityExpForm.class);
                Bundle b = new Bundle();
                b.putString("expName", mExpName);
                b.putString("expID", getExpForm.getReqExpId());

                b.putStringArrayList("expFormCampos", mExpFormCampos);
                b.putStringArrayList("expFormHints", mExpFormHints);

               // mTelaExpForm.setExpCampos(mExpFormCampos);

                intent.putExtras(b);
                getExpForm.getTelaExpedidora().startActivity(intent);



                break;
            case Definitions.BAD_CREDENTIALS:
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }

    }

    public static void showErrorMessage(Exception e, final Callable<Void> callback){

        String msg;

        if(e instanceof JSONException){
            msg = "Não criou json";
        }else{
            msg = e.getMessage();
        }

        AlertDialog alertDialog = new AlertDialog.Builder(mTelaEmUso).create();
        alertDialog.setTitle("Erro");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(callback!=null){
                            try{
                                callback.call();
                            }
                            catch (Exception e){}
                        }
                    }
                });

        alertDialog.show();
    }
    public static void showErrorMessage(Exception e){ showErrorMessage(e, null); }

    private static void backToLoginScreen(Context context){
        Intent intent = new Intent(context, ActivityLogin.class);
        context.startActivity(intent);
    }
}
