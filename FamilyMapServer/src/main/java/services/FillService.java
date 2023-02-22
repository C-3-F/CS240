package services;

import apiContract.FillRequest;
import apiContract.FillResponse;

public class FillService {
    /**
     * Fills the database with generated data for a specified username
     * @param request The request object that contains given parameters
     * @return A response object that gives a status of the call outcomes.
     */
    public FillResponse fill(FillRequest request) {
        return new FillResponse();
    }
}
