package models;

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
     * Type of the event (birth, death, marraige, baptism, christening, etc...)
     */
    public String eventType;

    /**
     * year the event took place
     */
    public int year;
}
