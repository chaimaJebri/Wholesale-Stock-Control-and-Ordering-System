import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * RetrieveCustomersHandler is an HTTP handler for processing requests related to retrieve customers from the database.
 * It handles retrieving all customers and search for customers by business name.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class RetrieveCustomersHandler implements HttpHandler {

    private CustomerDAO customerdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs a RetrieveCustomersHandler object with the specified dependencies.
     *
     * @param customerdao The data access object for managing customer data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public RetrieveCustomersHandler(CustomerDAO customerdao, RequestDecoder reqDecoder)
    {
        this.customerdao=customerdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles the http request for retrieving customers,
     * Depending on the request method, it either displays all customers or search for specific
     * customers by their business name.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        System.out.println("Retrieve Customers Handler Called...");

        if (httpex.getRequestMethod().equalsIgnoreCase("post"))
        {
            displayCustomersByName(httpex);
        }
        else
        {
            displayAllCustomers(httpex,customerdao.findAllCustomers(), null);
        }
    }

    /**
     *  Displays customers based on the search by business name.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     */
    private void displayCustomersByName(HttpExchange httpex)
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody())))
        {
            String line;
            String request="";
            while((line=in.readLine())!=null)
            {
                request=request+line;
            }
            HashMap<String,String> map=reqDecoder.requestStringToMap(request);
            String search=map.get("search");
            ArrayList<Customer> customersByName=customerdao.searchCustomersByBusinessName(search);
            displayAllCustomers(httpex,customersByName, search);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Chaima Jebri
    /**
     * Displays all the customers in the customer table from the database.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     * @param customers The list of customers to display
     * @param condition The search string for displaying specific customers.
     */
    private void displayAllCustomers(HttpExchange httpex, ArrayList<Customer> customers, String condition)
    {
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <link rel="stylesheet" href="/cssTable" type ="text/css">
                        <title>Customers</title>
                    </head>
                    <body>
                    <form class="top-left" method="get" action="/addCustomer">
                         <button type="submit">Add Customer</button>
                    </form>
                    <form class="top-right" method="post" action="/allCustomers">
                             <input type="text" id="search" name="search" placeholder="Search by Business Name" required>
                             <button type="submit">Search</button>
                    </form>
                    <div class="table-container">
                         <h1>Customer Directory</h1>
                    <table>
                        <thead>
                        <tr>
                            <th>CUSTOMER ID</th>
                            <th>BUSINESS NAME</th>
                            <th>ADDRESS</th>
                            <th>TELEPHONE NUMBER</th>
                            <th>EDIT</th>
                            <th>DELETE</th>
                        </tr>
                        </thead>
                        <tbody>
                    """);
            customers.forEach(customer ->
            {
                try
                {
                    out.write("<tr><td>" + customer.getCustomerID() + "</td>" + customer.toHTMLString() + "<td><a href= \"/editCustomer?customerID=" + customer.getCustomerID() + "\">Edit</a></td><td><a href=\"/deleteCustomer?customerID=" + customer.getCustomerID() + "\">Delete</a></td></tr>");
                }
                catch(IOException e)
                {
                    System.out.println(e.getMessage());
                }
            } );
            out.write("""
                    </tbody>
                    </table>
                    """);
            if(condition != null && !condition.isEmpty())
            {
                out.write("<a class=\"bottom-link\" href=\"/allCustomers\">All Customers</a>");
            }
            out.write("""
                    <a class="bottom-link" href="/welcomePageAdmin">Back to Main Menu</a>
                    </div>
                    </body>
                    </html>
                    """);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}