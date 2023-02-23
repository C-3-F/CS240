package models;

/**
 * This object represents a User of the application. Not to be confused with a
 * Person which is a Family History Object. Users have a name, email and login
 * information and their associated PersonID.
 */
public class User {
    /**
     * The Username of a user
     */
    public String username;

    /**
     * The password of a user
     */
    public String password;

    /**
     * The email of a user
     */
    public String email;

    /**
     * The first name of a user
     */
    public String firstName;

    /**
     * The last name of a user
     */
    public String lastName;

    /**
     * The gender of a user
     */
    public String gender;

    /**
     * The associated PersonID of a user
     */
    public String personID;
}