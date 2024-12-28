import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * AddProductHandler is an HTTP handler for processing requests related to adding a new food product
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class AddProductHandler implements HttpHandler {
    /**
     * Default constructor for the AddProductHandler class
     */
    public AddProductHandler ()
    {}

    /**
     * Handles the http requests for adding a new food product by displaying an HTML form
     * where users can input details related to the new product
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */

    //Chaima Jebri
    public void handle (HttpExchange httpex)
    {
        System.out.println("Add Product Handler Called...");
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200, 0);
            out.write("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <link rel="stylesheet" href="/cssAddEditForm" type ="text/css">
                        <title>Add Food Product</title>
                    </head>
                    <body>
                    <div>
                        <h1>ADD FOOD PRODUCT</h1>
                        <p>Kindly fill the details below to add a new Food Product</p>
                        <form method = "post" action="/addProductProcess">
                            <label class="top-bottom-fields" for="sku"> SKU </label>
                            <input type="text" id="sku" name="sku" placeholder="Enter SKU" required>
                            <label for="description"> Description </label>
                            <input type="text" id="description" name="description" placeholder="Enter Description" required>
                            <label for="category"> Category </label>
                            <input type="text" id="category" name="category" placeholder="Enter Category" required>
                            <label for="price"> Price </label>
                            <input type="text" id="price" name="price" placeholder="Enter Price" required>
                            <button class="top-bottom-fields" type="submit">ADD PRODUCT</button>
                        </form>
                    </div>
                        <form class="top-left" method="get" action="/allProducts">
                            <button type="submit">Cancel</button>
                        </form>
                    </body>
                    </html>
                    """);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
