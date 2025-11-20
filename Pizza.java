// Parent Class
public class Pizza {
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
            if (size.equalsIgnoreCase("small")) price = 150;
            else if (size.equalsIgnoreCase("medium")) price = 250;
            else price = 350;
        } else {
            if (size.equalsIgnoreCase("small")) price = 200;
            else if (size.equalsIgnoreCase("medium")) price = 300;
            else price = 400;
        }

        return price * quantity;
    }
    public String getPizzaDetails() {
        return type + " | " + size + " | Qty: " + quantity + " | Price: " + basePrice;
    }
}

