package CssHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * CssResultPage is an HTTP handler for serving CSS styles for result pages
 * (example:after adding or deleting a food product, after creating an account etc.).
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class CssResultPage implements HttpHandler {

    /**
     * Default constructor for the CssResultPage class.
     */
    public CssResultPage()
    {}

    /**
     * Handles the HTTP requests to provide the CSS styles for result pages.
     * 
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        try(BufferedWriter out=new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                        body {
                        text-align: center;
                        background-color: #f3f3f3;
                        height: 90vh;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        justify-content: center;
                        }
                        .top-left {
                            position: absolute;
                            top: 10px;
                            left: 10px;
                            margin: 30px;
                        }
                        .logout-admin {
                            position: absolute;
                            top: 10px;
                            left:10px;
                            margin: 30px;
                        }
                        .logout-admin button {
                            background-color: #cc0000;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                            padding: 15px;
                            font-size: 16px;
                        }
                        .logout-admin button:hover {
                            background-color: #9e1c17;
                        }
                        .welcomePageAdmin {
                            margin-top: 30px;
                        }
                        .welcomePageAdmin button {
                            padding: 15px;
                            font-size: 30px;
                            background-color: #009879;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                        }
                        .welcomePageAdmin button:hover {
                            background-color: #007a66;
                        }
                        a,p {
                            display: block;
                            margin-top: 20px;
                        }
                                         
                        h1, h2 {
                            margin: 20px 0;
                            letter-spacing: 2px;
                            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
                        }
                                            
                        table {
                            font-family: Arial, Helvetica, sans-serif;
                            border: 1px solid #000;
                            margin: 0 auto;
                        }
                        th,td{
                            border: 1px solid #000;
                            padding: 8px;
                        }
                        
                    """);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}