import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.image.ImageView;

public class Race {
    int raceLength;  // This is in pixels for simplicity, adjust if in different units like meters
    Horse[] horses = new Horse[3];
    ImageView[] horseViews = new ImageView[3];
    Timeline timeline;
    Stage raceStage; // Race window stage
    Stage primaryStage; // Main setup window stage
    Random rand = new Random();
    private long startTime;  // To capture the start time of the race
    private String winningHorse = null;
    private Points pointsManager;  // Use Points class for managing points
    private RaceTrack raceTrack;  // Instance of RaceTrack class
    private RaceStatistics raceStatistics; // Instance of RaceStatistics class

    public Race(int distance, Pane raceTrackPane, Stage raceStage, Stage primaryStage, Points pointsManager) {
        this.raceLength = distance * 10;
        this.raceStage = raceStage;
        this.primaryStage = primaryStage;
        this.pointsManager = pointsManager;
        this.raceTrack = new RaceTrack(raceTrackPane, raceLength);
        this.startTime = System.currentTimeMillis(); // Initialize start time
        this.raceStatistics = new RaceStatistics(); // Initialize the race statistics
        startRace();
    }

    public void addHorse(Horse horse, int lane) {
        if (lane < 1 || lane > 3) {
            System.out.println("Invalid lane number");
            return;
        }
        horses[lane - 1] = horse;
        displayHorse(horse, lane);
    }


    private void displayHorse(Horse horse, int lane) {
        if (horse == null) return;
        ImageView horseView = new ImageView(horse.getImage()); // Assuming horse.getImage() returns an Image object
        horseView.setX(10); // Starting X position
    
        // Adjust the Y position and size of the image
        horseView.setY(lane * 50); // Adjusting Y position based on lane to avoid overlap
        horseView.setFitWidth(100); // Set the width of the image
        horseView.setFitHeight(50); // Set the height of the image
        horseView.setPreserveRatio(true); // Preserve the aspect ratio
    
        horseViews[lane - 1] = horseView;
        raceTrack.getRaceTrack().getChildren().add(horseView);
    }


    private void startRace() {
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveHorses()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveHorses() {
        boolean allFinished = true;
        for (int i = 0; i < horseViews.length; i++) {
            ImageView horseView = horseViews[i];
            Horse horse = horses[i];
            if (!horse.hasFallen() && horseView.getX() < raceLength) {
                double xPosition = horseView.getX() + rand.nextInt(3) + 1;
                if (xPosition >= raceLength - 30) {
                    xPosition = raceLength;
                    if (horse.getLastRaceTime() == 0) {  // Ensure last race time is set only once
                        long finishTime = System.currentTimeMillis();
                        double raceTime = (finishTime - startTime) / 1000.0;
                        horse.setLastRaceTime(raceTime);
                    }
                } else {
                    allFinished = false;
                    horseView.setX(xPosition);
                }
            }
        }
        
        if (allFinished) {
            finishRace();
        }
    }

    private void updateRaceStatistics() {
        if (raceStatistics != null) {
            raceStatistics.updateRaceStats(this);
        }
    }
    
    private void finishRace() {
        StringBuilder resultsBuilder = new StringBuilder();
        resultsBuilder.append("Race Finished. Results:\n");
        for (Horse horse : horses) {
            resultsBuilder.append(horse.getName())
                          .append(": Time = ")
                          .append(String.format("%.2f", horse.getLastRaceTime()))
                          .append("s, Distance = ")
                          .append(horse.getDistanceTravelled())
                          .append("m\n");
        }
    
        // Update the race track with results
        raceTrack.updateResults(resultsBuilder.toString());
    
        // Close stages and finalize the race
        raceStage.close();
        primaryStage.close();
        updateRaceStatistics();  // Update stats and save
        raceStatistics.saveStatistics();  // Explicitly save to file after updating
    }
    
    public String getWinningHorse() {
        return winningHorse;
    }
    
    public boolean didYourHorseWin(String yourHorseName) {
        return yourHorseName.equals(winningHorse);
    }
}
