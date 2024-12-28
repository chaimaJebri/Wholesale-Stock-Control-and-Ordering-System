import java.util.ArrayList;
import java.util.Scanner;

/**
 * The CustomerConsole class provides a simple console-based menu for interacting with customers.
 * The menu options are displayed in the console, and the user input is processed accordingly.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class CustomerConsole {

    private CustomerDAO customerdao;

    /**
     * Constructs a CustomerConsole object with the customerdao dependency.
     *
     * @param customerdao The data access object for managing customer data.
     */

    //Chaima Jebri
    public CustomerConsole (CustomerDAO customerdao)
    {
        this.customerdao=customerdao;
    }

    /**
     * Displays the menu in the console application. The options in the menu includes retrieving all customers,
     * retrieve a customer by ID, add customer, delete customer,edit customer details and exit.
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
            System.out.println("[1] List all customers");
            System.out.println("[2] Search for a customer by ID");
            System.out.println("[3] Add a new customer");
            System.out.println("[4] Update a customer by ID");
            System.out.println("[5] Delete a customer by ID");
            System.out.println("[6] Exit");
            chosen = reader.nextInt();
            switch (chosen) {
                case 1: {
                    System.out.println("Retrieving all customers...");
                    ArrayList<Customer> customers = customerdao.findAllCustomers();
                    for (Customer customer : customers) {
                        System.out.println("Customer: {" + customer + "}");
                    }
                }
                break;
                case 2: {
                    System.out.println("Please enter the ID of the customer you're searching: ");
                    int id = reader.nextInt();
                    Customer customer = customerdao.findCustomer(id);
                    if (customer != null)
                        System.out.println("Customer: {" + customer + "}");
                    else
                        System.out.println("Sorry customer not found!");
                }
                break;
                case 3: {
                    System.out.println("Adding a new customer...");
                    System.out.println("Please enter the business name of the customer");
                    String name = new Scanner(System.in).nextLine();
                    System.out.println("Please enter the addres Line 1:");
                    String adrl1 = new Scanner(System.in).nextLine();
                    System.out.println("Please enter the addres Line 2:");
                    String adrl2 = new Scanner(System.in).nextLine();
                    System.out.println("Please enter the addres Line 3:");
                    String adrl3 = new Scanner(System.in).nextLine();
                    System.out.println("Please enter the country:");
                    String cntry = new Scanner(System.in).nextLine();
                    System.out.println("Please enter the postCode:");
                    String pstCode = new Scanner(System.in).nextLine();
                    System.out.println("Please enter the telephone number:");
                    String telNum = new Scanner(System.in).nextLine();
                    Address adr = new Address(adrl1, adrl2, adrl3, cntry, pstCode);
                    Customer customer = new Customer(name, adr, telNum);
                    customerdao.addCustomer(customer);
                }
                break;
                case 4: {
                    System.out.println("Updating a customer information... ");
                    System.out.println("Please enter the ID of the customer you want to update: ");
                    int id = reader.nextInt();
                    Customer customer = customerdao.findCustomer(id);
                    if (customer == null) {
                        System.out.println("Sorry customer not found!");
                    } else {
                        System.out.println("Customer: {" + customer + "}");
                        System.out.println("Please enter the new business name of the customer");
                        String name = new Scanner(System.in).nextLine();
                        System.out.println("Please enter the new addres Line 1:");
                        String adrl1 = new Scanner(System.in).nextLine();
                        System.out.println("Please enter the new addres Line 2:");
                        String adrl2 = new Scanner(System.in).nextLine();
                        System.out.println("Please enter the new addres Line 3:");
                        String adrl3 = new Scanner(System.in).nextLine();
                        System.out.println("Please enter the new country:");
                        String cntry = new Scanner(System.in).nextLine();
                        System.out.println("Please enter the new postCode:");
                        String pstCode = new Scanner(System.in).nextLine();
                        System.out.println("Please enter the new telephone number:");
                        String telNum = new Scanner(System.in).nextLine();
                        Address adr = new Address(adrl1, adrl2, adrl3, cntry, pstCode);
                        customer = new Customer(id, name, adr, telNum);
                        customerdao.updateCustomer(customer);
                    }
                }
                break;
                case 5: {
                    System.out.println("Deleting a customer...");
                    System.out.println("Please enter the ID of the customer you want to delete: ");
                    int id = reader.nextInt();
                    Customer customer = customerdao.findCustomer(id);
                    if (customer != null) {
                        customerdao.deleteCustomer(id);
                    } else {
                        System.out.println("Sorry customer not found!");
                    }
                }
                break;
                default: {
                    System.out.println("Exit");
                }
            }
        }while (chosen!=6);

    }
}
