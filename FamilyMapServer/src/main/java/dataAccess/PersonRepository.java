package dataAccess;

import models.Person;

public class PersonRepository {
    /**
     * Gets a person object by their ID
     * @param personID the personID to retrieve
     * @return the person object
     */
    public Person getPersonById(int personID) {
        return new Person();
    }


    /**
     * Creates a new person in the database
     * @param person The person object to insert
     */
    public void createPerson(Person person) {

    }


    /**
     * Clears the Person database and all data
     */
    public void clear() {

    }
}
