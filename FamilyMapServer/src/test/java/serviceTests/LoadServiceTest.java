package serviceTests;

import apiContract.LoadRequest;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.LoadService;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
public class LoadServiceTest {
    private LoadService service;
    private String filePath = "passoffFiles/LoadData.json";
    private LoadRequest request;


    @BeforeEach
    public void setup() throws DataAccessException, FileNotFoundException
    {
        Gson GSON = new Gson();
        service = new LoadService();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        //We are creating a LoadRequest from the JsonReader we made
        request = GSON.fromJson(jsonReader, LoadRequest.class);
    }

    @Test
    public void LoadSuccess()
    {
        var response = service.load(request);
        assertTrue(response.success);
    }

    @Test
    public void LoadFail()
    {

    }


    @AfterEach
    public void clear() throws DataAccessException
    {
        var clearService = new ClearService();
        clearService.clear();
    }
}
