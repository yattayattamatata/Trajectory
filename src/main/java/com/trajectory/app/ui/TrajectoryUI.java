package com.trajectory.app.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrajectoryUI extends Application {

    private BorderPane root;

    private Button dashboardButton;
    private Button budgetButton;
    private Button debtButton;
    private Button investmentsButton;

    private static final String DARK = "#252932";
    private static final String PINK = "#AE5276";

    @Override
    public void start(Stage stage) {

        root = new BorderPane();
        root.setStyle("-fx-background-color: #F8F9FB;");

        // Create navigation sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Open dashboard when application starts
        showDashboard();

        Scene scene = new Scene(root, 1050, 650);

        stage.setTitle("Trajectory - Personal Finance Manager");
        stage.setScene(scene);

        stage.setMinWidth(950);
        stage.setMinHeight(600);

        stage.show();
    }

    private VBox createSidebar() {

        Label logo = new Label("▥  Trajectory");
        logo.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        dashboardButton = createNavButton("⌂  Dashboard");
        budgetButton = createNavButton("▤  Budget");
        debtButton = createNavButton("▣  Debt");
        investmentsButton = createNavButton("▥  Investments");

        dashboardButton.setOnAction(e -> showDashboard());
        budgetButton.setOnAction(e -> showBudget());
        debtButton.setOnAction(e -> showDebt());
        investmentsButton.setOnAction(e -> showInvestments());

        VBox navigation = new VBox(
                10,
                dashboardButton,
                budgetButton,
                debtButton,
                investmentsButton
        );

        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button settingsButton = createNavButton("⚙  Settings");
        Button helpButton = createNavButton("?  Help");

        VBox sidebar = new VBox(
                25,
                logo,
                navigation,
                spacer,
                settingsButton,
                helpButton
        );

        sidebar.setPadding(new Insets(25, 12, 20, 12));
        sidebar.setPrefWidth(190);

        sidebar.setStyle(
                "-fx-background-color: " + DARK + ";"
        );

        return sidebar;
    }

    private Button createNavButton(String text) {

        Button button = new Button(text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #E4E4E8;" +
                        "-fx-font-size: 13px;" +
                        "-fx-background-radius: 6;"
        );

        return button;
    }

    private void selectButton(Button selected) {

        Button[] buttons = {
                dashboardButton,
                budgetButton,
                debtButton,
                investmentsButton
        };

        for (Button button : buttons) {

            if (button == selected) {

                button.setStyle(
                        "-fx-background-color: " + PINK + ";" +
                                "-fx-text-fill: white;" +
                                "-fx-font-size: 13px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 6;"
                );

            } else {

                button.setStyle(
                        "-fx-background-color: transparent;" +
                                "-fx-text-fill: #E4E4E8;" +
                                "-fx-font-size: 13px;" +
                                "-fx-background-radius: 6;"
                );
            }
        }
    }

    private void showDashboard() {

        selectButton(dashboardButton);

        Label title = new Label("Dashboard");
        title.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;"
        );

        Label welcome = new Label(
                "Welcome to Trajectory!"
        );

        welcome.setStyle("-fx-font-size: 16px;");

        VBox content = new VBox(15, title, welcome);
        content.setPadding(new Insets(25));

        root.setCenter(content);
    }

    private void showBudget() {

        selectButton(budgetButton);

        Label title = new Label("Budget Manager");
        title.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;"
        );

        Label message = new Label(
                "Budget management features will appear here."
        );

        VBox content = new VBox(15, title, message);
        content.setPadding(new Insets(25));

        root.setCenter(content);
    }

    private void showDebt() {

        selectButton(debtButton);

        DebtView debtView = new DebtView();

        debtView.setDashboardAction(() -> showDashboard());

        root.setCenter(debtView);
    }

    private void showInvestments() {

        selectButton(investmentsButton);

        Label title = new Label("Investments");
        title.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;"
        );

        Label message = new Label(
                "Investment management features will appear here."
        );

        VBox content = new VBox(15, title, message);
        content.setPadding(new Insets(25));

        root.setCenter(content);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
