package com.c3farr.familymapclient.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String firstName;
    private String lastName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    String getFirstName() {
        return firstName;
    }
    String getLastName() { return lastName; }
}