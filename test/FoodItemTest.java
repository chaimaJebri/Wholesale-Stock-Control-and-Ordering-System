import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit and Mockito test class for the FoodItem class.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class FoodItemTest {

    private FoodItem foodItem;

    /**
     * Sets up the test environment before each test method is called.
     *
     * @throws ParseException if there is an error parsing dates.
     */
    @BeforeEach
    void setUp() throws ParseException
    {
        FoodProduct product= new FoodProduct(1,"testSku","testDescription","testCategory",20);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date expiryDate=dateFormat.parse("2000-01-01") ;

        foodItem= new FoodItem(11,product,expiryDate);
    }

    /**
     * Tests the constructor of the FoodItem class
     *
     * @throws ParseException if there is an error parsing dates.
     */
    @Test
    void TestConstructor() throws ParseException
    {
        assertEquals(11, foodItem.getFoodItemId());
        assertEquals(1, foodItem.getFoodProduct().getId());
        assertEquals("testSku", foodItem.getFoodProduct().getSku());
        assertEquals("testDescription", foodItem.getFoodProduct().getDescription());
        assertEquals("testCategory", foodItem.getFoodProduct().getCategory());
        assertEquals(20, foodItem.getFoodProduct().getPrice());

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date expectedExpiryDate= dateFormat.parse("2000-01-01");

        assertEquals(expectedExpiryDate, foodItem.getExpiryDate());
    }

    /**
     * tests the assignParameters method
     *
     * @throws SQLException if there is an SQL error.
     * @throws ParseException if there is an error parsing dates.
     */
    @Test
    void TestAssignParameters() throws SQLException, ParseException
    {
        PreparedStatement mockPrepStmnt = Mockito.mock(PreparedStatement.class);
        foodItem.assignParameters(mockPrepStmnt);
        Mockito.verify(mockPrepStmnt).setInt(1,11);
        Mockito.verify(mockPrepStmnt).setInt(2, 1);

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date expiryDate= dateFormat.parse("2000-01-01");

        Mockito.verify(mockPrepStmnt).setDate(3,new java.sql.Date(expiryDate.getTime()));
    }

    /**
     * tests the setFoodItemID method
     */
    @Test
    void TestSetFoodItemId()
    {
        foodItem.setFoodItemId(12);
        assertEquals(12, foodItem.getFoodItemId());
    }

    /**
     * tests the getFoodItemId method
     */
    @Test
    void TestGetFoodItemId()
    {
        assertEquals(11, foodItem.getFoodItemId());
    }

    /**
     * tests the setFoodProduct method
     */
    @Test
    void TestSetFoodProduct()
    {
        FoodProduct newProduct= new FoodProduct(10,"test", "test", "test", 25);
        foodItem.setFoodProduct(newProduct);
        assertEquals(newProduct, foodItem.getFoodProduct());
    }

    /**
     * tests the getFoodProduct method
     */
    @Test
    void TestGetFoodProduct()
    {
        assertEquals(1, foodItem.getFoodProduct().getId());
        assertEquals("testSku", foodItem.getFoodProduct().getSku());
        assertEquals("testDescription", foodItem.getFoodProduct().getDescription());
        assertEquals("testCategory", foodItem.getFoodProduct().getCategory());
        assertEquals(20, foodItem.getFoodProduct().getPrice());
    }

    /**
     * tests the setExpiryDate and getExpiryDate methods
     *
     * @throws ParseException if there is an error parsing dates.
     */
    @Test
    void TestSetAndGetExpiryDate() throws ParseException
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date NewexpiryDate= dateFormat.parse("2000-01-01");
        foodItem.setExpiryDate(NewexpiryDate);
        assertEquals(NewexpiryDate, foodItem.getExpiryDate());
    }

    /**
     * tests the toHTMLString method
     */
    @Test
    void TestToHTMLString()
    {
        assertEquals("<td>11</td><td>ID:1 ,SKU:testSku ,Description:testDescription ,Category:testCategory ,Price:20£</td><td>Sat Jan 01 00:00:00 GMT 2000</td>",foodItem.toHTMLString());
    }

    /**
     * tests the toString method
     */
    @Test
    void TestToString()
    {
        assertEquals("Food Item ID: 11, Food Product: ID:1 ,SKU:testSku ,Description:testDescription ,Category:testCategory ,Price:20£, Expiry Date: Sat Jan 01 00:00:00 GMT 2000", foodItem.toString());
    }
}