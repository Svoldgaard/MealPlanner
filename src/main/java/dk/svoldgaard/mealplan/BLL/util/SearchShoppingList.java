package dk.svoldgaard.mealplan.BLL.util;

import dk.svoldgaard.mealplan.BE.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class SearchShoppingList {

    public List<Ingredient> search(List<Ingredient> searchBase, String query) {
        List<Ingredient> searchResult = new ArrayList<>();
        for(Ingredient ingredient : searchBase){
            if(compareToItem(query, ingredient) || compareToQuantity(query, ingredient)){
                searchResult.add(ingredient);
            }
        }
        return searchResult;
    }

    private boolean compareToQuantity(String query, Ingredient ingredient) {
        return Integer.toString(ingredient.getQuantity()).contains(query);

    }

    private boolean compareToItem(String query, Ingredient ingredient) {
        return ingredient.getItemName().toLowerCase().contains(query.toLowerCase());
    }
}
