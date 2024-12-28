import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit and Mockito test class for the Customer class.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class CustomerTest {

    private Customer customer1;
    private Customer customer2;

    /**
     * Sets up the test environment before each test method is called.
     */
    @BeforeEach
    void setup()
    {
        Address address = new Address("Main Street", "Flat1", "Manchester", "The UK", "123456");
        customer1= new Customer(1, "the small shop", address,"077458705869");
        customer2= new Customer("the small shop", address,"077458705869");
    }

    /**
     * Tests the first constructor of the customer class
     */
    @Test
    void testFirstConstructor()
    {
        assertEquals(1, customer1.getCustomerID());
        assertEquals("the small shop", customer1.getBusinessName());
        assertEquals("Main Street", customer1.getAddress().getAdrLine1());
        assertEquals("Flat1", customer1.getAddress().getAdrLine2());
        assertEquals("Manchester", customer1.getAddress().getAdrLine3());
        assertEquals("The UK", customer1.getAddress().getCountry());
        assertEquals("123456", customer1.getAddress().getPostCode());
        assertEquals("077458705869", customer1.getTelephoneNumber());
    }

    /**
     * Tests the second constructor of the customer class
     */
    @Test
    void testSecondConstructor()
    {
        assertEquals("the small shop", customer2.getBusinessName());
        assertEquals("Main Street", customer2.getAddress().getAdrLine1());
        assertEquals("Flat1", customer2.getAddress().getAdrLine2());
        assertEquals("Manchester", customer2.getAddress().getAdrLine3());
        assertEquals("The UK", customer2.getAddress().getCountry());
        assertEquals("123456", customer2.getAddress().getPostCode());
        assertEquals("077458705869", customer2.getTelephoneNumber());
    }

    /**
     * tests the assignParameters method in the customer class
     *
     * @throws SQLException if there is an SQL error.
     */
    @Test
    void testAssignParameters() throws SQLException
    {
        PreparedStatement mockPrepStmnt=Mockito.mock(PreparedStatement.class);
        customer2.assignParametersCustomer(mockPrepStmnt);
        Mockito.verify(mockPrepStmnt).setString(1,"the small shop");
        Mockito.verify(mockPrepStmnt).setString(2,"Main Street");
        Mockito.verify(mockPrepStmnt).setString(3,"Flat1");
        Mockito.verify(mockPrepStmnt).setString(4,"Manchester");
        Mockito.verify(mockPrepStmnt).setString(5,"The UK");
        Mockito.verify(mockPrepStmnt).setString(6,"123456");
        Mockito.verify(mockPrepStmnt).setString(7,"077458705869");
    }

    /**
     * tests the setCustomerID method
     */
    @Test
    void testSetCustomerID()
    {
        customer1.setCustomerID(10);
        assertEquals(10,customer1.getCustomerID());
    }

    /**
     * tests the getCustomerID method
     */
    @Test
    void testGetCustomerID()
    {
        assertEquals(1,customer1.getCustomerID());
    }

    /**
     * tests the setBusinessName method
     */
    @Test
    void testSetBusinessName()
    {
        customer1.setBusinessName("the lovely shop");
        assertEquals("the lovely shop",customer1.getBusinessName());
    }

    /**
     * Tests the getBusinessName method
     */
    @Test
    void testGetBusinessName()
    {
        assertEquals("the small shop", customer1.getBusinessName());
    }

    /**
     * tests the setAddress and getAddress methods
     */
    @Test
    void testSetAndGetAddress()
    {
        Address adr2= new Address("Elland Road","FlatNumber5","Leeds","The UK","123456789");
        customer1.setAddress(adr2);
        assertEquals(adr2,customer1.getAddress());
    }

    /**
     * tests the setTelephoneNumber method
     */
    @Test
    void testSetTelephoneNumber()
    {
        customer1.setTelephoneNumber("987654321");
        assertEquals("987654321", customer1.getTelephoneNumber());
    }

    /**
     * tests the getTelephoneNumber method
     */
    @Test
    void testGetTelephoneNumber()
    {
        assertEquals("077458705869",customer1.getTelephoneNumber());
    }

    /**
     * tests the toHTMLString method
     */
    @Test
    void testToHtmlString()
    {
        assertEquals("<td>the small shop</td><td>Main Street, Flat1, Manchester, The UK, 123456</td><td>077458705869</td>",customer2.toHTMLString());
    }

    /**
     * Tests the toString method
     */
    @Test
    void testToString()
    {
        assertEquals("Customer ID: 1, Business Name: the small shop, Address: Main Street, Flat1, Manchester, The UK, 123456, Telephone Number: 077458705869", customer1.toString());
    }

}