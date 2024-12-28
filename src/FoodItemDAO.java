import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * The FoodItemDAO class is the data access object that provides an interface to the foodItem table in the SQLite database.
 * It contains methods to retrieve, add and delete food items,
 * As well as a method to filter the expired items.
 * This class uses a FoodProductDAO object for retrieving associated FoodProduct information.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class FoodItemDAO {

    //Chaima Jebri
    private FoodProductDAO foodproductdao;

    /**
     * Constructs a food item object with a reference to a FoodProductDAO.
     *
     * @param foodproductdao the foodproductdao object used for retrieving related food product information.
     */
    public FoodItemDAO(FoodProductDAO foodproductdao)
    {
        this.foodproductdao=foodproductdao;
    }

    /**
     * Establishes a database connection.
     *
     * @return the database connection object
     */
    private Connection connectToDB ()
    {
        Connection dbConn = null;
        try
        {
            String dbFileURL = "jdbc:sqlite:foodstore.sqlite";
            dbConn= DriverManager.getConnection(dbFileURL);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return dbConn;
    }

    /**
     * Retrieves all food items from the foodItem table in the database.
     *
     * @return an ArrayList containing all food items.
     */
    public ArrayList<FoodItem> findAllItems() 
    {
        ArrayList<FoodItem> foodItems=new ArrayList<>();
        String query="SELECT * FROM foodItem;";

        try (Connection dbConn = connectToDB();
             PreparedStatement stmnt = dbConn.prepareStatement(query);
             ResultSet rslt = stmnt.executeQuery())
        {
            while(rslt.next())
            {
                int foodItemId=rslt.getInt("foodItemId");
                int foodproductId=rslt.getInt("id");
                Date expiryDate=rslt.getDate("expiryDate");
                FoodProduct product=foodproductdao.findProduct(foodproductId);
                FoodItem foodItem=new FoodItem(foodItemId,product,expiryDate);
                foodItems.add(foodItem);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return foodItems;
    }

    /**
     * Retrieves a food item from the database based on the foodItemID
     *
     * @param foodItemId the ID of the foodItem to retrieve
     * @return The food item object, which can be null if not found in the database.
     */
    public FoodItem findItem(int foodItemId)
    {
        String query = "SELECT * FROM foodItem WHERE foodItemId = ?";
        FoodItem foodItem=null;

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            stmnt.setInt(1,foodItemId);

            try(ResultSet rslt=stmnt.executeQuery())
            {
                while (rslt.next()) {
                    int foodItemid = rslt.getInt("foodItemId");
                    int foodproductId = rslt.getInt("id");
                    Date expiryDate = rslt.getDate("expiryDate");
                    FoodProduct product = foodproductdao.findProduct(foodproductId);
                    foodItem = new FoodItem(foodItemId, product, expiryDate);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return foodItem;
    }

    /**
     * Adds a new food item to the database
     *
     * @param foodItem the new food item to be added in the database
     * @return True if successfully added, false if not
     */
    public boolean addItem(FoodItem foodItem)
    {
        boolean added=false;
        String query ="INSERT INTO foodItem (foodItemId, id, expiryDate) VALUES(?,?,?)";

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            foodItem.assignParameters(stmnt);

            int rows=stmnt.executeUpdate();
            if(rows>0)
            {
                added=true;
                System.out.println("YES! item added");
            }
            else
            {
                System.out.println("Failed to add item!");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return added;
    }

    /**
     * Deletes a food item from the database based on the food item id
     *
     * @param foodItemId the ID of the food item to delete
     * @return True if successfully deleted, false if not
     */
    public boolean deleteItem(int foodItemId)
    {
        boolean deleted=false;
        String query="DELETE FROM foodItem WHERE foodItemId=?";

        try (Connection dbConn = connectToDB();
             PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            stmnt.setInt(1,foodItemId);
            int rows=stmnt.executeUpdate();
            if (rows>0)
            {
                deleted=true;
                System.out.println("Yes! Item deleted successfully");
            }
            else
            {
                System.out.println("Failed to delete the item ");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return deleted;
    }

    /**
     * Filters and retrieves all expired food items from the foodItem table in the database
     *
     * @return an ArrayList containing all expired food items
     */
    public ArrayList<FoodItem> filterExpiredItems()
    {
        ArrayList<FoodItem> expiredItems = new ArrayList<>();
        String query ="SELECT * FROM foodItem WHERE expiryDate <= ?";

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            Date currentDate = new Date();
            stmnt.setDate(1, new java.sql.Date(currentDate.getTime()));

            try(ResultSet rslt=stmnt.executeQuery())
            {
                while (rslt.next()) {
                    int foodItemId = rslt.getInt("foodItemId");
                    int id = rslt.getInt("id");
                    Date expiryDate = rslt.getDate("expiryDate");
                    FoodProduct product = foodproductdao.findProduct(id);
                    FoodItem expiredItem = new FoodItem(foodItemId, product, expiryDate);
                    expiredItems.add(expiredItem);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return expiredItems;
    }
}
