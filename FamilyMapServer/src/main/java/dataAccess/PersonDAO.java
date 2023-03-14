package dataAccess;

import models.Person;
import exceptions.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * The DAO that interacts with the Person Database. It has methods to Get,
 * Create, and Clear records.
 */
public class PersonDAO {

    private final Connection _conn;

    public PersonDAO(Connection conn) {
        _conn = conn;
    }

    /**
     * Gets a person object by their ID
     * 
     * @param personID the personID to retrieve
     * @return the person object
     */
    public Person getPersonById(String personID) throws DataAccessException {
        String sql = "SELECT * FROM Person WHERE personID = ?";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            var result = stmt.executeQuery();
            if (result.next()) {
                var p = new Person(result.getString("personID"), result.getString("associatedUsername"),
                        result.getString("firstName"), result.getString("lastName"), result.getString("gender"),
                        result.getString("fatherID"), result.getString("motherID"), result.getString("spouseID"));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error occurred getting person by ID in database");
        }

    }

    public ArrayList<Person> getPersonsByUsername(String username) throws DataAccessException {
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            var result = stmt.executeQuery();
            var persons = new ArrayList<Person>();
            while (result.next()) {
                persons.add(new Person(result.getString("personID"), result.getString("associatedUsername"),
                        result.getString("firstName"), result.getString("lastName"), result.getString("gender"),
                        result.getString("fatherID"), result.getString("motherID"), result.getString("spouseID")));
            }
            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error occurred getting person by Username in database");
        }
    }

    /**
     * Creates a new person in the database
     * 
     * @param person The person object to insert
     */
    public void createPerson(Person person) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, person.personID);
            stmt.setString(2, person.associatedUsername);
            stmt.setString(3, person.firstName);
            stmt.setString(4, person.lastName);
            stmt.setString(5, person.gender);
            stmt.setString(6, person.fatherID);
            stmt.setString(7, person.motherID);
            stmt.setString(8, person.spouseID);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public void updatePerson(Person person) throws DataAccessException {
        String sql = "UPDATE Person SET associatedUsername = ?, firstName = ?, lastName = ?, gender = ?, fatherID = ?, motherID = ?, spouseID = ? WHERE personID = ?;";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, person.associatedUsername);
            stmt.setString(2, person.firstName);
            stmt.setString(3, person.lastName);
            stmt.setString(4, person.gender);
            stmt.setString(5, person.fatherID);
            stmt.setString(6, person.motherID);
            stmt.setString(7, person.spouseID);
            stmt.setString(8, person.personID);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public void clearForUser(String username) throws DataAccessException {
        String sql = "DELETE from Person WHERE associatedUsername = ?";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to clear the Event table for username: " + username);
        }
    }

    /**
     * Clears the Person database and all data
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE from Person";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to clear from the Person table");
        }
    }
}
