/**
 * The Horse class models the properties and behaviors of a horse in a horse racing simulation.
 * This class maintains various attributes of a horse, such as its name, the symbol used to represent it,
 * its confidence level, and the distance it has traveled. Additional features include methods to simulate
 * the horse's movement in a race, check its fallen status, and reset its position.
 * 
 * The class provides methods to:
 * - Retrieve and update the horse's name, symbol, and confidence level.
 * - Monitor and update the distance traveled by the horse.
 * - Check if the horse has fallen and manage its fallen state.
 * - Reset the horse's position to the starting point of the race.
 * - Simulate the forward movement of the horse considering its current state.
 * 
 *
 * @author Haidar Shawki
 * @version 1.0 2024-04-18
 */



 import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class Horse {
    private String name;
    private Image image;
    private double confidence;
    private int distanceTravelled;
    private boolean fallen;
    private double lastRaceTime; // Time taken in the last race in seconds

    /**
     * Constructor for objects of class Horse
     */
    public Horse(String name, String imagePath, double confidence) {
        this.name = name;
        this.confidence = confidence;
        this.distanceTravelled = 0;
        this.fallen = false;
        try {
            // Using FileInputStream to load the image
            FileInputStream inputstream = new FileInputStream(imagePath);
            this.image = new Image(inputstream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading image: " + imagePath);
            // Optionally throw a runtime exception or handle the error as required
            throw new RuntimeException("Image file not found: " + imagePath);
        }
    }
    // Getter for image to use in ImageView
    public Image getImage() {
        return this.image;
    }

    // Other getters and setters
    public String getName() {
        return this.name;
    }

        // Increment the distance traveled by the horse
        public void incrementDistance(int distance) {
            this.distanceTravelled += distance; // Add the distance moved to the total distance
        }


    public void setConfidence(double newConfidence) {
        this.confidence = newConfidence;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public void fall() {
        this.fallen = true;
    }

    public boolean hasFallen() {
        return this.fallen;
    }

    public void goBackToStart() {
        this.distanceTravelled = 0;
    }

    public void moveForward() {
        if (!this.fallen) {
            this.distanceTravelled++;
        }
    }

    public void setLastRaceTime(double lastRaceTime) {
        this.lastRaceTime = lastRaceTime;
    }

    public double getLastRaceTime() {
        return this.lastRaceTime;
    }
}
