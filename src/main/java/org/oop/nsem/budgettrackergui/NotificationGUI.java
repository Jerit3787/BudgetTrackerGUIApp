package org.oop.nsem.budgettrackergui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class NotificationGUI {
    private uiUtility ui = new uiUtility();
    private String currentUserId;
    private TableView table = new TableView();
    private ArrayList<Notification> notifications = new ArrayList<Notification>();
    private NotificationFunction notification;
    private ObservableList<Notification> notificationsList = FXCollections.observableArrayList();

    public NotificationGUI(String user) {
        this.currentUserId = user;
        notification = new NotificationFunction(currentUserId, notifications);
    }

    public VBox createTable() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        notifications = notification.loadNotification();

        notificationsList.addAll(notifications);

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<Notification, String>("date"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<Notification, String>("type"));

        TableColumn messageCol = new TableColumn("Message");
        messageCol.setMinWidth(100);
        messageCol.setCellValueFactory(new PropertyValueFactory<Notification, String>("message"));

        table.setItems(notificationsList);
        table.getColumns().addAll(dateCol, typeCol, messageCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);

        return vbox;
    }

    public VBox createUI() {
        HBox addButton = ui.createButton("Remind", "mdi2c-calendar-plus", () -> {});
        HBox filterButton = ui.createButton("Clear", "mdi2n-notification-clear-all", () -> {});

        HBox topBar = ui.createTopBar("Notification", addButton, filterButton);

        VBox transactions = createTable();

        VBox root = new VBox(topBar, transactions);
        root.setSpacing(12);
        root.setPrefWidth(1160);
        root.setPrefHeight(720);
        root.getStylesheets().add(BudgetGUI.class.getResource("material-styling.css").toExternalForm());
        root.getStyleClass().add("dashboard");

        return root;
    }
}
