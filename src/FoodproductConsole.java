import java.util.ArrayList;
import java.util.Scanner;

/**
 * The FoodproductConsole class provides a simple console-based menu for interacting with food products.
 * The menu options are displayed in the console, and the user input is processed accordingly.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class FoodproductConsole {

    private FoodProductDAO foodproductdao;

    /**
     * Constructs a FoodproductConsole object with the specified dependency.
     *
     * @param foodproductdao The data access object for managing food product data.
     */
    public FoodproductConsole (FoodProductDAO foodproductdao)
    {
        this.foodproductdao=foodproductdao;
    }

    //Chaima Jebri
    /**
     * Displays the menu in the console application.
     * The options in the menu includes retrieving all products, retrieve a product by ID, add product,
     * delete product,edit product details and exit.
     */
    public void displayMenu()
    {
        Scanner reader = new Scanner(System.in);
        int chosen;
        do {
            System.out.println("-----------");
            System.out.println("The Food Store");
            System.out.println("Choose from these options");
            System.out.println("-----------");
            System.out.println("[1] List all products");
            System.out.println("[2] Search for a product by ID");
            System.out.println("[3] Add a new product");
            System.out.println("[4] Update a product by ID");
            System.out.println("[5] Delete a product by ID");
            System.out.println("[6] Exit");
            chosen = reader.nextInt();

            switch (chosen) {
                case 1: {
                    System.out.println("Retrieving all products...");
                    ArrayList<FoodProduct> foodproducts = foodproductdao.findAllProducts();
                    for (FoodProduct product : foodproducts) {
                        System.out.println("Product= {" + product + "}");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Please enter the ID of the product you're searching :");
                    int id = reader.nextInt();
                    FoodProduct product = foodproductdao.findProduct(id);
                    if (product != null)
                        System.out.println("Product: {" + product + "}");
                    else
                        System.out.println("Sorry product not found!");
                }
                break;
                case 3: {
                    System.out.println("Adding a new Product...");
                    System.out.println("Enter the SKU of the product:");
                    String sku = new Scanner(System.in).nextLine();
                    System.out.println("Enter the description of the product:");
                    String des = new Scanner(System.in).nextLine();
                    System.out.println("Enter the category of the product:");
                    String cat = new Scanner(System.in).nextLine();
                    System.out.println("Enter the price of the product:");
                    int p = new Scanner(System.in).nextInt();
                    FoodProduct product = new FoodProduct(sku, des, cat, p);
                    foodproductdao.addProduct(product);
                }
                break;
                case 4: {
                    System.out.println("Updating a product... ");
                    System.out.println("Enter the ID of the product you want to update");
                    int id = new Scanner(System.in).nextInt();
                    FoodProduct product = foodproductdao.findProduct(id);
                    if (product == null) {
                        System.out.println("Sorry the product is not found");
                    } else {
                        System.out.println("Product= {"+product+"}");
                        System.out.println("Enter the new SKU of the product");
                        String sku = new Scanner(System.in).nextLine();
                        System.out.println("Enter the new description of the product");
                        String des = new Scanner(System.in).nextLine();
                        System.out.println("Enter the new category of the product");
                        String cat = new Scanner(System.in).nextLine();
                        System.out.println("Enter the new price of the product");
                        int p = new Scanner(System.in).nextInt();
                        product = new FoodProduct(id, sku, des, cat, p); // no need to create a new product and put it in the place of that product, we can simply update it's values by the the constructor or the set methods.
                        foodproductdao.updateProduct(product);
                    }
                }
                break;
                case 5: {
                    System.out.println("Deleting a product...");
                    System.out.println("Enter the ID of the product you want to delete");
                    int id = reader.nextInt();
                    FoodProduct product = foodproductdao.findProduct(id);
                    if (product == null) {
                        System.out.println("Sorry the product is not found");
                    } else {
                        foodproductdao.deleteProduct(id);
                    }
                }
                break;
                default:
                    System.out.println("Exit");
            }
        }while (chosen!=6);
    }
}
