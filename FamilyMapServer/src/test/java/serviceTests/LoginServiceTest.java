package serviceTests;

import apiContract.LoginRequest;
import apiContract.RegisterRequest;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidPasswordException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.LoginService;
import services.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    private LoginService service;
    private RegisterService registerService;

    @BeforeEach
    public void setup()
    {
        try {
            service = new LoginService();
            registerService = new RegisterService();
        } catch (Exception ex)
        {
            fail();
        }
    }

    @Test
    public void loginSuccess()
    {
        try {
            registerService.register(new RegisterRequest("user1", "password", "test@test.com", "cannon", "farr", "m"));
            var request = new LoginRequest("user1", "password");
            var response = service.login(request);
            assertNotNull(response);
            assertEquals(request.username,response.username);
            assertNotNull(response.authtoken);
            assertNotNull(response.personID);
        }catch (Exception ex)
        {
            fail(ex.getMessage());
        }
    }

    @Test
    public void loginFailNonExistantUser(){
        var request = new LoginRequest("user1","password");
        assertThrows(EntityNotFoundException.class, () -> service.login(request));
    }

    @Test
    public void loginFailInvalidPassword()
    {
        try {
            registerService.register(new RegisterRequest("user1", "password", "test@test.com", "cannon", "farr", "m"));
            var request = new LoginRequest("user1", "someotherpassword");
            assertThrows(InvalidPasswordException.class, () -> service.login(request));
        }catch (Exception ex)
        {
            fail(ex.getMessage());
        }
    }

    @AfterEach
    public void clear() throws DataAccessException
    {
        var clearService = new ClearService();
        clearService.clear();
    }
}
