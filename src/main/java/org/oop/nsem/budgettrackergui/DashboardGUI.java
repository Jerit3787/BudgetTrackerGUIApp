package org.oop.nsem.budgettrackergui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    private ArrayList<Budget> budgetsList = new ArrayList<Budget>();
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

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
                System.out.println("Configuring notification");
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
                break;
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
        dashBox.getChildren().add(createUi());
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
        System.out.println("LAUNCHING NOTIFICATION");
        NotificationGUI noti = new NotificationGUI(currentUserId);
        dashBox.getChildren().remove(0);
        dashBox.getChildren().add(noti.createUI());
    }

    private HBox createBudgetCard(String name, double used) {
        Label subtitle = new Label(String.format("%s Budget", name));
        Label value = new Label(String.format("RM%.2f left", used));
        value.getStyleClass().add("value-text");

        Label changesText = new Label("Resets");
        Label dateText = new Label(" monthly");
        HBox changesBox = new HBox();
        changesBox.getChildren().addAll(changesText, dateText);

        VBox dataBox = new VBox();
        dataBox.getChildren().addAll(subtitle, value, changesBox);

        FontIcon icon = new FontIcon("mdi2s-shopping");
        icon.setIconSize(48);
        icon.setIconColor(Color.WHITE);

        VBox iconBox = new VBox();
        iconBox.getChildren().add(icon);
        iconBox.getStyleClass().add("material-icon-box");
        iconBox.setAlignment(Pos.CENTER);
        iconBox.setPrefHeight(64);
        iconBox.setPrefWidth(64);

        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);

        HBox card = new HBox();
        card.setPrefWidth(380);
        card.setPrefHeight(100);
        card.getChildren().addAll(dataBox, r, iconBox);
        card.getStyleClass().add("material-card");
        card.setSpacing(6);

        return card;
    }

    private HBox createRecentTransactionCard(String name, String desc, String date, double amount) {
        FontIcon icon = new FontIcon("mdi2s-shopping");
        icon.setIconSize(48);
        icon.setIconColor(Color.WHITE);

        VBox iconBox = new VBox();
        iconBox.getChildren().add(icon);
        iconBox.getStyleClass().add("material-icon-box");
        iconBox.setAlignment(Pos.CENTER);
        iconBox.setPrefHeight(64);
        iconBox.setPrefWidth(64);

        Label categoryText = new Label(name);
        categoryText.getStyleClass().add("category-text");
        Label subText = new Label(desc);

        VBox dataBox1 = new VBox();
        dataBox1.getChildren().addAll(categoryText, subText);

        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);

        Label dateText = new Label(date);
        Region r2 = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        Label amountText = new Label(String.format("RM%.2f", amount));
        amountText.getStyleClass().add("value-text");

        VBox dataBox2 = new VBox();
        dataBox2.getChildren().addAll(dateText, r2,  amountText);
        dataBox2.setSpacing(6);
        dataBox2.setAlignment(Pos.CENTER_RIGHT);

        HBox card = new HBox();
        card.getChildren().addAll(iconBox, dataBox1, r, dataBox2);
        card.getStyleClass().add("material-card");
        card.setSpacing(12);

        return card;
    }

    private VBox createUi() {
        Label welcomeText = new Label(String.format("Welcome, %s", displayName));
        welcomeText.getStyleClass().add("material-pageTitle");

        Label budgetLabel = new Label("Overview");
        budgetLabel.getStyleClass().add("budgets-title");
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        budgetLabel.setAlignment(Pos.CENTER_RIGHT);
        Button viewBudgetButton = new Button("View more");
        viewBudgetButton.getStyleClass().add("view-button");

        HBox topCardBudget = new HBox();
        topCardBudget.getChildren().addAll(budgetLabel, r, viewBudgetButton);

        BudgetFunction bud = new BudgetFunction(currentUserId, budgetsList);
        ArrayList<Budget> budList = bud.loadBudget();
        Double[] remaining = bud.checkRemaining();
        int index = 0;
        HBox cards = new HBox();
        cards.setSpacing(6);

        for (Budget budData: budList) {
            HBox card1 = createBudgetCard(budData.getName(), remaining[index]);
            cards.getChildren().add(card1);
            index++;
        }

        VBox budgetsPanel = new VBox();
        budgetsPanel.getChildren().addAll(topCardBudget, cards);

        PieChart budgetChart = new PieChart();
        budgetChart.setTitle("Budget Distribution");



        ArrayList<PieChart.Data> testData = new ArrayList<PieChart.Data>();

        Double[] total = bud.checkTotal();
        int indexs = 0;

        for(Double budData: total) {
            testData.add(new PieChart.Data(budList.get(indexs).getName(), budData));
        }

        budgetChart.getData().addAll(testData);

        VBox chartCard = new VBox();
        chartCard.getChildren().add(budgetChart);
        chartCard.getStyleClass().add("material-card");

        Label transLabel = new Label("Recent Transactions");
        transLabel.getStyleClass().add("budgets-title");
        budgetLabel.setAlignment(Pos.CENTER_RIGHT);
        Region r2 = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        Button viewTransButton = new Button("View more");
        viewTransButton.getStyleClass().add("view-button");

        HBox topCardTrans = new HBox();
        topCardTrans.getChildren().addAll(transLabel, r2, viewTransButton);
        topCardTrans.setAlignment(Pos.CENTER_LEFT);
        topCardTrans.setSpacing(140);

        TransactionFunction trans = new TransactionFunction(currentUserId, transactions);

        ArrayList<Transaction> transList = trans.loadTransaction();

        VBox transCard = new VBox();
        transCard.getChildren().addAll(topCardTrans);
        transCard.setPrefWidth(580);
        transCard.setSpacing(12);

        for (Transaction transData: transList) {
            HBox trans1 = createRecentTransactionCard(transData.getCategory(), transData.getDescription(), transData.getDate(), transData.getAmount());
            transCard.getChildren().add(trans1);
        }



        HBox bottomPanel = new HBox();
        bottomPanel.getChildren().addAll(chartCard, transCard);
        bottomPanel.setSpacing(12);

        VBox dashboardPanel = new VBox();
        dashboardPanel.getChildren().addAll(welcomeText, budgetsPanel, bottomPanel);
        dashboardPanel.getStyleClass().add("dashboard");
        dashboardPanel.setPrefWidth(1160);
        dashboardPanel.setPrefHeight(720);
        dashboardPanel.setSpacing(12);

        return dashboardPanel;
    }

    public void loadUI(Stage stage) {
        VBox homeBox = createNavigationButton("Home", "mdi2h-home", true, "Home");
        VBox budgetBox = createNavigationButton("Budget", "mdi2c-chart-pie", false, "Budgets");
        VBox transactionBox = createNavigationButton("Transaction", "mdi2r-receipt", false, "Transactions");
        VBox notificationBox = createNavigationButton("Notification", "mdi2b-bell", false, "Notifications");

        VBox navigationPanel = new VBox();
        navigationPanel.setMinWidth(80);
        navigationPanel.setMinHeight(720);
        navigationPanel.setStyle("-fx-background-color: white;");
        navigationPanel.getChildren().addAll(homeBox, budgetBox, transactionBox, notificationBox);
        navigationPanel.getStyleClass().add("navigation-bar");

        HBox root = new HBox();
        root.setMaxWidth(1240);
        root.setMaxHeight(720);


        dashBox.getChildren().add(createUi());

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
