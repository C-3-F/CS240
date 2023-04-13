package com.c3farr.familymapclient.backgroundTasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.c3farr.familymapclient.http.HttpClient;

import apiContract.LoginResponse;
import apiContract.PersonDetailsResponse;
import apiContract.PersonRequest;

public class GetPersonDetailsTask implements Runnable{
    private final Handler handler;
    private PersonRequest request;
    private String serverName;
    private String serverPort;

    public GetPersonDetailsTask(Handler handler, PersonRequest request, String servername, String serverPort)
    {
        this.handler = handler;
        this.request = request;
        this.serverName = servername;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        Log.d("PersonTask","running Person");
        HttpClient httpClient = new HttpClient("http://"+serverName+":"+serverPort);
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
