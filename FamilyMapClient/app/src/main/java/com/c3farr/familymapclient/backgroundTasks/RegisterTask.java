package com.c3farr.familymapclient.backgroundTasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.c3farr.familymapclient.DataCache;
import com.c3farr.familymapclient.http.HttpClient;

import apiContract.RegisterRequest;
import apiContract.RegisterResponse;

public class RegisterTask implements Runnable{
    private Handler handler;
    private RegisterRequest request;
    private HttpClient httpClient;

    public RegisterTask(Handler handler, RegisterRequest request) {
        this.handler = handler;
        this.request = request;
    }

    @Override
    public void run() {
        Log.d("registerTask","Register task called");
        httpClient = DataCache.getInstance().httpClient;
        RegisterResponse response = httpClient.register(request);
        Log.d("registerTask","preSend");
        sendMessage(response);
    }


    private void sendMessage(RegisterResponse data)
    {
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();
        if(data == null)
        {
            messageBundle.putBoolean("success",false);

        } else {
            messageBundle.putString("authToken",data.authtoken);
            messageBundle.putString("personId",data.personID);
            messageBundle.putBoolean("success",data.success);
        }
        message.setData(messageBundle);
        handler.sendMessage(message);
    }
}
