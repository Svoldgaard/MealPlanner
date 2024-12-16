package dk.svoldgaard.mealplan.BLL;

import dk.svoldgaard.mealplan.BE.Meal;
import dk.svoldgaard.mealplan.BLL.util.SearchMeal;
import dk.svoldgaard.mealplan.DAL.MealDAO_DB;

import java.io.IOException;
import java.util.List;

public class MealManager {

    private static MealDAO_DB mealDao;
    private SearchMeal searchMeal = new SearchMeal();

    public MealManager() {
        mealDao = new MealDAO_DB();
    }

    public List<Meal> searchMeal(String query){
        List<Meal> allMeals = getAllMeals();
        List<Meal> searchResultMeal = searchMeal.search(allMeals, query);
        return searchResultMeal;
    }

    public static List<Meal> getAllMeals() {
        try {
            return mealDao.getAllMeals();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Meal createMeal(Meal newMeal) throws IOException {
        return mealDao.createMeal(newMeal);  // Use the instance of MealDAO_DB
    }
    
    public Meal updateMeal(Meal meal) throws IOException {
        return mealDao.updateMeal(meal);
    }
    
    public void deleteMeal(Meal meal) throws IOException {
        mealDao.deleteMeal(meal);
    }
}