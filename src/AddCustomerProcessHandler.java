import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;

/**
 * AddCustomerProcessHandler is an HTTP handler for processing requests related to adding a new customer
 * through a form submission
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class AddCustomerProcessHandler implements HttpHandler {

    private CustomerDAO customerdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs an AddCustomerProcessHandler object with the specified dependencies.
     *
     * @param customerdao The data access object for managing customer data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */

    //Chaima Jebri
    public AddCustomerProcessHandler(CustomerDAO customerdao,RequestDecoder reqDecoder)
    {
        this.customerdao=customerdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles the http requests for adding a new customer through the form submission
     * by extracting data from the incoming request, validates and adds the customer to the database
     * then sending a response with the result.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody()));
            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String line;
            String request = "";
            while ((line = in.readLine()) != null)
            {
                request = request + line;
            }
            HashMap<String, String> map = reqDecoder.requestStringToMap(request);
            String businessName=map.get("businessName");
            String addressLine1=map.get("addressLine1");
            String addressLine2=map.get("addressLine2");
            String addressLine3=map.get("addressLine3");
            String country=map.get("country");
            String postcode=map.get("postcode");
            String telephoneNumber=map.get("telephoneNumber");
            Address address = new Address(addressLine1,addressLine2,addressLine3,country,postcode);
            Customer customer = new Customer(businessName,address,telephoneNumber);
            boolean added = customerdao.addCustomer(customer);
            if (added)
            {
                httpex.sendResponseHeaders(200,0);
                out.write("""
                        <!DOCTYPE html>
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                <title>Add Customer</title>
                            </head>
                            <body>
                            <h1>Customer Added successfully</h1>
                            <h2>Added Customer Details</h2>
                            <table>
                            <thead>
                            <th>BUSINESS NAME</th>
                            <th>ADDRESS</th>
                            <th>TELEPHONE NUMBER</th>
                            </thead>
                            <tbody>
                            <tr>""" + customer.toHTMLString() + """
                        </tr>
                        </tbody>
                        </table>
                        <a class="top-left" href="/addCustomer">Add Another Customer</a>
                        <a href="/allCustomers">All Customers</a>
                        </body>
                        </html>
                        """);
            }
            else
            {
                httpex.sendResponseHeaders(404,0);
                out.write("""
                        <!DOCTYPE html>
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                <title>Add Customer</title>
                            </head>
                            <body>
                                <h1>Oops!</h1>
                                <h2>Failed to add the customer. Please Try Again</h2>
                                <a href="/addCustomer">Try again</a>
                                <a class="top-left" href="/allCustomers">All Customers</a>
                            </body>
                            </html>
                        """);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
