package com.trajectory.app.ui;

import com.trajectory.app.model.Debt;
import com.trajectory.app.model.DebtPayoffResult;
import com.trajectory.app.ui.components.Theme;
import com.trajectory.app.ui.components.UIComponents;
import com.trajectory.app.ui.components.ChartUtils;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

// This page lets the user add debts and compare payoff plans.
public class DebtView extends BorderPane {

    // Short names for the shared colors so the styles below are easier to read.
    private static final String BG = Theme.BG;
    private static final String WHITE = Theme.WHITE;
    private static final String DARK = Theme.DARK;
    private static final String GRAY = Theme.GRAY;
    private static final String BORDER = Theme.BORDER;
    private static final String PINK = Theme.PINK;
    private static final String SOFT_PINK = Theme.SOFT_PINK;
    private static final String SELECTED_PINK = Theme.SELECTED_PINK;
    private static final String GREEN_BG = Theme.GREEN_BG;
    private static final String GREEN_BORDER = Theme.GREEN_BORDER;
    private static final String GREEN_TEXT = Theme.GREEN_TEXT;

    // Saves debts on this computer between app sessions.
    private static final Path SAVE_FILE = Paths.get(
            System.getProperty("user.home"), ".trajectory", "debts.properties");

    // Keeps track of all debts and which ones are selected for the graph.
    private final ObservableList<Debt> debts = FXCollections.observableArrayList();
    private final Set<Debt> selectedDebts = new HashSet<>();
    private Debt editingDebt;
    private Runnable dashboardAction;

    // Fields for adding or editing a debt.
    private final TextField nameField = new TextField();
    private final TextField balanceField = new TextField();
    private final TextField aprField = new TextField();
    private final TextField paymentField = new TextField();
    private final Button saveButton = new Button("+ Add Debt");
    private final Button clearButton = new Button("Clear / Cancel Edit");

    // Table and controls for picking debts.
    private final TableView<Debt> table = new TableView<>();
    private final Button allDebtsButton = new Button("All Debts");
    private final Label selectedCount = new Label("0 of 0 Debts Selected");

    // The graph shows how the remaining balance changes over time.
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
    private final Label graphTitle = new Label("Payoff Projection");
    private final Label graphMessage = new Label("Select debts to view your payoff projection.");
    private final Label currentLegend = new Label("Current Payment");
    private final Label extraLegend = new Label("+ Extra Payment");
    private final HBox extraLegendRow = new HBox();

    // Results for the normal monthly payments.
    private final Label payoffDate = new Label("—");
    private final Label totalInterest = new Label("—");
    private final Label totalPaid = new Label("—");

    // Results for adding extra money each month.
    private boolean whatIfEnabled = true;
    private final StackPane switchTrack = new StackPane();
    private final javafx.scene.shape.Circle switchKnob = new javafx.scene.shape.Circle(9);
    private final TextField extraField = new TextField("100");
    private final Label comparisonDate = new Label("—");
    private final Label comparisonInterest = new Label("—");
    private final Label comparisonTotal = new Label("—");
    private final Label monthsSaved = new Label("");
    private final Label interestSaved = new Label("");
    private final Button updateButton = new Button("Update Projection");

    // Sets up the page and loads any debts saved earlier.
    public DebtView() {
        setStyle("-fx-background-color: " + BG + ";");
        setPadding(new Insets(12));
        loadDebts();
        selectedDebts.addAll(debts);
        configureChart();
        configureTable();
        buildLayout();
        configureActions();
        refreshEverything();
    }

    // Lets the main app decide what happens when Back is clicked.
    public void setDashboardAction(Runnable action) {
        dashboardAction = action;
    }

    // Puts the debt form and list above the graph and payoff cards.
    private void buildLayout() {
        Label title = new Label("Debt");
        title.setStyle("-fx-font-size: 23px;-fx-font-weight: bold;-fx-text-fill: " + DARK + ";");
        Label subtitle = new Label("Manage your debts and explore payoff options.");
        subtitle.setStyle("-fx-font-size: 11px;-fx-text-fill: " + GRAY + ";");
        VBox titleBox = new VBox(2, title, subtitle);

        Button backButton = new Button("← Back to Dashboard");
        UIComponents.styleSecondaryButton(backButton);
        backButton.setOnAction(e -> {
            if (dashboardAction != null) dashboardAction.run();
        });
        HBox header = new HBox(10, titleBox, backButton);
        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        setTop(header);
        BorderPane.setMargin(header, new Insets(0, 0, 12, 0));

        // These cards all use the shared white-card styling.
        VBox formCard = createFormCard();
        VBox listCard = createListCard();
        VBox graphCard = createGraphCard();
        VBox summaryCard = createSummaryCard();
        VBox whatIfCard = createWhatIfCard();

        formCard.setPrefHeight(325);
        formCard.setMinHeight(325);
        listCard.setPrefHeight(205);
        listCard.setMinHeight(205);
        HBox topRow = new HBox(12, formCard, listCard);
        topRow.setAlignment(Pos.TOP_LEFT);
        formCard.setMinWidth(0);
        listCard.setMinWidth(0);
        formCard.prefWidthProperty().bind(topRow.widthProperty().multiply(0.25));
        listCard.prefWidthProperty().bind(topRow.widthProperty().multiply(0.73));

        // Gives the graph the most space in the bottom row.
        HBox bottomRow = new HBox(12, graphCard, summaryCard, whatIfCard);
        bottomRow.setAlignment(Pos.TOP_LEFT);
        graphCard.setMinWidth(0);
        summaryCard.setMinWidth(0);
        whatIfCard.setMinWidth(0);
        graphCard.prefWidthProperty().bind(bottomRow.widthProperty().multiply(0.50));
        summaryCard.prefWidthProperty().bind(bottomRow.widthProperty().multiply(0.24));
        whatIfCard.prefWidthProperty().bind(bottomRow.widthProperty().multiply(0.22));
        graphCard.setPrefHeight(275);
        summaryCard.setPrefHeight(275);
        whatIfCard.setPrefHeight(275);

        VBox content = new VBox(12, topRow, bottomRow);
        VBox.setVgrow(bottomRow, Priority.ALWAYS);
        setCenter(content);
    }

    // Builds the Add / Edit Debt card.
    private VBox createFormCard() {
        VBox card = UIComponents.createCard();
        card.setSpacing(5);
        card.setPadding(new Insets(10));
        nameField.setPromptText("e.g. Car Loan");
        balanceField.setPromptText("0.00");
        aprField.setPromptText("0.00");
        paymentField.setPromptText("0.00");

        // Makes all four fields match.
        for (TextField field : new TextField[]{nameField, balanceField, aprField, paymentField}) {
            UIComponents.styleTextField(field);
            field.setPrefHeight(27);
            field.setMinHeight(27);
        }
        UIComponents.stylePinkButton(saveButton);
        UIComponents.styleSecondaryButton(clearButton);
        saveButton.setPrefHeight(27);
        saveButton.setMinHeight(27);
        clearButton.setPrefHeight(27);
        clearButton.setMinHeight(27);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setMaxWidth(Double.MAX_VALUE);
        card.getChildren().addAll(
                UIComponents.sectionTitle("Add / Edit Debt"),
                UIComponents.fieldLabel("Debt Name"), nameField,
                UIComponents.fieldLabel("Current Balance ($)"), balanceField,
                UIComponents.fieldLabel("Interest Rate (APR %)"), aprField,
                UIComponents.fieldLabel("Monthly Payment ($)"), paymentField,
                saveButton, clearButton);
        return card;
    }

    // Shows the debt table and how many debts are selected.
    private VBox createListCard() {
        VBox card = UIComponents.createCard();
        allDebtsButton.setStyle(
                "-fx-background-color: #F8E7EF;-fx-text-fill: " + PINK + ";" +
                        "-fx-font-size: 11px;-fx-font-weight: bold;-fx-border-color: " + PINK + ";" +
                        "-fx-border-radius: 7;-fx-background-radius: 7;-fx-cursor: hand;");
        selectedCount.setStyle("-fx-font-size: 10px;-fx-text-fill: " + GRAY + ";");
        HBox selectionBar = new HBox(10, allDebtsButton, selectedCount);
        selectionBar.setAlignment(Pos.CENTER_LEFT);
        table.setPlaceholder(new Label("No debts yet. Add your first debt."));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setFixedCellSize(32);
        table.setStyle(
                "-fx-background-color: white;-fx-border-color: " + BORDER + ";" +
                        "-fx-border-radius: 7;-fx-background-radius: 7;" +
                        "-fx-selection-bar: " + SELECTED_PINK + ";" +
                        "-fx-selection-bar-non-focused: " + SELECTED_PINK + ";");
        VBox.setVgrow(table, Priority.ALWAYS);
        card.getChildren().addAll(UIComponents.sectionTitle("Debt List"), selectionBar, table);
        return card;
    }

    // Adds the table columns and lets the user select rows by clicking them.
    private void configureTable() {
        TableColumn<Debt, String> nameColumn = textColumn("Name", Debt::getLabel);
        TableColumn<Debt, String> balanceColumn = textColumn("Balance", debt -> money(debt.getBalance()));
        TableColumn<Debt, String> aprColumn = textColumn("APR", debt ->
                String.format(Locale.US, "%.2f%%", debt.getAPRDecimal() * 100));
        TableColumn<Debt, String> paymentColumn = textColumn("Monthly", debt -> money(debt.getMinimumPayment()));
        TableColumn<Debt, String> dateColumn = textColumn("Payoff Date", debt ->
                formatDate(debt.projectPayoffTimeline(debt.getMinimumPayment())));
        TableColumn<Debt, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(75);

        // Every row gets its own Edit and Delete buttons.
        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button edit = new Button("✎");
            private final Button delete = new Button("×");
            private final HBox buttons = new HBox(4, edit, delete);
            {
                buttons.setAlignment(Pos.CENTER);
                edit.setStyle("-fx-background-color: #F0F1F4;-fx-text-fill: " + DARK + ";-fx-background-radius: 5;");
                delete.setStyle("-fx-background-color: " + PINK + ";-fx-text-fill: white;-fx-font-weight: bold;-fx-background-radius: 5;");
                edit.setOnAction(e -> {
                    Debt debt = getTableRow().getItem();
                    if (debt != null) editDebt(debt);
                    e.consume();
                });
                delete.setOnAction(e -> {
                    Debt debt = getTableRow().getItem();
                    if (debt != null) deleteDebt(debt);
                    e.consume();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });

        table.getColumns().addAll(nameColumn, balanceColumn, aprColumn,
                paymentColumn, dateColumn, actionsColumn);
        table.setItems(debts);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setRowFactory(tv -> {
            TableRow<Debt> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldItem, newItem) -> updateRowAppearance(row));
            row.emptyProperty().addListener((obs, oldValue, newValue) -> updateRowAppearance(row));
            row.setOnMouseClicked(event -> {
                if (event.getButton() != MouseButton.PRIMARY || row.isEmpty()) return;

                // Don't change the selection when Edit or Delete was clicked.
                Node target = event.getPickResult().getIntersectedNode();
                while (target != null) {
                    if (target instanceof Button) return;
                    target = target.getParent();
                }
                Debt debt = row.getItem();
                if (selectedDebts.contains(debt)) selectedDebts.remove(debt);
                else selectedDebts.add(debt);
                refreshEverything();
            });
            return row;
        });
    }

    // Keeps selected debt rows pink even after the table updates.
    private void updateRowAppearance(TableRow<Debt> row) {
        if (row.isEmpty() || row.getItem() == null) {
            row.setStyle("");
            return;
        }
        String background = selectedDebts.contains(row.getItem()) ? SELECTED_PINK : WHITE;
        row.setStyle(
                "-fx-background-color: " + background + ";" +
                        "-fx-control-inner-background: " + background + ";" +
                        "-fx-selection-bar: " + background + ";" +
                        "-fx-selection-bar-non-focused: " + background + ";" +
                        "-fx-table-cell-border-color: #E8DCE2;");
        for (Node node : row.lookupAll(".table-cell")) {
            node.setStyle("-fx-background-color: " + background + ";-fx-text-fill: " + DARK + ";");
        }
    }

    // Builds the graph card using the shared chart legend markers.
    private VBox createGraphCard() {
        VBox card = UIComponents.createCard();
        graphTitle.setStyle("-fx-font-size: 14px;-fx-font-weight: bold;-fx-text-fill: " + DARK + ";");
        graphTitle.setWrapText(true);
        currentLegend.setStyle("-fx-font-size: 10px;-fx-text-fill: " + DARK + ";");
        extraLegend.setStyle("-fx-font-size: 10px;-fx-text-fill: " + DARK + ";");
        HBox currentLegendRow = new HBox(7, ChartUtils.createLegendMarker(PINK), currentLegend);
        currentLegendRow.setAlignment(Pos.CENTER_LEFT);
        extraLegendRow.getChildren().addAll(ChartUtils.createLegendMarker(DARK), extraLegend);
        extraLegendRow.setSpacing(7);
        extraLegendRow.setAlignment(Pos.CENTER_LEFT);
        VBox legend = new VBox(5, currentLegendRow, extraLegendRow);
        legend.setAlignment(Pos.TOP_RIGHT);
        HBox heading = new HBox(8, graphTitle, legend);
        heading.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(graphTitle, Priority.ALWAYS);
        graphMessage.setStyle("-fx-font-size: 10px;-fx-text-fill: " + GRAY + ";");
        graphMessage.setWrapText(true);
        chart.setMinHeight(170);
        chart.setPrefHeight(225);
        chart.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(chart, Priority.ALWAYS);
        card.getChildren().addAll(heading, graphMessage, chart);
        return card;
    }

    // Sets the debt-specific axes, then uses shared chart styling.
    private void configureChart() {
        xAxis.setLabel(null);
        yAxis.setLabel(null);
        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(false);
        xAxis.setMinorTickVisible(false);
        xAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number value) { return String.valueOf(value.intValue()); }
            @Override
            public Number fromString(String text) { return Integer.parseInt(text); }
        });
        yAxis.setForceZeroInRange(true);
        yAxis.setMinorTickVisible(false);
        yAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number value) {
                double amount = value.doubleValue();
                if (amount >= 1000) return String.format(Locale.US, "$%.0fK", amount / 1000);
                return String.format(Locale.US, "$%.0f", amount);
            }
            @Override
            public Number fromString(String text) { return 0; }
        });
        ChartUtils.styleLineChart(chart);
    }

    // Shows the payoff date, interest, and total for the current plan.
    private VBox createSummaryCard() {
        VBox card = UIComponents.createCard();
        Label currentHeading = new Label("Current Plan");
        currentHeading.setMaxWidth(Double.MAX_VALUE);
        currentHeading.setAlignment(Pos.CENTER);
        currentHeading.setStyle("-fx-font-size: 14px;-fx-font-weight: bold;-fx-text-fill: " + PINK + ";");
        Region divider = new Region();
        divider.setPrefHeight(1.5);
        divider.setMinHeight(1.5);
        divider.setStyle("-fx-background-color: #DDA8BF;");
        styleSummaryValue(payoffDate);
        styleSummaryValue(totalInterest);
        styleSummaryValue(totalPaid);
        VBox pinkPanel = new VBox(9, currentHeading, divider,
                centeredResult("Payoff Date", payoffDate),
                centeredResult("Total Interest", totalInterest),
                centeredResult("Total Amount Paid", totalPaid));
        pinkPanel.setPadding(new Insets(13));
        pinkPanel.setAlignment(Pos.TOP_CENTER);
        pinkPanel.setStyle(
                "-fx-background-color: " + SOFT_PINK + ";-fx-background-radius: 12;" +
                        "-fx-border-color: #E8B5CA;-fx-border-width: 1.5;-fx-border-radius: 12;");
        pinkPanel.setMinHeight(Region.USE_PREF_SIZE);
        pinkPanel.setMaxHeight(Region.USE_PREF_SIZE);
        card.getChildren().addAll(UIComponents.sectionTitle("Payoff Summary"), pinkPanel);
        return card;
    }

    // Puts a small label above a bigger result.
    private VBox centeredResult(String title, Label value) {
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 11px;-fx-font-weight: normal;-fx-text-fill: #626773;");
        value.setAlignment(Pos.CENTER);
        value.setMaxWidth(Double.MAX_VALUE);
        VBox result = new VBox(3, label, value);
        result.setAlignment(Pos.CENTER);
        return result;
    }

    // Makes real payoff amounts bold but leaves empty dashes gray.
    private void styleSummaryValue(Label label) {
        label.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;-fx-text-fill: " + DARK + ";");
        label.textProperty().addListener((obs, oldText, newText) -> {
            if ("—".equals(newText)) {
                label.setStyle("-fx-font-size: 16px;-fx-font-weight: normal;-fx-text-fill: #A0A5AE;");
            } else {
                label.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;-fx-text-fill: " + DARK + ";");
            }
        });
    }

    // Builds the What If card for trying a higher monthly payment.
    private VBox createWhatIfCard() {
        VBox card = UIComponents.createCard();
        card.setSpacing(7);
        configureSwitch();
        HBox heading = new HBox(9, switchTrack, UIComponents.sectionTitle("What If?"));
        heading.setAlignment(Pos.CENTER_LEFT);
        Label description = new Label("Compare different payment options.");
        description.setWrapText(true);
        description.setStyle("-fx-font-size: 10px;-fx-text-fill: " + GRAY + ";");
        UIComponents.styleTextField(extraField);
        styleComparisonValue(comparisonDate);
        styleComparisonValue(comparisonInterest);
        styleComparisonValue(comparisonTotal);
        styleSavingsLabel(monthsSaved);
        styleSavingsLabel(interestSaved);
        VBox comparison = new VBox(13,
                comparisonResult("Payoff Date", comparisonDate, monthsSaved),
                comparisonResult("Total Interest", comparisonInterest, interestSaved),
                comparisonResult("Total Paid", comparisonTotal, null));
        comparison.setPadding(new Insets(10));
        comparison.setStyle(
                "-fx-background-color: " + GREEN_BG + ";-fx-background-radius: 12;" +
                        "-fx-border-color: " + GREEN_BORDER + ";-fx-border-width: 1.5;-fx-border-radius: 12;");
        UIComponents.stylePinkButton(updateButton);
        updateButton.setMaxWidth(Double.MAX_VALUE);
        card.getChildren().addAll(heading, description,
                UIComponents.fieldLabel("Extra Monthly Payment ($)"), extraField,
                UIComponents.fieldLabel("Comparison"), comparison, updateButton);
        return card;
    }

    // Lines up each What If result with its savings message.
    private HBox comparisonResult(String title, Label value, Label savings) {
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 10px;-fx-text-fill: #626773;");
        label.setWrapText(true);
        VBox resultValues = new VBox(3);
        resultValues.getChildren().add(value);
        if (savings != null) resultValues.getChildren().add(savings);
        resultValues.setAlignment(Pos.TOP_LEFT);
        HBox row = new HBox(6, label, resultValues);
        row.setAlignment(Pos.TOP_LEFT);
        label.setMinWidth(62);
        label.setPrefWidth(62);
        HBox.setHgrow(resultValues, Priority.ALWAYS);
        return row;
    }

    // Keeps the comparison numbers easy to see.
    private void styleComparisonValue(Label label) {
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: " + DARK + ";");
    }

    // Uses green for the months and interest saved.
    private void styleSavingsLabel(Label label) {
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 10px;-fx-text-fill: " + GREEN_TEXT + ";");
    }

    // Lets the user turn the What If comparison on and off.
    private void configureSwitch() {
        switchTrack.setPrefSize(45, 24);
        switchTrack.setMinSize(45, 24);
        switchTrack.setMaxSize(45, 24);
        switchKnob.setFill(javafx.scene.paint.Color.WHITE);
        switchTrack.getChildren().add(switchKnob);
        switchTrack.setOnMouseClicked(e -> {
            whatIfEnabled = !whatIfEnabled;
            updateSwitch();
            refreshEverything();
        });
        updateSwitch();
    }

    // Moves the switch and enables or disables the extra-payment controls.
    private void updateSwitch() {
        switchTrack.setStyle("-fx-background-color: " +
                (whatIfEnabled ? PINK : "#AEB4BF") + ";-fx-background-radius: 20;-fx-cursor: hand;");
        StackPane.setAlignment(switchKnob, whatIfEnabled ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        StackPane.setMargin(switchKnob, new Insets(0, 4, 0, 4));
        extraField.setDisable(!whatIfEnabled);
        updateButton.setDisable(!whatIfEnabled);
        extraLegendRow.setVisible(whatIfEnabled);
        extraLegendRow.setManaged(whatIfEnabled);
    }

    // Connects the buttons to the actions they should perform.
    private void configureActions() {
        saveButton.setOnAction(e -> saveDebt());
        clearButton.setOnAction(e -> clearForm());
        allDebtsButton.setOnAction(e -> {
            if (debts.isEmpty()) return;
            if (selectedDebts.size() == debts.size()) selectedDebts.clear();
            else {
                selectedDebts.clear();
                selectedDebts.addAll(debts);
            }
            refreshEverything();
        });
        updateButton.setOnAction(e -> refreshEverything());
        extraField.setOnAction(e -> refreshEverything());
    }

    // Checks the form before adding a debt or saving changes.
    private void saveDebt() {
        try {
            String name = nameField.getText().trim();
            double balance = parse(balanceField.getText());
            double aprPercent = parse(aprField.getText());
            double payment = parse(paymentField.getText());
            if (name.isEmpty()) throw new IllegalArgumentException("Please enter a debt name.");
            if (balance <= 0) throw new IllegalArgumentException("Balance must be greater than zero.");
            if (aprPercent < 0 || aprPercent > 100)
                throw new IllegalArgumentException("APR must be between 0 and 100.");
            if (payment <= 0) throw new IllegalArgumentException("Monthly payment must be greater than zero.");
            double aprDecimal = aprPercent / 100.0;
            if (payment <= balance * aprDecimal / 12.0)
                throw new IllegalArgumentException("Monthly payment must exceed monthly interest.");

            // A new debt uses Andrew's Debt model; an edit updates the existing one.
            if (editingDebt == null) {
                Debt debt = new Debt(balance, aprDecimal, payment, name);
                debts.add(debt);
                selectedDebts.add(debt);
            } else {
                editingDebt.setLabel(name);
                editingDebt.setBalance(balance);
                editingDebt.setAPRDecimal(aprDecimal);
                editingDebt.setMinimumPayment(payment);
            }
            saveDebts();
            clearForm();
            refreshEverything();
        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric amounts.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // Fills in the form with the debt the user wants to edit.
    private void editDebt(Debt debt) {
        editingDebt = debt;
        nameField.setText(debt.getLabel());
        balanceField.setText(String.valueOf(debt.getBalance()));
        aprField.setText(String.valueOf(debt.getAPRDecimal() * 100));
        paymentField.setText(String.valueOf(debt.getMinimumPayment()));
        saveButton.setText("Save Changes");
    }

    // Clears the fields and switches back to Add Debt mode.
    private void clearForm() {
        editingDebt = null;
        nameField.clear();
        balanceField.clear();
        aprField.clear();
        paymentField.clear();
        saveButton.setText("+ Add Debt");
    }

    // Asks before removing a debt so it isn't deleted by accident.
    private void deleteDebt(Debt debt) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + debt.getLabel() + "?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Delete Debt");
        if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) return;
        debts.remove(debt);
        selectedDebts.remove(debt);
        if (editingDebt == debt) clearForm();
        saveDebts();
        refreshEverything();
    }

    // Combines the selected debts into one payoff projection.
    private CombinedProjection calculateProjection(List<Debt> chosen, double extraPayment) {
        if (chosen.isEmpty()) return CombinedProjection.empty();

        // The extra payment goes to the debt with the highest APR.
        Debt extraTarget = null;
        for (Debt debt : chosen) {
            if (extraTarget == null || debt.getAPRDecimal() > extraTarget.getAPRDecimal())
                extraTarget = debt;
        }
        int longestTimeline = 0;
        double interestSum = 0;
        double totalSum = 0;
        List<DebtPayoffResult> results = new ArrayList<>();
        for (Debt debt : chosen) {
            double payment = debt.getMinimumPayment();
            if (debt == extraTarget) payment += extraPayment;

            // Andrew's model calculates each debt's payoff timeline.
            DebtPayoffResult result = debt.projectPayoffTimeline(payment);
            if (!result.isPossible()) return CombinedProjection.empty();
            results.add(result);
            longestTimeline = Math.max(longestTimeline, result.getMonthsToPayoff());
            interestSum += result.getTotalInterestPaid();
            totalSum += debt.getBalance() + result.getTotalInterestPaid();
        }

        // Adds the balances together so the graph can show one combined line.
        ArrayList<Double> combinedBalances = new ArrayList<>();
        double startingBalance = 0;
        for (Debt debt : chosen) startingBalance += debt.getBalance();
        combinedBalances.add(startingBalance);
        for (int month = 0; month < longestTimeline; month++) {
            double balanceThisMonth = 0;
            for (DebtPayoffResult result : results) {
                ArrayList<Double> balances = result.getMonthlyBalance();
                if (month < balances.size()) balanceThisMonth += balances.get(month);
            }
            combinedBalances.add(balanceThisMonth);
        }
        return new CombinedProjection(true, longestTimeline, interestSum, totalSum, combinedBalances);
    }

    // Updates the selected count, summary cards, and graph together.
    private void refreshEverything() {
        selectedDebts.retainAll(debts);
        int selected = selectedDebts.size();
        int total = debts.size();
        selectedCount.setText(selected + " of " + total + " Debts Selected");
        allDebtsButton.setText(total > 0 && selected == total ? "✓ All Debts" : "All Debts");
        table.getSelectionModel().clearSelection();
        table.refresh();

        // Reapplies the pink highlighting after JavaFX redraws the rows.
        Platform.runLater(() -> {
            for (Node node : table.lookupAll(".table-row-cell")) {
                if (node instanceof TableRow<?>) {
                    @SuppressWarnings("unchecked")
                    TableRow<Debt> row = (TableRow<Debt>) node;
                    updateRowAppearance(row);
                }
            }
        });
        chart.getData().clear();
        if (selectedDebts.isEmpty()) {
            graphTitle.setText("Payoff Projection");
            graphMessage.setText("Select one or more debts to view a projection.");
            currentLegend.setText("Current Payment");
            extraLegend.setText("+ Extra Payment");
            clearSummary();
            clearComparison();
            return;
        }

        // Keeps the chosen debts in the same order as the table.
        List<Debt> chosen = new ArrayList<>();
        for (Debt debt : debts) if (selectedDebts.contains(debt)) chosen.add(debt);
        if (chosen.size() == 1)
            graphTitle.setText("Payoff Projection - " + chosen.get(0).getLabel());
        else graphTitle.setText("Payoff Projection - " + chosen.size() + " Debts");
        graphMessage.setText("");
        double combinedPayment = 0;
        for (Debt debt : chosen) combinedPayment += debt.getMinimumPayment();
        currentLegend.setText("Current Payment (" + moneyWhole(combinedPayment) + ")");

        // First calculate the plan with only the regular monthly payments.
        CombinedProjection current = calculateProjection(chosen, 0);
        if (!current.possible) {
            clearSummary();
            clearComparison();
            graphMessage.setText("The current payment is too low to pay off a debt.");
            return;
        }
        payoffDate.setText(formatDate(current.months));
        totalInterest.setText(money(current.interest));
        totalPaid.setText(money(current.totalPaid));

        // Then calculate the optional extra-payment plan.
        CombinedProjection alternative = null;
        if (whatIfEnabled) {
            try {
                double extra = parse(extraField.getText());
                if (extra < 0) throw new NumberFormatException();
                alternative = calculateProjection(chosen, extra);
                extraLegend.setText("+ " + moneyWhole(extra) + " Extra (" +
                        moneyWhole(combinedPayment + extra) + ")");
            } catch (NumberFormatException ex) {
                clearComparison();
                graphMessage.setText("Enter a valid extra monthly payment.");
            }
        }

        // Sets the year range based on how long payoff takes.
        int maxMonths = current.months;
        if (alternative != null && alternative.possible)
            maxMonths = Math.max(maxMonths, alternative.months);
        int startingYear = LocalDate.now().getYear();
        int endingYear = LocalDate.now().plusMonths(maxMonths).getYear();
        xAxis.setLowerBound(startingYear);
        xAxis.setUpperBound(Math.max(startingYear + 1, endingYear + 1));
        xAxis.setTickUnit(1);

        // Uses the shared chart helper to color the current plan pink.
        XYChart.Series<Number, Number> currentSeries = createSeries("Current Payment", current);
        chart.getData().add(currentSeries);
        ChartUtils.colorSeries(currentSeries, PINK);
        if (!whatIfEnabled || alternative == null || !alternative.possible) {
            clearComparison();
            return;
        }
        comparisonDate.setText(formatDate(alternative.months));
        comparisonInterest.setText(money(alternative.interest));
        comparisonTotal.setText(money(alternative.totalPaid));
        int difference = Math.max(0, current.months - alternative.months);
        double savings = Math.max(0, current.interest - alternative.interest);
        monthsSaved.setText("(" + difference + " months sooner)");
        interestSaved.setText("(" + money(savings) + " less)");

        // Adds the darker line for the extra-payment plan.
        XYChart.Series<Number, Number> extraSeries = createSeries("Extra Payment", alternative);
        chart.getData().add(extraSeries);
        ChartUtils.colorSeries(extraSeries, DARK);
    }

    // Picks a few monthly balances to show as points on the graph.
    private XYChart.Series<Number, Number> createSeries(String name, CombinedProjection projection) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        int size = projection.balances.size();
        int step = Math.max(1, (int) Math.ceil((size - 1) / 8.0));
        for (int i = 0; i < size; i += step)
            addGraphPoint(series, i, projection.balances.get(i));
        if ((size - 1) % step != 0)
            addGraphPoint(series, size - 1, projection.balances.get(size - 1));
        return series;
    }

    // Converts a month number into a position on the year axis.
    private void addGraphPoint(XYChart.Series<Number, Number> series, int month, double balance) {
        LocalDate date = LocalDate.now().plusMonths(month);
        double yearPosition = date.getYear() + (date.getMonthValue() - 1) / 12.0;
        series.getData().add(new XYChart.Data<>(yearPosition, balance));
    }

    // Clears old numbers when there isn't a valid current plan.
    private void clearSummary() {
        payoffDate.setText("—");
        totalInterest.setText("—");
        totalPaid.setText("—");
    }

    // Clears old numbers when the What If plan isn't available.
    private void clearComparison() {
        comparisonDate.setText("—");
        comparisonInterest.setText("—");
        comparisonTotal.setText("—");
        monthsSaved.setText("");
        interestSaved.setText("");
    }

    // Writes each debt's details to the local save file.
    private void saveDebts() {
        try {
            Files.createDirectories(SAVE_FILE.getParent());
            Properties properties = new Properties();
            properties.setProperty("count", String.valueOf(debts.size()));
            for (int i = 0; i < debts.size(); i++) {
                Debt debt = debts.get(i);
                String prefix = "debt." + i + ".";
                properties.setProperty(prefix + "name", debt.getLabel());
                properties.setProperty(prefix + "balance", String.valueOf(debt.getBalance()));
                properties.setProperty(prefix + "apr", String.valueOf(debt.getAPRDecimal() * 100));
                properties.setProperty(prefix + "payment", String.valueOf(debt.getMinimumPayment()));
            }
            try (OutputStream output = Files.newOutputStream(SAVE_FILE)) {
                properties.store(output, "Trajectory Saved Debts");
            }
        } catch (Exception ex) {
            showError("Unable to save debts: " + ex.getMessage());
        }
    }

    // Reads saved debts when the user opens the page again.
    private void loadDebts() {
        if (!Files.exists(SAVE_FILE)) return;
        try {
            Properties properties = new Properties();
            try (InputStream input = Files.newInputStream(SAVE_FILE)) {
                properties.load(input);
            }
            int count = Integer.parseInt(properties.getProperty("count", "0"));
            for (int i = 0; i < count; i++) {
                String prefix = "debt." + i + ".";
                String name = properties.getProperty(prefix + "name", "Debt");
                double balance = Double.parseDouble(properties.getProperty(prefix + "balance", "0"));
                double aprPercent = Double.parseDouble(properties.getProperty(prefix + "apr", "0"));
                double payment = Double.parseDouble(properties.getProperty(prefix + "payment", "0"));
                debts.add(new Debt(balance, aprPercent / 100.0, payment, name));
            }
        } catch (Exception ex) {
            showError("Unable to load saved debts: " + ex.getMessage());
        }
    }

    // Removes dollar signs and commas before reading an amount.
    private static double parse(String text) {
        double value = Double.parseDouble(text.replace("$", "").replace(",", "")
                .replace("%", "").trim());
        if (!Double.isFinite(value)) throw new NumberFormatException();
        return value;
    }

    // Keeps dollar amounts formatted the same way on this page.
    private static String money(double amount) {
        return String.format(Locale.US, "$%,.2f", amount);
    }

    private static String moneyWhole(double amount) {
        return String.format(Locale.US, "$%,.0f", amount);
    }

    // Shows the payoff month and year from Andrew's calculation.
    private static String formatDate(DebtPayoffResult result) {
        if (!result.isPossible()) return "Not Possible";
        return formatDate(result.getMonthsToPayoff());
    }

    private static String formatDate(int months) {
        return LocalDate.now().plusMonths(months)
                .format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.US));
    }

    // Shows a popup when the user enters something invalid.
    private static void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

    // Makes a table column using the text we want for each debt.
    private static <T> TableColumn<T, String> textColumn(String title, Function<T, String> getter) {
        TableColumn<T, String> column = new TableColumn<>(title);
        column.setCellValueFactory(data -> new SimpleStringProperty(getter.apply(data.getValue())));
        return column;
    }

    // Keeps the numbers and graph balances for a combined payoff plan.
    private static class CombinedProjection {
        final boolean possible;
        final int months;
        final double interest;
        final double totalPaid;
        final ArrayList<Double> balances;

        CombinedProjection(boolean possible, int months, double interest,
                           double totalPaid, ArrayList<Double> balances) {
            this.possible = possible;
            this.months = months;
            this.interest = interest;
            this.totalPaid = totalPaid;
            this.balances = balances;
        }

        // Used when a payoff plan can't be calculated.
        static CombinedProjection empty() {
            return new CombinedProjection(false, 0, 0, 0, new ArrayList<>());
        }
    }
}