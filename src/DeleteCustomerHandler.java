import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * DeleteCustomerHandler is an HTTP handler for processing requests related to deleting a customer.
 * It handles HTTP delete requests and deletes the customer with the specified ID from the database.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class DeleteCustomerHandler implements HttpHandler {

    private CustomerDAO customerdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs the DeleteCustomerHandler object with the specified dependencies.
     *
     * @param customerdao The data access object for managing customer data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    //Chaima Jebri
    public DeleteCustomerHandler (CustomerDAO customerdao, RequestDecoder reqDecoder)
    {
        this.customerdao=customerdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles the HTTP DELETE requests for deleting  a customer
     * by extracting the customer ID from the incoming request, validates and deletes the customer from the database
     * then sending a response with the result.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        System.out.println("Deleting Customer Handler Called...");
        String request=httpex.getRequestURI().getQuery();
        HashMap<String, String> map = reqDecoder.requestStringToMap(request);
        int customerID = Integer.parseInt(map.get("customerID"));
        boolean deleted = customerdao.deleteCustomer(customerID);
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            if (deleted)
            {
                httpex.sendResponseHeaders(200,0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Delete Customer</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>YES!</h1>" +
                        "    <h2>Customer with ID:" + customerID + " deleted successfully</h2>" +
                        "    <a href=\"/allCustomers\">All Customers</a>"+
                        "</body>" +
                        "</html>");
            }
            else
            {
                httpex.sendResponseHeaders(404,0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Delete Customer</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Oops!</h1>" +
                        "    <h2>Failed to delete customer. Customer with ID:" + customerID + " not found</h2>" +
                        "    <a href=\"/allCustomers\">All Customers</a>"+
                        "</body>" +
                        "</html>"
                );
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}