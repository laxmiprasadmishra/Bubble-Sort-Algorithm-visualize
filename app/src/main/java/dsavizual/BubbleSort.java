package dsavizual;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * The BubbleSort class implements the Bubble Sort algorithm and visualizes it
 * by creating a JavaFX Timeline animation.
 */
public class BubbleSort {
    // The data model containing the array of numbers to sort.
    private SortingModel model;
    
    // The array of Rectangle nodes to animate.
    private Rectangle[] bars;

    /**
     * Constructs a new BubbleSort visualizer.
     *
     * @param model The data model containing the array to sort.
     * @param bars The array of JavaFX Rectangle objects to visualize.
     */
    public BubbleSort(SortingModel model, Rectangle[] bars) {
        this.model = model;
        this.bars = bars;
    }

    /**
     * Creates and returns a Timeline that visualizes the Bubble Sort algorithm.
     *
     * @return The JavaFX Timeline object.
     */
    public Timeline sort() {
        Timeline timeline = new Timeline();
        int[] data = model.getData();
        int n = data.length;
        
        Duration duration = Duration.ZERO;
        final Duration stepDuration = Duration.millis(15); // Adjust this value to change animation speed.
        
        // This is the core Bubble Sort algorithm.
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // KeyFrame for highlighting the two bars being compared.
                duration = duration.add(stepDuration);
                final int finalJ = j;
                timeline.getKeyFrames().add(new KeyFrame(duration, e -> {
                    bars[finalJ].setStyle("-fx-fill: red;");
                    bars[finalJ + 1].setStyle("-fx-fill: red;");
                }));

                // If the current element is greater than the next one, swap them.
                if (data[j] > data[j + 1]) {
                    // Swap the values in the data model.
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    
                    // Create an animation to swap the positions of the two bars.
                    duration = duration.add(stepDuration);
                    
                    // We need to use final variables in the KeyFrame lambda expression.
                    final double startX = bars[finalJ].getX();
                    final double endX = bars[finalJ + 1].getX();

                    // Animate the horizontal movement.
                    timeline.getKeyFrames().add(new KeyFrame(duration, 
                        new KeyValue(bars[finalJ].xProperty(), endX),
                        new KeyValue(bars[finalJ + 1].xProperty(), startX)
                    ));
                    
                    // Also swap the references in the bars array for the next iteration.
                    Rectangle tempBar = bars[finalJ];
                    bars[finalJ] = bars[finalJ + 1];
                    bars[finalJ + 1] = tempBar;
                }
                
                // KeyFrame to reset the colors of the bars after comparison.
                duration = duration.add(stepDuration);
                timeline.getKeyFrames().add(new KeyFrame(duration, e -> {
                    bars[finalJ].setStyle("-fx-fill: blue;");
                    bars[finalJ + 1].setStyle("-fx-fill: blue;");
                }));
            }
        }
        
        // Final KeyFrame to highlight all bars as sorted (green).
        duration = duration.add(Duration.millis(50));
        timeline.getKeyFrames().add(new KeyFrame(duration, e -> {
            for (Rectangle bar : bars) {
                bar.setStyle("-fx-fill: green;");
            }
        }));

        return timeline;
    }
}
