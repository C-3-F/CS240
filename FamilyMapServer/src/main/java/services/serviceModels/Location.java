package services.serviceModels;

public class Location {
    public String country;
    public String city;
    public float longitude;
    public float latitude;

    public Location(String country, String city, float longitude, float latitude) {
        this.country = country;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
