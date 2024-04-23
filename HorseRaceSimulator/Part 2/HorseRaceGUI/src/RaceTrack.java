import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class RaceTrack {
    private Pane raceTrack;
    private int raceLength;

    public RaceTrack(Pane raceTrack, int raceLength) {
        this.raceTrack = raceTrack;
        this.raceLength = raceLength;
        drawRaceTrack(this.raceLength);
    }

    private void drawRaceTrack(int raceLength) {

        // Debugging output to ensure raceLength is correctly calculated
        System.out.println("Race length in pixels: " + raceLength);


        for (int i = 0; i < 2; i++) {
            Line lineAbove = new Line(10, (50 + i * 100) - 10, raceLength, (50 + i * 100) - 10);
            Line lineBelow = new Line(10, (80 + i * 100) + 10, raceLength , (80 + i * 100) + 10);
            lineAbove.getStrokeDashArray().addAll(10.0, 10.0);
            lineBelow.getStrokeDashArray().addAll(10.0, 10.0);
            raceTrack.getChildren().addAll(lineAbove, lineBelow);
        }

        // draw vertical lines for track at 0 and TrackEnd
        Line startLine = new Line(10, 10, 10, 200);
        Line endLine = new Line(raceLength, 40, raceLength, 340);
        startLine.setStrokeWidth(2);  // Make the line thicker for visibility
        endLine.setStrokeWidth(2);    // Make the line thicker for visibility
        raceTrack.getChildren().addAll(startLine, endLine);
    
        // Debugging to confirm lines are added
        System.out.println("Start line and end line added with end line at: " + (raceLength));
    }

    public Pane getRaceTrack() {
        return raceTrack;
    }
}
