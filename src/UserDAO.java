import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Formatter;

/**
 * The UserDAO class is the data access object that provides an interface to the user table in the SQLite database.
 * It contains methods to retrieve, add, and authenticate users.
 * The passwords are hashed before the storage in the database and during the authentication for security.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class UserDAO {
    
    /**
     * Default constructor for the UserDAO class
     */
    public UserDAO(){}

    /**
     * Establishes a database connection
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
     * Hashes the given password using the MD5 algorithm.
     *
     * @param password The password to hash
     * @return The hashed password
     */
    private String hashPassword(String password)
    {
        try
        {
            MessageDigest message = MessageDigest.getInstance("MD5");
            message.update(password.getBytes());

            try (Formatter formatter = new Formatter())
            {
                for (byte b : message.digest()) {
                    formatter.format("%02x", b);
                }
                return formatter.toString();
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Retrieves a user from the database based on the username
     *
     * @param username the username of the user to retrieve
     * @return The user object, which can be null if not found in the database
     */
    public User findUser (String username)
    {

        String query= "SELECT * FROM users WHERE username=?";
        User user=null;

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            stmnt.setString(1,username);
            try (ResultSet rslt =stmnt.executeQuery())
            {
                while (rslt.next())
                {
                    String password = rslt.getString("password");
                    String role = rslt.getString("role");
                    user = new User(username, password, role);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return user;
    }

    /**
     * Adds a new user into the database
     *
     * @param user the new user to be added in the database
     * @return True if successfully added, false if not
     */
    public boolean addUser(User user)
    {
        boolean added=false;
        String query="INSERT INTO users (username,password,role) VALUES(?,?,?)";

        try (Connection dbConn = connectToDB();
             PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            String hashedPassword =hashPassword(user.getPassword());

            stmnt.setString(1,user.getUsername());
            stmnt.setString(2,hashedPassword);
            stmnt.setString(3,user.getRole());

            int rows =stmnt.executeUpdate();
            if (rows>0)
            {
                added=true;
                System.out.println("YES! user: "+user.getUsername()+" added");
            }
            else
            {
                System.out.println("Failed to add user!");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return added;
    }

    /**
     * Authenticates a user based on the username and password
     *
     * @param username The username of the user to authenticate
     * @param password The password related to the user to authenticate
     * @return True if successfully authenticated, false if not
     */
    public boolean authenticateUser(String username,String password) 
    {
        boolean authenticated =false;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try(Connection dbConn = connectToDB();
            PreparedStatement stmnt = dbConn.prepareStatement(query))
        {
            stmnt.setString(1,username);
            stmnt.setString(2, hashPassword(password));

            try (ResultSet rslt=stmnt.executeQuery())
            {
                if (rslt.next())
                {
                    authenticated = true;
                    System.out.println("Authentication successful for user: " + username);
                }
                else
                {
                    System.out.println("Authentication Failed for user: " + username);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return authenticated;
    }
}
