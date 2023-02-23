package models;

/**
 * This object represents a person in the Family Tree. It has relationships to
 * other person objects representing family members. It also connects to an
 * associated User object.
 */
public class Person {
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
}
