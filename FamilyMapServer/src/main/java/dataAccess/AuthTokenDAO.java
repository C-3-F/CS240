package dataAccess;

import java.sql.*;

import models.AuthToken;
import exceptions.DataAccessException;

/**
 * The DAO that interacts with the AuthToken Database. It has methods to Get,
 * Create, and Clear records.
 */
public class AuthTokenDAO {
    private final Connection _conn;

    public AuthTokenDAO(Connection conn) {
        _conn = conn;
    }

    /**
     * Returns an authToken Object with an associated user from an AuthToken string
     * 
     * @param authToken The auth token to retrieve
     * @return The authToken object
     */
    public AuthToken getAuthToken(String authToken) throws DataAccessException {
        String sql = "SELECT * FROM AuthToken WHERE authtoken = ?";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            var result = stmt.executeQuery();
            if (result.next()) {
                var token = new AuthToken(result.getString("authToken"), result.getString("username"));
                return token;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error occured getting authToken from Database");
        }
    }

    /**
     * Creates a new AuthToken entity in the database
     * 
     * @param authToken The authToken object to insert
     */
    public void create(AuthToken authToken) throws DataAccessException {
        String sql = "INSERT INTO AuthToken (authToken, username) VALUES(?,?)";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.authToken);
            stmt.setString(2, authToken.username);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Unable to create new Auth Token in DB");
        }
    }

    /**
     * Clears the AuthToken table and any data inside it.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE from AuthToken";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to clear the AuthToken table");
        }
    }
}
