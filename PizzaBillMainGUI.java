// File : PizzaBillGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

// Parent Class
class Pizza {
    protected String type;
    protected String size;
    protected int quantity;
    protected double basePrice;

    public Pizza(String type, String size, int quantity) {
        this.type = type;
        this.size = size;
        this.quantity = quantity;
        this.basePrice = calculatePrice();
    }

    public double calculatePrice() {
        double price = 0;

        if (type.equalsIgnoreCase("veg")) {
            switch (size.toLowerCase()) {
                case "small": price = 150; break;
                case "medium": price = 250; break;
                case "large": price = 350; break;
            }
        } else {
            switch (size.toLowerCase()) {
                case "small": price = 200; break;
                case "medium": price = 300; break;
                case "large": price = 400; break;
            }
        }
        return price * quantity;
    }
}

// Child Class
class ExtraItems extends Pizza {
    public double cheese = 0;
    public double drink = 0;
    public double toppings = 0;

    public ExtraItems(String type, String size, int quantity) {
        super(type, size, quantity);
    }

    public void addCheese() { cheese = 50; }
    public void addDrink() { drink = 40; }
    public void addToppings() { toppings = 60; }

    public double getExtraCost() {
        return cheese + drink + toppings;
    }
}

// GUI Main Class
public class PizzaBillMainGUI extends JFrame {

    ExtraItems order = null;

    public PizzaBillMainGUI() {
        setTitle("Pizza Bill Generator System");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        showLoginScreen();
    }

    // LOGIN SCREEN 
    void showLoginScreen() {
        JFrame login = new JFrame("Login");
        login.setSize(350, 250);
        login.setLayout(new GridLayout(4, 2));
        login.setLocationRelativeTo(null);

        JLabel nameLbl = new JLabel("Enter Name:");
        JTextField nameField = new JTextField();

        JLabel emailLbl = new JLabel("Enter Email:");
        JTextField emailField = new JTextField();

        JButton loginBtn = new JButton("Login");

        login.add(nameLbl); login.add(nameField);
        login.add(emailLbl); login.add(emailField);
        login.add(new JLabel(""));
        login.add(loginBtn);

        login.setVisible(true);

        loginBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();

            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

            if (!Pattern.matches(regex, email)) {
                JOptionPane.showMessageDialog(login, "Invalid Email Format!");
                return;
            }

            JOptionPane.showMessageDialog(login,
                    "Login Successful!\nWelcome: " + name);

            login.dispose();
            showMainMenu();
        });
    }

    // MAIN MENU 
    void showMainMenu() {
        getContentPane().removeAll();
        setLayout(new GridLayout(5, 1));

        JButton btn1 = new JButton("1. Order Pizza");
        JButton btn2 = new JButton("2. Add Extra Items");
        JButton btn3 = new JButton("3. Generate Bill");
        JButton btn4 = new JButton("4. Exit");

        add(btn1);
        add(btn2);
        add(btn3);
        add(btn4);

        btn1.addActionListener(e -> orderPizza());
        btn2.addActionListener(e -> addExtras());
        btn3.addActionListener(e -> showBill());
        btn4.addActionListener(e -> System.exit(0));

        revalidate();
        repaint();
    }

    // ORDER PIZZA 
    void orderPizza() {
        JFrame win = new JFrame("Order Pizza");
        win.setSize(400, 300);
        win.setLayout(new GridLayout(5, 2));
        win.setLocationRelativeTo(null);

        JTextField type = new JTextField("Veg");
        JTextField size = new JTextField("Medium");
        JTextField qty = new JTextField("1");

        win.add(new JLabel("Type (Veg/NonVeg):")); win.add(type);
        win.add(new JLabel("Size (Small/Medium/Large):")); win.add(size);
        win.add(new JLabel("Quantity:")); win.add(qty);

        JButton addBtn = new JButton("Place Order");
        win.add(new JLabel("")); win.add(addBtn);

        win.setVisible(true);

        addBtn.addActionListener(a -> {
            try {
                order = new ExtraItems(type.getText(), size.getText(), Integer.parseInt(qty.getText()));
                JOptionPane.showMessageDialog(win, "Pizza Ordered Successfully!");
                win.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(win, "Invalid Input!");
            }
        });
    }

    // EXTRA ITEMS 
    void addExtras() {
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Order pizza first!");
            return;
        }

        JFrame extras = new JFrame("Add Extra Items");
        extras.setSize(350, 300);
        extras.setLocationRelativeTo(null);
        extras.setLayout(new GridLayout(4, 1));

        JButton cheeseBtn = new JButton("Add Cheese (50)");
        JButton drinkBtn = new JButton("Add Cold Drink (40)");
        JButton toppingsBtn = new JButton("Add Toppings (60)");

        extras.add(cheeseBtn);
        extras.add(drinkBtn);
        extras.add(toppingsBtn);

        cheeseBtn.addActionListener(e -> { order.addCheese(); JOptionPane.showMessageDialog(extras, "Cheese Added!"); });
        drinkBtn.addActionListener(e -> { order.addDrink(); JOptionPane.showMessageDialog(extras, "Cold Drink Added!"); });
        toppingsBtn.addActionListener(e -> { order.addToppings(); JOptionPane.showMessageDialog(extras, "Toppings Added!"); });

        extras.setVisible(true);
    }

    // Bill Calculation 
    void showBill() {
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Order pizza first!");
            return;
        }

        double pizzaCost = order.calculatePrice();
        double extraCost = order.getExtraCost();
        double subtotal = pizzaCost + extraCost;
        double tax = subtotal * 0.05;
        double total = subtotal + tax;

        String bill =
                "===== BILL =====\n" +
                "Pizza Type: " + order.type + "\n" +
                "Size: " + order.size + "\n" +
                "Quantity: " + order.quantity + "\n" +
                "Pizza Cost: " + pizzaCost + "\n\n" +
                "Extra Cheese: " + order.cheese + "\n" +
                "Cold Drink: " + order.drink + "\n" +
                "Toppings: " + order.toppings + "\n\n" +
                "Subtotal: " + subtotal + "\n" +
                "Tax (5%): " + tax + "\n" +
                "Total: " + total + "\n";

        JOptionPane.showMessageDialog(this, bill);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PizzaBillMainGUI().setVisible(true));
    }
}