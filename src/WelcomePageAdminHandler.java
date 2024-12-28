import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * WelcomePageAdmin is an HTTP handler for displaying the administrator welcome page.
 * It provides options to the admin to navigate between different sections on the portal.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
//Chaima Jebri
public class WelcomePageAdminHandler implements HttpHandler {
    
    /**
     * Default constructor for the WelcomePageAdminHandler class
     */
    public WelcomePageAdminHandler()
    {}

    /**
     * Handles HTTP requests for displaying the administrator welcome page.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        System.out.println("Welcome Page Admin Handler Called...");
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200, 0);
            out.write("""
                            <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                    <title>The Food Store</title>
                                </head>
                                <body>
                                    <form class="logout-admin" method="get" action="/login" onsubmit="return confirm('Are you sure you want to log out?')">
                                      <button type="submit">Log out</button>
                                    </form>
                                    <h1>Glad to have you back at The Food Store, Administrator!</h1>
                                    <h2>In your portal, feel free to choose from the following options</h2>
                                    
                                    <form class="welcomePageAdmin" method="get" action="/allProducts">
                                      <button type="submit">Food Products & Items Directory</button>
                                    </form>
                                    
                                    <form class="welcomePageAdmin" method="get" action="/allCustomers">
                                      <button type="submit">Customers Directory</button>
                                    </form>
                                    
                                    <form class="welcomePageAdmin" method="get" action="">
                                      <button type="submit">View Receiving Orders</button>
                                    </form>
                                    
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
