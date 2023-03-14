package services;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;

import apiContract.FillRequest;
import apiContract.FillResponse;
import dataAccess.Database;
import dataAccess.EventDAO;
import dataAccess.PersonDAO;
import dataAccess.UserDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;
import models.Event;
import models.Person;
import models.User;
import services.serviceModels.*;

/**
 * This service is responsible for inserting generated data into the database
 * for a specified username
 */
public class FillService {
    private UserDAO userDAO;
    private PersonDAO personDAO;
    private EventDAO eventDAO;
    private Database db;
    private ArrayList<String> maleNames;
    private ArrayList<String> femaleNames;
    private ArrayList<String> surnames;
    private ArrayList<Location> locations;
    private boolean success = true;
    private String outMessage = "";
    private int personCount = 0;
    private int eventCount = 0;

    public FillService() throws IOException, DataAccessException {

        db = new Database();

        userDAO = new UserDAO(db.getConnection());
        personDAO = new PersonDAO(db.getConnection());
        eventDAO = new EventDAO(db.getConnection());

        var gson = new Gson();
        String json = new String(Files.readAllBytes(Paths.get("json/locations.json")));
        locations = gson.fromJson(json, LocationData.class).data;

        json = new String(Files.readAllBytes(Paths.get("json/fnames.json")));
        femaleNames = gson.fromJson(json, NameData.class).data;

        json = new String(Files.readAllBytes(Paths.get("json/mnames.json")));
        maleNames = gson.fromJson(json, NameData.class).data;

        json = new String(Files.readAllBytes(Paths.get("json/snames.json")));
        surnames = gson.fromJson(json, NameData.class).data;
    }

    /**
     * Fills the database with generated data for a specified username
     * 
     * @param request The request object that contains given parameters
     * @return A response object that gives a status of the call outcomes.
     */
    public FillResponse fill(FillRequest request) {
        try {
            var user = userDAO.getUserById(request.username);
            if (user == null)
            {
                throw new EntityNotFoundException("Username doesn't exist");
            }
            personDAO.clearForUser(user.username);
            eventDAO.clearForUser(user.username);
            var userPerson = generateUserPerson(user);
            user.personID = userPerson.personID;
            userDAO.updateUser(user);
            fillHelper(1, request.generations, userPerson);
            success = true;
            outMessage = "Successfully added " + personCount + " persons and " + eventCount + " events to the database";
        } catch (DataAccessException ex) {
            success = false;
            outMessage = "Failure to retrieve user in fill service";
        } catch(EntityNotFoundException ex) {
            success = false;
            outMessage = "Username doesn't exist";
        }finally
         {
            db.closeConnection(success);
        }
        return new FillResponse(outMessage, success);

    }

    public void fillHelper(int currGen, int generations, Person child) {
        if (currGen <= generations) {
            var parents = generateCouple(child);
            generateParentEvents(child.personID);
            fillHelper(currGen + 1, generations, parents.get(0));
            fillHelper(currGen + 1, generations, parents.get(1));
        }
    }

    private Person generateUserPerson(User user) {
        int baseBirthday = generateRandom(1995, 2005, 1).get(0);
        var id = UUID.randomUUID().toString();
        var person0 = new Person(id, user.username, user.firstName, user.lastName, user.gender, null, null, null);
        try {

            personDAO.createPerson(person0);
            personCount += 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            success = false;
            outMessage = "Failure to create user person";
        }
        generateEvent(person0.personID, baseBirthday, getRandomLocation(), "birth");
        return person0;
    }

    private ArrayList<Person> generateCouple(Person child) {
        // Father Info
        var fatherID = UUID.randomUUID().toString();
        var fatherFirstName = getRandomMaleName();
        var fatherSurname = child.lastName;

        // Mother
        var motherID = UUID.randomUUID().toString();
        var motherFirstName = getRandomFemaleName();
        var motherSurname = getRandomSurname();

        String fatherSpouse = motherID;
        String motherSpouse = fatherID;

        var father = new Person(fatherID, child.associatedUsername, fatherFirstName, fatherSurname, "m", null, null,
                fatherSpouse);
        var mother = new Person(motherID, child.associatedUsername, motherFirstName, motherSurname, "f", null, null,
                motherSpouse);

        var parentList = new ArrayList<Person>();
        parentList.add(father);
        parentList.add(mother);

        try {

            personDAO.createPerson(father);
            personDAO.createPerson(mother);
            personCount += 2;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            success = false;
            outMessage = "Failure to create parents in FillService";
        }

        child.fatherID = fatherID;
        child.motherID = motherID;
        try {

            personDAO.updatePerson(child);
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            success = false;
            outMessage = "Failure to update childs parents";
        }

        return parentList;
    }

    private void generateParentEvents(String childID) {
        try {

            var child = personDAO.getPersonById(childID);
            var childEvents = eventDAO.getPersonsEvents(childID);
            // Get child birthday
            int childBirthday = 0;
            for (int i = 0; i < childEvents.size(); i++) {
                if (childEvents.get(i).eventType.equals("birth")) {
                    childBirthday = childEvents.get(i).year;
                }
            }

            // Father
            int fBirth = childBirthday - generateRandom(18,35, 1).get(0);
            Location marrLocation = getRandomLocation();
            int fDeath = fBirth + generateRandom(50, 105, 1).get(0);



            // Mother
            int mbirth = childBirthday - generateRandom(18, 35, 1).get(0);
            int highBirth = fBirth > mbirth ? fBirth : mbirth;
            int marr = generateRandom(highBirth + 15, childBirthday + 5, 1).get(0);
            int mDeath = mbirth + generateRandom(50, 105, 1).get(0);
            generateEvent(child.fatherID, fBirth, getRandomLocation(), "birth");
            generateEvent(child.fatherID, marr, marrLocation, "marriage");
            generateEvent(child.fatherID, fDeath, getRandomLocation(), "death");
            generateEvent(child.motherID, mbirth, getRandomLocation(), "birth");
            generateEvent(child.motherID, marr, marrLocation, "marriage");
            generateEvent(child.motherID, mDeath, getRandomLocation(), "death");
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            success = false;
            outMessage = "Failure to retrieve child record and events";
        }
    }

    private void generateEvent(String personID, int year, Location location, String eventType) {
        try {
            var person = personDAO.getPersonById(personID);
            var eventID = UUID.randomUUID().toString();

            var event = new Event(eventID, person.associatedUsername, personID, location.latitude, location.longitude,
                    location.country, location.city, eventType, year);

            eventDAO.createEvent(event);
            eventCount += 1;
        } catch (DataAccessException ex) {
            success = false;
            outMessage = "Failure to write events to database";
        }
    }

    private ArrayList<Integer> generateRandom(int min, int max, int count) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        var rand = new Random();
        for (int i = 0; i < count; i++) {
            int newNum = rand.nextInt(max - min) + min;
            nums.add(newNum);
        }
        Collections.sort(nums);
        return nums;
    }

    private String getRandomFemaleName() {
        return femaleNames.get(generateRandom(0, 145, 1).get(0));
    }

    private String getRandomMaleName() {
        return maleNames.get(generateRandom(0, 143, 1).get(0));
    }

    private String getRandomSurname() {
        return surnames.get(generateRandom(0, 150, 1).get(0));

    }

    private Location getRandomLocation() {
        return locations.get(generateRandom(0, 975, 1).get(0));
    }
}
