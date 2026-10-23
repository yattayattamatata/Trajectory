package com.trajectory.app.ui.components;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.geometry.Pos;

// Keeps the charts looking the same throughout the app.
public final class ChartUtils {

    // We don't need to create an object to use these methods.
    private ChartUtils() {
    }

    // Sets up the basic look of a line chart.
    public static void styleLineChart(LineChart<Number, Number> chart) {

        // Turns off animations so the graph updates right away.
        chart.setAnimated(false);

        // Shows the little circles on the graph lines.
        chart.setCreateSymbols(true);

        // Hides the default legend since we're making our own.
        chart.setLegendVisible(false);

        // Shows the lines going across the graph.
        chart.setHorizontalGridLinesVisible(true);

        // Hides the lines going up and down.
        chart.setVerticalGridLinesVisible(false);

        // Removes the extra background shading.
        chart.setAlternativeRowFillVisible(false);
        chart.setAlternativeColumnFillVisible(false);

        // Makes the chart background clear.
        chart.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;"
        );
    }

    // Makes the little line and circle shown next to the graph labels.
    public static HBox createLegendMarker(String color) {

        // Makes a short line using the color we choose.
        Line line = new Line(0, 0, 22, 0);
        line.setStroke(Color.web(color));
        line.setStrokeWidth(2.5);

        // Adds a small circle in the same color.
        Circle circle = new Circle(4);
        circle.setFill(Color.web(color));

        // Puts the circle in the middle of the line.
        StackPane marker = new StackPane(line, circle);
        marker.setPrefWidth(24);

        // Centers everything so it looks right next to the label.
        HBox wrapper = new HBox(marker);
        wrapper.setAlignment(Pos.CENTER);

        return wrapper;
    }

    // Changes the color of a graph line and its dots.
    public static void colorSeries(
            XYChart.Series<Number, Number> series,
            String color
    ) {

        // This runs when the graph is ready to be styled.
        Runnable apply = () -> {

            // Gets the line from the graph.
            Node seriesNode = series.getNode();

            // Makes sure the line exists before changing it.
            if (seriesNode != null) {

                Node line = seriesNode.lookup(".chart-series-line");

                if (line != null) {

                    // Changes the line's color and thickness.
                    line.setStyle(
                            "-fx-stroke: " + color + ";" +
                                    "-fx-stroke-width: 2.5px;"
                    );
                }
            }

            // Goes through each dot on the graph.
            for (XYChart.Data<Number, Number> point : series.getData()) {

                Node symbol = point.getNode();

                if (symbol != null) {

                    // Makes the dot match the line with a white center.
                    symbol.setStyle(
                            "-fx-background-color: " + color + ", white;" +
                                    "-fx-background-insets: 0, 2;" +
                                    "-fx-background-radius: 100;" +
                                    "-fx-padding: 5px;"
                    );
                }
            }
        };

        // Styles the line once JavaFX has created it.
        series.nodeProperty().addListener(
                (obs, oldNode, newNode) -> Platform.runLater(apply)
        );

        // Styles the dots once JavaFX has created them.
        for (XYChart.Data<Number, Number> point : series.getData()) {

            point.nodeProperty().addListener(
                    (obs, oldNode, newNode) -> Platform.runLater(apply)
            );
        }

        // Runs the styling after the graph loads.
        Platform.runLater(apply);
    }
}