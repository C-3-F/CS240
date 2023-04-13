package com.c3farr.familymapclient.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class AuthResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    AuthResult(@Nullable Integer error) {
        this.error = error;
    }

    AuthResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}