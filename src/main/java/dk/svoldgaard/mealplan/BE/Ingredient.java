package dk.svoldgaard.mealplan.BE;

public class Ingredient {

    private int id; // New field for unique identification
    private String itemName;
    private int quantity;
    private int price;

    public Ingredient(int id, String itemName, int quantity, int price) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
