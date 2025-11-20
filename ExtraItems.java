// Child Class
public class ExtraItems extends Pizza {
    public double extraCheese = 0;
    public double coldDrink = 0;
    public double toppings = 0;

    public ExtraItems(String type, String size, int quantity) {
        super(type, size, quantity);
    }

    public void addCheese() {
        extraCheese = 50;
    }

    public void addColdDrink() {
        coldDrink = 40;
    }

    public void addToppings() {
        toppings = 60;
    }

    public double getTotalExtraCost() {
        return extraCheese + coldDrink + toppings;
    }

    public String getExtraDetails() {
        return "Cheese: " + extraCheese + " | Cold Drink: " + coldDrink + " | Toppings: " + toppings;
    }
}

