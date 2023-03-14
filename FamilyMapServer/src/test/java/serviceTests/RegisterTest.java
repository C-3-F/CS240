package serviceTests;
import apiContract.RegisterRequest;
import exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    private RegisterService service;
    private RegisterRequest request;
    @BeforeEach
    public void setup()
    {
        service = new RegisterService();
        request = new RegisterRequest("The Farrnacle","password","test@test.com","Cannon","Farr","m");
    }

    @Test
    public void RegisterSuccess()
    {
        try {
            var response = service.register(request);
            assertNotNull(response.personID);
            assertNotNull(response.authtoken);
            assertEquals(response.username,request.username);
        } catch (Exception ex)
        {
            fail(ex.getMessage());
        }
    }

    @Test
    public void RegisterFail()
    {
        request = new RegisterRequest(null,null,null,null,null,null);
        assertThrows(NullPointerException.class,() -> service.register(request));
    }

    @AfterEach
    public void clear() throws DataAccessException
    {
        var clearService = new ClearService();
        clearService.clear();
    }
}
