package CssHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *  CssTable is an HTTP handler for serving CSS styles for pages that contains a table.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class CssTable implements HttpHandler {

    /**
     * Default constructor for the CssTable class.
     */
    public CssTable()
    {}

    /**
     * Handles the HTTP request to serve the CSS styles for table pages.
     *
     * @param httpex the exchange containing the request from the client and used to send the response.
     */
    public void handle(HttpExchange httpex)
    {
        try(BufferedWriter out =new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                        body {
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            height: 100vh;
                            margin: 0;
                            background-color: #f0f0f0;
                        }
                        .logout-customer {
                            position: absolute;
                            top: 5px;
                            left: 0;
                            margin: 30px;
                        }
                        .logout-customer button {
                            background-color: #cc0000;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                            padding: 10px;
                            font-size: 13px;
                        }
                        .logout-customer button:hover {
                            background-color: #9e1c17;
                        }
                        .top-left {
                            position: absolute;
                            top: 5px;
                            left: 0;
                            margin: 30px;
                        }
                        .top-left button {
                            padding: 10px;
                            font-size: 13px;
                            background-color: #009879;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                        }
                        .top-left button:hover {
                            background-color: #007a66;
                        }
                        .expired{  /*show expired items in the retrieve items class*/
                            position: absolute;
                            top: 10px;
                            right: 0;
                            margin: 30px;
                        }
                        .expired button {
                            background-color: #009879;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                            padding: 10px;
                            font-size: 13px;
                        }
                        .expired button:hover {
                            background-color: #007a66;
                        }
                        .top-leftLink{
                            position: absolute;
                            top: 60px;
                            left: 0;
                            margin: 30px;
                        }
                        .top-right {
                            position: absolute;
                            top: 30px;
                            right: 10px;
                        }
                        .top-right form {
                            margin-bottom: 5px;
                        }
                        .top-right input {
                            margin-bottom: 5px;
                            padding: 5px;
                            border-radius: 2px;
                        }
                        .top-right button {
                            padding: 5px 10px;
                            width: 25%;
                            font-size: 13px;
                            background-color: #009879;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                        }
                        .top-right button:hover {
                            background-color: #007a66;
                        }
                        .table-container {
                            text-align: center;
                            background-color: #fff;
                            border: 1px solid #ccc; /* Border for the block */
                            padding: 20px; /* Padding for the block */
                            border-radius: 8px; /* Rounded corners for the block */
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Box shadow for a subtle effect */
                        }
                        h1{
                            margin-bottom: 30px;
                            text-transform: uppercase;
                            letter-spacing: 2px;
                            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
                        }
                        table {
                            font-family: Arial, Helvetica, sans-serif;
                            border-collapse: collapse;
                            width: 100%;
                            max-height: 380px; /* max height to get the scroller */
                            overflow-y: auto;
                            display: block;
                        }
                        th, td {
                            border: 1px solid #ddd; /*to add a border for the cases */
                            padding: 8px;
                        }
                        th {
                            background-color: #1f7db0; /*blue bilel 3ejbou #176e9e */
                            color: #FFFFFF;
                        }
                        tr:last-of-type {
                            border-bottom: 2px solid #1f7db0;
                        }
                        tr:hover {
                            background-color: #ddd; /*to colour the row you putting the mouse in*/
                        }
                        .bottom-link { /*back to main menu/show items customers products*/
                            display: block;
                            margin-top: 20px;
                            text-decoration: none;
                        }
                    """);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}