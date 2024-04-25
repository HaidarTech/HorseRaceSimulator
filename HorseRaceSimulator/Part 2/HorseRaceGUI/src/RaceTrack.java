import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RaceTrack {
    private Pane raceTrack;
    private int raceLength;
    private Text resultsLabel;  // Label to display race results
    private Text currentLeaderLabel;  // Label to display the current leader
    private Text winnerLabel; // Label to display the race winner
    private Button goBackButton; // Button to return to the main menu
    private Stage primaryStage;
    private Stage mainMenuStage; // Reference to the main menu stage

    public RaceTrack(Pane raceTrack, int raceLength, Stage primaryStage) {
        this.raceTrack = raceTrack;
        this.raceLength = raceLength;
        this.primaryStage = primaryStage; // Assume primaryStage is passed to RaceTrack for use with goBackButton
        this.mainMenuStage = mainMenuStage; // Assign the main menu stage
        initializeUI();
    }

    private void initializeUI() {
        this.resultsLabel = new Text();
        this.currentLeaderLabel = new Text("Current winner: ");
        this.winnerLabel = new Text();
        this.goBackButton = new Button("Go Back");

        this.resultsLabel.setFont(Font.font("Verdana", 14));
        this.currentLeaderLabel.setFont(Font.font("Verdana", 14));
        this.winnerLabel.setFont(Font.font("Verdana", 16));
        this.goBackButton.setFont(Font.font("Verdana", 14));

        this.currentLeaderLabel.setX(10);
        this.currentLeaderLabel.setY(20);  // Top of the pane
        double bottomY = raceTrack.getHeight() - 30;
        this.winnerLabel.setX((raceTrack.getWidth() / 2) - (winnerLabel.getLayoutBounds().getWidth() / 2));
        this.winnerLabel.setY(bottomY - 20);  // 20 pixels above the current leader label
        this.goBackButton.setLayoutX(250);
        this.goBackButton.setLayoutY(370);  // Below the winner label
        this.goBackButton.setOnAction(e -> {
            primaryStage.close(); // Close the current RaceTrack stage
            mainMenuStage.show(); // Show the main menu stage
        });
        this.goBackButton.setVisible(false); // Initially hide the Go Back button

        drawRaceTrack(raceLength);
        raceTrack.getChildren().addAll(resultsLabel, currentLeaderLabel, winnerLabel, goBackButton);
    }

    private void drawRaceTrack(int raceLength) {
        System.out.println("Race length in pixels: " + raceLength);

        for (int i = 0; i < 2; i++) {
            Line lineAbove = new Line(10, (50 + i * 100) - 10, raceLength, (50 + i * 100) - 10);
            Line lineBelow = new Line(10, (80 + i * 100) + 10, raceLength , (80 + i * 100) + 10);
            lineAbove.getStrokeDashArray().addAll(10.0, 10.0);
            lineBelow.getStrokeDashArray().addAll(10.0, 10.0);
            raceTrack.getChildren().addAll(lineAbove, lineBelow);
        }

        Line startLine = new Line(10, 40, 10, 190);
        Line endLine = new Line(raceLength, 40, raceLength, 190);
        startLine.setStrokeWidth(2);
        endLine.setStrokeWidth(2);
        raceTrack.getChildren().addAll(startLine, endLine);
        System.out.println("Start line and end line added with end line at: " + raceLength);

    }

    public void updateCurrentLeader(String horseName) {
        currentLeaderLabel.setText("Current winner: " + horseName);
    }

    public void showWinnerMessage(String winner) {
        winnerLabel.setText("Winner is = " + winner);
        
        // Dynamically calculate the X position to center the label
        double centerX = (raceTrack.getWidth() / 2) - (winnerLabel.getLayoutBounds().getWidth() / 2);
        winnerLabel.setX(centerX);
        
        double bottomYPosition = raceTrack.getHeight() - 30; // Adjust this value to place it closer to the bottom
        winnerLabel.setY(bottomYPosition - 20);
    }
    

    public Pane getRaceTrack() {
        return raceTrack;
    }

    public String getCurrentLeader() {
        return currentLeaderLabel.getText().replace("Current winner: ", "");
    }
    

    public void updateResults(String resultsText) {
        // Dynamically calculate the Y position to place the text closer to the bottom of the pane
        double bottomYPosition = raceTrack.getHeight() - 30;
        this.resultsLabel.setY(bottomYPosition - 100);
        this.resultsLabel.setText(resultsText);
    
        // Debugging: Print out the results text to ensure it is received correctly
        System.out.println("Results Updated: " + resultsText);
    }
    

}