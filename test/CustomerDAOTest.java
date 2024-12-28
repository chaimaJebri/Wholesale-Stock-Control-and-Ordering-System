import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the CustomerDAO class.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class CustomerDAOTest {
    /**
     * Tests the findAllCustomers method of the CustomerDAO class.
     */
    @Test
    void TestFindAllCustomers()
    {
        assertInstanceOf(ArrayList.class, new CustomerDAO().findAllCustomers());
    }

    /**
     * Tests the findCustomer method of the CustomerDAO class.
     */
    @Test
    void TestFindCustomer()
    {
        assertInstanceOf(Customer.class, new CustomerDAO().findCustomer(4));
    }

    /**
     * Tests the findCustomer method with a non-existent customer ID.
     */
    @Test
    void TestFindNonExistentCustomer()
    {
        assertNull(new CustomerDAO().findCustomer(300));
    }

    /**
     * Tests the updateCustomer method of the CustomerDAO class.
     */
    @Test
    void TestUpdateCustomer()
    {
        Address address = new Address("Royce Road","Manchester", "ls1", "United Kingdom","M11 YO3");
        Customer customer = new Customer(4,"The Fresh Store", address,"+44 77354668");
        assertTrue(new CustomerDAO().updateCustomer(customer));
    }

    /**
     * Tests the updateCustomer method with a non-existent customer ID.
     */
    @Test
    void TestUpdateNonExistentCustomer()
    {
        Address address = new Address("test","test", "test", "test","test");
        Customer customer = new Customer(300,"test", address,"test");
        assertFalse(new CustomerDAO().updateCustomer(customer));
    }

    /**
     * Tests the addCustomer method of the CustomerDAO class.
     */
    @Test
    void TestAddCustomer()
    {
        Address address = new Address(".",".", ".", ".",".");
        Customer customer = new Customer("For Test", address,"+345674224");
        assertTrue(new CustomerDAO().addCustomer(customer));
    }

    /**
     * Tests the deleteCustomer method of the CustomerDAO class.
     */
    @Test
    void TestDeleteCustomer()
    {
        assertFalse(new CustomerDAO().deleteCustomer(300));
    }

    /**
     * Tests the searchCustomersByBusinessName method of the CustomerDAO class.
     */
    @Test
    void TestSearchCustomersByBusinessName()
    {
        String businessName="yummy store";
        assertInstanceOf(ArrayList.class, new CustomerDAO().searchCustomersByBusinessName(businessName));
    }

}