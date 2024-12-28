import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * EditCustomerHandler is an HTTP handler for processing requests related to editing customer details.
 * It handles HTTP requests to edit a customer by displaying a form with the current details for editing.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class EditCustomerHandler implements HttpHandler {

    private CustomerDAO customerdao;
    private RequestDecoder reqDecoder;

    //Chaima Jebri
    /**
     * Constructs an EditCustomerHandler object with the specified dependencies.
     *
     * @param customerdao The data access object for managing customer data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public EditCustomerHandler (CustomerDAO customerdao, RequestDecoder reqDecoder)
    {
        this.customerdao=customerdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles HTTP requests for editing customer information by extracting the customer ID from the incoming request,
     * validates and retrieves the customer details from the database then displaying a form with
     * the current information and allows editing.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        System.out.println("Edit Customer Handler Called...");
        String request = httpex.getRequestURI().getQuery();
        HashMap<String, String> map = reqDecoder.requestStringToMap(request);
        int customerID= Integer.parseInt(map.get("customerID"));
        Customer customer=customerdao.findCustomer(customerID);
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            if(customer!=null)
            {
                httpex.sendResponseHeaders(200,0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssAddEditForm\" type =\"text/css\">"+
                        "    <title>Edit Customer</title>" +
                        "</head>" +
                        "<body>" +
                        "<div>" +
                        "<h1>Edit Customer</h1>" +
                        "<p>Make adjustments to the Customer details as required</p>" +
                        "<form method=\"post\" action=\"/editCustomerProcess\">" +
                        "<input type=\"hidden\" name=\"customerID\" value=\"" + customerID + "\">" +
                        "<label class=\"top-bottom-fields\" for=\"businessName\"> Business Name </label>" +
                        "<input type=\"text\" id=\"businessName\" name=\"businessName\" placeholder=\"Enter Customer Name\" value=\"" + customer.getBusinessName() + "\" required>" +
                        "<label for=\"addressLine1\"> Address Line 01 </label>" +
                        "<input type=\"text\" id=\"addressLine1\" name=\"addressLine1\" placeholder=\"Enter Address Line 01\" value=\"" + customer.getAddress().getAdrLine1() + "\" required>" +
                        "<label for=\"addressLine2\"> Address Line 02 </label>" +
                        "<input type=\"text\" id=\"addressLine2\" name=\"addressLine2\" placeholder=\"Enter Address Line 02\" value=\"" + customer.getAddress().getAdrLine2() + "\" required>" +
                        "<label for=\"addressLine3\"> Address Line 03 </label>" +
                        "<input type=\"text\" id=\"addressLine3\" name=\"addressLine3\" placeholder=\"Enter Address Line 03\" value=\"" + customer.getAddress().getAdrLine3() + "\" required>" +
                        "<label for=\"country\"> Country </label>" +
                        "<input type=\"text\" id=\"country\" name=\"country\" placeholder=\"Enter Country\" value=\"" + customer.getAddress().getCountry() + "\" required>" +
                        "<label for=\"postcode\"> PostCode </label>" +
                        "<input type=\"text\" id=\"postcode\" name=\"postcode\" placeholder=\"Enter PostCode\" value=\"" + customer.getAddress().getPostCode() + "\" required>" +
                        "<label for=\"telephoneNumber\"> Telephone Number </label>" +
                        "<input type=\"text\" id=\"telephoneNumber\" name=\"telephoneNumber\" placeholder=\"Enter Phone Number\" value=\"" + customer.getTelephoneNumber() + "\" required>" +
                        "<button class=\"top-bottom-fields\" type=\"submit\">Save Changes</button>" +
                        "</form>" +
                        "</div>" +
                        "<form class=\"top-left\" method=\"get\" action=\"/allCustomers\">"+
                        "<button type=\"submit\">Cancel</button>"+
                        "</form>"+
                        "</body>" +
                        "</html>"
                );
            }
            else
            {
                httpex.sendResponseHeaders(404,0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssAddedEditedTable\" type =\"text/css\">"+
                        "    <title>Edit Customer</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Oops</h1>" +
                        "    <h2>Failed to edit customer! Customer with ID: " + customerID + " not found</h2>" +
                        "    <a href=\"/allCustomers\">All Customers</a>"+
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