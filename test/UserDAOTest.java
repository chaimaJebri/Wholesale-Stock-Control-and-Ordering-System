import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the UserDAO class.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class UserDAOTest {
    /**
     * Tests the findUser method of the UserDAO class for an existing user.
     */
    @Test
    void TestFindUser()
    {
        assertInstanceOf(User.class, new UserDAO().findUser("test"));
    }

    /**
     * Tests the findUser method of the UserDAO class for a non-existent user.
     */
    @Test
    void TestFindNonExistantUser()
    {
        assertNull(new UserDAO().findUser("TEST"));
    }

    /**
     * Tests the addUser method
     */
    @Test
    void TestAddUser()
    {
        User user=new User("test", "test", "test");
        assertFalse(new UserDAO().addUser(user));

    }

    /**
     * Tests the authenticateUser method of the UserDAO class for a successful authentication.
     */
    @Test
    void TestAuthenticateUser()
    {
        assertTrue(new UserDAO().authenticateUser("test","test"));
    }

    /**
     * Tests the authenticateUser method of the UserDAO class for a failed authentication.
     */
    @Test
    void TestFailedAuthenticateUser()
    {
        assertFalse(new UserDAO().authenticateUser("TEST","TEST"));
    }
}