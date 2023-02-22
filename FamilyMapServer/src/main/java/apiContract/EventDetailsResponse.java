package apiContract;

public class EventDetailsResponse {
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

    /**
     * Status result of whether or not the call was successful
     */
    public boolean success;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
