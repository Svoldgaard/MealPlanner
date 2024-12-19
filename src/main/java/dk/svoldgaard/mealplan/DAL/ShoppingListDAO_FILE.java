package dk.svoldgaard.mealplan.DAL;

import dk.svoldgaard.mealplan.BE.Ingredient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class ShoppingListDAO_FILE {

    private static final String SHOPPING_LIST_FILE = "/Users/majkensvoldgaard/Desktop/Jave projects/MealPlan/src/main/resources/dk/svoldgaard/mealplan/DatabaseFile/ShoppingList.txt";
    private static Path filePath = Paths.get(SHOPPING_LIST_FILE);

    public List<Ingredient> getAllShoppingList() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        List<Ingredient> ingredients = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(",");
            int id = Integer.parseInt(split[0]); // Parse the ID
            String itemName = split[1];
            int quantity = Integer.parseInt(split[2]);
            int price = Integer.parseInt(split[3]);

            // Use the ID field when creating the Ingredient object
            ingredients.add(new Ingredient(id, itemName, quantity, price));
        }

        return ingredients;
    }

    public Ingredient createShoppingList(Ingredient newIngredient) throws IOException {
        List<String> ingredients = Files.readAllLines(filePath);

        int nextId = 1;
        if (!ingredients.isEmpty()) {
            String[] lastIngredient = ingredients.get(ingredients.size() - 1).split(",");
            nextId = Integer.parseInt(lastIngredient[0]) + 1; // Increment the ID
        }

        // Prepare the new ingredient data
        String newIngredientLine = nextId + "," + newIngredient.getItemName() + "," + newIngredient.getQuantity() + "," + newIngredient.getPrice();

        // Append the new ingredient with a newline at the end
        Files.write(filePath, (newIngredientLine + "\n").getBytes(), APPEND);

        // Return the ingredient with its assigned ID
        return new Ingredient(nextId, newIngredient.getItemName(), newIngredient.getQuantity(), newIngredient.getPrice());
    }


    public Ingredient updateShoppingList(int id, Ingredient updatedIngredient) throws IOException {
        List<String> ingredients = Files.readAllLines(filePath);
        List<String> updatedIngredients = new ArrayList<>();
        boolean updated = false;

        for (String ingredientLine : ingredients) {
            String[] split = ingredientLine.split(",");
            int currentId = Integer.parseInt(split[0]);

            if (currentId == id) {
                // Replace with updated ingredient
                updatedIngredients.add(currentId + "," + updatedIngredient.getItemName() + "," + updatedIngredient.getQuantity() + "," + updatedIngredient.getPrice());
                updated = true;
            } else {
                updatedIngredients.add(ingredientLine);
            }
        }

        if (!updated) {
            throw new IOException("Ingredient with ID " + id + " not found.");
        }

        // Write the updated ingredients list back to the file
        Files.write(filePath, updatedIngredients);

        // Return the updated ingredient with its ID
        return new Ingredient(id, updatedIngredient.getItemName(), updatedIngredient.getQuantity(), updatedIngredient.getPrice());
    }


    public void deleteShoppingList(int id) throws IOException {
        // Read all lines from the file
        List<String> ingredients = Files.readAllLines(filePath);

        // Filter out the ingredient to delete based on its ID
        List<String> updatedIngredients = new ArrayList<>();
        for (String ingredientLine : ingredients) {
            String[] split = ingredientLine.split(",");
            int currentId = Integer.parseInt(split[0]);

            if (currentId != id) { // Keep only ingredients that don't match the target ingredient
                updatedIngredients.add(ingredientLine);
            }
        }

        // Rewrite the file with the updated ingredient list
        Files.write(filePath, updatedIngredients);
    }

}
