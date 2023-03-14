package models;

import java.util.Objects;

/**
 * This object represents a Family History Event such as a death, marriage or
 * birth. It has location information as well as the person data it is
 * associated with.
 */
public class Event {
    /**
     * Id of the event
     */
    public String eventID;

    /**
     * Username of the associated user to the event
     */
    public String associatedUsername;

    /**
     * PersonID of the person associated with this event
     */
    public String personID;

    /**
     * latitude of event
     */
    public float latitude;

    /**
     * longitude of event
     */
    public float longitude;

    /**
     * country of the event
     */
    public String country;

    /**
     * city of the event
     */
    public String city;

    /**
     * Type of the event (birth, death, marriage, baptism, christening, etc...)
     */
    public String eventType;

    /**
     * year the event took place
     */
    public int year;

    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude,
            String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Float.compare(event.latitude, latitude) == 0 && Float.compare(event.longitude, longitude) == 0 && year == event.year && eventID.equals(event.eventID) && associatedUsername.equals(event.associatedUsername) && personID.equals(event.personID) && country.equals(event.country) && city.equals(event.city) && eventType.equals(event.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
    }
}
