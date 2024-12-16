package dk.svoldgaard.mealplan.DAL;

import dk.svoldgaard.mealplan.BE.Meal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class MealDAO_DB {

    private static final String MEAL_FILE = "/Users/majkensvoldgaard/Desktop/Jave projects/MealPlan/src/main/resources/dk/svoldgaard/mealplan/DatabaseFile/Meals";
    private static Path filePath = Paths.get(MEAL_FILE);

    public List<Meal> getAllMeals() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        List<Meal> meals = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(",");
            int id = Integer.parseInt(split[0]);
            StringBuilder name = new StringBuilder(split[1]);
            for (int i = 2; i < split.length; i++) {
                name.append(",").append(split[i]);
            }

            Meal meal = new Meal(name.toString());
            meals.add(meal);
        }

        return meals;
    }

    public Meal createMeal(Meal newMeal) throws IOException {
        List<String> meals = Files.readAllLines(filePath);

        int nextId = 1;
        if (meals.size() > 0) {
            String[] lastMeal = meals.get(meals.size() - 1).split(",");
            nextId = Integer.parseInt(lastMeal[0]) + 1;  // Increment the ID
        }

        // Prepare the new meal data
        String newMealsLine = nextId + "," + newMeal.getName();

        // Append the new meal with a newline at the end
        Files.write(filePath, (newMealsLine + "\n").getBytes(), APPEND);

        // Return the meal with its assigned ID
        return new Meal(nextId, newMeal.getName());
    }

    public Meal updateMeal(Meal updatedMeal) throws IOException {
        List<String> meals = Files.readAllLines(filePath);
        List<String> updatedMeals = new ArrayList<>();
        boolean updated = false;

        for (String mealLine : meals) {
            String[] split = mealLine.split(",");
            int id = Integer.parseInt(split[0]);

            if (id == updatedMeal.getId()) {
                // Replace with updated meal
                updatedMeals.add(id + "," + updatedMeal.getName());
                updated = true;
            } else {
                updatedMeals.add(mealLine);
            }
        }

        if (!updated) {
            throw new IOException("Meal with ID " + updatedMeal.getId() + " not found.");
        }

        // Write the updated meals list back to the file
        Files.write(filePath, updatedMeals);

        return updatedMeal;
    }

    public void deleteMeal(Meal meal) throws IOException {
        // Read all lines from the file
        List<String> meals = Files.readAllLines(filePath);

        // Filter out the meal to delete based on its name (or any unique property, like ID if available)
        List<String> updatedMeals = new ArrayList<>();
        for (String mealLine : meals) {
            String[] split = mealLine.split(",");
            String mealName = split[1];

            if (!mealName.equals(meal.getName())) { // Keep only meals that don't match the target meal
                updatedMeals.add(mealLine);
            }
        }

        // Rewrite the file with the updated meal list
        Files.write(filePath, updatedMeals);

    }
}
