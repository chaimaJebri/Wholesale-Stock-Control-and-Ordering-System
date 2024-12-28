import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * DeleteItemHandler is an HTTP handler for processing requests related to deleting a food item.
 * It handles HTTP delete requests and deletes the item with the specified ID from the database.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class DeleteItemHandler implements HttpHandler {

    private FoodItemDAO foodItemdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs the DeleteItemHandler object with the specified dependencies.
     *
     * @param foodItemdao The data access object for managing food item data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    //Chaima Jebri
    public DeleteItemHandler (FoodItemDAO foodItemdao, RequestDecoder reqDecoder)
    {
        this.foodItemdao=foodItemdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Handles the HTTP DELETE requests for deleting a food item by extracting the item ID from the incoming request,
     * validates and deletes the item from the database then sending a response with the result.
     * 
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */

    public void handle(HttpExchange httpex)
    {
        System.out.println("Deleting Item Handler Called...");
        String request=httpex.getRequestURI().getQuery();
        HashMap<String, String> map = reqDecoder.requestStringToMap(request);
        int foodItemId=Integer.parseInt(map.get("foodItemId"));
        boolean deleted =foodItemdao.deleteItem(foodItemId);
        try (BufferedWriter out =new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            if (deleted)
            {
                httpex.sendResponseHeaders(200,0);
                out.write("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <link rel=\"stylesheet\" href=\"/cssResultPage\" type =\"text/css\">"+
                        "    <title>Delete Item</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>YES!</h1>" +
                        "    <h2>Food Item with ID: " + foodItemId + " deleted successfully</h2>" +
                        "    <a href=\"/allItems\">All Items</a>"+
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
                        "    <title>Delete Item</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h1>Oops!</h1>" +
                        "    <h2>Failed to delete the item! Food Item with ID: " + foodItemId + " not found</h2>" +
                        "    <a href=\"/allItems\">All Items</a>"+
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
