package org.oop.nsem.budgettrackergui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;

public class DashboardGUI {

    private VBox dashBox = new VBox();
    private Button home = new Button();
    private Button budget = new Button();
    private Button trans = new Button();
    private Button noti = new Button();
    private String currentUserId;
    private ArrayList<User> user;
    private String displayName;

    public DashboardGUI(String id, ArrayList<User> user) {
        this.currentUserId = id;
        this.user = user;
        for (User userData: user){
            if (userData.getUsername().contains(id));
            this.displayName = userData.getDisplayName();
        }
    }

    private VBox createNavigationButton(String label, String icon, boolean enabled, String menu) {
        // Home Button
        FontIcon buttonIcon = new FontIcon();
        buttonIcon.setIconLiteral(icon);
        buttonIcon.setIconSize(24);
        buttonIcon.setIconColor(Color.BLACK);

        Button button;
        switch(menu) {
            case "Home":
                button = home;
                break;
            case "Budgets":
                button = budget;
                break;
            case "Transactions":
                button = trans;
                break;
            case "Notifications":
                button = noti;
                break;
            default:
                button = new Button();
                break;
        }


        button.setGraphic(buttonIcon);
        button.getStyleClass().add("navigation-button");
        if (enabled)
            button.getStyleClass().add("navigation-button-enabled");
        button.setPrefWidth(56);
        button.setPrefHeight(32);

        EventHandler<ActionEvent> clickEvent = event -> {

        };

        switch(menu) {
            case "Home":
                clickEvent = event -> {
                    home.getStyleClass().add("navigation-button-enabled");
                    budget.getStyleClass().remove("navigation-button-enabled");
                    trans.getStyleClass().remove("navigation-button-enabled");
                    noti.getStyleClass().remove("navigation-button-enabled");
                    triggerHome();
                };
                break;
            case "Budgets":
                clickEvent = event -> {
                    budget.getStyleClass().add("navigation-button-enabled");
                    home.getStyleClass().remove("navigation-button-enabled");
                    trans.getStyleClass().remove("navigation-button-enabled");
                    noti.getStyleClass().remove("navigation-button-enabled");
                    triggerBudget();
                };
                break;
            case "Transactions":
                clickEvent = event -> {
                    trans.getStyleClass().add("navigation-button-enabled");
                    home.getStyleClass().remove("navigation-button-enabled");
                    budget.getStyleClass().remove("navigation-button-enabled");
                    noti.getStyleClass().remove("navigation-button-enabled");
                    triggerTransaction();
                };
                break;
            case "Notifications":
                clickEvent = event -> {
                    trans.getStyleClass().remove("navigation-button-enabled");
                    home.getStyleClass().remove("navigation-button-enabled");
                    budget.getStyleClass().remove("navigation-button-enabled");
                    noti.getStyleClass().add("navigation-button-enabled");
                    triggerNotifications();
                };
            default:
                clickEvent = event -> {};
                break;
        }

        button.setOnAction(clickEvent);

        Label buttonLabel = new Label();
        buttonLabel.setText(label);
        buttonLabel.getStyleClass().add("navigation-label");

        VBox box = new VBox();
        box.setPrefWidth(80);
        box.setPrefHeight(56);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(button, buttonLabel);
        box.getStyleClass().add("navigation-box");

        return box;
    }

    private void triggerHome() {
        DashboardGUI dashboard = new DashboardGUI(currentUserId, user);
        dashBox.getChildren().remove(0);
        dashBox.getChildren().add(new VBox());
    }

    private void triggerBudget() {
        BudgetGUI budget = new BudgetGUI(currentUserId);
        dashBox.getChildren().remove(0);
        dashBox.getChildren().add(budget.createUI());
    }

    private void triggerTransaction() {
        TransactionGUI trans = new TransactionGUI(currentUserId);
        dashBox.getChildren().remove(0);
        dashBox.getChildren().add(trans.createUI());
    }
    private void triggerNotifications() {
        NotificationGUI noti = new NotificationGUI(currentUserId);
        dashBox.getChildren().remove(0);
        dashBox.getChildren().add(noti.createUI());
    }


    public void loadUI(Stage stage) {
        VBox homeBox = createNavigationButton("Home", "mdi2h-home", true, "Home");
        VBox budgetBox = createNavigationButton("Budget", "mdi2c-chart-pie", false, "Budgets");
        VBox transactionBox = createNavigationButton("Transaction", "mdi2r-receipt", false, "Transactions");
        VBox expensesBox = createNavigationButton("Expenses", "mdi2r-receipt", false, "");
        VBox notificationBox = createNavigationButton("Notification", "mdi2b-bell", false, "Notifications");
        VBox settingsBox = createNavigationButton("Settings", "mdi2a-account-settings", false, "");

        VBox navigationPanel = new VBox();
        navigationPanel.setMinWidth(80);
        navigationPanel.setMinHeight(720);
        navigationPanel.setStyle("-fx-background-color: white;");
        navigationPanel.getChildren().addAll(homeBox, budgetBox, transactionBox, expensesBox, notificationBox, settingsBox);
        navigationPanel.getStyleClass().add("navigation-bar");

        HBox root = new HBox();
        root.setMaxWidth(1240);
        root.setMaxHeight(720);


        dashBox.getChildren().add(new VBox());

        root.getChildren().addAll(navigationPanel, dashBox);

        Scene appPanel = new Scene(root);

        appPanel.getStylesheets().add(DashboardGUI.class.getResource("material-styling.css").toExternalForm());

        stage.setTitle("Budget Tracker App");
        stage.setWidth(1240);
        stage.setHeight(720);
        stage.setScene(appPanel);

        stage.show();
    }
}
