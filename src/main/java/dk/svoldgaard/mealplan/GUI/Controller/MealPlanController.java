package dk.svoldgaard.mealplan.GUI.Controller;

import dk.svoldgaard.mealplan.BE.InventoryItem;
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
import javafx.scene.input.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private List<ListView<Meal>> weekdayMealLists;

    public MealPlanController() {
        try {
            mealPlanModel = new MealPlanModel();
        } catch (Exception e) {
            displayError(e);
            mealPlanModel = null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (mealPlanModel != null) {
            lstMeal.setItems(mealPlanModel.getObservableMeal());
        } else {
            lstMeal.setItems(FXCollections.observableArrayList());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Initialization Warning");
            alert.setHeaderText("Meal Plan Model is unavailable.");
            alert.setContentText("Some features may not work as expected.");
            alert.showAndWait();
        }

        // Initialize the collection of weekday ListViews
        weekdayMealLists = List.of(
                lstMondayMeal,
                lstTuesdayMeal,
                lstWednesdayMeal,
                lstThursdayMeal,
                lstFridayMeal,
                lstSaturdayMeal,
                lstSundayMeal
        );

        // Set drag-and-drop handlers for all ListViews
        setupDragAndDrop(lstMeal);
        for (ListView<Meal> weekdayList : weekdayMealLists) {
            setupDragAndDrop(weekdayList);
        }

        // search in the meal txt field
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
        btnClearMealPlan(actionEvent);
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void btnCreateNewMeal(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/svoldgaard/mealplan/FXML/CreateNewMeal.fxml"));
        Parent scene = loader.load();

        NewMealController controller = loader.getController();
        controller.setMealPlanModel(mealPlanModel);
        controller.setParent(this);

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Create New Meal");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void btnEditMeal(ActionEvent actionEvent) throws IOException {
        Meal selectedMeal = lstMeal.getSelectionModel().getSelectedItem();

        // alert if there is not selected a meal
        if (selectedMeal == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Meal Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a meal to edit");
            alert.showAndWait();
            return;
        }

        // Load the FXML for editing
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/FXML/CreateNewMeal.fxml"));
        Parent scene = loader.load();


        NewMealController controller = loader.getController();
        controller.setParent(this);
        controller.setMealPlanModel(mealPlanModel);
        controller.setMeal(selectedMeal);

        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Edit Meal");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    @FXML
    private void btnAddInventory(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/FXML/NewItemInventory.fxml"));
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
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/FXML/NewItemInventory.fxml"));
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
        loader.setLocation(getClass().getResource("/dk/svoldgaard/mealplan/FXML/AddItemShoppingList.fxml"));
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
        //InventoryItem selectedInventory = InventoryTable.getSelectionModel().getSelectedItem();
        //delete(selectedInventory, "inventoryItem", false);
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
        for (ListView<Meal> weekdayList : weekdayMealLists) {
            // Move all meals back to lstMeal
            lstMeal.getItems().addAll(weekdayList.getItems());
            weekdayList.getItems().clear(); // Clear the weekday ListView
        }
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


    public void dgdMeal(DragEvent dragEvent) {
        Dragboard db = ((ListView<?>) dragEvent.getSource()).startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString("Custom String"); // Example data
        db.setContent(content);
        dragEvent.consume();
    }

    public void dgdTuesday(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            System.out.println("Dropped: " + db.getString());
        }
        dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }


    // Set up drag-and-drop handlers for a ListView
    private void setupDragAndDrop(ListView<Meal> listView) {
        listView.setOnDragDetected(event -> startDrag(event, listView));
        listView.setOnDragOver(event -> allowDragOver(event));
        listView.setOnDragDropped(event -> handleDrop(event, listView));
    }

    // Start Drag from any ListView
    private void startDrag(MouseEvent event, ListView<Meal> sourceList) {
        Meal selectedMeal = sourceList.getSelectionModel().getSelectedItem();
        if (selectedMeal != null) {
            Dragboard dragboard = sourceList.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(selectedMeal.getName()); // Add Meal name to Dragboard
            dragboard.setContent(content);

            // Attach the source ListView as part of the dragboard content
            sourceList.getProperties().put("sourceList", sourceList);
            event.consume();
        }
    }

    // Allow Drag Over Target ListView
    private void allowDragOver(DragEvent event) {
        if (event.getGestureSource() != event.getGestureTarget() && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    // Handle Drop on Target ListView
    private void handleDrop(DragEvent event, ListView<Meal> targetList) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if (dragboard.hasString()) {
            // Get the source ListView from the dragboard properties
            @SuppressWarnings("unchecked")
            ListView<Meal> sourceList = (ListView<Meal>) event.getGestureSource();

            // Find the dragged Meal by its name
            String mealName = dragboard.getString();
            Meal draggedMeal = findMealByName(sourceList, mealName);

            if (draggedMeal != null) {
                // Add to target ListView and remove from source ListView
                targetList.getItems().add(draggedMeal);
                sourceList.getItems().remove(draggedMeal);
                success = true;
            }
        }

        event.setDropCompleted(success);
        event.consume();
    }

    // Helper Method: Find Meal Object by Name in a ListView
    private Meal findMealByName(ListView<Meal> listView, String mealName) {
        for (Meal meal : listView.getItems()) {
            if (meal.getName().equals(mealName)) {
                return meal;
            }
        }
        return null; // If not found
    }
}