/**
 * The Main class is the entry point of the Food Store website.
 * It sets up components such as the DAOs, consoles, the server and the request decoder.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class Main {
    /**
     * This is the main method for the Food Store website.
     *
     * @param args stores the incoming command line arguments for the application.
     */

    //Chaima Jebri
    
    public static void main(String[] args)
    {
        FoodProductDAO productDao = new FoodProductDAO();
        CustomerDAO customerDao = new CustomerDAO();
        UserDAO userdao = new UserDAO();
        FoodItemDAO foodItemdao = new FoodItemDAO(productDao);
        FoodproductConsole productConsole = new FoodproductConsole(productDao);
        CustomerConsole customerConsole = new CustomerConsole(customerDao);
        FoodItemConsole itemConsole=new FoodItemConsole(foodItemdao,productDao);
        RequestDecoder reqDecoder = new RequestDecoder();
        MyServer server = new MyServer(productDao,customerDao,userdao,foodItemdao,reqDecoder);
        server.startServer();
        ConsoleMenu consoleMenu = new ConsoleMenu(productConsole,customerConsole,itemConsole);
        consoleMenu.menu();
    }

    /**
     * Default Constructor for the main class
     */
    public Main()
    {}
}