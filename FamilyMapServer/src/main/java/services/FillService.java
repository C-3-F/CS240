package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import apiContract.FillRequest;
import apiContract.FillResponse;
import dataAccess.PersonDAO;
import dataAccess.UserDAO;

/**
 * This service is responsible for inserting generated data into the database
 * for a specified username
 */
public class FillService {
    private UserDAO userDAO;
    private PersonDAO personDAO;
    /**
     * Fills the database with generated data for a specified username
     * 
     * @param request The request object that contains given parameters
     * @return A response object that gives a status of the call outcomes.
     */
    public FillResponse fill(FillRequest request) {


        var User = userDAO.getUserById(request.username);
        var person0 = new Person();


        return new FillResponse();
    }

    private ArrayList<Integer> generateYears(int min, int max, int count) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        var rand = new Random();
        for (int i = 0; i < count; i++) {
            int newNum = rand.nextInt(max - min) + min;
            nums.add(newNum);
        }
        Collections.sort(nums);
        return nums;
    }
}
