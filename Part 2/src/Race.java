import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

import javafx.scene.control.Button;
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
    private int betAmount;  // Store bet amount here
    private Points pointsManager;  // Use Points class for managing points
    private RaceTrack raceTrack;  // Instance of RaceTrack class
    private RaceStatistics raceStatistics; // Instance of RaceStatistics class

    public Race(int distance, Pane raceTrackPane, Stage raceStage, Stage primaryStage, Points pointsManager) {
        this.raceLength = distance * 10;
        this.raceStage = raceStage;
        this.primaryStage = primaryStage;
        this.pointsManager = pointsManager;
        this.betAmount = betAmount;  // Initialize betAmount
        this.raceTrack = new RaceTrack(raceTrackPane, raceLength, primaryStage);
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
        String currentLeader = "";
        double maxPosition = 0;
        boolean raceOfficiallyFinished = false;
    
        for (int i = 0; i < horseViews.length; i++) {
            ImageView horseView = horseViews[i];
            Horse horse = horses[i];
            double xPosition = horseView.getX();
    
            if (!horse.hasFallen() && xPosition < raceLength) {
                int moveDistance = rand.nextInt(3) + 1;
                xPosition += moveDistance;
                horseView.setX(xPosition);
                horse.incrementDistance(moveDistance);
    
                if (xPosition > maxPosition) {
                    maxPosition = xPosition;
                    currentLeader = horse.getName();
                    raceTrack.updateCurrentLeader(currentLeader);
                }
            }
    
            if (xPosition >= raceLength - 50 && !horse.hasFallen()) {
                horse.setLastRaceTime((System.currentTimeMillis() - startTime) / 1000.0);
                horse.fall();
                if (!raceOfficiallyFinished) {
                    winningHorse = horse.getName();
                    raceOfficiallyFinished = true;
                    System.out.println("Horse " + horse.getName() + " finished first with time " + horse.getLastRaceTime() + "s and distance " + horse.getDistanceTravelled() + "m");
                }
            }
        }
    
        if (raceOfficiallyFinished) {
            timeline.stop();  // Only stop the timeline when all horses have finished or fallen
            System.out.println("Race officially finished. Winner: " + winningHorse);
            finishRace(winningHorse);
        }
    }
    
    
    
    

    private void updateRaceStatistics() {
        if (raceStatistics != null) {
            raceStatistics.updateRaceStats(this);
        }
    }
    
    private void finishRace(String currentLeader) {
        System.out.println("Finishing race and calculating results...");
        StringBuilder resultsBuilder = new StringBuilder("Race Finished. Results:\n");
        double bestTime = Double.MAX_VALUE;
        String winnerName = currentLeader; // Start with the current leader as the presumed winner
    
        for (Horse horse : horses) {
            double time = horse.getLastRaceTime();
            resultsBuilder.append(horse.getName())
                          .append(": Time = ")
                          .append(String.format("%.2f", time))
                          .append("s, Distance = ")
                          .append(horse.getDistanceTravelled())
                          .append("m\n");
    
            System.out.println("Checking horse " + horse.getName() + " - Time: " + time + "s, Distance: " + horse.getDistanceTravelled() + "m, Fallen: " + horse.hasFallen());
    
            if (!horse.hasFallen() && time < bestTime) {
                bestTime = time;
                winnerName = horse.getName();
            }
        }
    
        System.out.println("Current Leader at finish: " + currentLeader);
    
        raceTrack.updateResults(resultsBuilder.toString());
        if (!winnerName.isEmpty()) {
            raceTrack.showWinnerMessage(winnerName); // Change here to just pass winnerName
            System.out.println("Winner determined: " + winnerName);
            pointsManager.updatePoints(winnerName.equals(winningHorse), betAmount);  // Update points based on the outcome
        } else {
            raceTrack.showWinnerMessage("No valid winner could be determined.");
            System.out.println("No valid winner could be determined or current leader does not match the fastest horse.");
        }
    
        if (raceStatistics != null) {
            raceStatistics.updateRaceStats(this);
            raceStatistics.saveStatistics();
        }
    }
    
    
    
    
    
    
    
    
    
    public String getWinningHorse() {
        return winningHorse;
    }
    
    public boolean didYourHorseWin(String yourHorseName) {
        return yourHorseName.equals(winningHorse);
    }
}
