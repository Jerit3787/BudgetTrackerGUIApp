package org.oop.nsem.budgettrackergui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NotificationGUI {
    private uiUtility ui = new uiUtility();
    private String currentUserId;

    public NotificationGUI(String user) {
        this.currentUserId = user;
    }

    public VBox createUI() {
        HBox addButton = ui.createButton("Remind", "mdi2c-calendar-plus", () -> {});
        HBox filterButton = ui.createButton("Clear", "mdi2n-notification-clear-all", () -> {});

        HBox topBar = ui.createTopBar("Notification", addButton, filterButton);

        //VBox transactions = createTransactionUI();

        VBox root = new VBox(topBar/*, transactions*/);
        root.setSpacing(12);
        root.setPrefWidth(1160);
        root.setPrefHeight(720);
        root.getStylesheets().add(BudgetGUI.class.getResource("material-styling.css").toExternalForm());
        root.getStyleClass().add("dashboard");

        return root;
    }
}
