package dataAccess;

import java.sql.*;

import exceptions.DataAccessException;
import models.User;

/**
 * The DAO that interacts with the User Database. It has methods to Get, Create,
 * and Clear records.
 */
public class UserDAO {

    private final Connection _conn;

    public UserDAO(Connection conn) {
        _conn = conn;
    }

    /**
     * Gets a user by a given userID
     * 
     * @param username The userID to retrieve
     * @return The User object
     */
    public User getUserById(String username) throws DataAccessException {
        String sql = "SELECT * FROM User WHERE username = ?";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            var result = stmt.executeQuery();
            if (result.next()) {
                var p = new User(result.getString("username"), result.getString("password"), result.getString("email"),
                        result.getString("firstName"), result.getString("lastName"), result.getString("gender"),
                        result.getString("personID"));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error occurred getting person by ID in database");
        }
    }

    /**
     * Creates a new user in the database
     * 
     * @param user the User object to insert
     */
    public void createUser(User user) throws DataAccessException {
        String sql = "INSERT INTO User (username,password,email,firstName,lastName,gender,personID) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, user.username);
            stmt.setString(2, user.password);
            stmt.setString(3, user.email);
            stmt.setString(4, user.firstName);
            stmt.setString(5, user.lastName);
            stmt.setString(6, user.gender);
            stmt.setString(7, user.personID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to create user in database");
        }
    }




    public void updateUser(User user) throws DataAccessException {
        String sql = "UPDATE User password = ?,email = ?,firstName = ?,lastName = ?,gender = ?,personID = ? WHERE username = ?";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(2, user.password);
            stmt.setString(3, user.email);
            stmt.setString(4, user.firstName);
            stmt.setString(5, user.lastName);
            stmt.setString(6, user.gender);
            stmt.setString(7, user.personID);
            stmt.setString(1, user.username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to create user in database");
        }
    }

    /**
     * Clears the User table and data inside it
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE from User";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to clear the User table");
        }
    }

}
