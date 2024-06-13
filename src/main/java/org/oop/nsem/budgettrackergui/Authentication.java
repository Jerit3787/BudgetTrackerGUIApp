package org.oop.nsem.budgettrackergui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    private static final String FILE_PATH = "database/auth/user_accounts.txt";
    private ArrayList<User> userList = new ArrayList<>();

    public void start(Stage primaryStage) {
        loadUserData(userList);

        primaryStage.setTitle("Budget Tracker App");

        // Login Screen
        GridPane loginGrid = createLoginGrid(primaryStage);
        Scene loginScene = new Scene(loginGrid, 400, 200);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void saveUserData(ArrayList<User> userList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User user : userList) {
                writer.println(user.getUsername() + "," + user.getPassword() + "," + user.getDisplayName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserData(ArrayList<User> userList) {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    userList.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found.");
        }
    }

    public static boolean authenticate(String username, String password, ArrayList<User> userList) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean userExists(String username, ArrayList<User> userList) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private GridPane createLoginGrid(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Label messageLabel = new Label();
        GridPane.setConstraints(messageLabel, 1, 3);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (authenticate(username, password, userList)) {
                DashboardGUI dash = new DashboardGUI(username, userList);
                primaryStage.close();
                Stage newStage = new Stage();
                dash.loadUI(newStage);
            } else {
                messageLabel.setText("Invalid login. Please try again.");
                // Reset username and password fields
                usernameInput.clear();
                passwordInput.clear();
            }
        });

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 2, 2);
        registerButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Username and password cannot be empty.");
            } else if (userExists(username, userList)) {
                messageLabel.setText("Username already exists.");
                // Reset username and password fields
                usernameInput.clear();
                passwordInput.clear();
            } else {
                messageLabel.setText("User registered successfully.");

                GridPane grid2 = new GridPane();
                Label displayName = new Label("Display Name:");
                TextField displayField = new TextField();
                Button saveButton = new Button("Save");
                grid2.add(displayName, 0, 0);
                grid2.add(displayField,1, 0);
                grid2.add(saveButton,2,0);
                grid2.setPrefWidth(500);
                grid2.setPrefHeight(200);
                grid2.setAlignment(Pos.CENTER);
                grid2.setHgap(12);
                Scene scene2 = new Scene(grid2);
                Stage stage2 = new Stage();
                stage2.setScene(scene2);
                stage2.show();
                saveButton.setOnAction(event -> {
                    submitData(usernameInput.getText(), passwordInput.getText(), displayField.getText());
                    stage2.close();
                });
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, registerButton, messageLabel);

        return grid;
    }
    private void submitData(String username, String password, String displayName) {
        userList.add(new User(username, password, displayName));
        saveUserData(userList);
    }
}


