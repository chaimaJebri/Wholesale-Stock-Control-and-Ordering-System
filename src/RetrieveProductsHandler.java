import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * RetrieveProductsHandler is an HTTP handler for handling requests related to retrieve food products.
 * It supports displaying all products, filtering products by category, searching products by description.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class RetrieveProductsHandler implements HttpHandler {

    private FoodProductDAO foodproductdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs a RetrieveProductHandler object with the specified dependencies.
     *
     * @param foodproductdao The data access object for managing food product data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public RetrieveProductsHandler (FoodProductDAO foodproductdao, RequestDecoder reqDecoder)
    {
        this.foodproductdao=foodproductdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles HTTP requests for retrieving food products.It supports displaying all products,
     * filtering products by category and searching products by description.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        System.out.println("Retrieve Products Handler Called...");
        if (httpex.getRequestMethod().equalsIgnoreCase("post"))
        {
            String path = httpex.getRequestURI().getPath();
            if (path.equals("/allProducts"))
            {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody()))) {
                    String line;
                    String request = "";
                    while ((line = in.readLine()) != null)
                    {
                        request = request + line;
                    }
                    HashMap<String, String> map = reqDecoder.requestStringToMap(request);

                    if (map.containsKey("search"))
                    {
                        displayProductsByDescription(httpex, map.get("search"));
                    }
                    else if (map.containsKey("category"))
                    {
                        displayProductsByCategory(httpex, map.get("category"));
                    }
                    else
                    {
                        displayAllProducts(httpex, foodproductdao.findAllProducts(), null);
                    }
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        else
        {
            displayAllProducts(httpex, foodproductdao.findAllProducts(), null);
        }
    }

    /**
     * Displays food products filtered by category.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     * @param category The category to filter products
     */
    private void displayProductsByCategory(HttpExchange httpex, String category)
    {
        ArrayList<FoodProduct> foodproducts=foodproductdao.filterProductsByCategory(category);
        displayAllProducts(httpex,foodproducts,category);
    }

    /**
     * Displays food products searched by description.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     * @param search The search string to search for products by description
     */
    private void displayProductsByDescription(HttpExchange httpex, String search)
    {
        ArrayList<FoodProduct> foodproducts=foodproductdao.searchProductsByDescription(search);
        displayAllProducts(httpex,foodproducts,search);
    }

    //Chaima Jebri
    /**
     * Displays all food products.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     * @param foodproducts the food product to display
     * @param condition the condition for additional filtering or searching.
     */
    private void displayAllProducts (HttpExchange httpex, ArrayList<FoodProduct> foodproducts, String condition)
    {
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200, 0);
            out.write("""
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Food Products</title>
                            <link rel="stylesheet" href="/cssTable" type ="text/css">
                        </head>
                        <body>
                        <form class="top-left" method="get" action="/addProduct">
                             <button type="submit">Add Food Product</button>
                        </form>
                        <div class="top-right">
                            <form method="post" action="/allProducts">
                                 <input type="text" id="category" name="category" placeholder="Filter by Category" required>
                                 <button type="submit">Filter</button>
                            </form>
                            <form method="post" action="/allProducts">
                                 <input type="text" id="search" name="search" placeholder="Search by Description" required>
                                 <button type="submit">Search</button>
                            </form>
                        </div>
                        <div class="table-container">
                        <h1>Food Products Available</h1>
                            <table>
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Stock-keeping Unit</th>
                                    <th>DESCRIPTION</th>
                                    <th>CATEGORY</th>
                                    <th>PRICE</th>
                                    <th>EDIT</th>
                                    <th>DELETE</th>
                                </tr>
                                </thead>
                                <tbody>
                        """
            );
            foodproducts.forEach(foodProduct ->
            {
                try
                {
                    out.write("<tr><td>" + foodProduct.getId() + "</td>" + foodProduct.toHTMLString() + "<td><a href= \"/editProduct?id=" + foodProduct.getId() + "\">Edit</a></td><td><a href= \"/deleteProduct?id=" + foodProduct.getId() + "\">Delete</a></td></tr>");
                }
                catch(IOException e)
                {
                    System.out.println(e.getMessage());
                }
            });
            out.write("""
                        </tbody>
                        </table>
                        """);
            if (condition != null && !condition.isEmpty())
            {
                out.write("<a class=\"bottom-link\" href=\"/allProducts\">All Food Products</a>");
            }
            out.write("""
                           <a class="bottom-link" href="/welcomePageAdmin">Back to Main Menu</a>
                       </div>
                       <a class="top-leftLink" href="/allItems">Items with Expiry Date</a>
                        </body>
                        </html>
                        """
            );
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
