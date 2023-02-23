package dataAccess;

import models.User;


/**
 * The DAO that interacts with the User Database. It has methods to Get, Create, and Clear records.
 */
public class UserDAO {
    /**
     * Gets a user by a given userID
     * 
     * @param userId The userID to retrieve
     * @return The User object
     */
    public User getUserById(int userId) {
        return new User();
    }

    /**
     * Creates a new user in the database
     * 
     * @param user the User object to insert
     */
    public void createUser(User user) {
    }

    /**
     * Clears the User table and data inside it
     */
    public void clear() {

    }

}
