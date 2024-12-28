import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;

/**
 * LoginHandler is an HTTP handler responsible for processing login requests.
 * It handles both the login form display and the login process.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class LoginHandler implements HttpHandler {

    private RequestDecoder reqDecoder;
    private UserDAO userdao;

    /**
     * Constructs a LoginHandler object with the specified dependencies.
     *
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     * @param userdao The data access object for managing user data.
     */
    public LoginHandler (RequestDecoder reqDecoder, UserDAO userdao)
    {
        this.reqDecoder=reqDecoder;
        this.userdao=userdao;
    }

    /**
     * Handles an HTTP request for logging a user.
     * Depending on the request method it either processes the login form submission
     * or displays the login form to fill by the user.
     *
     * @param httpex the exchange containing the request from the
     *                 client and used to send the response
     */
    public void handle (HttpExchange httpex)
    {
        System.out.println("Login Handler Called...");

        if (httpex.getRequestMethod().equalsIgnoreCase("post"))
        {
            loginProcess(httpex);
        }
        else
        {
            loginForm(httpex);
        }

    }

    /**
     * Processes the login form submission by extracting the data from the incoming request,
     * authenticates the user, and redirects him to the appropriate page based on its role.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     */
    //Chaima Jebri
    private void loginProcess(HttpExchange httpex)
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(httpex.getRequestBody()));
            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String line;
            String request="";
            while ((line=in.readLine())!=null)
            {
                request=request+line;
            }
            HashMap<String, String> map = reqDecoder.requestStringToMap(request);
            String username=map.get("username");
            String password=map.get("password");
            boolean authenticated = userdao.authenticateUser(username,password);
            if (authenticated)
            {
                User user = userdao.findUser(username);
                String role = user.getRole();
                if ("administrator".equals(role))
                {
                    httpex.sendResponseHeaders(200, 0);
                    out.write("""
                            <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <meta http-equiv="refresh" content="3;url=/welcomePageAdmin">
                                    <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                    <title>The Food Store</title>
                                </head>
                                <body>
                                    <h1>Welcome to the Food Store &#x2764;</h1>
                                    <h2>Ready to Manage Your Stock and Orders ?</h2>
                                    <p>Hold tight! You will be redirected in few seconds ...</p>
                                </body>
                                </html>
                            """);
                }
                else if ("customer".equals(role))
                {
                    httpex.sendResponseHeaders(200, 0);
                    out.write("""
                            <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <meta http-equiv="refresh" content="3;url=/welcomePageCustomer">
                                    <link rel="stylesheet" href="/cssResultPage" type ="text/css">
                                    <title>The Food Store</title>
                                </head>
                                <body>
                                    <h1>Welcome to the Food Store &#x2764;</h1>
                                    <h2>Ready to discover our fresh Food Products ?</h2>
                                    <p>Hold tight! You will be redirected in few seconds ...</p>
                                </body>
                                </html>
                            """);
                }
                else
                {
                    httpex.getResponseHeaders().set("location", "/login?error=1");
                    httpex.sendResponseHeaders(302,0);
                }
            }
            else
            {
                httpex.getResponseHeaders().set("location", "/login?error=1");
                httpex.sendResponseHeaders(302,0);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays a login form, where users need to input their username and password,
     * and it shows an error message if authentication fails.
     *
     * @param httpex the exchange containing the request from the client and used to send the response
     */
    private void loginForm(HttpExchange httpex)
    {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpex.getResponseBody())))
        {
            String query =httpex.getRequestURI().getQuery();
            boolean error= query!=null && query.contains("error=1");

            httpex.sendResponseHeaders(200,0);

            out.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <link rel=\"stylesheet\" href=\"/cssLoginRegistrationForm\" type =\"text/css\">"+
                    "    <title>Login | The Food Store</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    " <div>\n" +
                    "    <h1>Login</h1>\n" +
                    "    <p>Hey there! Great to see you again</p>"+
                    "    <form method=\"post\" action=\"/login\">" +
                    "        <label for=\"username\"> Username </label>" +
                    "        <input type=\"text\" id=\"username\" name=\"username\" placeholder=\"Enter Username\" required>" +
                    "        <label for=\"password\"> Password </label>" +
                    "        <input type=\"password\" id=\"password\" name=\"password\" placeholder=\"Enter Password\" required>" +
                    "        <h5>If this is a public or shared device, don't forget to sign out when you're done to protect your account.</h5>"+
                    "        <button type=\"submit\">Login</button>" +
                    "    </form>\n" +
                    "    " + (error ? "<p style=\"color: red;\">Authentication failed. Please try again.</p>" : "") +
                    "    <p>Not a member yet? </p>"+
                    "    <a href=\"/register\">Join Now!</a>"+
                    " </div>\n" +
                    "    <h1 class=\"top-left\">The Food <br> Store</h1>"+
                    "</body>\n" +
                    "</html>");
        }
        catch (IOException e )
        {
            System.out.println(e.getMessage());
        }
    }
}
