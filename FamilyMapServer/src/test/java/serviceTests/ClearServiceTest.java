package serviceTests;

import exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService service;

    @BeforeEach
    public void setup() throws DataAccessException
    {
        service = new ClearService();
    }

    @Test
    public void clearSuccess()
    {
        var response = service.clear();
        assertTrue(response.success);
    }

}
