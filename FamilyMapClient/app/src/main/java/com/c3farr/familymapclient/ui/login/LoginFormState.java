package com.c3farr.familymapclient.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;

    private boolean isLoginDataValid;
    private boolean isRegisterDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isLoginDataValid = false;
        this.isRegisterDataValid = false;
    }

    LoginFormState(boolean isLoginDataValidDataValid, boolean isRegisterDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isLoginDataValid = isLoginDataValidDataValid;
        this.isRegisterDataValid = isRegisterDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isLoginDataValidDataValid() {
        return isLoginDataValid;
    }

    boolean isRegisterDataValid() {return isRegisterDataValid;}
}