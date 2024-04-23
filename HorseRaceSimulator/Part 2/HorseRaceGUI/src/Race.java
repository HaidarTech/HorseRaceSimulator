import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class Race {
    int raceLength;  // This is in pixels for simplicity, adjust if in different units like meters
    Horse[] horses = new Horse[3];
    Text[] horseLabels = new Text[3];
    Timeline timeline;
    Stage raceStage; // Race window stage
    Stage primaryStage; // Main setup window stage
    Random rand = new Random();
    private String winningHorse = null;
    private Points pointsManager;  // Use Points class for managing points
    private RaceTrack raceTrack;  // Instance of RaceTrack class

    public Race(int distance, Pane raceTrackPane, Stage raceStage, Stage primaryStage, Points pointsManager) {
        this.raceLength = distance * 10; // scale distance to pixel if needed
        this.raceStage = raceStage;
        this.primaryStage = primaryStage;
        this.pointsManager = pointsManager;
        this.raceTrack = new RaceTrack(raceTrackPane, raceLength); // Initialize the race track
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
        Text horseLabel = new Text(horse.getSymbol() + " " + horse.getName());
        horseLabel.setFont(Font.font("Verdana", 20));
        horseLabel.setY(50 * lane + 15);
        horseLabel.setX(10);
        horseLabels[lane - 1] = horseLabel;
        raceTrack.getRaceTrack().getChildren().add(horseLabel);
    }

    private void startRace() {
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveHorses()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveHorses() {
        boolean allFallen = true;
        double trackEnd = raceLength;
        
        for (int i = 0; i < horseLabels.length; i++) {
            Text horseLabel = horseLabels[i];
            Horse horse = horses[i];
            if (!horse.hasFallen() && Math.random() < 0.01 * (1 - horse.getConfidence())) {
                horse.fall();
                horseLabel.setText(horse.getSymbol() + " X");
            }

            if (!horse.hasFallen()) {
                double xPosition = horseLabel.getLayoutX() + rand.nextInt(3) + 1;
                if (xPosition >= trackEnd) {
                    horseLabel.setLayoutX(trackEnd);
                    timeline.stop();
                    if (winningHorse == null) {
                        winningHorse = horse.getName();
                        pointsManager.addPoints(10); // Add points only once for the first horse to finish
                    }
                    finishRace();
                } else {
                    horseLabel.setLayoutX(xPosition);
                    allFallen = false;
                }
            }
        }

        if (allFallen) {
            System.out.println("All horses have fallen! Race is over.");
            timeline.stop();
            finishRace();
        }
    }

    private void finishRace() {
        raceStage.close();
        primaryStage.close();
    }

    public boolean didYourHorseWin(String yourHorseName) {
        return yourHorseName.equals(winningHorse);
    }
}