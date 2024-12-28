import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The FoodItem class represents a food item object
 * It contains information about the food item including a unique ID, a reference to the associated FoodProduct,
 * and an expiry date.
 * It also has the getters and setters for the different attributes,
 * a method to generate an HTML string and a method to generate a string representation.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class FoodItem {

    //Chaima Jebri
    private int foodItemId; //Primary Key - not autoincrement
    private FoodProduct foodProduct;
    private Date expiryDate;

    /**
     * Constructs a food item object with the given attributes.
     *
     * @param foodItemId The unique identifier of the food item.
     * @param foodProduct The related food product of the food item
     * @param expiryDate The expiry date of the food item
     */
    public FoodItem(int foodItemId, FoodProduct foodProduct, Date expiryDate) {
        this.foodItemId=foodItemId;
        this.foodProduct = foodProduct;
        this.expiryDate = expiryDate;
    }

    /**
     * Assigns the attributes of the food item to a prepared statement for the database operations.
     *
     * @param stmnt The prepared statement object to which the attributes of the food item are assigned
     * @throws SQLException If a database access error happens
     */
    public void assignParameters(PreparedStatement stmnt) throws SQLException
    {
        stmnt.setInt(1,this.foodItemId);
        stmnt.setInt(2,foodProduct.getId());
        stmnt.setDate(3, new java.sql.Date(expiryDate.getTime()));
    }

    /**
     * Sets the unique identifier of the food item
     *
     * @param foodItemId the unique identifier of the food item
     */
    public void setFoodItemId(int foodItemId)
    {
        this.foodItemId=foodItemId;
    }

    /**
     * Gets the unique identifier of the food item.
     *
     * @return the unique identifier of the food item
     */
    public int getFoodItemId()
    {
        return this.foodItemId;
    }

    /**
     * Sets the associated FoodProduct of the food item
     *
     * @param foodProduct the associated food product object of the food item
     */
    public void setFoodProduct(FoodProduct foodProduct)
    {
        this.foodProduct=foodProduct;
    }

    /**
     * Gets the associated FoodProduct of the food item
     *
     * @return the associated food product object of the food item
     */
    public FoodProduct getFoodProduct()
    {
        return this.foodProduct;
    }

    /**
     * Sets the expiry date of the food item
     *
     * @param expiryDate The expiry date of the food item
     */
    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate=expiryDate;
    }

    /**
     * Gets the expiry date of the food item
     *
     * @return the expiry date of the food item
     */
    public Date getExpiryDate()
    {
        return this.expiryDate;
    }

    /**
     * Generates an HTML representation of the food item.
     *
     * @return an HTML string representing the food item
     */

    public String toHTMLString()
    {
        return "<td>"+this.foodItemId+"</td><td>"+this.foodProduct.toString()+"</td><td>"+this.expiryDate+"</td>";
    }

    /**
     * Generates a string representation of the food item
     *
     * @return a string representing the food item
     */
    public String toString()
    {
        return "Food Item ID: "+this.foodItemId+", Food Product: "+this.foodProduct.toString()+", Expiry Date: "+this.expiryDate;
    }
}

