
package com.trajectory.app.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// Keeps the cards, buttons, and text fields looking the same on each page.
public final class UIComponents {

    // We can use these methods without creating an object.
    private UIComponents() {
    }

    // Makes a white card that we can use on any page.
    public static VBox createCard() {

        VBox card = new VBox(7);

        // Adds some space inside the card.
        card.setPadding(new Insets(12));

        // Lets the card adjust to the available space.
        card.setMinWidth(0);

        // Gives the card rounded corners, a border, and a small shadow.
        card.setStyle(
                "-fx-background-color: " + Theme.WHITE + ";" +
                        "-fx-background-radius: 13;" +
                        "-fx-border-color: " + Theme.BORDER + ";" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 13;" +
                        "-fx-effect: dropshadow(gaussian,rgba(0,0,0,0.08),8,0,0,2);"
        );

        return card;
    }

    // Makes the titles shown at the top of each card.
    public static Label sectionTitle(String text) {

        Label label = new Label(text);

        // Makes the title bigger, bold, and dark.
        label.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + Theme.DARK + ";"
        );

        return label;
    }

    // Makes the smaller labels above input fields.
    public static Label fieldLabel(String text) {

        Label label = new Label(text);

        // Keeps the labels small but easy to read.
        label.setStyle(
                "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + Theme.DARK + ";"
        );

        return label;
    }

    // Gives text fields the same look throughout the app.
    public static void styleTextField(TextField field) {

        // Sets the height of the text field.
        field.setPrefHeight(29);

        // Adds a light background, border, and rounded corners.
        field.setStyle(
                "-fx-background-color: #FAFAFB;" +
                        "-fx-border-color: " + Theme.BORDER + ";" +
                        "-fx-border-radius: 7;" +
                        "-fx-background-radius: 7;" +
                        "-fx-font-size: 11px;" +
                        "-fx-text-fill: " + Theme.DARK + ";"
        );
    }

    // Makes the main buttons pink with white text.
    public static void stylePinkButton(Button button) {

        // Sets the button height.
        button.setPrefHeight(30);

        // Gives the button its pink color and rounded corners.
        button.setStyle(
                "-fx-background-color: " + Theme.PINK + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 7;" +
                        "-fx-cursor: hand;"
        );
    }

    // Makes the secondary buttons white instead of pink.
    public static void styleSecondaryButton(Button button) {

        // Sets the button height.
        button.setPrefHeight(28);

        // Adds a white background, gray border, and dark text.
        button.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: " + Theme.DARK + ";" +
                        "-fx-border-color: " + Theme.BORDER + ";" +
                        "-fx-border-radius: 7;" +
                        "-fx-background-radius: 7;" +
                        "-fx-cursor: hand;"
        );
    }
}
