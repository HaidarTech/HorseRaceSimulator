import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class Race {
    int raceLength;
    Horse[] horses = new Horse[3];
    Pane raceTrack;
    Text[] horseLabels = new Text[3];
    Timeline timeline;
    Stage raceStage; // Race window stage
    Stage primaryStage; // Main setup window stage
    Random rand = new Random();

    public Race(int distance, Pane raceTrack, Stage raceStage, Stage primaryStage) {
        this.raceLength = distance;
        this.raceTrack = raceTrack;
        this.raceStage = raceStage;
        this.primaryStage = primaryStage;
        drawRaceTrack();
        startRace();
    }

    private void drawRaceTrack() {
        for (int i = 0; i < 3; i++) {  // Loop for three lanes
            // Line above each horse
            Line lineAbove = new Line(10, (50 + i * 100) - 10, raceLength * 10 + 10, (50 + i * 100) - 10);
            // Line below each horse
            Line lineBelow = new Line(10, (80 + i * 100) + 10, raceLength * 10 + 10, (80 + i * 100) + 10);

            // Set the dash pattern for both lines
            lineAbove.getStrokeDashArray().addAll(10.0, 10.0); // Dash length of 10, space length of 10
            lineBelow.getStrokeDashArray().addAll(10.0, 10.0); // Dash length of 10, space length of 10

            // Adding lines to the Pane
            raceTrack.getChildren().addAll(lineAbove, lineBelow);
        }

        // Vertical lines at the start and end of the track
        Line startLine = new Line(10, 40, 10, 260);
        Line endLine = new Line(raceLength * 10 + 10, 40, raceLength * 10 + 10, 260);

        // Also making vertical lines dashed if desired
        startLine.getStrokeDashArray().addAll(10.0, 10.0);
        endLine.getStrokeDashArray().addAll(10.0, 10.0);

        raceTrack.getChildren().addAll(startLine, endLine);
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
        raceTrack.getChildren().add(horseLabel);
    }

    private void startRace() {
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveHorses()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void moveHorses() {
        boolean raceEnded = false;
        boolean allFallen = true;  // Track if all horses have fallen
    
        for (int i = 0; i < horseLabels.length; i++) {
            Text horseLabel = horseLabels[i];
            Horse horse = horses[i];
            if (horseLabel != null && !horse.hasFallen()) {
                allFallen = false; // At least one horse has not fallen
    
                // Decrease the chance of falling further; using a very low base chance of 2%
                double fallChance = 0.02 * (1 - horse.getConfidence());
                if (Math.random() < fallChance) {
                    horse.fall();
                    horseLabel.setText(horse.getSymbol() + " X"); // Mark as fallen
                }
    
                if (!horse.hasFallen()) {
                    double xPosition = horseLabel.getLayoutX() + rand.nextInt(3) + 1; // Small random increment
                    if (xPosition < raceLength * 10 + 10) {
                        horseLabel.setLayoutX(xPosition);
                    } else {
                        raceEnded = true;
                        timeline.stop();
                        System.out.println(horse.getName() + " wins!");
                        finishRace();
                        break;
                    }
                }
            }
        }
    
        // Check if all horses have fallen and stop the race if true
        if (allFallen) {
            timeline.stop();
            System.out.println("All horses have fallen! Race is over.");
            finishRace();
        }
    }
    
    private void finishRace() {
        raceStage.close(); // Close the race stage
        primaryStage.close(); // Close the primary stage
    }
}
