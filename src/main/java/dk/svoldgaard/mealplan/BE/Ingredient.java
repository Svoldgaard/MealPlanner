package dk.svoldgaard.mealplan.BE;

public class Ingredient {

    private String itemName;
    private int quantity;
    private int price;

    public Ingredient(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
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
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
