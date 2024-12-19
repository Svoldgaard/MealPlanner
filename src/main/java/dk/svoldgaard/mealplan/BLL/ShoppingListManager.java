package dk.svoldgaard.mealplan.BLL;

import dk.svoldgaard.mealplan.BE.Ingredient;
import dk.svoldgaard.mealplan.BLL.util.SearchShoppingList;
import dk.svoldgaard.mealplan.DAL.ShoppingListDAO_FILE;

import java.io.IOException;
import java.util.List;

public class ShoppingListManager {

    private static ShoppingListDAO_FILE shoppingListDAO_file;
    private SearchShoppingList searchShoppingList;

    public ShoppingListManager() {
        shoppingListDAO_file = new ShoppingListDAO_FILE();
        searchShoppingList = new SearchShoppingList();
    }

    public List<Ingredient> searchResultShoppingList(String query) {
        List<Ingredient> allShoppingList = getAllShoppingList();
        return searchShoppingList.search(allShoppingList, query);
    }

    public static List<Ingredient> getAllShoppingList() {
        try {
            return shoppingListDAO_file.getAllShoppingList();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Ingredient createShoppingList(Ingredient newIngredient) throws IOException {
        return shoppingListDAO_file.createShoppingList(newIngredient);
    }

    public Ingredient updateShoppingList(int id, Ingredient updatedIngredient) throws IOException {
        return shoppingListDAO_file.updateShoppingList(id, updatedIngredient);
    }

    public void deleteShoppingList(int id) throws IOException {
        shoppingListDAO_file.deleteShoppingList(id);
    }
}
