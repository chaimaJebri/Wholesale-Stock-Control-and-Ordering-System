import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the FoodProductDAO class.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
class FoodProductDAOTest {
    /**
     * Tests the findAllProducts method of the FoodProductDAO class.
     */
    @Test
    void TestFindAllProducts()
    {
        assertInstanceOf(ArrayList.class,new FoodProductDAO().findAllProducts());
    }

    /**
     * Tests the findProduct method of the FoodProductDAO class for an existing product.
     */
    @Test
    @DisplayName("It'll successfully retrieve a food product that exists")
    void TestFindProduct()
    {
        assertInstanceOf(FoodProduct.class, new FoodProductDAO().findProduct(3));
    }

    /**
     * Tests the findProduct method of the FoodProductDAO class for a non-existent product.
     */
    @Test
    @DisplayName("It'll return null while trying to retrieve a product that not exist")
    void TestFindNonExistentProduct()
    {
        assertNull(new FoodProductDAO().findProduct(300));
    }

    /**
     * Tests the updateProduct method of the FoodProductDAO class for an existing product.
     */
    @Test
    void TestUpdateProduct()
    {
        FoodProduct product=new FoodProduct(3,"BR83","10Packets Toast Bread","bread",20);
        assertTrue(new FoodProductDAO().updateProduct(product));
    }

    /**
     * Tests the updateProduct method of the FoodProductDAO class for a non-existent product.
     */
    @Test
    void TestUpdateNonExistentProduct()
    {
        FoodProduct product = new FoodProduct(300,"test", "test","test", 25);
        assertFalse(new FoodProductDAO().updateProduct(product));
    }

    /**
     * Tests the addProduct method of the FoodProductDAO class for a product that already exists.
     */
    @Test
    @DisplayName("It will return false because the food product with the SKU(BMN64) already exists")
    void TestAddProduct()
    {
        FoodProduct product = new FoodProduct("BMN64", "10KG Beef Minced", "beef", 48);
        assertFalse(new FoodProductDAO().addProduct(product));
    }

    /**
     * Tests the deleteProduct method of the FoodProductDAO class for a non-existent product.
     */
    @Test
    @DisplayName("It will return false because the product with the ID 300 does not exist")
    void TestDeleteProduct()
    {
        assertFalse(new FoodProductDAO().deleteProduct(300));
    }

    /**
     * Tests the filterProductsByCategory method
     */
    @Test
    void TestFilterProductsByCategory()
    {
        String category = "pasta";
        assertInstanceOf(ArrayList.class, new FoodProductDAO().filterProductsByCategory(category));
    }

    /**
     * Tests the searchProductsByDescription method
     */
    @Test
    void TestSearchProductsByDescription()
    {
        String search ="pas";
        assertInstanceOf(ArrayList.class, new FoodProductDAO().searchProductsByDescription(search));
    }
}