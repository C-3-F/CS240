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

    public User(String username, String password, String email, String firstName, String lastName, String gender,
            String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((personID == null) ? 0 : personID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (personID == null) {
            if (other.personID != null)
                return false;
        } else if (!personID.equals(other.personID))
            return false;
        return true;
    }
}