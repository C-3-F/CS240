package com.c3farr.familymapclient.backgroundTasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.c3farr.familymapclient.R;
import com.c3farr.familymapclient.http.HttpClient;


import apiContract.LoginRequest;
import apiContract.LoginResponse;
import apiContract.PersonDetailsResponse;
import apiContract.PersonRequest;

public class LoginTask implements Runnable{

    private final Handler messageHandler;
    private LoginRequest request;
    private String serverName;
    private String serverPort;
    public LoginTask(Handler handler, LoginRequest request, String serverName, String serverPort)
    {
        messageHandler = handler;
        this.request = request;
        this.serverName =serverName;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        HttpClient httpClient = new HttpClient("http://"+serverName+":"+serverPort);
        LoginResponse result = httpClient.login(request);
        Log.d("loginTask","preSend");

        sendMessage(result);
    }

    private void sendMessage(LoginResponse data) {
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        if (data == null)
        {
            messageBundle.putBoolean("success",false);
        } else {
            messageBundle.putString("authToken", data.authtoken);
            messageBundle.putString("personId", data.personID);
            messageBundle.putBoolean("success", data.success);
        }
        message.setData(messageBundle);
        messageHandler.sendMessage(message);

    }
}
