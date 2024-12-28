import CssHandlers.CssAddEditForm;
import CssHandlers.CssLoginRegistrationForm;
import CssHandlers.CssResultPage;
import CssHandlers.CssTable;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * MyServer is a class that represents an HTTP server for handling various requests
 * related to the CRUD operations for the food products, customers, users and food items.
 * This class includes also context for login, registration, welcome pages,
 * as well as CSS handlers for styling.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class MyServer {
    //Chaima Jebri
    private static final int port=8080;
    private FoodProductDAO foodproductdao;
    private CustomerDAO customerdao;
    private UserDAO userdao;
    private FoodItemDAO foodItemdao;
    private RequestDecoder reqDecoder;

    /**
     * Constructs a MyServer object with the specified dependencies.
     *
     * @param foodproductdao The data access object for managing food product data.
     * @param customerdao The data access object for managing customer data.
     * @param userdao The data access object for managing user data.
     * @param foodItemdao The data access object for managing food item data.
     * @param reqDecoder The request decoder responsible for parsing incoming HTTP requests.
     */
    public MyServer (FoodProductDAO foodproductdao,CustomerDAO customerdao,UserDAO userdao,FoodItemDAO foodItemdao, RequestDecoder reqDecoder)
    {
        this.foodproductdao=foodproductdao;
        this.customerdao=customerdao;
        this.userdao=userdao;
        this.foodItemdao=foodItemdao;
        this.reqDecoder=reqDecoder;
    }

    /**
     * Starts the HTTP server, configures the context handlers and sets the server executor to null.
     */
    public void startServer ()
    {
        try
        {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/login", new LoginHandler(reqDecoder,userdao));
            server.createContext("/welcomePageAdmin", new WelcomePageAdminHandler());
            server.createContext("/welcomePageCustomer",new WelcomePageCustomerHandler(foodproductdao,reqDecoder));
            server.createContext("/register", new RegistrationHandler(reqDecoder,userdao));
            // Context related to Food Products
            server.createContext("/allProducts", new RetrieveProductsHandler(foodproductdao,reqDecoder));
            server.createContext("/deleteProduct", new DeleteProductHandler(foodproductdao, reqDecoder));
            server.createContext("/editProduct", new EditProductHandler(foodproductdao,reqDecoder));
            server.createContext("/editProductProcess",new EditProductProcessHandler(foodproductdao,reqDecoder));
            server.createContext("/addProduct", new AddProductHandler());
            server.createContext("/addProductProcess", new AddProductProcessHandler(foodproductdao,reqDecoder));
            // Context related to Customers
            server.createContext("/allCustomers", new RetrieveCustomersHandler(customerdao,reqDecoder));
            server.createContext("/deleteCustomer", new DeleteCustomerHandler(customerdao,reqDecoder));
            server.createContext("/editCustomer",new EditCustomerHandler(customerdao,reqDecoder));
            server.createContext("/editCustomerProcess", new EditCustomerProcessHandler(customerdao,reqDecoder));
            server.createContext("/addCustomer", new AddCustomerHandler());
            server.createContext("/addCustomerProcess", new AddCustomerProcessHandler(customerdao,reqDecoder));
            // Context related to Food Items
            server.createContext("/allItems",new RetrieveItemsHandler(foodItemdao));
            server.createContext("/addItem", new AddItemHandler(reqDecoder,foodproductdao,foodItemdao));
            server.createContext("/deleteItem", new DeleteItemHandler(foodItemdao,reqDecoder));
            //Css Handlers
            server.createContext("/cssLoginRegistrationForm",new CssLoginRegistrationForm());
            server.createContext("/cssTable", new CssTable());
            server.createContext("/cssResultPage", new CssResultPage());
            server.createContext("/cssAddEditForm", new CssAddEditForm());
            server.setExecutor(null);
            server.start();
            System.out.println("The server is running on port: "+port);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
