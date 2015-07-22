package com.unifei.stefano.lab_ead_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.unifei.stefano.lab_ead_app.activities.ActivityExpForm;
import com.unifei.stefano.lab_ead_app.activities.ActivityExpInfo;
import com.unifei.stefano.lab_ead_app.activities.ActivityExpList;
import com.unifei.stefano.lab_ead_app.activities.ActivityLogin;
import com.unifei.stefano.lab_ead_app.operations.IniciarOperacao;
import com.unifei.stefano.lab_ead_app.operations.Operation;
import com.unifei.stefano.lab_ead_app.operations.OperationGetExpInfo;
import com.unifei.stefano.lab_ead_app.operations.OperationGetExpList;
import com.unifei.stefano.lab_ead_app.operations.OperationLogin;
import com.unifei.stefano.lab_ead_app.operations.OperationLogout;
import com.unifei.stefano.lab_ead_app.operations.OperationRegister;
import com.unifei.stefano.lab_ead_app.operations.OperationStartExp;

import org.json.JSONException;

import java.util.ArrayList;

public class Controller {

    private static ActivityExpForm mTelaExpForm;
    private static ActivityLogin mTelaLogin;
    private static ActivityExpList mTelaLista;
    private static ActivityExpInfo mTelaExpInfo;

    private static Activity mTelaEmUso;

    private static ArrayList<String> mExpNamesList;
    private static ArrayList<String> mExpKeysList;
    private static ArrayList<String> mExpFormCampos;
    private static ArrayList<String> mExpFormHints;
    private static String mExpDescricao;
    private static String mExpName;
    private static String mExpID;



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

    public static void receberResposta(ArrayList<String> error, Operation operation){
        if(error.size()==0) {

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
                default:
                    showErrorMessage(new Exception("Operação <" + operation.getName() + "> nao implementada"));
            }
        }
        else {
            String erros = "";
            for(String s: error) erros +=" " + s;

            showErrorMessage(new Exception(erros));
        }
    }


    private static void handleRegisterResponse(OperationRegister registerOp) {
        String responseMsg = registerOp.getResponseMessage();
        switch (responseMsg){
            case Definitions.SUCCESS:
                // Após registro, inicia login automaticamente
                String email = registerOp.getReqEmail();
                String password = registerOp.getReqPassword();
                Activity sender = registerOp.getTelaExpedidora();
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
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
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

    private static void handleGetExpListResponse(OperationGetExpList getExpListOp){
        String responseMsg = getExpListOp.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:
                IniciarOperacao.refreshTimeOutDate();
                mExpNamesList = getExpListOp.getExpNames();
                mExpKeysList = getExpListOp.getExpKeys();

                if(mTelaEmUso!= mTelaLista){
                    Intent intent = new Intent(getExpListOp.getTelaExpedidora(),ActivityExpList.class);
                    getExpListOp.getTelaExpedidora().startActivity(intent);
                }

                mTelaLista.setExpList(mExpNamesList, mExpKeysList);

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

    private static void handleStartExp(OperationStartExp operation) {
        String responseMsg = operation.getResponseMessage();
        switch (responseMsg) {
            case Definitions.SUCCESS:
                IniciarOperacao.refreshTimeOutDate();
                mExpID = operation.getReqExpId();

                if(mTelaExpForm != null){
                    mTelaExpForm.finish();
                    mTelaExpInfo = null;
                }

                Intent intent = new Intent(operation.getTelaExpedidora(),ActivityExpForm.class);
                Bundle b = new Bundle();
                b.putString("expKey", mExpID);
                intent.putExtras(b);
                operation.getTelaExpedidora().startActivity(intent);



                break;
            case Definitions.BAD_CREDENTIALS:
                showErrorMessage(new Exception(mTelaEmUso.getString(R.string.error_bad_credentials)));
                break;
            default:
                showErrorMessage(new Exception(responseMsg));
        }

    }

    public static void showErrorMessage(Exception e){

        String msg;

        if(e instanceof JSONException){
            msg = "Não criou json";
        }else{
//            StringWriter sw = new StringWriter();
//            e.printStackTrace(new PrintWriter(sw));
//            msg = sw.toString();
            msg = e.getMessage();
        }

        AlertDialog alertDialog = new AlertDialog.Builder(mTelaEmUso).create();
        alertDialog.setTitle("Erro");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
