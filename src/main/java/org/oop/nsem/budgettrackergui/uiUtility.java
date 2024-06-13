package org.oop.nsem.budgettrackergui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;

public class uiUtility {
    @FunctionalInterface
    public interface Function {
        void run();
    }

    public MaterialTextBox createComboBox(String title, ObservableList<String> list) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("material-textfield-text");
        ComboBox<String> comboBox = new ComboBox<String>(list);
        comboBox.setValue(comboBox.getItems().get(0));

        VBox box = new VBox(titleLabel, comboBox);

        box.setPrefHeight(56);
        box.getStyleClass().add("material-textfield-box");

        return new MaterialTextBox(comboBox, box);
    }

    public MaterialTextBox createTextfield(String title, String prefilled) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("material-textfield-text");
        TextField textField = new TextField(prefilled);
        textField.getStyleClass().add("material-textfield");

        VBox box = new VBox(titleLabel, textField);

        box.setPrefHeight(56);
        box.getStyleClass().add("material-textfield-box");

        return new MaterialTextBox(textField, box);
    }

    public VBox createIconBox(String iconCode) {
        FontIcon icon = createIcon(iconCode, 48, Color.WHITE);

        VBox iconBox = new VBox();
        iconBox.getChildren().add(icon);
        iconBox.getStyleClass().add("material-icon-box");
        iconBox.setAlignment(Pos.CENTER);
        iconBox.setMinHeight(64);
        iconBox.setMinWidth(64);
        iconBox.setMaxWidth(64);
        iconBox.setPrefWidth(64);

        return iconBox;
    }

    public FontIcon createIcon(String iconCode, int size, Color color) {
        FontIcon icon = new FontIcon(iconCode);
        icon.setIconSize(size);
        icon.setIconColor(color);

        return icon;
    }

    public HBox createButton(String title, String icon, Function action) {
        FontIcon iconObj = createIcon(icon, 18, Color.BLACK);
        Label buttonText = new Label(title);
        buttonText.getStyleClass().add("material-button-text");
        HBox button = new HBox(iconObj, buttonText);
        button.getStyleClass().add("material-button");
        button.setAlignment(Pos.CENTER);
        button.setSpacing(8);
        button.setMinHeight(40);
        button.setOnMouseClicked(event -> action.run());

        return button;
    }

    public Label createTitle(String text) {
        Label pageTitle = new Label(text);
        pageTitle.getStyleClass().add("material-pageTitle");

        return pageTitle;
    }

    public HBox createCard(Node... node) {
        HBox card = new HBox(node);
        card.getStyleClass().add("material-card");

        return card;
    }

    public Region createSpacing() {
        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        VBox.setVgrow(spacing, Priority.ALWAYS);

        return spacing;
    }

    public HBox createTopBar(String title, Node... node) {
        Label pageTitle = createTitle(title);

        HBox titleBox = new HBox(pageTitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Region spacing = createSpacing();

        HBox buttons = new HBox();
        buttons.getChildren().addAll(node);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(12);

        HBox topBar = new HBox();
        topBar.getChildren().addAll(titleBox, spacing, buttons);

        return topBar;
    }
}
