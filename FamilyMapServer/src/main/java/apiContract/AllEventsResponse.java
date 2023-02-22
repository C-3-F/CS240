package apiContract;

import java.util.List;

import models.Event;


public class AllEventsResponse {
    /**
     * A list of all the Events that get returned
     */
    public List<Event> data;

    /**
     * Status result of whether the call was successful
     */
    public boolean success;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
