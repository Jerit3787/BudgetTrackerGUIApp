package org.oop.nsem.budgettrackergui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.javafx.Icon;

import java.util.ArrayList;

public class BudgetGUI {

    private final uiUtility ui = new uiUtility();
    private String currentUserId;
    private ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
    private ArrayList<Budget> budgetsList = new ArrayList<Budget>();
    private TransactionFunction transaction;
    private BudgetFunction budget;
    private TextField nameField;
    private TextField amountField;
    private ComboBox<String> periodComboBox;
    private Stage stageForm;

    public BudgetGUI(String user) {
        this.currentUserId = user;
        transaction = new TransactionFunction(currentUserId, transactionsList);
        budget = new BudgetFunction(currentUserId, budgetsList);
    }

    private HBox createCard(String title, double remaining, double total, String date, String iconCode) {
        VBox iconBox = ui.createIconBox(iconCode);

        Label budgetTitle = new Label(title);
        budgetTitle.getStyleClass().add("material-card-title");
        Label usedString = new Label("Used so far");
        Label usedLabel = new Label(String.format("RM %.2f of RM %.2f", remaining, total));
        usedLabel.getStyleClass().add("material-text-bold");
        HBox usedProgress = new HBox();

        double usedLength = (remaining / total) * 1000;

        usedProgress.getStyleClass().add("no-category");
        usedProgress.setPrefWidth(usedLength);
        HBox budgetProgress = new HBox(usedProgress);
        budgetProgress.setPrefHeight(12);
        budgetProgress.getStyleClass().add("yes-category");
        budgetProgress.setPrefWidth(1000);
        Rectangle rect = new Rectangle(1000,12);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        budgetProgress.setClip(rect);
        Label resetLabel = new Label(String.format("Resets %s", date));
        VBox budgetProgressBox = new VBox(usedString, usedLabel, budgetProgress, resetLabel);
        budgetProgressBox.setSpacing(2);

        Region spacing = ui.createSpacing();

        VBox budgetInfo = new VBox(budgetTitle, spacing, budgetProgressBox);

        VBox iconHold = new VBox(iconBox);
        iconHold.setAlignment(Pos.CENTER);

        HBox card = ui.createCard(iconHold, budgetInfo);
        card.setSpacing(12);
        card.setPrefHeight(130);

        card.setOnMouseClicked(event -> createManagePopup(title, total, (total - remaining), remaining));

        return card;
    }

    private VBox createBudgetDetailsCard(String title, double value) {
        VBox iconBox = ui.createIconBox("mdi2s-shopping");

        Label detailTitle = new Label(title);
        Label detailValue = new Label(String.format("RM%.2f", value));
        VBox detail = new VBox(detailTitle, detailValue);

        return new VBox(iconBox, detail);
    }

    private HBox createBudgetManageTopBar(String title, Stage stage) {
        Label budgetTitle = ui.createTitle(title);

        HBox addButton = ui.createButton("Add", "mdi2c-chart-box-plus-outline", () -> {});

        HBox buttons = new HBox(addButton);

        HBox topBar = new HBox(budgetTitle, buttons);

        return topBar;
    }

    private Stage createManagePopup(String title, double income, double expenses, double remaining) {

        Stage manageStage = new Stage();

        HBox topBar = createBudgetManageTopBar(title, manageStage);

        HBox chart = new HBox();

        VBox incomeDetail = createBudgetDetailsCard("Income", income);
        VBox expensesDetail = createBudgetDetailsCard("Expenses", income);
        VBox remainingDetail = createBudgetDetailsCard("Remaining", income);

        VBox details = new VBox(incomeDetail, expensesDetail, remainingDetail);

        VBox content = new VBox(chart, details);

        VBox root = new VBox(topBar, content);

        Scene manageScene = new Scene(root);
        manageStage.setScene(manageScene);

        return manageStage;
    }



    private void createRenamePopup(String previousTitle) {
        Label title = new Label("Rename Budget");
        MaterialTextBox textField = ui.createTextfield("Budget Name", previousTitle);
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

    private void createForm(String type) {
        Label title = ui.createTitle(String.format("Add %s Budget", type));

        ObservableList<String> period = FXCollections.observableArrayList();
        period.addAll("Weekly", "Monthly");

        MaterialTextBox textfield1 = ui.createTextfield("Name","");
        MaterialTextBox textfield2 = ui.createTextfield("Amount","");
        MaterialTextBox textfield3 = ui.createComboBox("Category", period);

        nameField = (TextField) textfield1.object;
        amountField = (TextField) textfield2.object;
        periodComboBox = (ComboBox<String>) textfield3.object;

        HBox addButton = ui.createButton("Add", "mdi2p-plus", () -> handleAddExpense(type));

        HBox buttons = new HBox(addButton);
        buttons.setPrefWidth(400);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(title, textfield1.box, textfield2.box, textfield3.box, buttons);
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
        budget.addBudget(nameField.getText(), periodComboBox.getValue(), type);
        switch (type) {
            case "Entertainment":
                budgetsList.add(new Entertainment(nameField.getText(), periodComboBox.getValue()));
                break;

            case "Groceries":
                budgetsList.add(new Groceries(nameField.getText(), periodComboBox.getValue()));
                break;

            case "Utility":
                budgetsList.add(new Utility(nameField.getText(), periodComboBox.getValue()));
                break;

            default:
                budgetsList.add(new Other(nameField.getText(), periodComboBox.getValue()));
                break;
        }
        transaction.addTransaction("13/06/2024", Double.parseDouble(amountField.getText()), nameField.getText(), "Income", String.format("%s - Initial Balance", nameField.getText()));
    }

    private void createTypeForm() {
        Label title = ui.createTitle("Select Budget Type");
        Stage stage = new Stage();

        HBox card1 = createTypeCard("Entertainment", "mdi2g-gamepad-variant-outline", () -> {stage.close(); createForm("Entertainment");});
        HBox card2 = createTypeCard("Groceries", "mdi2s-shopping-outline", () -> {stage.close(); createForm("Groceries");});
        HBox card3 = createTypeCard("Utility", "mdi2t-tools", () -> {stage.close(); createForm("Utility");});
        HBox card4 = createTypeCard("Other", "mdi2f-finance", () -> {stage.close(); createForm("Other");});


        VBox cards = new VBox(card1, card2, card3, card4);
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

    public VBox createUI() {

        HBox addButton = ui.createButton("Add", "mdi2c-chart-box-plus-outline", () -> createTypeForm());

        HBox topBar = ui.createTopBar("Budgets", addButton);

        transaction.loadTransaction();
        budget.loadBudget();

        Double[] remainingList = budget.checkRemaining();
        Double[] totalList = budget.checkTotal();
        int index = 0;
        VBox cards = new VBox();

        budgetsList = budget.loadBudget();

        System.out.println(budgetsList.size());

        for (Budget budget: budgetsList) {
            try {
                cards.getChildren().add(createCard(budget.getName(), remainingList[index], totalList[index], budget.getPeriod(), ((Entertainment) budget).getIcon()));
            } catch (Exception e) {
                System.out.println("not entertainment");
                try {
                    cards.getChildren().add(createCard(budget.getName(), remainingList[index], totalList[index], budget.getPeriod(), ((Groceries) budget).getIcon()));
                } catch (Exception ee) {
                    System.out.println("not groceries");
                    try {
                        cards.getChildren().add(createCard(budget.getName(), remainingList[index], totalList[index], budget.getPeriod(), ((Utility) budget).getIcon()));
                    } catch (Exception eee) {
                        System.out.println("not utility");
                        try {
                            cards.getChildren().add(createCard(budget.getName(), remainingList[index], totalList[index], budget.getPeriod(), ((Other) budget).getIcon()));
                        } catch (Exception eeee) {
                            System.out.println("not other");
                        }
                    }
                }
            }
            index++;
        }

        cards.setSpacing(12);

        VBox root = new VBox(topBar, cards);
        root.setSpacing(12);
        root.setPrefWidth(1160);
        root.setPrefHeight(720);

        root.getStylesheets().add(BudgetGUI.class.getResource("material-styling.css").toExternalForm());
        root.getStyleClass().add("dashboard");

        return root;
    }
}
