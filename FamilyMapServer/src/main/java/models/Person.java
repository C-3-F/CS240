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

    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender,
            String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((personID == null) ? 0 : personID.hashCode());
        result = prime * result + ((associatedUsername == null) ? 0 : associatedUsername.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((fatherID == null) ? 0 : fatherID.hashCode());
        result = prime * result + ((motherID == null) ? 0 : motherID.hashCode());
        result = prime * result + ((spouseID == null) ? 0 : spouseID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (personID == null) {
            if (other.personID != null)
                return false;
        } else if (!personID.equals(other.personID))
            return false;
        if (associatedUsername == null) {
            if (other.associatedUsername != null)
                return false;
        } else if (!associatedUsername.equals(other.associatedUsername))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (fatherID == null) {
            if (other.fatherID != null)
                return false;
        } else if (!fatherID.equals(other.fatherID))
            return false;
        if (motherID == null) {
            if (other.motherID != null)
                return false;
        } else if (!motherID.equals(other.motherID))
            return false;
        if (spouseID == null) {
            if (other.spouseID != null)
                return false;
        } else if (!spouseID.equals(other.spouseID))
            return false;
        return true;
    }
}
