import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the User class.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class UserTest {

    private User user;

    /**
     * Sets up the test environment before each test method is called.
     */
    @BeforeEach
    void setup()
    {
        user=new User("chaima", "chaima123456", "administrator");
    }

    /**
     * tests the constructor of the User class
     */
    @Test
    void testConstructor()
    {
        assertEquals("chaima", user.getUsername());
        assertEquals("chaima123456", user.getPassword());
        assertEquals("administrator", user.getRole());
    }

    /**
     * tests the setUsername method
     */
    @Test
    void testSetUsername()
    {
        user.setUsername("12jebri");
        assertEquals("12jebri", user.getUsername());
    }

    /**
     * tests the getUsername method
     */
    @Test
    void testGetUsername()
    {
        assertEquals("chaima", user.getUsername());
    }

    /**
     * tests the getPassword method
     */
    @Test
    void testGetPassword()
    {
        assertEquals("chaima123456", user.getPassword());
    }

    /**
     * tests the setRole method
     */
    @Test
    void testSetRole()
    {
        user.setRole("customer");
        assertEquals("customer", user.getRole());
    }

    /**
     * tests the getRole method
     */
    @Test
    void testGetRole()
    {
        assertEquals("administrator", user.getRole());
    }
}