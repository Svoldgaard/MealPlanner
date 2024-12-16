module dk.svoldgaard.mealplan {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.svoldgaard.mealplan to javafx.fxml;
    exports dk.svoldgaard.mealplan;
    exports dk.svoldgaard.mealplan.GUI.Controller;
    opens dk.svoldgaard.mealplan.GUI.Controller to javafx.fxml;
}