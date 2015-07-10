package com.unifei.stefano.lab_ead_app.operations;

import android.app.Activity;

import com.unifei.stefano.lab_ead_app.HttpsOperation;
import org.json.JSONException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Objects;


/**
 * Created by stefano on 10/07/15.
 */
public class IniciarOperacao {
    private static String token;
    private static String email;
    private static int timeOutLimit;
    private static int timeOutDate;

    public static void setToken(String token) {
        IniciarOperacao.token = token;
    }

    public static void setEmail(String email) {
        IniciarOperacao.email = email;
    }

    public static void setTimeOutLimit(int timeOutLimit) {
        IniciarOperacao.timeOutLimit = timeOutLimit;
    }

    public static void refreshTimeOutDate() {
        timeOutDate =  Calendar.getInstance().get(Calendar.SECOND) + timeOutLimit;
    }

    public static void iniciar(Class operationClass, Object[] arguments){

        //pega o primeiro construtor
        Constructor constructor = operationClass.getConstructors()[0];

        try {
            //cria operaçao
            Operation operation = (Operation) constructor.newInstance(arguments);

            //inicia requisiçao
            new HttpsOperation(operation).start();

        } catch (   InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    IllegalArgumentException e)
        {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    // Um metodo para cada operaçao
    public static void login(Activity sender, String email, String password) {
        try{
            OperationLogin loginOp = new OperationLogin(email, password, sender);
            new HttpsOperation(loginOp).start();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void register(Activity sender, String email, String password, String name) {
        try{
            OperationLogin loginOp = new OperationLogin(email, password, sender);
            new HttpsOperation(loginOp).start();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void startExp(Activity sender, String ExpID) {
        try{
            OperationStartExp startExpOp = new OperationStartExp(sender, IniciarOperacao.email, IniciarOperacao.token, ExpID);
            new HttpsOperation(startExpOp).start();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
