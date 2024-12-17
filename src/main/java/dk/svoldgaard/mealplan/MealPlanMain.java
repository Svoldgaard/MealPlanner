package dk.svoldgaard.mealplan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MealPlanMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MealPlanMain.class.getResource("/dk/svoldgaard/mealplan/FXML/MealPlan.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 760, 605);
        stage.setTitle("Meal Planner");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }
}