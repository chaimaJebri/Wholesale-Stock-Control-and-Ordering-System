import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;

/**
 * EditProductProcessHandler is an HTTP handler for processing requests related to updating food product information.
 * It handles HTTP requests to update a product's information based on the provided form data.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class EditProductProcessHandler implements HttpHandler {
    private  FoodProductDAO foodproductdao;
    private RequestDecoder reqDecoder;

    //Chaima Jebri
    /**
     * Constructs an EditProductProcessHandler object with the specified dependencies.
     *
     * @param foodproductdao The data access object for managing food product data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public EditProductProcessHandler (FoodProductDAO foodproductdao, RequestDecoder reqDecoder)
    {
        this.foodproductdao=foodproductdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles HTTP requests for editing food product details through the form submission
     * by extracting data from the incoming request,validates and edited the database accordingly
     * then sending a response with the result.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException if an I/O error occurs while handling the HTTP request.
     */
    public void handle (HttpExchange httpex) throws IOException
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String line;
            String request = "";
            while ((line = in.readLine()) != null)
            {
                request = request + line;
            }
            HashMap<String, String> map = reqDecoder.requestStringToMap(request);
            int id = Integer.parseInt(map.get("id"));
            String sku = map.get("sku");
            String description = map.get("description");
            String category = map.get("category");
            int price = Integer.parseInt(map.get("price"));
            FoodProduct product = new FoodProduct(id, sku, description, category, price);
            boolean updated = foodproductdao.updateProduct(product);
            if (updated) {
                httpex.sendResponseHeaders(200, 0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Edit Food Product</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Food Product with ID: " + product.getId() + " updated successfully</h1>" +
                        "    <h2>Product Details</h2>" +
                        "    <table>" +
                        "        <thead>" +
                        "            <th>SKU</th>" +
                        "            <th>DESCRIPTION</th>" +
                        "            <th>CATEGORY</th>" +
                        "            <th>PRICE</th>" +
                        "        </thead>" +
                        "        <tbody>" +
                        "            <tr>" +
                        "                " + product.toHTMLString() +
                        "            </tr>" +
                        "        </tbody>" +
                        "    </table>" +
                        "<a href=\"/allProducts\">All products</a>"+
                        "</body>" +
                        "</html>");
            }
            else {
                httpex.sendResponseHeaders(404, 0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Edit Food Product</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Oops!</h1>" +
                        "    <h2>Failed to edit product with ID: " + id + " The SKU must be unique. Please choose a different one and try again</h2>" +
                        "    <a href=\"/editProduct?id="+id+"\">Try again</a>"+
                        "    <a class=\"top-left\" href=\"/allProducts\">All Food Products</a>"+
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