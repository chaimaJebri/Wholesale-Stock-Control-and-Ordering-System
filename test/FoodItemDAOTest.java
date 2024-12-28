import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the FoodItemDAO class
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class FoodItemDAOTest {
    /**
     * Tests the findAllItems method of the FoodItemDAO class.
     */
    @Test
    void TestFindAllItems()
    {
        assertInstanceOf(ArrayList.class, new FoodItemDAO(new FoodProductDAO()).findAllItems());
    }

    /**
     * Tests the findItem method of the FoodItemDAO class.
     */
    @Test
    void TestFindItem()
    {
        assertInstanceOf(FoodItem.class,new FoodItemDAO(new FoodProductDAO()).findItem(31));
    }

    /**
     * Tests the findItem method with a non-existent item ID.
     */
    @Test
    void TestFindNonExistantItem()
    {
        assertNull(new FoodItemDAO(new FoodProductDAO()).findItem(300));
    }

    /**
     * Tests the addItem method of the FoodItemDAO class.
     *
     * @throws ParseException if there is an error parsing dates.
     */
    @Test
    void TestAddItem() throws ParseException
    {
        FoodProduct product = new FoodProduct(3,"test", "test","test", 20);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date expiryDate=dateFormat.parse("2000-01-01") ;

        FoodItem item = new FoodItem(31,product,expiryDate);
        assertFalse(new FoodItemDAO(new FoodProductDAO()).addItem(item));
    }

    /**
     * Tests the deleteItem method of the FoodItemDAO class.
     */
    @Test
    void TestDeleteItem()
    {
        assertFalse(new FoodItemDAO(new FoodProductDAO()).deleteItem(300));
    }

    /**
     * Tests the filterExpiredItems method of the FoodItemDAO class.
     */
    @Test
    void TestFilterExpiredItems()
    {
        assertInstanceOf(ArrayList.class, new FoodItemDAO(new FoodProductDAO()).filterExpiredItems());
    }
}