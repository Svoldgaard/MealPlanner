package dk.svoldgaard.mealplan.BLL.util;

import dk.svoldgaard.mealplan.BE.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchMeal {

    public List<Meal> search(List<Meal> searchBase, String query){
        List<Meal> results = new ArrayList<>();
        for (Meal meal : searchBase) {
            if (compareToName(query, meal)){
                results.add(meal);
            }
        }
        return results;
    }

    private boolean compareToName(String query, Meal meal){
        return meal.getName().toLowerCase().contains(query.toLowerCase());
    }


}
