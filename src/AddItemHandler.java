import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * AddItemHandler is an HTTP handler for processing requests related to adding food items.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class AddItemHandler implements HttpHandler {

    private RequestDecoder reqDecoder;
    private FoodProductDAO foodproductdao;
    private FoodItemDAO foodItemdao;

    /**
     * Constructs an AddItemHandler object with the specified dependencies.
     *
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     * @param foodproductdao The data access object for managing food product data.
     * @param foodItemdao The data access object for managing food item data.
     */
    public AddItemHandler(RequestDecoder reqDecoder, FoodProductDAO foodproductdao, FoodItemDAO foodItemdao)
    {
        this.reqDecoder=reqDecoder;
        this.foodproductdao=foodproductdao;
        this.foodItemdao=foodItemdao;
    }

    /**
     * Handles the http requests for adding a new food item.
     * Depending on the request method, it either processes the form submission or displays the form
     * to fill for adding a new food item.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        System.out.println("Add Item Handler Called...");
        if (httpex.getRequestMethod().equalsIgnoreCase("post"))
        {
            try
            {
                addItemProcess(httpex);
            }
            catch (ParseException e)
            {
                System.out.println("Please enter the date Format as 'yyyy-MM-dd' ");
            }
        }
        else
        {
            addNewItem(httpex);
        }

    }

    /**
     * Process the form submission to add a new food item by extracting data from the incoming request,
     * validates and adds the item to the database then sending a response with the result.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     * @throws ParseException If there is an error parsing the date format.
     */
    
    //Chaima Jebri
    private void addItemProcess(HttpExchange httpex) throws ParseException
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody()));
            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String line;
            String request = "";
            while ((line = in.readLine()) != null) {
                request = request + line;
            }
            HashMap<String, String> map = reqDecoder.requestStringToMap(request);
            try
            {
                int foodItemId = Integer.parseInt(map.get("foodItemId"));
                int foodProductId = Integer.parseInt(map.get("id"));
                FoodProduct product = foodproductdao.findProduct(foodProductId);
                if (product!=null)
                {
                    String date = map.get("expiryDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date expiryDate = dateFormat.parse(date);
                    FoodItem item = new FoodItem(foodItemId, product, expiryDate);
                    boolean added = foodItemdao.addItem(item);
                    if (added)
                    {
                        httpex.sendResponseHeaders(200, 0);
                        out.write("""
                                <!DOCTYPE html>
                                    <html lang="en">
                                    <head>
                                        <meta charset="UTF-8">
                                        <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                        <title>Add Food Item</title>
                                    </head>
                                    <body>
                                    <h1>Food Item Added successfully</h1>
                                    <h2>Added Item Details</h2>
                                    <table>
                                        <thead>
                                          <th>Food Item ID</th>
                                          <th>Food Product related</th>
                                          <th>Expiry Date</th>
                                        </thead>
                                        <tbody>
                                        <tr>""" + item.toHTMLString() + """
                                        </tr>
                                        </tbody>
                                    </table>
                                <a class="top-left" href="/addItem">Add Another Item</a>
                                <a href="/allItems">All Items</a>
                                </body>
                                </html>
                                """);
                    }
                    else
                    {
                        httpex.sendResponseHeaders(404, 0);
                        out.write("""
                                <!DOCTYPE html>
                                    <html lang="en">
                                    <head>
                                        <meta charset="UTF-8">
                                        <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                        <title>Add Food Item</title>
                                    </head>
                                    <body>
                                        <h1>Oops!</h1>
                                        <h2>Failed to add an item! The food item ID must be unique. Please choose a different ID and try again</h2>
                                        <a href="/addItem">Try again</a>
                                        <a class="top-left" href="/allItems">All Items</a>
                                        </body>
                                    </html>
                                """);
                    }
                }
                else
                {
                    httpex.sendResponseHeaders(404,0);
                    out.write("""
                                <!DOCTYPE html>
                                    <html lang="en">
                                    <head>
                                        <meta charset="UTF-8">
                                        <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                        <title>Add Food Item</title>
                                    </head>
                                    <body>
                                        <h1>Oops!</h1>
                                        <h2>Failed to add an item! The Food Product ID you entered is not available. Please choose a valid ID and try again.</h2>
                                        <a href="/addItem">Try again</a>
                                        <a class="top-left" href="/allItems">All Items</a>
                                        </body>
                                    </html>
                                """);
                }
            }
            catch (ParseException e)
            {
                System.out.println(e.getMessage());
                httpex.sendResponseHeaders(404,0);
                out.write("""
                                <!DOCTYPE html>
                                    <html lang="en">
                                    <head>
                                        <meta charset="UTF-8">
                                        <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                        <title>Add Food Item</title>
                                    </head>
                                    <body>
                                        <h1>Oops!</h1>
                                        <h2>Failed to add Item! Invalid date format. Please enter the date in the format 'yyyy-MM-dd'</h2>
                                        <a href="/addItem">Try again</a>
                                        <a class="top-left" href="/allItems">All Items</a>
                                        </body>
                                    </html>
                                """);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays an HTML form for adding a new food item, where users can input details related to the new item
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     */
    private void addNewItem(HttpExchange httpex)
    {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <link rel="stylesheet" href="/cssAddEditForm" type ="text/css">
                        <title>Add Food Item with expiry date</title>
                    </head>
                    <body>
                    <div>
                        <h1>Add Food Item</h1>
                        <p>Kindly fill the details below to add a new Food Item</p>
                        <form method = "post" action="/addItem">
                            <label class="top-bottom-fields" for="foodItemId">Food Item ID</label>
                            <input type="text" id="foodItemId" name="foodItemId" placeholder="Enter Food Item ID" required>
                            <label for="id">Food Product related ID</label>
                            <input type="text" id="id" name="id" placeholder="Enter valid Food Product ID" required>
                            <label for="expiryDate">Expiry Date</label>
                            <input type="text" id="expiryDate" name="expiryDate" placeholder="yyyy-MM-dd" required>
                            <button class="top-bottom-fields" type="submit">ADD ITEM</button>
                        </form>
                    </div>
                        <form class="top-left" method="get" action="/allItems">
                            <button type="submit">Cancel</button>
                        </form>
                    </body>
                    </html>
                    """);

        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
