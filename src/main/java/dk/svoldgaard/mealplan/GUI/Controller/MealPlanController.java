package dk.svoldgaard.mealplan.GUI.Controller;

import dk.svoldgaard.mealplan.BE.Meal;
import dk.svoldgaard.mealplan.GUI.Model.MealPlanModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MealPlanController implements Initializable {

    @FXML
    private TextField txtSearchInventory;
    @FXML
    private TextField txtSearchMeal;
    @FXML
    private TextField txtSearchShoppingList;
    @FXML
    private Text txtTotalPrice;
    @FXML
    private TableView InventoryTable;
    @FXML
    private ListView<Meal> lstMeal;
    @FXML
    private TableView GroceryTable;
    @FXML
    private ListView lstMondayMeal;
    @FXML
    private ListView lstTuesdayMeal;
    @FXML
    private ListView lstWednesdayMeal;
    @FXML
    private ListView lstThursdayMeal;
    @FXML
    private ListView lstFridayMeal;
    @FXML
    private ListView lstSaturdayMeal;
    @FXML
    private ListView lstSundayMeal;


    private MealPlanModel mealPlanModel;

    public MealPlanController() {
        try {
            mealPlanModel = new MealPlanModel();
        } catch (Exception e) {
            displayError(e);
            mealPlanModel = null; // Continue with a null model if initialization fails.
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (mealPlanModel != null) {
            lstMeal.setItems(mealPlanModel.getObservableMeal());
        } else {
            lstMeal.setItems(FXCollections.observableArrayList()); // Show an empty list.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Initialization Warning");
            alert.setHeaderText("Meal Plan Model is unavailable.");
            alert.setContentText("Some features may not work as expected.");
            alert.showAndWait();
        }

        // this code make so the selected item is casted to the txtField
        /*
        lstMeal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           txtSearchMeal.setText(newValue.toString());
        });

        */

        txtSearchMeal.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                mealPlanModel.searchMeal(newValue);
            }catch(Exception e){
                displayError(e);
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void btnClose(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void btnCreateNewMeal(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/svoldgaard/mealplan/CreateNewMeal.fxml"));

        // Load the FXML and create the new scene
        Parent scene = loader.load();

        // Get the controller from the loader
        NewMealController controller = loader.getController();

        // Set the MealPlanModel in the controller
        controller.setMealPlanModel(mealPlanModel);  // Assuming 'mealPlanModel' is available

        // Set the parent controller (this)
        controller.setParent(this);

        // Show the new window
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Create New Meal");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void btnEditMeal(ActionEvent actionEvent) throws IOException {
        Meal selectedMeal = lstMeal.getSelectionModel().getSelectedItem();

        if (selectedMeal == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Meal Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a meal to edit");
            alert.showAndWait();
            return; // Exit the method if no meal is selected
        }

        // Load the FXML for editing
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/CreateNewMeal.fxml"));
        Parent scene = loader.load();

        // Get the controller
        NewMealController controller = loader.getController();

        // Pass the parent controller and the mealPlanModel
        controller.setParent(this); // Set the parent controller
        controller.setMealPlanModel(mealPlanModel); // Pass the MealPlanModel to the controller
        controller.setMeal(selectedMeal); // Pass the selected meal for editing

        // Show the stage
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Edit Meal");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    @FXML
    private void btnAddInventory(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/NewItemInventory.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        stage.setTitle("Add to Inventory");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void btnEditInventory(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/NewItemInventory.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        stage.setTitle("Edit Inventory");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void btnAddItem(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/AddItemShoppingList.fxml"));
        Parent scene = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        stage.setTitle("Add item to ShoppingList");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    @FXML
    private void btnDeleteItem(ActionEvent actionEvent) {
        Object selectedItem = new Object();
        delete(selectedItem, "item", false);
    }

    @FXML
    private void btnDeleteMeal(ActionEvent actionEvent) {
        Meal selectedMeal = lstMeal.getSelectionModel().getSelectedItem();
        delete(selectedMeal, "meal", false);
    }

    @FXML
    private void btnDeleteItemInInventory(ActionEvent actionEvent) {
        Object selectedItem = new Object() /* Logic to get the selected inventory item */;
        delete(selectedItem, "inventoryItem", false);
    }

    private void delete(Object selectedItem, String itemType, boolean bypass) {
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No item selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
            return;
        }

        if (!bypass) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete this item?\n" + selectedItem);
            confirmationAlert.setContentText("This action cannot be undone.");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    performDeletion(selectedItem, itemType);
                }
            });
        } else {
            performDeletion(selectedItem, itemType);
        }
    }

    private void performDeletion(Object selectedItem, String itemType) {
        boolean itemDeleted = false;

        try {
            if ("meal".equals(itemType)) {
                Meal mealToDelete = (Meal) selectedItem;
                mealPlanModel.deleteMeal(mealToDelete); // Delete using the model
                itemDeleted = true;
            }

            // Notify the user of success
            if (itemDeleted) {
                Platform.runLater(() -> {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Item Deleted");
                    successAlert.setContentText("The selected item has been successfully deleted.");
                    successAlert.showAndWait();
                });
            }
        } catch (Exception e) {
            // Notify the user of errors
            Platform.runLater(() -> {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error deleting the item: " + e.getMessage());
                errorAlert.showAndWait();
            });
        }
    }


    @FXML
    private void btnCreateShoppingList(ActionEvent actionEvent) {
    }

    @FXML
    private void btnClearShoppingList(ActionEvent actionEvent) {
    }

    @FXML
    private void btnClearMealPlan(ActionEvent actionEvent) {
    }

    public void refreshMealList() {
        lstMeal.setItems(mealPlanModel.getObservableMeal());  // Update ListView with new data
    }

    private void displayError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(null);
        alert.showAndWait();
    }





}