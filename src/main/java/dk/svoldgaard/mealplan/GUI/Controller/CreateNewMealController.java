package dk.svoldgaard.mealplan.GUI.Controller;

import dk.svoldgaard.mealplan.BE.Meal;
import dk.svoldgaard.mealplan.BLL.MealManager;
import dk.svoldgaard.mealplan.GUI.Model.MealPlanModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewMealController {


    @FXML
    private TextField txtAddIngredient;
    @FXML
    private TableView TableAddIngredient;
    @FXML
    private TextField txtAddQuantity;
    @FXML
    private TextField txtMealName;

    private Meal existingMeal;


    private MealPlanController parentController;
    private MealPlanModel mealPlanModel;

    public CreateNewMealController() {
    }

    public void setMealPlanModel(MealPlanModel mealPlanModel) {
        this.mealPlanModel = mealPlanModel;
    }

    public void setParent(MealPlanController mealPlanController) {
        this.parentController = mealPlanController;
    }

    @FXML
    private void btnAddIngredient(ActionEvent actionEvent) {
    }

    public void setMeal(Meal meal) {
        this.existingMeal = meal;
        if (this.existingMeal != null) {
            txtMealName.setText(this.existingMeal.getName());
        }
    }

    @FXML
    private void btnSave(ActionEvent actionEvent) {
        String mealName = txtMealName.getText().trim();

        if (mealName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Meal Name Required");
            alert.setContentText("Please enter a meal name before saving.");
            alert.showAndWait();
            return;
        }

        try {
            if (existingMeal != null) {
                // Update existing meal
                existingMeal.setName(mealName);
                mealPlanModel.updateMeal(existingMeal); // Save changes to the file
            } else {
                // Create new meal
                Meal newMeal = new Meal(mealName);
                mealPlanModel.createMeal(newMeal); // Save new meal to the file
            }

            if (parentController != null) {
                parentController.refreshMealList();
            }
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Save Meal");
            alert.setContentText("An error occurred while saving the meal: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void btnCancel(ActionEvent actionEvent) {
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }
}