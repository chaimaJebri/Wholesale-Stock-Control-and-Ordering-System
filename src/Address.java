

/**
 * The Address class represents an address object. It contains information about the address including
 * the address line 01, the address line 02, the address line03,
 * the country and the postcode.
 * It also has the getters and setters for the different attributes
 * and a method to generate a string representation of the address object.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String country;
    private String postCode;

    /**
     * Constructs an address object with the given attributes.
     *
     * @param adrL1 The first line of the address
     * @param adrL2 The second line of the address
     * @param adrL3 The third line of the address
     * @param country The country of the address
     * @param postCode The postal code of the address
     */
    public Address (String adrL1,String adrL2, String adrL3, String country,String postCode)
    {
        this.addressLine1 =adrL1;
        this.addressLine2=adrL2;
        this.addressLine3=adrL3;
        this.country=country;
        this.postCode=postCode;
    }

    /**
     * Sets the first line of the address
     *
     * @param addressLine1 the first line of the address
     */
    public void setAdrLine1 (String addressLine1)
    {this.addressLine1=addressLine1;}

    /**
     * Gets the first line of the address
     *
     * @return the first line of the address
     */
    public String getAdrLine1()
    {return this.addressLine1;}

    /**
     * Sets the second line of the address
     *
     * @param addressLine2 the second line of the address
     */
    public void setAdrLine2 (String addressLine2)
    {this.addressLine2=addressLine2;}

    /**
     * Gets the second line of the address
     *
     * @return the second line of the address
     */
    public String getAdrLine2()
    {return this.addressLine2;}

    /**
     * Sets the third line of the address
     *
     * @param addressLine3 the third line of the address
     */
    public void setAdrLine3 (String addressLine3)
    {this.addressLine3=addressLine3;}

    /**
     * Gets the third line of the address
     *
     * @return the third line of the address
     */
    public String getAdrLine3()
    {return this.addressLine3;}

    /**
     * Sets the country of the address
     *
     * @param country the country of the address
     */
    public void setCountry(String country)
    {this.country=country;}

    /**
     * Gets the country of the address
     *
     * @return the country of the address
     */
    public String getCountry()
    {return this.country;}

    /**
     * Sets the postal code of the address
     *
     * @param postCode the postal code of the address
     */
    public void setPostCode(String postCode)
    {this.postCode=postCode;}

    /**
     * Gets the postal code of the address
     *
     * @return the postal code of the address
     */
    public String getPostCode()
    {return this.postCode;}

    /**
     * Generates a string representation of the address
     *
     * @return a string representing the address
     */
//Chaima Jebri
    public String toString()
    {
        return this.addressLine1+", "+this.addressLine2+", "+this.addressLine3+", "+this.country+", "+this.postCode;
    }

}
