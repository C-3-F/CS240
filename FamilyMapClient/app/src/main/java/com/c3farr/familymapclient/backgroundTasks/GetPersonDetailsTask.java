package com.c3farr.familymapclient.backgroundTasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.c3farr.familymapclient.DataCache;
import com.c3farr.familymapclient.http.HttpClient;

import apiContract.LoginResponse;
import apiContract.PersonDetailsResponse;
import apiContract.PersonRequest;

public class GetPersonDetailsTask implements Runnable{
    private final Handler handler;
    private String request;
    private HttpClient httpClient;

    public GetPersonDetailsTask(Handler handler, String personId)
    {
        this.handler = handler;
        this.request = personId;
    }

    @Override
    public void run() {
        Log.d("PersonTask","running Person");
        httpClient = DataCache.getInstance().httpClient;
        Log.d("PersonTask","httpClientCreated");
        PersonDetailsResponse response = httpClient.getPersonDetails(request);
        sendMessage(response);
    }

    private void sendMessage(PersonDetailsResponse data) {
        Log.d("PersonTask","sending message");
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        if (data == null)
        {
            messageBundle.putBoolean("success",false);
        } else {
            messageBundle.putString("firstName", data.firstName);
            messageBundle.putString("lastName",data.lastName);
            messageBundle.putString("personId",data.personID);
            messageBundle.putString("associatedUsername",data.associatedUsername);
            messageBundle.putString("fatherId",data.fatherID);
            messageBundle.putString("motherId",data.motherID);
            messageBundle.putString("spouseId",data.spouseID);
            messageBundle.putBoolean("success",data.success);
        }
        message.setData(messageBundle);
        handler.sendMessage(message);

    }
}
