import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class RaceTrack {
    private Pane raceTrack;
    private int raceLength;
    private Text resultsLabel; // Label to display race results

    public RaceTrack(Pane raceTrack, int raceLength) {
        this.raceTrack = raceTrack;
        this.raceLength = raceLength;
        this.resultsLabel = new Text(); // Initialize the text for results
        this.resultsLabel.setFont(Font.font("Verdana", 14));
        this.resultsLabel.setX(raceLength + 20); // Position it just past the end line
        this.resultsLabel.setY(50); // Vertical position
        drawRaceTrack(this.raceLength);
        this.raceTrack.getChildren().add(resultsLabel); // Add results label to the race track
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

    public Pane getRaceTrack() {
        return raceTrack;
    }

    // Method to update results display
    public void updateResults(String resultsText) {
    this.resultsLabel.setText(resultsText); // Update the label with provided text
}

}