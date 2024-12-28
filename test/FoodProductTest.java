import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit and Mockito test class for the FoodProduct class.
 *
 * @author Chaima Jebri
 * @version 1.2.
 */
class FoodProductTest {

    private FoodProduct product1;
    private FoodProduct product2;

    /**
     * Sets up the test environment before each test method is called.
     */
    @BeforeEach
    void setup()
    {
        product1=new FoodProduct("BN09","Pack of Bananas","Fruit",12);
        product2= new FoodProduct(9,"BN09", "Pack of Bananas", "Fruit", 12);
    }

    /**
     * tests the first constructor
     */
    @Test
    void testFirstConstructor()
    {
        assertEquals("BN09",product1.getSku());
        assertEquals("Pack of Bananas",product1.getDescription());
        assertEquals("Fruit",product1.getCategory());
        assertEquals(12,product1.getPrice());
    }

    /**
     * tests the second constructor
     */
    @Test
    void testSecondConstructor()
    {
        assertEquals(9,product2.getId());
        assertEquals("BN09",product2.getSku());
        assertEquals("Pack of Bananas",product2.getDescription());
        assertEquals("Fruit",product2.getCategory());
        assertEquals(12,product2.getPrice());
    }

    /**
     * tests the assignParameters method
     *
     * @throws SQLException if there is an SQL error.
     */
    @Test
    void testAssignParameters()throws SQLException
    {
        PreparedStatement mockPrepStmnt = Mockito.mock(PreparedStatement.class);
        product1.assignParametersProduct(mockPrepStmnt);
        Mockito.verify(mockPrepStmnt).setString(1,"BN09");
        Mockito.verify(mockPrepStmnt).setString(2,"Pack of Bananas");
        Mockito.verify(mockPrepStmnt).setString(3,"Fruit");
        Mockito.verify(mockPrepStmnt).setInt(4,12);
    }

    /**
     * tests then setId method
     */
    @Test
    void testSetId()
    {
        product2.setId(45);
        assertEquals(45,product2.getId());
    }

    /**
     * tests the getId method
     */
    @Test
    void testGetId()
    {
        assertEquals(9,product2.getId());
    }

    /**
     * tests the setSku method
     */
    @Test
    void testSetSku()
    {
        product2.setSku("MN34");
        assertEquals("MN34",product2.getSku());
    }

    /**
     * tests the getSku method
     */
    @Test
    void testGetSku()
    {
        assertEquals("BN09",product2.getSku());
    }

    /**
     * test the setDescription method
     */
    @Test
    void testSetDescription()
    {
        product2.setDescription("10KG BANANAS");
        assertEquals("10KG BANANAS",product2.getDescription());
    }

    /**
     * tests the getDescription method
     */
    @Test
    void testGetDescription()
    {
        assertEquals("Pack of Bananas",product2.getDescription());
    }

    /**
     * tests the setCategory method
     */
    @Test
    void testSetCategory()
    {
        product2.setCategory("DeliciousFruit");
        assertEquals("DeliciousFruit",product2.getCategory());
    }

    /**
     * tests the getCategory method
     */
    @Test
    void testGetCategory()
    {
        assertEquals("Fruit",product2.getCategory());
    }

    /**
     * tests the setPrice method
     */
    @Test
    void testSetPrice()
    {
        product2.setPrice(24);
        assertEquals(24,product2.getPrice());
    }

    /**
     * tests the getPrice method
     */
    @Test
    void testGetPrice()
    {
        assertEquals(12,product2.getPrice());
    }

    /**
     * tests the toHTMLString method
     */
    @Test
    void testToHtmlString()
    {
        assertEquals("<td>BN09</td><td>Pack of Bananas</td><td>Fruit</td><td>12</td>",product1.toHTMLString());
    }

    /**
     * tests the toString method
     */
    @Test
    void testToString()
    {
        assertEquals("ID:9 ,SKU:BN09 ,Description:Pack of Bananas ,Category:Fruit ,Price:12£",product2.toString());
    }

}