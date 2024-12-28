package CssHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * CssLoginRegistrationForm is an HTTP handler for serving CSS styles for the login and the registration forms.
 *
 * @author Chaima Jebri
 * @version 1.2
 */

public class CssLoginRegistrationForm implements HttpHandler {

    /**
     * Default constructor for the CssLoginRegistrationForm class.
     */
    public CssLoginRegistrationForm()
    {}

    /**
     * Handles the HTTP requests to provide the CSS styles for login/registration forms.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        try(BufferedWriter out=new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            httpex.sendResponseHeaders(200,0);
            out.write("""
                        body {
                            font-family: Arial, sans-serif;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            height: 100vh;
                            margin: 0;
                        }
                                            
                        div {
                            text-align: center;
                            padding: 20px;
                            border: 1px solid #ccc;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        }
                                            
                        h1 {
                            margin-bottom: 20px;
                            letter-spacing: 2px;
                            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
                        }
                        h5 {
                            font-weight: normal;
                        }
                        form {
                            width: 350px; /* Set a width for the form */
                            margin: 0 auto;
                            display: flex;
                            flex-direction: column;
                        }
                                            
                        label {
                            margin-top: 10px;
                            text-align: left;
                        }
                                            
                        input {
                            padding: 8px;
                            margin: 5px 0;
                            border: 1px solid #ddd;
                            border-radius: 4px;
                            margin-bottom: 5px;
                        }
                                         
                        button {
                            padding: 10px;
                            background-color: #009879; /*this*/
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                            margin-top: 5px;
                        }
                                      
                        button:hover {
                            background-color: #007a66; /*this darker green*/
                        }
                                            
                        p {
                            margin-top: 15px;
                        }
                                            
                        a {
                            text-decoration: none;
                            color: #009879;  /*this*/
                        }
                                            
                        a:hover {
                            text-decoration: underline;
                        }
                                            
                        p.error-message {
                            color: red;
                            margin-top: 10px;
                        }
                        .top-left {
                            position: absolute;
                            top: 30px;
                            left: 30px;
                            margin: 30px;
                            text-transform: uppercase;
                            letter-spacing: 2px;
                            text-shadow: 2px 2px 4px #cccccc;
                            color: #1f7db0; /*this*/
                        }
                        .cancelButton{
                            position: absolute;
                            top: 15px;
                            left: 15px;
                            margin: 30px;
                            width: 150px; /* Set your desired width */
                            height: 40px;
                        }
                        .cancelButton button {
                            background-color: #cc0000;
                            color: #fff;
                            border: none;
                            border-radius: 4px;
                        }
                        .cancelButton button:hover {
                            background-color: #9e1c17;
                        }
                    """);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}