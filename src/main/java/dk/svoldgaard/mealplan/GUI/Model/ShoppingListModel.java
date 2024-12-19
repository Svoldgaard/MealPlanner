package dk.svoldgaard.mealplan.GUI.Model;

import dk.svoldgaard.mealplan.BE.Ingredient;
import dk.svoldgaard.mealplan.BLL.ShoppingListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ShoppingListModel {

    private ObservableList<Ingredient> groceryTable;
    private ShoppingListManager shoppingListManager;

    public ShoppingListModel() {
        shoppingListManager = new ShoppingListManager();
        groceryTable = FXCollections.observableArrayList();
    }

    public ObservableList<Ingredient> getGroceryTable() {
        return groceryTable;
    }

    public void searchShoppingList(String query) {
        List<Ingredient> searchResults = shoppingListManager.searchResultShoppingList(query);
        groceryTable.clear();
        groceryTable.addAll(searchResults);
    }

    public Ingredient createShoppingList(Ingredient newIngredient) throws IOException {
        Ingredient createdIngredient = shoppingListManager.createShoppingList(newIngredient);
        groceryTable.add(createdIngredient);
        return createdIngredient;
    }

    public Ingredient updateShoppingList(int id, Ingredient updatedIngredient) throws IOException {
        Ingredient updated = shoppingListManager.updateShoppingList(id, updatedIngredient);
        groceryTable.set(
                groceryTable.indexOf(groceryTable.stream().filter(i -> i.getId() == id).findFirst().orElse(null)),
                updated
        );
        return updated;
    }

    public void deleteShoppingList(int id) throws IOException {
        shoppingListManager.deleteShoppingList(id);
        groceryTable.removeIf(ingredient -> ingredient.getId() == id);
    }
}
