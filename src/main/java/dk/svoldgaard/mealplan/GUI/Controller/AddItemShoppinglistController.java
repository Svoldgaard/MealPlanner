package dk.svoldgaard.mealplan.GUI.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemShoppinglistController {

    public TextField txtItemName;
    public TextField txtQty;
    public TextField txtPrice;
    private MealPlanController parentController;

    public void setParent(MealPlanController mealPlanController) {
        this.parentController = mealPlanController;
    }

    public void btnSave(ActionEvent actionEvent) {
    }

    public void btnCancel(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
}
