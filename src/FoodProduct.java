import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The FoodProduct class represents a food product object.
 * It has the getters and setters methods for the different attributes
 * also a method to generate an html string and a method to generate a string representation.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class FoodProduct {
    private int id;
    private String SKU;
    private String description;
    private String category;
    private int price;

    /**
     * Constructs a food product object with the given attributes.
     *
     * @param sku The unique stock-keeping unit for the product
     * @param description The description of the product
     * @param category The category of the product
     * @param price The price of the product
     */
    public FoodProduct (String sku, String description, String category, int price)
    {
        this.SKU=sku;
        this.description=description;
        this.category=category;
        this.price=price;
    }

    /**
     * Constructs a food product object with the given attributes including an auto-increment ID.
     *
     * @param id The unique identifier of the product which is auto-increment
     * @param sku The unique stock-keeping unit for the product
     * @param description The description of the product
     * @param category The category of the product
     * @param price The price of the product
     */
    public FoodProduct (int id,String sku, String description, String category, int price )
    {
        this.id=id;
        this.SKU=sku;
        this.description=description;
        this.category=category;
        this.price=price;
    }

    /**
     * Assigns the attributes of the product to a prepared statement for the database operations.
     *
     * @param stmnt The prepared statement object to which the attributes of the product are assigned
     * @throws SQLException If a database access error happens
     */
    public void assignParametersProduct(PreparedStatement stmnt)throws SQLException {
        stmnt.setString(1, this.SKU);
        stmnt.setString(2, this.description);
        stmnt.setString(3, this.category);
        stmnt.setInt(4, this.price);
    }

    /**
     * Sets the unique identifier for the product.
     *
     * @param id The unique identifier for the product
     */
    public void setId(int id)
    {
        this.id=id;
    }

    /**
     * Gets the unique identifier of the product.
     *
     * @return The unique identifier of the product
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets the unique stock-keeping unit for the product.
     *
     * @param sku The unique stock-keeping unit for the product
     */
    public void setSku(String sku)
    {
        this.SKU=sku;
    }

    /**
     * Gets the unique stock-keeping unit for the product.
     *
     * @return the unique stock-keeping unit for the product.
     */
    public String getSku()
    {
        return this.SKU;
    }

    /**
     * Sets the description of the product
     *
     * @param description the description of the product
     */
    public void setDescription(String description)
    {
        this.description=description;
    }

    /**
     * Gets the description of the product
     *
     * @return the description of the product
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Sets the category of the product.
     *
     * @param category the category of the product
     */
    public void setCategory(String category)
    {
        this.category=category;
    }

    /**
     * Gets the category of the product.
     *
     * @return the category of the product
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * Sets the price of the product
     *
     * @param price The price of the product
     */
    public void setPrice(int price)
    {
        this.price=price;
    }

    /**
     * Gets the price of the product
     *
     * @return the price of the product
     */
    public int getPrice()
    {
        return this.price;
    }

    /**
     * Generates an HTML representation of the product.
     *
     * @return an HTML string representing the product
     */

    //Chaima Jebri

    public String toHTMLString()
    {
        return "<td>" +this.SKU +"</td><td>"+this.description+"</td><td>"+this.category+"</td><td>"+this.price+"</td>";
    }

    /**
     * Generates a string representation of the product
     *
     * @return a string representing the product
     */
    public String toString ()
    {
        return "ID:"+this.id + " ,SKU:"+this.SKU + " ,Description:"+ this.description + " ,Category:"+ this.category + " ,Price:"+this.price+"£";
    }

}
