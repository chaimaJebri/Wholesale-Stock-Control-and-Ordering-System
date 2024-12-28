import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;

/**
 * EditCustomerProcessHandler is an HTTP handler for processing requests related to updating customer information.
 * It handles HTTP requests to update a customer's information based on the provided form data.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class EditCustomerProcessHandler implements HttpHandler {

    private CustomerDAO customerdao;
    private RequestDecoder reqDecoder;

    //Chaima Jebri
    /**
     * Constructs an EditCustomerProcessHandler object with the specified dependencies.
     *
     * @param customerdao The data access object for managing customer data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public EditCustomerProcessHandler (CustomerDAO customerdao, RequestDecoder reqDecoder)
    {
        this.customerdao=customerdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles HTTP requests for editing customer details through the form submission
     * by extracting data from the incoming request, validates and edited the database accordingly
     * then sending a response with the result.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String line;
            String request="";
            while((line=in.readLine())!=null)
            {
                request=request+line;
            }
            HashMap<String, String> map = reqDecoder.requestStringToMap(request);
            int customerID=Integer.parseInt(map.get("customerID"));
            String businessName=map.get("businessName");
            String addressLine1=map.get("addressLine1");
            String addressLine2=map.get("addressLine2");
            String addressLine3=map.get("addressLine3");
            String country=map.get("country");
            String postcode=map.get("postcode");
            String telephoneNumber=map.get("telephoneNumber");
            Address address= new Address(addressLine1,addressLine2,addressLine3,country,postcode);
            Customer customer= new Customer(customerID,businessName,address,telephoneNumber);
            boolean updated= customerdao.updateCustomer(customer);
            if(updated)
            {
                httpex.sendResponseHeaders(200,0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Edit Customer</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Customer with ID: " + customer.getCustomerID() + " updated successfully</h1>" +
                        "    <h2>Customer Details</h2>" +
                        "    <table>" +
                        "        <thead>" +
                        "            <th>BUSINESS NAME</th>" +
                        "            <th>ADDRESS</th>" +
                        "            <th>TELEPHONE NUMBER</th>" +
                        "        </thead>" +
                        "        <tbody>" +
                        "            <tr>" +
                        "                " + customer.toHTMLString() +
                        "            </tr>" +
                        "        </tbody>" +
                        "    </table>" +
                        "<a href=\"/allCustomers\">All Customers</a>"+
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
                        "    <title>Edit Customer</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Oops!</h1>" +
                        "    <h2>Failed to update Customer with ID: " + customerID + " ,Please Try again</h2>" +
                        "    <a href=\"/editCustomer?customerID="+customerID+"\">Try again</a>"+
                        "    <a class=\"top-left\" href=\"/allCustomers\">All Customers</a>"+
                        "</body>" +
                        "</html>");
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
}