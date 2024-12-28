import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Customer class represents a customer object.
 * It contains information about the customer including a unique ID, a business name, an address
 * and a telephone number.
 * It also has the getters and setters for the different attributes,
 * a method to generate an html string and a method to generate a string representation
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class Customer {
    private int customerID;
    private String businessName;
    private Address address;
    private String telephoneNumber;

    /**
     * Constructs a customer object with the given attributes, including an auto-increment customerID.
     *
     * @param id  The unique identifier of the customer which is auto-increment
     * @param name The business name of the customer
     * @param adr The address of the customer
     * @param telNum The telephone number of the customer
     */

    //Chaima Jebri
    
    public Customer (int id,String name, Address adr,String telNum)
    {
        this.customerID=id;
        this.businessName=name;
        this.address=adr;
        this.telephoneNumber=telNum;
    }

    /**
     * Constructs a customer object with the given attributes.
     *
     * @param name The business name of the customer
     * @param adr The address of the customer
     * @param telNum The telephone number of the customer
     */
    public Customer (String name, Address adr,String telNum)
    {
        this.businessName=name;
        this.address=adr;
        this.telephoneNumber=telNum;
    }

    /**
     * Assigns the attributes of the customer to a prepared statement for database operations.
     *
     * @param stmnt The prepared statement object to which the attributes of the customer are assigned
     * @throws SQLException If a database access error occurs
     */
    public void assignParametersCustomer(PreparedStatement stmnt) throws SQLException
    {
        stmnt.setString(1, this.businessName);
        stmnt.setString(2, this.address.getAdrLine1());
        stmnt.setString(3,this.address.getAdrLine2());
        stmnt.setString(4,this.address.getAdrLine3());
        stmnt.setString(5,this.address.getCountry());
        stmnt.setString(6,this.address.getPostCode());
        stmnt.setString(7,this.telephoneNumber);
    }

    /**
     * Sets the unique identifier of the customer
     *
     * @param id the unique identifier of the customer.
     */
    public void setCustomerID(int id)
    {this.customerID=id;}

    /**
     * Gets the unique identifier of the customer
     *
     * @return the unique identifier of the customer
     */
    public int getCustomerID()
    {return this.customerID;}

    /**
     * Sets the business name of the customer
     *
     * @param name the business name of the customer
     */
    public void setBusinessName(String name)
    {this.businessName=name;}

    /**
     * Gets the business name of the customer
     *
     * @return the business name of the customer
     */
    public String getBusinessName()
    {return this.businessName;}

    /**
     * Sets the address of the customer
     *
     * @param adr The address object containing detailed address information.
     */
    public void setAddress(Address adr)
    {this.address=adr;}

    /**
     * Gets the address of the customer
     *
     * @return The address object containing detailed address information.
     */
    public Address getAddress()
    {return this.address;}

    /**
     * Sets the telephone number of the customer
     *
     * @param telNum the telephone number of the customer
     */
    public void setTelephoneNumber(String telNum)
    {this.telephoneNumber=telNum;}

    /**
     * Gets the telephone number of the customer
     *
     * @return the telephone number of the customer
     */
    public String getTelephoneNumber()
    {return this.telephoneNumber;}

    /**
     * Generates an HTML representation of the customer
     *
     * @return an HTML string representing the customer
     */
    public String toHTMLString()
    {
        return "<td>"+this.businessName+"</td><td>"+this.address+"</td><td>"+this.telephoneNumber+"</td>";
    }

    /**
     * Generates a string representation of the customer
     *
     * @return a string representing the customer
     */

    public String toString()
    {
        return "Customer ID: "+this.customerID+", Business Name: "+this.businessName+", Address: "+this.address+ ", Telephone Number: "+this.telephoneNumber;
    }

}
