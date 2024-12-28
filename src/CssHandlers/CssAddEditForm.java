package CssHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * CssAddEditForm is an HTTP handler for serving CSS styles for the add and edit forms.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class CssAddEditForm implements HttpHandler {

    /**
     * Default constructor for the CssAddEditForm class.
     */
    public CssAddEditForm()
    {}

    /**
     * Handles the HTTP requests to provide the CSS styles for add/edit forms.
     * 
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        try(BufferedWriter out=new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                        body {
                            background-color: #f3f3f3;
                            display: flex;
                            flex-direction: column;
                            align-items: center;
                            justify-content: center;
                            height: 100vh;
                            margin: 0;
                        }
                                            
                        div {
                            background-color: #ffffff;
                            padding: 19px;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                                            
                        h1 {
                            text-align: center;
                            letter-spacing: 2px;
                            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
                        }
                        
                        form {
                            display: flex;
                            flex-direction: column;
                            align-items: center;
                        }
                                           
                        label,p {
                            margin: 4px 0;
                            font-family: 'Arial', sans-serif;
                        }
                                       
                        input {
                            width: 70%;
                            padding: 8px;
                            margin-bottom: 5px;
                            border: 1px solid #ccc;
                            border-radius: 4px;
                        }
                        .top-bottom-fields{
                            margin-top: 10px;
                        }
                        button.top-bottom-fields{
                            padding: 15px;
                            font-size: 16px;
                            background-color: #009879;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                        }
                        button.top-bottom-fields:hover {
                            background-color: #007a66;
                        }
                        .top-left {
                            position: absolute;
                            top: 10px;
                            left: 10px;
                            margin: 30px;
                        }
                        .top-left button {
                            padding: 15px;
                            font-size: 16px;
                            background-color: #cc0000;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                        }
                        .top-left button:hover {
                           background-color: #9e1c17;
                        }
                    """);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}