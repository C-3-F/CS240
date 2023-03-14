package apiContract;

// import lombok.Getter;
// import lombok.Setter;


/**
 * The response object returned when getting the details for a Person
 */
// @Getter
// @Setter
public class PersonDetailsResponse {
    /**
     * The unique PersonID for the person to be retrieved
     */
    public String personID;

    /**
     * The Associated username of the person
     */
    public String associatedUsername;

    /**
     * The firstName of the Person
     */
    public String firstName;

    /**
     * The last name of the person
     */
    public String lastName;

    /**
     * The gender of the person
     */
    public String gender;

    /**
     * The PersonID of the Person's Father
     */
    public String fatherID;

    /**
     * The PersonID of the person's Mother
     */
    public String motherID;

    /**
     * The PersonID of the Person's Spouse
     */
    public String spouseID;

    /**
     * A Status indicator of whether the call was successful
     */
    public boolean success;

    public PersonDetailsResponse(String personID, String associatedUsername, String firstName, String lastName,
            String gender, String fatherID, String motherID, String spouseID, boolean success) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }
}
