import java.sql.*;
import java.util.ArrayList;

/**
 * The FoodProductDAO class is the data access object that provides an interface to the foodproduct table
 * in the SQLite database. It contains methods to retrieve, add, update and delete food products,
 * as well as methods for filtering products by category and searching products by description
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class FoodProductDAO {

    /**
     * Default constructor for the FoodProductDAO class
     */
    public FoodProductDAO(){}

    /**
     * Establishes a database connection.
     *
     * @return the database connection object
     */
    //Chaima Jebri
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
     * Retrieves all food products from the foodproduct table in the database
     *
     * @return an ArrayList containing all food products
     */
    public ArrayList<FoodProduct> findAllProducts()
    {
        ArrayList<FoodProduct> foodproducts = new ArrayList<>();
        String query = "SELECT * FROM foodproduct;";

        try (Connection dbConn=connectToDB();
             PreparedStatement stmnt =dbConn.prepareStatement(query);
             ResultSet rslt =stmnt.executeQuery())
        {
            while (rslt.next())
            {
                int id = rslt.getInt("id");
                String SKU = rslt.getString("SKU");
                String description = rslt.getString("description");
                String category = rslt.getString("category");
                int price = rslt.getInt("price");
                FoodProduct product = new FoodProduct(id, SKU, description,category,price);
                foodproducts.add(product);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return foodproducts;
    }

    /**
     * Retrieves a food product from the database based on its ID
     *
     * @param id the ID of the food product to retrieve
     * @return The food product object, which can be null if not found in the database
     */
    public  FoodProduct findProduct(int id)
    {
        String query = "SELECT * FROM foodproduct WHERE id = ?";
        FoodProduct product =null;
        try(Connection dbConn=connectToDB();
            PreparedStatement stmnt =dbConn.prepareStatement(query))
        {
            stmnt.setInt(1, id);

            try (ResultSet rslt =stmnt.executeQuery())
            {
                while (rslt.next()) {
                    String SKU = rslt.getString("SKU");
                    String description = rslt.getString("description");
                    String category = rslt.getString("category");
                    int price = rslt.getInt("price");
                    product = new FoodProduct(id, SKU, description, category, price);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return product;
    }

    /**
     * Deletes a food product from the database based on its ID
     *
     * @param id The ID of the food product to delete
     * @return True if the food product is successfully deleted, false if not
     */
    public boolean deleteProduct(int id)
    {
        boolean deleted=false;
        String query = "DELETE FROM foodproduct WHERE id = ?";
        try (Connection dbConn =connectToDB();
             Statement pragmaStmt = dbConn.createStatement())
        {
            pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            try (PreparedStatement stmnt=dbConn.prepareStatement(query))
            {
                stmnt.setInt(1, id);
                int rows = stmnt.executeUpdate();
                if (rows > 0)
                {
                    deleted = true;
                    System.out.println("YES! Product deleted");
                } else
                {
                    System.out.println("Failed !");
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return deleted;
    }

    /**
     * Updates the information of a food product in the database
     *
     * @param product The food product with the new updated information
     * @return True if the food product is successfully updated, false if not
     */
    public boolean updateProduct (FoodProduct product)
    {
        boolean updated =false;
        String query = "UPDATE foodproduct SET SKU =?, description =?, category=?, price =? WHERE id =?";

        try (Connection dbConn =connectToDB();
             PreparedStatement stmnt =dbConn.prepareStatement(query))
        {
            product.assignParametersProduct(stmnt);
            stmnt.setInt(5,product.getId());

            int rows = stmnt.executeUpdate();
            if (rows>0)
            {
                updated=true;
                System.out.println("YES! product updated");
            }
            else
            {
                System.out.println("Failed!");
            }

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return updated;
    }

    /**
     * Adds a new food product to the foodproduct table in the database
     *
     * @param product The new food product object to add
     * @return True if successfully added, false if not
     */

    public boolean addProduct (FoodProduct product)
    {
        boolean added=false;
        String query ="INSERT INTO foodproduct (SKU, description, category,price) VALUES (?,?,?,?)";

        try (Connection dbConn =connectToDB();
             PreparedStatement stmnt =dbConn.prepareStatement(query))
        {
            product.assignParametersProduct(stmnt);

            int rows=stmnt.executeUpdate();
            if (rows>0)
            {
                added=true;
                System.out.println("YES! product added");
            }
            else
            {
                System.out.println("Failed!");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return added;
    }

    /**
     * Filters food products in the databases based on a specific category
     *
     * @param category the category used for filtering
     * @return an ArrayList containing food products with the specific category
     */
    public ArrayList<FoodProduct> filterProductsByCategory(String category)
    {
        ArrayList<FoodProduct> foodproductsBycategory=new ArrayList<>();
        String query ="SELECT * FROM foodproduct WHERE category=?";

        try (Connection dbConn=connectToDB();
             PreparedStatement stmnt =dbConn.prepareStatement(query))
        {
            stmnt.setString(1,category);
            try (ResultSet rslt =stmnt.executeQuery())
            {
                while (rslt.next()) {
                    int id = rslt.getInt("id");
                    String SKU = rslt.getString("SKU");
                    String description = rslt.getString("description");
                    int price = rslt.getInt("price");
                    FoodProduct product = new FoodProduct(id, SKU, description, category, price);
                    foodproductsBycategory.add(product);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return foodproductsBycategory;
    }

    /**
     * Searches for food products in the database based on a search string with the product description
     *
     * @param search The search string used for matching descriptions.
     * @return an ArrayList containing food products with the specific description matching the search string
     */
    public ArrayList<FoodProduct> searchProductsByDescription(String search) 
    {
        ArrayList<FoodProduct> foodproductsByDescription=new ArrayList<>();
        String query ="SELECT * FROM foodproduct WHERE description LIKE ?";
        try(Connection dbConn=connectToDB();
            PreparedStatement stmnt =dbConn.prepareStatement(query))
        {
            stmnt.setString(1,'%'+search+'%');

            try (ResultSet rslt=stmnt.executeQuery())
            {
                while (rslt.next())
                {
                    int id = rslt.getInt("id");
                    String SKU = rslt.getString("SKU");
                    String description = rslt.getString("description");
                    String category = rslt.getString("category");
                    int price = rslt.getInt("price");
                    FoodProduct product = new FoodProduct(id, SKU, description, category, price);
                    foodproductsByDescription.add(product);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return foodproductsByDescription;
    }
}
