import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the Address class.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class AddressTest {

    private Address address;

    /**
     * Sets up the test environment before each test method is called.
     */
    @BeforeEach
    void setup()
    {
        address=new Address("Main Street", "Flat1", "Manchester","The UK", "123456");
    }

    /**
     * Tests the constructor of the address class
     */
    @Test
    void testConstructor()
    {
        assertEquals("Main Street", address.getAdrLine1());
        assertEquals("Flat1", address.getAdrLine2());
        assertEquals("Manchester", address.getAdrLine3());
        assertEquals("The UK", address.getCountry());
        assertEquals("123456", address.getPostCode());
    }

    /**
     * Tests the set address line 1 method
     */
    @Test
    void testSetAdrLine1()
    {
        address.setAdrLine1("flower street");
        assertEquals("flower street",address.getAdrLine1());
    }

    /**
     * Tests the get address line 1 method
     */
    @Test
    void testGetAdrLin1()
    {
        assertEquals("Main Street",address.getAdrLine1());
    }

    /**
     * Tests the set address line 2 method
     */
    @Test
    void testSetAdrLine2()
    {
        address.setAdrLine2("flat10");
        assertEquals("flat10",address.getAdrLine2());
    }

    /**
     * Tests the get address line 2 method
     */
    @Test
    void testGetAdrLin2()
    {
        assertEquals("Flat1",address.getAdrLine2());
    }

    /**
     * Tests the set address line 3 method
     */
    @Test
    void testSetAdrLine3()
    {
        address.setAdrLine3("Leeds");
        assertEquals("Leeds",address.getAdrLine3());
    }

    /**
     * Tests the get address line 3 method
     */
    @Test
    void testGetAdrLin3()
    {
        assertEquals("Manchester",address.getAdrLine3());
    }

    /**
     * Tests the set country method
     */
    @Test
    void testSetCountry()
    {
        address.setCountry("Tunisia");
        assertEquals("Tunisia",address.getCountry());
    }

    /**
     * Tests the get country method
     */
    @Test
    void testGetCountry()
    {
        assertEquals("The UK", address.getCountry());
    }

    /**
     * Tests the set postcode method
     */
    @Test
    void testSetPostcode()
    {
        address.setPostCode("78910");
        assertEquals("78910",address.getPostCode());
    }

    /**
     * Tests the get postcode method
     */
    @Test
    void testGetPostcode()
    {
        assertEquals("123456", address.getPostCode());
    }

    /**
     * Tests the toString method
     */
    @Test
    void testToString()
    {
        assertEquals("Main Street, Flat1, Manchester, The UK, 123456", address.toString());
    }

}