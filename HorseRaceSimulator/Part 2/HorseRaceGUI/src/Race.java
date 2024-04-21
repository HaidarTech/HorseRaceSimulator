import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Race{
    int raceLength;
    Horse lane1Horse;
    Horse lane2Horse;
    Horse lane3Horse;
    Pane raceTrack;
    Text[] horseLabels;
    Timeline timeline;
    Stage raceStage; // Race window stage
    Stage primaryStage; // Main setup window stage

    public Race(int distance, Pane raceTrack, Stage raceStage, Stage primaryStage) {
        this.raceLength = distance;
        this.raceTrack = raceTrack;
        this.raceStage = raceStage;
        this.primaryStage = primaryStage;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
        horseLabels = new Text[3];
        drawRaceTrack();
    }

    private void drawRaceTrack() {
        for (int i = 0; i < 2; i++) {
            Line lineAbove = new Line(10, (50 + i * 100) - 10, raceLength * 10 + 10, (50 + i * 100) - 10);
            Line lineBelow = new Line(10, (80 + i * 100) + 10, raceLength * 10 + 10, (80 + i * 100) + 10);
            raceTrack.getChildren().addAll(lineAbove, lineBelow);
        }

            //vertical lines for startline and endline
    }

    public void addHorse(Horse horse, int lane) {
        if (lane < 1 || lane > 3) {
            System.out.println("Invalid lane number");
            return;
        }

        switch (lane) {
            case 1: lane1Horse = horse; break;
            case 2: lane2Horse = horse; break;
            case 3: lane3Horse = horse; break;
        }
        displayHorse(horse, lane);
        startRace();
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
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> moveHorses()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveHorses() {
        boolean raceEnded = false;
        for (int i = 0; i < horseLabels.length; i++) {
            Text horseLabel = horseLabels[i];
            if (horseLabel != null && !raceEnded) {
                double xPosition = horseLabel.getLayoutX() + 1;
                if (xPosition < raceLength * 10 + 10) {
                    horseLabel.setLayoutX(xPosition);
                } else {
                    raceEnded = true;
                    timeline.stop();
                    System.out.println("Race finished!");
                }
            }
        }
        if (raceEnded) {
            raceStage.close(); // Close the race stage
            primaryStage.close(); // Close the primary stage
        }
    }
}
