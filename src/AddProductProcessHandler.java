import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;

/**
 * AddProductProcessHandler is an HTTP handler for processing requests related
 * to adding a new food product through a form submission.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class AddProductProcessHandler implements HttpHandler {

    private FoodProductDAO foodproductdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs an AddProductProcessHandler object with the specified dependencies.
     *
     * @param foodproductdao The data access object for managing food product data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    //Chaima Jebri
    public AddProductProcessHandler (FoodProductDAO foodproductdao,RequestDecoder reqDecoder)
    {
        this.foodproductdao=foodproductdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles the http requests for adding a new food product
     * through the form submission by extracting data from the incoming request,
     * validates and adds the food product to the database then sending a response with the result.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException If an I/O error occurs while handling the HTTP request.
     */
    public void handle (HttpExchange httpex) throws IOException
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
            String sku = map.get("sku");
            String description = map.get("description");
            String category = map.get("category");
            int price = Integer.parseInt(map.get("price"));
            FoodProduct product = new FoodProduct(sku, description, category, price);
            boolean added = foodproductdao.addProduct(product);
            if (added) {
                httpex.sendResponseHeaders(200, 0);
                out.write("""
                            <!DOCTYPE html>
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                <title>Add Food Product</title>
                            </head>
                            <body>
                            <h1>Food Product Added Successfully</h1>
                            <h2>Added Product Details</h2>
                            <table>
                            <thead>
                            <th>SKU</th>
                            <th>DESCRIPTION</th>
                            <th>CATEGORY</th>
                            <th>PRICE</th>
                            </thead>
                            <tbody>
                            <tr>""" + product.toHTMLString() + """
                           </tr>
                           </tbody>
                           </table>
                           <a class="top-left" href="/addProduct">Add Another Food Product</a>
                           <a href="/allProducts">All Food Products</a>
                           </body>
                           </html>
                        """);
            } else {
                httpex.sendResponseHeaders(404, 0);
                out.write("""
                        <!DOCTYPE html>
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                <title>Add Food Product</title>
                            </head>
                            <body>
                                <h1>Oops!</h1>
                                <h2>Failed to add the food product. Please Try Again</h2>
                                <a href="/addProduct">Try again</a>
                                <a class="top-left" href="/allProducts">All Food Products</a>
                                </body>
                            </html>
                        """);

            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}