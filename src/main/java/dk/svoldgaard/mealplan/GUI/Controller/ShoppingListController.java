package dk.svoldgaard.mealplan.GUI.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShoppingListController {

    public TextField txtItemName;
    public TextField txtQty;
    public TextField txtPrice;
    private MealPlanController parentController;

    public void setParent(MealPlanController mealPlanController) {
        this.parentController = mealPlanController;
    }

    public void btnSave(ActionEvent actionEvent) {
        String itemName = txtItemName.getText();
        String qty = txtQty.getText();
        String price = txtPrice.getText();

        if(itemName.isEmpty() || qty.isEmpty() || price.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        }

        if(parentController == null) {
            parentController.refreshMealList();
        }

        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnCancel(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
}
