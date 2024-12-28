import java.sql.*;
import java.util.ArrayList;

/**
 * The CustomerDAO class is the data access object that provides an interface to the customer table in the SQLite database.
 * It contains methods to retrieve, add, delete, and update customers, as well as a method to search customers by business name.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class CustomerDAO {

    /**
     * Default constructor for the CustomerDAO class
     */
    public CustomerDAO ()
    {}
    
    /**
     * Establishes a database connection.
     *
     * @return the database connection object
     */

    //Chaima Jebri
     private Connection connectToDB()
    {
        Connection dbConn=null;
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
     * Retrieves all customers from the customer table in the database
     *
     * @return an ArrayList containing all customers
     */
    public ArrayList<Customer> findAllCustomers()
    {
        String query="SELECT * FROM customer;";
        ArrayList<Customer> customers =new ArrayList<>();

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query);
            ResultSet rslt = stmnt.executeQuery())
        {
            while (rslt.next())
            {
                int id =rslt.getInt("customerID");
                String name=rslt.getString("businessName");
                String adrl1=rslt.getString("addressLine1");
                String adrl2=rslt.getString("addressLine2");
                String adrl3=rslt.getString("addressLine3");
                String ctry=rslt.getString("country");
                String pstcode=rslt.getString("postCode");
                String telnum=rslt.getString("telephoneNumber");
                Address adr = new Address(adrl1,adrl2,adrl3,ctry,pstcode);
                Customer customer = new Customer(id,name,adr,telnum);
                customers.add(customer);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    /**
     * Retrieves a customer from the database based on its ID
     *
     * @param id the ID of the customer to retrieve
     * @return the customer object, which can be null if not found in the database
     */
    public  Customer findCustomer(int id)
    {
        Customer customer=null;
        String query= "SELECT * FROM customer WHERE customerID=?";

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            stmnt.setInt(1,id);

            try(ResultSet rslt=stmnt.executeQuery())
            {
                while (rslt.next()) {
                    String name = rslt.getString("businessName");
                    String adrl1 = rslt.getString("addressLine1");
                    String adrl2 = rslt.getString("addressLine2");
                    String adrl3 = rslt.getString("addressLine3");
                    String ctry = rslt.getString("country");
                    String pstcode = rslt.getString("postCode");
                    String telnum = rslt.getString("telephoneNumber");
                    Address a = new Address(adrl1, adrl2, adrl3, ctry, pstcode);
                    customer = new Customer(id, name, a, telnum);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    /**
     * Deletes a customer from the database based on its ID
     *
     * @param id the ID of the customer to delete
     * @return True if successfully deleted, false if not
     */
    public  boolean deleteCustomer (int id)
    {
        boolean deleted=false;
        String query ="DELETE FROM customer where customerID =?";

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            stmnt.setInt(1,id);

            int rows=stmnt.executeUpdate();
            if (rows>0)
            {
                deleted=true;
                System.out.println("YES!, customer deleted");
            }
            else
            {
                System.out.println("Failed!");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return deleted;
    }

    /**
     * Updated a customer information in the database
     *
     * @param customer the customer object with the new updated information
     * @return True if successfully updated, false if not
     */
    public  boolean updateCustomer (Customer customer)
    {
        boolean updated =false;
        String query ="UPDATE customer SET businessName=?,addressLine1=?, addressLine2=?,addressLine3=?, country=?,postCode=?, telephoneNumber=? WHERE customerID =?";

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            customer.assignParametersCustomer(stmnt);
            stmnt.setInt(8,customer.getCustomerID());

            int rows= stmnt.executeUpdate();
            if (rows>0)
            {
                updated=true;
                System.out.println("YES! customer updated");
            }
            else
            {
                System.out.println("Failed !");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return updated;
    }

    /**
     * Adds a new customer to the database
     *
     * @param customer the customer object to add to the database
     * @return True if successfully added, false if not
     */
    public boolean addCustomer (Customer customer)
    {
        boolean added = false;
        String query = " INSERT INTO customer (businessName, addressLine1,addressLine2,addressLine3, country,postCode,telephoneNumber)VALUES (?,?,?,?,?,?,?)";

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            customer.assignParametersCustomer(stmnt);

            int rows = stmnt.executeUpdate();
            if (rows > 0)
            {
                added = true;
                System.out.println("YES! customer added");
            }
            else
            {
                System.out.println("Failed! ");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return added;
    }

    /**
     * Searches for a customer in the database based on a search string with the business name of the customer
     *
     * @param search the search string used for matching business names
     * @return an ArrayList containing customers with the specific business name matching the search string
     */
    public ArrayList<Customer> searchCustomersByBusinessName(String search)
    {
        ArrayList<Customer> customersByName =new ArrayList<>();
        String query="SELECT * FROM customer WHERE businessName LIKE ? ";

        try (Connection dbConn =connectToDB();
             PreparedStatement stmnt =dbConn.prepareStatement(query))
        {
            stmnt.setString(1, '%'+ search +'%');

            try(ResultSet rslt=stmnt.executeQuery())
            {
                while(rslt.next())
                {
                    int customerID =rslt.getInt("customerID");
                    String businessName=rslt.getString("businessName");
                    String adrLine1=rslt.getString("addressLine1");
                    String adrLine2=rslt.getString("addressLine2");
                    String adrLine3 =rslt.getString("addressLine3");
                    String country=rslt.getString("country");
                    String postCode=rslt.getString("postCode");
                    String telephoneNumber=rslt.getString("telephoneNumber");
                    Address adr=new Address(adrLine1,adrLine2,adrLine3,country,postCode);
                    Customer customer=new Customer(customerID,businessName,adr,telephoneNumber);
                    customersByName.add(customer);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return  customersByName;
    }
}
