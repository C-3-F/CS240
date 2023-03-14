package dataAccess;

import java.sql.*;
import java.util.ArrayList;

import exceptions.DataAccessException;
import models.Event;

/**
 * The DAO that interacts with the Event Database. It has methods to Get,
 * Create, and Clear records.
 */
public class EventDAO {

    private final Connection _conn;

    public EventDAO(Connection conn) {
        _conn = conn;
    }

    /**
     * Returns an event from a given ID
     * 
     * @param eventID the eventID to retrieve
     * @return An event object
     */
    public Event getEventById(String eventID) throws DataAccessException {
        String sql = "SELECT * FROM Event WHERE eventID = ?";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            var result = stmt.executeQuery();
            if (result.next()) {
                var e = new Event(result.getString("eventID"), result.getString("associatedUsername"),
                        result.getString("personID"), result.getFloat("latitude"), result.getFloat("longitude"),
                        result.getString("country"), result.getString("city"), result.getString("eventType"),
                        result.getInt("year"));
                return e;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error getting event by ID in DB");
        }
    }

    public ArrayList<Event> getEventsByUsername(String username) throws DataAccessException {
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            var result = stmt.executeQuery();
            var events = new ArrayList<Event>();
            while (result.next()) {
                events.add(new Event(result.getString("eventID"), result.getString("associatedUsername"),
                        result.getString("personID"), result.getFloat("latitude"), result.getFloat("longitude"),
                        result.getString("country"), result.getString("city"), result.getString("eventType"),
                        result.getInt("year")));
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error getting event by Username in DB");
        }
    }

    public ArrayList<Event> getPersonsEvents(String personID) throws DataAccessException {
        String sql = "SELET * FROM Event WHERE personID = ?";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            var result = stmt.executeQuery();
            var events = new ArrayList<Event>();
            while (result.next()) {
                events.add(new Event(result.getString("eventID"), result.getString("associatedUsername"),
                        result.getString("personID"), result.getFloat("latitude"), result.getFloat("longitude"),
                        result.getString("country"), result.getString("city"), result.getString("eventType"),
                        result.getInt("year")));
            }
            return events;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error getting person events in DB");
        }
    }

    /**
     * Creates a new event to insert in the database
     * 
     * @param event the event to insert
     */
    public void createEvent(Event event) throws DataAccessException {
        String sql = "INSERT INTO Event (eventID,associatedUsername,personID,latitude,longitude,country,city,eventType,year)";

        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.setString(1, event.eventID);
            stmt.setString(2, event.associatedUsername);
            stmt.setString(3, event.personID);
            stmt.setFloat(4, event.latitude);
            stmt.setFloat(5, event.longitude);
            stmt.setString(6, event.country);
            stmt.setString(7, event.city);
            stmt.setString(8, event.eventType);
            stmt.setInt(9, event.year);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Unable to create new event in DB");
        }
    }

    /**
     * Clears the Event table and data inside it
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE from Event";
        try (PreparedStatement stmt = _conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to clear the Event table");
        }
    }
}
