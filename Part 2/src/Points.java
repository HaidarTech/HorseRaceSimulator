import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Points {
    private int points;
    private static final String PROPS_FILE = "points.properties";
    private Properties props = new Properties();

    public Points() {
        loadPoints();
    }

    public int getPoints() {
        return points;
    }

    public void updatePoints(boolean won, int betAmount) {
        if (won) {
            addPoints(10);
            addPoints(betAmount);  // If won, double the bet
        } else {
            addPoints(10);
            subtractPoints(betAmount);  // If lost, subtract the bet
        }
    }

    public void addPoints(int newPoints) {
        this.points += newPoints;
        savePoints();
    }
    

    public void subtractPoints(int pointsToSubtract) {
        if (pointsToSubtract <= this.points) {
            this.points -= pointsToSubtract;
            savePoints();
        }
    }

    private void loadPoints() {
        try {
            props.load(new FileInputStream(PROPS_FILE));
            points = Integer.parseInt(props.getProperty("points", "0"));
        } catch (IOException e) {
            System.out.println("Failed to load points: " + e.getMessage());
            points = 0;
        }
    }

    private void savePoints() {
        try {
            props.setProperty("points", String.valueOf(points));
            props.store(new FileOutputStream(PROPS_FILE), null);
        } catch (IOException e) {
            System.out.println("Failed to save points: " + e.getMessage());
        }
    }


    public boolean canPlaceBet(int betAmount) {
        return betAmount > 0 && betAmount <= points;
    }
}
