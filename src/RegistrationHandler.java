import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;

/**
 * RegistrationHandler is an HTTP handler responsible for processing user registration requests.
 * It handles both the registration form display and the registration process.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class RegistrationHandler implements HttpHandler {
    //Chaima Jebri
    private RequestDecoder reqDecoder;
    private UserDAO userdao;

    /**
     * Constructs a RegistrationHandler object with the specified dependencies.
     *
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     * @param userdao The data access object for managing user data.
     */
    public RegistrationHandler (RequestDecoder reqDecoder, UserDAO userdao)
    {
        this.reqDecoder=reqDecoder;
        this.userdao=userdao;
    }

    /**
     * Handles the HTTP request for registering a user.
     * Depending on the request method it either processes the registration form submission
     * or displays the registration form to fill by the user.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle(HttpExchange httpex)
    {
        System.out.println("Registration Handler Called...");

        if(httpex.getRequestMethod().equalsIgnoreCase("post"))
        {
            registrationProcess(httpex);
        }
        else
        {
            registrationForm(httpex);
        }
    }

    /**
     * Processes the registration form by extracting the data from the incoming request,
     * checking if the passwords match, and adds the user to the database if successful.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     */
    private void registrationProcess(HttpExchange httpex)
    {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String line;
            String request="";
            while ((line=in.readLine())!=null)
            {
                request=request+line;
            }
            HashMap<String,String> map=reqDecoder.requestStringToMap(request);
            String username =map.get("username");
            String password=map.get("password");
            String confirmPassword=map.get("confirmPassword");
            if (password.equals(confirmPassword))
            {
                String role = map.get("role");
                User user = new User(username, password, role);
                boolean added = userdao.addUser(user);
                if (added)
                {
                    httpex.sendResponseHeaders(200, 0);
                    out.write("""
                            <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                    <title>Sign Up</title>
                                </head>
                                <body>
                                <h1>Welcome! Your Account Has Been Successfully Created.</h1>
                                <h2>Unlock a world of possibilities by logging in.</h2>
                                <a href="/login">Login</a>
                            </body>
                            </html>
                            """);
                }
                else
                {
                    httpex.getResponseHeaders().set("location", "/register?error=1");
                    httpex.sendResponseHeaders(302, 0);
                }
            }
            else
            {
                httpex.getResponseHeaders().set("location", "/register?error=2");
                httpex.sendResponseHeaders(302, 0);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays a registration form where users input their username, password, confirm password, and role.
     * It shows error messages if registration fails or passwords don't match.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     */
    private void registrationForm(HttpExchange httpex)
    {
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String query =httpex.getRequestURI().getQuery();
            boolean error= query!=null && query.contains("error=1");
            boolean error2= query!=null && query.contains("error=2");

            httpex.sendResponseHeaders(200,0);
            out.write("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <link rel="stylesheet" href="/cssLoginRegistrationForm" type ="text/css">
                        <title>Sign Up</title>
                    </head>
                    <body>
                    <div>
                        <h1>Sign Up</h1>
                        <p>Please fill in this form to create an account</p>
                        <form method = "post" action="/register">
                            <label for="username"> Username </label>
                            <input type="text" id="username" name="username" placeholder="Enter Username" required>
                            <label for="password"> Password </label>
                            <input type="password" id="password" name="password" placeholder="Enter Password" required>
                            <label for="confirmPassword"> Confirm Password </label>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
                            <label for="role"> Role </label>
                            <input type="text" id="role" name="role" placeholder="Enter Role" required>
                            <h5>Please note: Our PRIVACY CENTRE provides more details on marketing, how we use your information and about your Privacy rights. You can unsubscribe at any time.</h5>
                            <button type="submit">Sign Up</button>
                        </form>"""+
                    "    " + (error ? "<p style=\"color: red;\">Registration failed. Please try again.</p>" : "") +
                    "    " + (error2 ? "<p style=\"color: red;\">Passwords don't match.Please try again</p>" : "") +
                    """
                </div>
                
                <form class="cancelButton" method="get" action="/login">
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
