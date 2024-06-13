package org.oop.nsem.budgettrackergui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class BudgetTrackerGUIApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        Font.loadFont(BudgetTrackerGUIApp.class.getResourceAsStream("Roboto-Bold.tff"), 100);
        Font.loadFont(BudgetTrackerGUIApp.class.getResourceAsStream("Roboto-Medium.tff"), 100);
        Font.loadFont(BudgetTrackerGUIApp.class.getResourceAsStream("Roboto-Regular.tff"), 100);
        Font.loadFont(BudgetTrackerGUIApp.class.getResourceAsStream("Roboto-Black.tff"), 100);
        Font.loadFont(BudgetTrackerGUIApp.class.getResourceAsStream("Roboto-Bold.tff"), 100);
        Font.loadFont(BudgetTrackerGUIApp.class.getResourceAsStream("Roboto-Thin.tff"), 100);
        Font.loadFont(BudgetTrackerGUIApp.class.getResourceAsStream("Roboto-Light.tff"), 100);


        Authentication auth = new Authentication();

        auth.start(stage);
    }
}
