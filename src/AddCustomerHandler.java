import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * AddCustomerHandler is an HTTP handler for processing requests related to adding a new customer
 *
 * @author Chaima Jebri
 * @version 1.2
 */

//Chaima Jebri
public class AddCustomerHandler implements HttpHandler {
    
    /**
     * Default constructor for the AddCustomerHandler class
     */
    public AddCustomerHandler()
    {}
    /**
     * Handles the http requests for adding a new customer by displaying an HTML form
     * where users can input details related to the new customer
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */

    public void handle (HttpExchange httpex)
    {
        System.out.println("Add Customer Handler Called...");
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <link rel="stylesheet" href="/cssAddEditForm" type ="text/css">
                        <title>Add Customer</title>
                    </head>
                    <body>
                    <div>
                        <h1>Add Customer</h1>
                        <p>Kindly fill the details below to add a new Customer</p>
                        <form method = "post" action="/addCustomerProcess">
                            <label class="top-bottom-fields" for="businessName"> Business Name </label>
                            <input type="text" id="businessName" name="businessName" placeholder="Enter Business Name" required>
                            <label for="addressLine1"> Address Line 01 </label>
                            <input type="text" id="addressLine1" name="addressLine1" placeholder="Enter Address Line 01" required>
                            <label for="addressLine2"> Address Line 02 </label>
                            <input type="text" id="addressLine2" name="addressLine2" placeholder="Enter Address Line 02" required>
                            <label for="addressLine3"> Address Line 03 </label>
                            <input type="text" id="addressLine3" name="addressLine3" placeholder="Enter Address Line 03" required>
                            <label for="country"> Country </label>
                            <input type="text" id="country" name="country" placeholder="Enter Country" required>
                            <label for="postcode"> PostCode </label>
                            <input type="text" id="postcode" name="postcode" placeholder="Enter PostCode" required>
                            <label for="telephoneNumber"> Telephone Number </label>
                            <input type="text" id="telephoneNumber" name="telephoneNumber" placeholder="Enter Telephone Number" required>
                            <button class="top-bottom-fields" type="submit">ADD Customer</button>
                        </form>
                    </div>
                        <form class="top-left" method="get" action="/allCustomers">
                            <button type="submit">Cancel</button>
                        </form>
                    </body>
                    </html>
                    """);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}