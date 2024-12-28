import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarEntry;

/**
 * WelcomePageCustomerHandler is an HTTP handler for displaying the customers welcome page.
 * It displays the food products and allows customers to filter products by category,
 * search products by description and add products to their baskets.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class WelcomePageCustomerHandler implements HttpHandler {

    //Chaima Jebri
    private FoodProductDAO foodproductdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs a WelcomePageCustomerHandler object with the specified dependencies.
     *
     * @param foodproductdao The data access object for managing food product data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public WelcomePageCustomerHandler(FoodProductDAO foodproductdao, RequestDecoder reqDecoder)
    {
        this.foodproductdao=foodproductdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles HTTP requests for displaying the welcome page designed for customers.
     * It supports displaying all products, filtering products by category,
     * searching products by description and adding products to the baskets.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        System.out.println("Welcome page Customer Handler Called...");

        if (httpex.getRequestMethod().equalsIgnoreCase("post"))
        {
            String path = httpex.getRequestURI().getPath();
            if (path.equals("/welcomePageCustomer"))
            {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody())))
                {
                    String line;
                    String request="";
                    while ((line=in.readLine())!=null)
                    {
                        request=request+line;
                    }
                    HashMap<String,String> map=reqDecoder.requestStringToMap(request);

                    if (map.containsKey("search"))
                    {
                        displayProductsByDescription(httpex,map.get("search"));
                    }
                    else if (map.containsKey("category"))
                    {
                        displayProductsByCategory(httpex,map.get("category"));
                    }
                    else
                    {
                        displayAllProducts(httpex,foodproductdao.findAllProducts(),null);
                    }
                }
                catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        else
        {
            displayAllProducts(httpex, foodproductdao.findAllProducts(),null);
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
        ArrayList<FoodProduct> productsByCategory =foodproductdao.filterProductsByCategory(category);
        displayAllProducts(httpex,productsByCategory,category);
    }

    /**
     * Displays food products searched by description.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     * @param search The search string to search for products by description
     */
    private void displayProductsByDescription(HttpExchange httpex, String search)
    {
        ArrayList<FoodProduct> productsByDescription=foodproductdao.searchProductsByDescription(search);
        displayAllProducts(httpex,productsByDescription,search);
    }

    /**
     * Displays all food products.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     * @param foodproducts  the food product to display
     * @param condition the condition for additional filtering or searching.
     */
    private void displayAllProducts(HttpExchange httpex, ArrayList<FoodProduct> foodproducts, String condition)
    {
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200, 0);
            out.write("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <link rel="stylesheet" href="/cssTable" type ="text/css">
                        <title>Food Products</title>
                    </head>
                    <body>
                        <form class="logout-customer" method="get" action="/login" onsubmit="return confirm('Are you sure you want to log out?')">
                               <button type="submit">Log out</button>
                        </form>
                        <div class="top-right">
                            <form method="post" action="/welcomePageCustomer">
                                 <input type="text" id="category" name="category" placeholder="Enter Category" required>
                                 <button type="submit">Filter</button>
                            </form>
                            <form method="post" action="/welcomePageCustomer">
                                 <input type="text" id="search" name="search" placeholder="Enter Description" required>
                                 <button type="submit">Search</button>
                            </form>
                        </div>
                        <div class="table-container">
                        <h1>Food Products Available</h1>
                    <table>
                        <thead>
                            <tr>
                               <th>ID</th>
                               <th>SKU</th>
                               <th>DESCRIPTION</th>
                               <th>CATEGORY</th>
                               <th>PRICE</th>
                               <th>Add to basket</th>
                            </tr>
                        </thead>
                        <tbody>
                    """);
            foodproducts.forEach(foodProduct ->
            {
                try
                {
                    out.write("<tr><td>" + foodProduct.getId() + "</td>" + foodProduct.toHTMLString() + "<td><a href=\"\" >ADD</a></td></tr>");
                }
                catch(IOException e)
                {
                    System.out.println(e.getMessage());
                }
            });
            out.write("""
                    </tbody>
                    </table>
                    """
            );
            if (condition != null && !condition.isEmpty())
            {
                out.write("<a class=\"bottom-link\" href=\"/welcomePageCustomer\">All Food Products</a>");
            }
            out.write("""
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
