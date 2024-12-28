import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * RetrieveItemsHandler is an HTTP handler for processing requests to retrieve food items from the database.
 * It handles both, displaying all food items or the expired items.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class RetrieveItemsHandler implements HttpHandler {

    private FoodItemDAO foodItemdao;

    /**
     * Constructs a RetrieveItemsHandler object with the specified dependencies.
     *
     * @param foodItemdao The data access object for managing food item data.
     */
    public RetrieveItemsHandler (FoodItemDAO foodItemdao)
    {
        this.foodItemdao=foodItemdao;
    }

    /**
     * Handles the http request for retrieving items
     * Depending on the request method, it either displays all food items or filters the expired ones.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        System.out.println("Retrieve Food ITEMS Handler Called...");
        if (httpex.getRequestMethod().equalsIgnoreCase("post"))
        {
            displayItems(httpex,foodItemdao.filterExpiredItems(),"expiredItems");
        }
        else
        {
            displayItems(httpex,foodItemdao.findAllItems(), null);
        }
    }

    /**
     * Displays all food items from the database.
     *
     * @param httpex the exchange containing the request from the client and used to send the response.
     * @param foodItems The food items to display
     * @param condition The condition for displaying specific food items
     */
    private void displayItems (HttpExchange httpex, ArrayList<FoodItem> foodItems, String condition)
    {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                   <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Food Items</title>
                        <link rel="stylesheet" href="/cssTable" type ="text/css">
                    </head>
                    <body>
                    <form class="top-left" method="get" action="/addItem">
                         <button type="submit">Add Item</button>
                    </form>
                    <form class="expired" method="post" action="/allItems">
                         <input type="hidden" name="expiredItems" value="expiredItems">
                         <button type="submit">Check expired Items</button>
                    </form>
                    <div class="table-container">
                         <h1>Food items Directory</h1>
                    <table>
                        <thead>
                        <tr>
                            <th>Food Item ID</th>
                            <th>Food Product Related</th>
                            <th>Expiry Date</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                   """);
            foodItems.forEach(foodItem ->
            {
                try
                {
                    out.write("<tr>" + foodItem.toHTMLString() + "<td><a href=\"/deleteItem?foodItemId=" + foodItem.getFoodItemId() + "\">Delete</a></td></tr>");
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
            if(condition != null && !condition.isEmpty())
            {
                out.write("<a class=\"bottom-link\" href=\"/allItems\">All Items</a>");
            }
            out.write("""
                    <a class="bottom-link" href="/allProducts">Display Food Products</a>
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
