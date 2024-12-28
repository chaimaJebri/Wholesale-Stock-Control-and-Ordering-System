import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * EditProductHandler is an HTTP handler for processing requests related to editing food product details.
 * It handles HTTP requests to edit a product by displaying a form with the current details for editing.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class EditProductHandler implements HttpHandler {
    private FoodProductDAO foodproductdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs an EditProductHandler object with the specified dependencies.
     *
     * @param foodproductdao The data access object for managing food product data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    //Chaima Jebri
    public EditProductHandler (FoodProductDAO foodproductdao, RequestDecoder reqDecoder)
    {
        this.foodproductdao=foodproductdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles HTTP requests for editing food product information
     * by extracting the product ID from the incoming request, validates and retrieves the product details from the database,
     * then displaying a form with the current information and allows editing.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */

    public void handle (HttpExchange httpex)
    {
        System.out.println("Edit Product Handler Called...");
        String request = httpex.getRequestURI().getQuery();
        HashMap<String, String> map = reqDecoder.requestStringToMap(request);
        int id = Integer.parseInt(map.get("id"));
        FoodProduct product = foodproductdao.findProduct(id);
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            if(product!=null)
            {
                httpex.sendResponseHeaders(200,0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssAddEditForm\" type =\"text/css\">"+
                        "    <title>Edit Food Product</title>" +
                        "</head>" +
                        "<body>" +
                        "<div>" +
                        "<h1>Edit Food Product</h1>" +
                        "<p>Make adjustments to the Food Product details as required</p>" +
                        "<form method=\"post\" action=\"/editProductProcess\">" +
                        "<input type=\"hidden\" name=\"id\" value=\"" + id + "\">" +
                        "<label class=\"top-bottom-fields\" for=\"sku\"> Stock-Keeping Unit </label>" +
                        "<input type=\"text\" id=\"sku\" name=\"sku\" placeholder=\"Enter SKU\" value=\"" + product.getSku() + "\" required>" +
                        "<label for=\"description\"> Description </label>" +
                        "<input type=\"text\" id=\"description\" name=\"description\" placeholder=\"Enter Description\" value=\"" + product.getDescription() + "\" required>" +
                        "<label for=\"category\"> Category </label>" +
                        "<input type=\"text\" id=\"category\" name=\"category\" placeholder=\"Enter Category\" value=\"" + product.getCategory() + "\" required>" +
                        "<label for=\"price\"> Price </label>" +
                        "<input type=\"text\" id=\"price\" name=\"price\" placeholder=\"Enter Price\" value=\"" + product.getPrice() + "\" required>" +
                        "<button class=\"top-bottom-fields\" type=\"submit\">Save Changes</button>" +
                        "</form>" +
                        "</div>" +
                        "<form class=\"top-left\" method=\"get\" action=\"/allProducts\">"+
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
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Edit Food Product</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Oops!</h1>" +
                        "    <h2>Failed to edit the product! Food Product with ID: " + id + " not found</h2>" +
                        "    <a href=\"/allProducts\">All Products</a>"+
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