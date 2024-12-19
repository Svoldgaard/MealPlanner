package dk.svoldgaard.mealplan.GUI.Model;

import dk.svoldgaard.mealplan.BE.Meal;
import dk.svoldgaard.mealplan.BLL.MealManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class MealPlanModel {

    private final ObservableList<Meal> lstMeals;
    private final ObservableList<Meal> lstMondayMeal;
    private final ObservableList<Meal> lstTuesdayMeal;
    private final ObservableList<Meal> lstWednesdayMeal;
    private final ObservableList<Meal> lstThursdayMeal;
    private final ObservableList<Meal> lstFridayMeal;
    private final ObservableList<Meal> lstSaturdayMeal;
    private final ObservableList<Meal> lstSundayMeal;


    private final MealManager mealManager;

    public MealPlanModel() {
        mealManager = new MealManager();

        // Initialize all the list
        lstMeals = FXCollections.observableArrayList();
        lstMondayMeal = FXCollections.observableArrayList();
        lstTuesdayMeal = FXCollections.observableArrayList();
        lstWednesdayMeal = FXCollections.observableArrayList();
        lstThursdayMeal = FXCollections.observableArrayList();
        lstFridayMeal = FXCollections.observableArrayList();
        lstSaturdayMeal = FXCollections.observableArrayList();
        lstSundayMeal = FXCollections.observableArrayList();

        lstMeals.addAll(MealManager.getAllMeals());  // Get meals from the manager
    }

    public ObservableList<Meal> getObservableMeal() {
        return lstMeals;
    }

    public void searchMeal(String query) {
        List<Meal> searchResulteMeal = mealManager.searchMeal(query);
        lstMeals.clear();
        lstMeals.addAll(searchResulteMeal);
    }

    public Meal createMeal(Meal newMeal) throws IOException {
        Meal mealCreated = mealManager.createMeal(newMeal);
        lstMeals.add(mealCreated);  // Add to the local list
        return mealCreated;
    }

    public Meal updateMeal(Meal meal) throws IOException {
        // Update the meal in the database (file)
        Meal updatedMeal = mealManager.updateMeal(meal);

        // Find and update the local list entry
        for (int i = 0; i < lstMeals.size(); i++) {
            if (lstMeals.get(i).getId() == meal.getId()) { // Use ID to find the meal
                lstMeals.set(i, updatedMeal);
                break;
            }
        }
        return updatedMeal;
    }

    public void deleteMeal(Meal meal) throws IOException {
        mealManager.deleteMeal(meal); // Delete from file through the manager
        lstMeals.remove(meal); // Remove from the local observable list
    }
}
