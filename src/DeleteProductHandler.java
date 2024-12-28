import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * DeleteProductHandler is an HTTP handler for processing requests related to deleting a food product.
 * It handles HTTP delete requests and deletes the product with the specified ID from the database.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class DeleteProductHandler implements HttpHandler {
    private FoodProductDAO foodproductdao;
    private RequestDecoder reqDecoder;
//Chaima Jebri
    /**
     * Constructs a DeleteProductHandler object with the specified dependencies.
     *
     * @param foodproductdao The data access object for managing food product data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public DeleteProductHandler (FoodProductDAO foodproductdao, RequestDecoder reqDecoder)
    {
        this.foodproductdao=foodproductdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles the HTTP DELETE requests for deleting a food product by extracting the product ID from the incoming request,
     * validates and deletes the product from the database then sending a response with the result.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        System.out.println("Deleting Product Handler Called...");
        String request=httpex.getRequestURI().getQuery();
        HashMap<String, String> map = reqDecoder.requestStringToMap(request);
        int id =Integer.parseInt(map.get("id"));
        boolean deleted =foodproductdao.deleteProduct(id);
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody()))) {
            if (deleted)
            {
                httpex.sendResponseHeaders(200, 0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Delete Food Product</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>YES!</h1>" +
                        "    <h2>Food Product with ID: " + id + " deleted successfully</h2>" +
                        "    <a href=\"/allProducts\">All Food Products</a>"+
                        "</body>" +
                        "</html>"
                );
            }
            else
            {
                httpex.sendResponseHeaders(404, 0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Delete Food Product</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Oops!</h1>" +
                        "    <h2>Failed to delete the product! Food Product with ID: " + id + " not found</h2>" +
                        "    <a href=\"/allProducts\">All Food Products</a>"+
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
