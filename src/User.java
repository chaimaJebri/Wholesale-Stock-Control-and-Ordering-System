/**
 * The User class represents a user object, It contains information about the user
 * including a unique username, a password and a role.
 * It also contains the getters and setters for the different attributes.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class User {

    /* 1) sysAdmin/123admin456/administrator
       2) sysCustomer/123customer456/customer
       3) myuser/externalUser/user */
    
    private String username; // constraint: Primary Key
    private String password;
    private String role;

    //Chaima Jebri
    /**
     * Constructs a user object with the given attributes.
     *
     * @param username The unique username of the user
     * @param password The password associated with the user
     * @param role The role of the user (example administrator, customer)
     */
    public User (String username, String password, String role)
    {
        this.username=username;
        this.password=password;
        this.role=role;
    }

    /**
     * Gets the unique username of the user
     *
     * @return the unique username of the user
     */
    public String getUsername()
    {
        return  this.username;
    }

    /**
     * Sets the unique username of the user
     *
     * @param username the unique username of the user
     */
    public void setUsername(String username)
    {
        this.username=username;
    }

    /**
     * Gets the password associated with the user
     *
     * @return the password associated with the user
     */
    public String getPassword()
    {
        return  this.password;
    }

    /**
     * Gets the role of the user
     *
     * @return the role of the user
     */
    public String getRole()
    {
        return  this.role;
    }

    /**
     * Sets the role of the user
     *
     * @param role the role of the user
     */
    public void setRole(String role)
    {
        this.role=role;
    }
}
