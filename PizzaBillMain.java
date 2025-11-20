import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PizzaBillMain {

    static Scanner sc = new Scanner(System.in);
    static ExtraItems order = null;

    public static void main(String[] args) {

        System.out.println("===== PIZZA BILL GENERATOR SYSTEM =====");

        // LOGIN SYSTEM
        login();

        int choice;
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Order Pizza");
            System.out.println("2. Add Extra Items");
            System.out.println("3. Generate Bill");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        orderPizza();
                        break;
                    case 2:
                        if (order == null) {
                            System.out.println("Order pizza first!");
                        } else extraItems();
                        break;
                    case 3:
                        generateBill();
                        break;
                    case 4:
                        System.out.println("Exiting... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Please enter valid input!");
                sc.nextLine();
                choice = 0;
            }
        } while (choice != 4);
    }
    // LOGIN
    public static void login() {
        System.out.print("\nEnter Your Name: ");
        String name = sc.nextLine();

        String email = "";
        while (true) {
            System.out.print("Enter Your Email: ");
            email = sc.nextLine();

            // Email Validation
            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

            if (Pattern.matches(regex, email)) {
                break;
            } else {
                System.out.println("Invalid Email Format! Try again.");
            }
        }

        System.out.println("Login Successful! Welcome " + name + " (" + email + ")");
    }
    // Order Pizza
    public static void orderPizza() {
        System.out.println("\n----- ORDER PIZZA -----");
        System.out.print("Enter Type (Veg/NonVeg): ");
        String type = sc.nextLine();

        System.out.print("Enter Size (Small/Medium/Large): ");
        String size = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();

        order = new ExtraItems(type, size, qty);
        System.out.println("Pizza added successfully!");
    }
    // Extra Items 
    public static void extraItems() {
        int ch;
        do {
            System.out.println("\n----- ADD EXTRA ITEMS -----");
            System.out.println("1. Add Cheese (50)");
            System.out.println("2. Add Cold Drink (40)");
            System.out.println("3. Add Toppings (60)");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    order.addCheese();
                    System.out.println("Cheese added.");
                    break;
                case 2:
                    order.addColdDrink();
                    System.out.println("Cold Drink added.");
                    break;
                case 3:
                    order.addToppings();
                    System.out.println("Toppings added.");
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (ch != 4);
    }

    // Bill Generation 
    public static void generateBill() {
        if (order == null) {
            System.out.println("Order pizza first!");
            return;
        }

        double pizzaCost = order.calculatePrice();
        double extraCost = order.getTotalExtraCost();
        double subtotal = pizzaCost + extraCost;
        double tax = subtotal * 0.05;
        double total = subtotal + tax;

        System.out.println("\n===== BILL DETAILS =====");
        System.out.println(order.getPizzaDetails());
        System.out.println(order.getExtraDetails());
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Tax (5%): " + tax);
        System.out.println("Total Amount: " + total);

        // File Handling 
        try {
            FileWriter fw = new FileWriter("bill.txt");
            fw.write("===== PIZZA BILL =====\n");
            fw.write(order.getPizzaDetails() + "\n");
            fw.write(order.getExtraDetails() + "\n");
            fw.write("Subtotal: " + subtotal + "\n");
            fw.write("Tax (5%): " + tax + "\n");
            fw.write("TOTAL: " + total + "\n");
            fw.close();

            System.out.println("Bill saved to bill.txt");

        } catch (IOException e) {
            System.out.println("Error writing file!");
        }
    }
}

