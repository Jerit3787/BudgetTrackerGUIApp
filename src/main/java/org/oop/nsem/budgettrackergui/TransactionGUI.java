package org.oop.nsem.budgettrackergui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class TransactionGUI {
    private uiUtility ui = new uiUtility();
    private TableView table = new TableView();
    private String currentUserId;
    private TextField amountField;
    private TextField dateField;
    private TextField descField;
    private ComboBox<String> categoryComboBox;

    private Stage stageForm;

    private ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private ArrayList<String> budgets = new ArrayList<String>();
    private ArrayList<Budget> budgetsReal = new ArrayList<Budget>();
    private ObservableList<String> budgetsLists = FXCollections.observableArrayList();

    private TransactionFunction transaction;

    public TransactionGUI(String user) {
        this.currentUserId = user;
        transaction = new TransactionFunction(currentUserId, transactions);
    }

    private HBox createTransactionTitle() {
        Label date = new Label("13 June 2024");
        date.getStyleClass().add("material-table-title-text");

        HBox dateBox = new HBox(date);
        dateBox.getStyleClass().add("material-table-title");

        return dateBox;
    }

    private HBox createTransactionBody() {

        Label budgetData = new Label(String.format("%s - %s", "Budget1", "Initial Balance"));
        Region spacing = ui.createSpacing();
        Label transAmount = new Label("RM 10.00");
        transAmount.getStyleClass().add("material-text-bold");
        HBox transactionsBox = new HBox(budgetData, spacing, transAmount);
        transactionsBox.getStyleClass().add("material-table-body");

        return transactionsBox;
    }

    private VBox createTransactionUI() {
        HBox date1 = createTransactionTitle();
        HBox trans1 = createTransactionBody();
        HBox trans2 = createTransactionBody();
        HBox date2 = createTransactionTitle();
        HBox trans3 = createTransactionBody();

        VBox trans = new VBox(date1, trans1, trans2, date2, trans3);

        return trans;
    }

    private HBox createTypeCard(String text, String iconCode, uiUtility.Function action) {
        Label title = ui.createTitle(text);
        VBox iconBox = ui.createIconBox(iconCode);
        HBox card = new HBox(iconBox, title);
        card.setSpacing(8);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("material-selection-card");
        card.setOnMouseClicked(event -> action.run());

        return card;
    }

    private void createTypeForm() {
        Label title = ui.createTitle("Select Transaction Type");
        Stage stage = new Stage();

        HBox card1 = createTypeCard("Income", "mdi2c-cash-plus", () -> {stage.close(); createForm("Income");});
        HBox card2 = createTypeCard("Expenses", "mdi2c-cash-minus", () -> {stage.close(); createForm("Expenses");});

        VBox cards = new VBox(card1, card2);
        cards.setSpacing(12);

        VBox root = new VBox(title, cards);
        root.getStylesheets().add(BudgetGUI.class.getResource("material-styling.css").toExternalForm());
        root.setPrefHeight(300);
        root.setPrefWidth(400);
        root.setSpacing(12);
        root.getStyleClass().add("dashboard");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    private void createForm(String type) {
        Label title = ui.createTitle(String.format("Add %s Transaction", type));
        BudgetFunction budget = new BudgetFunction(currentUserId, budgetsReal);
        ArrayList<String> budgetsName = budget.loadBudgetName();

        budgetsLists.addAll(budgetsName);

        MaterialTextBox textfield1 = ui.createTextfield("Date","");
        MaterialTextBox textfield2 = ui.createTextfield("Amount","");
        MaterialTextBox textfield3 = ui.createComboBox("Category", budgetsLists);
        MaterialTextBox textfield4 = ui.createTextfield("Description","");

        dateField = (TextField) textfield1.object;
        amountField = (TextField) textfield2.object;
        categoryComboBox = (ComboBox<String>) textfield3.object;
        descField = (TextField) textfield4.object;

        HBox addButton = ui.createButton("Add", "mdi2p-plus", () -> handleAddExpense(type));

        HBox buttons = new HBox(addButton);
        buttons.setPrefWidth(400);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(title, textfield1.box, textfield2.box, textfield3.box, textfield4.box, buttons);
        root.getStylesheets().add(BudgetGUI.class.getResource("material-styling.css").toExternalForm());
        root.setPrefHeight(300);
        root.setPrefWidth(400);
        root.setSpacing(12);
        root.getStyleClass().add("dashboard");

        Scene scene = new Scene(root);

        stageForm = new Stage();
        stageForm.setScene(scene);
        stageForm.show();
    }

    public void handleAddExpense(String type) {
        stageForm.close();
        transaction.addTransaction(dateField.getText(), Double.parseDouble(amountField.getText()), categoryComboBox.getValue(), type, descField.getText());
        if (type.contains("Income")) {
            transactionsList.add(new Income(dateField.getText(), Double.parseDouble(amountField.getText()), categoryComboBox.getValue(), descField.getText()));
        } else {
            transactionsList.add(new Expenses(dateField.getText(), Double.parseDouble(amountField.getText()), categoryComboBox.getValue(), descField.getText()));
        }
    }

    public VBox createTable() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        transactions = transaction.loadTransaction();

        transactionsList.addAll(transactions);

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("date"));

        TableColumn amountCol = new TableColumn("Amount (RM)");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));

        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setMinWidth(100);
        categoryCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("type"));

        TableColumn descCol = new TableColumn("Description");
        descCol.setMinWidth(300);
        descCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));

        table.setItems(transactionsList);
        table.getColumns().addAll(dateCol, amountCol, categoryCol, typeCol, descCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);

        return vbox;
    }

    public VBox createUI() {
        HBox addButton = ui.createButton("Add", "mdi2c-chart-box-plus-outline", () -> createTypeForm());

        HBox topBar = ui.createTopBar("Transaction", addButton);

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
