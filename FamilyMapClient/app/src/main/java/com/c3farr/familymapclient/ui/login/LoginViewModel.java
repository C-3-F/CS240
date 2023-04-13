package com.c3farr.familymapclient.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Patterns;

import com.c3farr.familymapclient.R;
import com.c3farr.familymapclient.backgroundTasks.GetPersonDetailsTask;
import com.c3farr.familymapclient.backgroundTasks.LoginTask;
import com.c3farr.familymapclient.backgroundTasks.RegisterTask;
import com.c3farr.familymapclient.http.HttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;

import apiContract.LoginRequest;
import apiContract.LoginResponse;
import apiContract.PersonDetailsResponse;
import apiContract.PersonRequest;
import apiContract.RegisterRequest;
import apiContract.RegisterResponse;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<AuthResult> registerResult = new MutableLiveData<>();

    LoginViewModel() {
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<AuthResult> getLoginResult() {
        return loginResult;
    }

    LiveData<AuthResult> getRegisterResult() {return registerResult; }

    public void login(String username, String password, String serverName, String serverPort) {
        LoginRequest request = new LoginRequest(username,password);

        Handler loginHandler = new Handler() {
            @Override
            public void handleMessage(Message message){
                Bundle bundle = message.getData();
                String authToken = bundle.getString("authToken");
                String personId = bundle.getString("personId");
                Boolean success = bundle.getBoolean("success");
                Log.d("LoginViewModel","LoginCallback");
                if (success)
                {
                    getPersonDetails(authToken,personId,serverName,serverPort);
                } else {
                    loginResult.setValue(new AuthResult(R.string.login_failed));
                }
            }
        };

        LoginTask task = new LoginTask(loginHandler,request,serverName,serverPort);
        ExecutorService executer = Executors.newSingleThreadExecutor();
        executer.submit(task);
    }

    public void register(String username, String password, String serverName, String serverPort, String firstName, String lastName, String email,String gender)
    {
        RegisterRequest request = new RegisterRequest(username,password,email,firstName,lastName,gender);

        Handler handleRegister = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                String authToken = bundle.getString("authToken");
                String personId = bundle.getString("personId");
                Boolean success = bundle.getBoolean("success");
                Log.d("loginViewModel","Register Success: "+success);
                if (success)
                {
                    getPersonDetails(authToken,personId,serverName,serverPort);
                } else {
                    loginResult.setValue(new AuthResult(R.string.register_failed));
                }
            }
        };

        RegisterTask task = new RegisterTask(handleRegister,request,serverName,serverPort);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
    }

    public void getPersonDetails(String authToken, String personId, String serverName, String serverPort)
    {
        PersonRequest personRequest = new PersonRequest(authToken,personId);
        Log.d("viewModel","getPersonDetails() Called");
        Handler getPersonDetailsHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d("LoginViewModel","PersonDetailsCallback");
                Bundle bundle = msg.getData();
                String firstName = bundle.getString("firstName");
                String lastName = bundle.getString("lastName");
                Boolean success = bundle.getBoolean("success");

                if (success)
                {
                    loginResult.setValue(new AuthResult(new LoggedInUserView(firstName,lastName)));
                } else {
                    loginResult.setValue(new AuthResult(R.string.login_failed));
                }
            }
        };

        GetPersonDetailsTask personDetailsTask = new GetPersonDetailsTask(getPersonDetailsHandler,personRequest,serverName,serverPort);
        ExecutorService personDetailsExecuter = Executors.newSingleThreadExecutor();
        personDetailsExecuter.submit(personDetailsTask);
    }






    public void loginDataChanged(String username, String password, String serverName, String serverPort, String firstName, String lastName, String email) {
        if (serverPort.isEmpty() || serverName.isEmpty()) {
            loginFormState.setValue(new LoginFormState(false, false));
        } else if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            loginFormState.setValue(new LoginFormState(true,false));
        } else {
            loginFormState.setValue(new LoginFormState(true,true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}