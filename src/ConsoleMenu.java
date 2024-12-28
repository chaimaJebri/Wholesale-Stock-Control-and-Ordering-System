import java.util.Scanner;

/**
 * The ConsoleMenu class provides a simple console-based menu for interacting with different sections in the portal.
 * Users can choose to access either food products console menu, customers console menu, food items console menu or exit.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class ConsoleMenu {

    private FoodproductConsole foodProductConsole;
    private CustomerConsole customerConsole;
    private FoodItemConsole foodItemConsole;

    /**
     * Constructs a ConsoleMenu object with the specified dependencies.
     *
     * @param foodProductConsole The console menu for food products
     * @param customerConsole The console menu for customers
     * @param foodItemConsole The console menu for food items.
     */
    //Chaima Jebri
    public ConsoleMenu (FoodproductConsole foodProductConsole,CustomerConsole customerConsole, FoodItemConsole foodItemConsole)
    {
        this.foodProductConsole=foodProductConsole;
        this.customerConsole=customerConsole;
        this.foodItemConsole=foodItemConsole;
    }

    /**
     * Displays the menu in the console application.
     * The options in the menu includes the food products console menu, the customers console menu,
     * the food items console menu and exit.
     */
    public void menu ()
    {
        Scanner reader = new Scanner(System.in);
        int chosen;
        do {
            System.out.println("-----------");
            System.out.println("The Food Store");
            System.out.println("Choose from these options");
            System.out.println("-----------");
            System.out.println("[1] Food products console menu");
            System.out.println("[2] Customers console menu");
            System.out.println("[3] Food Items console menu");
            System.out.println("[4] Exit");
            chosen = reader.nextInt();
            switch (chosen) {
                case 1: {
                   foodProductConsole.displayMenu();
                }
                break;
                case 2: {
                    customerConsole.displayMenu();
                }
                break;
                case 3: {
                    foodItemConsole.displayMenu();
                }
                break;
                default: {
                    System.out.println("Exit");
                }
            }
        } while (chosen != 4);
    }
}
