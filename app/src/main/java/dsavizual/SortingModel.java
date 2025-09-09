package dsavizual;

import java.util.Random;

/**
 * The SortingModel class represents the data model for our visualizer.
 * It holds the array of numbers and methods to manipulate the data.
 */
public class SortingModel {

    // The size of the array and the range of values.
    // Making this public so it can be accessed from the App class.
    public final int ARRAY_SIZE = 50;
    
    // The array that holds the data.
    private int[] data;

    // A random number generator to create new data sets.
    private final Random random = new Random();

    /**
     * Constructs a new SortingModel and initializes it with random data.
     */
    public SortingModel() {
        data = new int[ARRAY_SIZE];
        initializeRandomData();
    }

    /**
     * Fills the array with a new set of random numbers.
     */
    public void initializeRandomData() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            this.data[i] = random.nextInt(ARRAY_SIZE) + 1;
        }
    }

    /**
     * Gets the data array.
     * @return The array of integers.
     */
    public int[] getData() {
        return data;
    }
}
