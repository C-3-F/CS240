package com.c3farr.familymapclient.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.c3farr.familymapclient.databinding.FragmentLoginBinding;

import com.c3farr.familymapclient.R;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private Listener listener;

    public interface Listener {
        void notifySwitch();
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new LoginViewModel();

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final EditText serverNameEditText = binding.serverName;
        final EditText serverPortEditText = binding.serverPort;
        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;
        final EditText emailEditText = binding.email;
        final RadioGroup genderSelection = binding.genderSelection;
        final Button loginButton = binding.login;
        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isLoginDataValidDataValid());
                registerButton.setEnabled(loginFormState.isRegisterDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<AuthResult>() {
            @Override
            public void onChanged(@Nullable AuthResult authResult) {
                if (authResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (authResult.getError() != null) {
                    showLoginFailed(authResult.getError());
                }
                if (authResult.getSuccess() != null) {
                    updateUiWithUser(authResult.getSuccess());
                }
            }
        });



        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),serverNameEditText.getText().toString(),serverPortEditText.getText().toString(),firstNameEditText.getText().toString(),lastNameEditText.getText().toString(), emailEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        serverNameEditText.addTextChangedListener(afterTextChangedListener);
        serverPortEditText.addTextChangedListener(afterTextChangedListener);
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),serverNameEditText.getText().toString(),serverPortEditText.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.register(usernameEditText.getText().toString(),passwordEditText.getText().toString(),serverNameEditText.getText().toString(),serverPortEditText.getText().toString(),firstNameEditText.getText().toString(),lastNameEditText.getText().toString(),emailEditText.getText().toString(),getGenderValue());
            }
        });
    }

    private String getGenderValue()
    {
        final RadioButton maleButton = binding.maleSelection;
        final RadioButton femaleButton = binding.femaleSelection;
        if (maleButton.isChecked())
        {
            return "m";
        }
        else if (femaleButton.isChecked()) {
            return "f";
        } else {
            return null;
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        // TODO : initiate successful logged in experience
        String toastContent = "First name: " + model.getFirstName() + "\nLast Name: " +model.getLastName();
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), toastContent, Toast.LENGTH_LONG).show();
            listener.notifySwitch();
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}