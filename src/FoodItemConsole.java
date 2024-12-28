import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * The FoodItemConsole class provides a simple console-based menu for interacting with food items.
 * The menu options are displayed in the console, and the user input is processed accordingly.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class FoodItemConsole {

    private FoodItemDAO foodItemdao;
    private FoodProductDAO foodproductdao;

    //Chaima Jebri
    /**
     * Constructs a FoodItemConsole object with the specified dependencies.
     *
     * @param foodItemdao The data access object for managing food item data.
     * @param foodproductdao The data access object for managing food product data.
     */
    public FoodItemConsole(FoodItemDAO foodItemdao, FoodProductDAO foodproductdao)
    {
        this.foodItemdao=foodItemdao;
        this.foodproductdao=foodproductdao;
    }

    /**
     * Displays the menu in the console application.
     * The options in the menu includes retrieving all food items, retrieve an item by ID, add a new food item
     * delete a food item, list the expired food items and exit.
     */
    public void displayMenu()
    {
        Scanner reader = new Scanner(System.in);
        int chosen;
        do
        {
            System.out.println("-----------");
            System.out.println("The Food Store");
            System.out.println("Choose from these options");
            System.out.println("-----------");
            System.out.println("[1] List all Food Items");
            System.out.println("[2] Search for a food item by ID");
            System.out.println("[3] Add a new food item");
            System.out.println("[4] Delete a food item");
            System.out.println("[5] List all Expired Food Items");
            System.out.println("[6] Exit");
            chosen=reader.nextInt();

            switch (chosen)
            {
                case 1:
                {
                    System.out.println("Retrieving all food items...");
                    ArrayList<FoodItem> foodItems=foodItemdao.findAllItems();
                    for(FoodItem foodItem : foodItems)
                    {
                        System.out.println("FoodItem= {"+foodItem+"}");
                    }
                }
                break;
                case 2:
                {
                    System.out.println("Please enter the ID of the food item you're searching :");
                    int foodItemId=reader.nextInt();
                    FoodItem foodItem=foodItemdao.findItem(foodItemId);
                    if(foodItem!=null)
                        System.out.println("FoodItem= {"+foodItem+"}");
                    else
                        System.out.println("Sorry food item not found!");
                }
                break;
                case 3:
                {
                    System.out.println("Adding a new food item...");
                    System.out.println("Please enter the ID of the food product related to the food Item");
                    int foodproductId=reader.nextInt();
                    FoodProduct foodproduct=foodproductdao.findProduct(foodproductId);
                    if(foodproduct!=null)
                    {
                        System.out.println("Please enter the ID of the item ");
                        int foodItemId=reader.nextInt();
                        System.out.println("Please enter the expiry date of the food item with the format 'yyyy-MM-dd' ");
                        String Input=new Scanner(System.in).nextLine();
                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        try
                        {
                            Date expiryDate = dateFormat.parse(Input);
                            FoodItem foodItem = new FoodItem(foodItemId,foodproduct, expiryDate);
                            foodItemdao.addItem(foodItem);
                        }
                        catch (ParseException e)
                        {
                            System.out.println("Please enter the date in the format 'yyyy-MM-dd' "+e.getMessage());
                        }
                    }
                    else
                    {
                        System.out.println("Sorry food product not found");
                    }
                }
                break;
                case 4:
                {
                    System.out.println("Deleting an item...");
                    System.out.println("Enter the ID of the item you want to delete");
                    int foodItemId=reader.nextInt();
                    FoodItem foodItem=foodItemdao.findItem(foodItemId);
                    if (foodItem!=null)
                    {
                        foodItemdao.deleteItem(foodItemId);
                    }
                    else
                    {
                        System.out.println("Sorry! Food item not found");
                    }
                }
                break;
                case 5:
                {
                    System.out.println("Retrieving all expired food items...");
                    ArrayList<FoodItem> expiredItems =foodItemdao.filterExpiredItems();
                    for(FoodItem foodItem : expiredItems)
                    {
                        System.out.println("Expired Item= {"+foodItem+"}");
                    }
                }
                break;
                default:
                    System.out.println("Exit");
            }

        }while (chosen != 6);
    }
}