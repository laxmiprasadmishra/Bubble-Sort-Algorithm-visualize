package dsavizual;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The main application class for the DSA Visualizer.
 * Sets up the main UI, manages the data model, and triggers the visualization.
 */
public class App extends Application {

    private SortingModel model = new SortingModel();
    private Rectangle[] bars;
    private Pane chartPane = new Pane();
    private Button newArrayButton;
    private Button startSortButton;

    // The width and height of our application window.
    private final double WINDOW_WIDTH = 800;
    private final double WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Create the chart area where the bars will be displayed.
        chartPane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT * 0.8);
        root.setCenter(chartPane);

        // Create the controls panel at the bottom.
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10, 0, 0, 0));

        // Create the buttons.
        newArrayButton = new Button("New Array");
        startSortButton = new Button("Start Sort");
        controls.getChildren().addAll(newArrayButton, startSortButton);
        root.setBottom(controls);

        // Set up the button actions.
        newArrayButton.setOnAction(e -> handleNewArray());
        startSortButton.setOnAction(e -> handleStartSort());

        // Initialize the UI with the initial data.
        initializeUI();

        // Set up the scene and show the stage.
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("DSA Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes the user interface by drawing the bars for the current data.
     */
    private void initializeUI() {
        chartPane.getChildren().clear();
        int[] data = model.getData();
        bars = new Rectangle[data.length];
        double barWidth = WINDOW_WIDTH / data.length;
        double chartHeight = WINDOW_HEIGHT * 0.8;

        for (int i = 0; i < data.length; i++) {
            // The height of the bar is based on the data value.
            // The bar's height is scaled to fit within the chart area.
            double barHeight = (double) data[i] / model.ARRAY_SIZE * chartHeight;
            Rectangle bar = new Rectangle(barWidth, barHeight);
            
            // Set the initial position of the bar.
            // The x-coordinate is based on its index.
            // The y-coordinate is calculated to anchor the bar to the bottom of the pane.
            bar.setX(i * barWidth);
            bar.setY(chartPane.getPrefHeight() - barHeight);
            
            // Set the default color and add it to the pane.
            bar.setStyle("-fx-fill: blue;");
            bars[i] = bar;
            chartPane.getChildren().add(bar);
        }
    }
    
    /**
     * Handles the "New Array" button click.
     * Generates new random data and updates the UI.
     */
    private void handleNewArray() {
        model.initializeRandomData();
        initializeUI();
    }
    
    /**
     * Handles the "Start Sort" button click.
     * Creates and plays the Bubble Sort animation.
     */
    private void handleStartSort() {
        // Disable buttons to prevent new actions during the animation
        newArrayButton.setDisable(true);
        startSortButton.setDisable(true);
        
        // Create the BubbleSort instance and get the timeline.
        BubbleSort bubbleSort = new BubbleSort(model, bars);
        Timeline timeline = bubbleSort.sort();
        
        // Play the timeline and re-enable buttons after it finishes.
        timeline.play();
        timeline.setOnFinished(e -> {
            newArrayButton.setDisable(false);
            startSortButton.setDisable(false);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
