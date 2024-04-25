import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class BetManager {
    private int points;
    private static final String PROPS_FILE = "points.properties";

    public BetManager() {
        loadPoints();
    }

    private void loadPoints() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(PROPS_FILE));
            points = Integer.parseInt(props.getProperty("points", "0"));
        } catch (IOException e) {
            points = 0;  // Default to 0 if no file found
        }
    }

    public void savePoints() {
        Properties props = new Properties();
        props.setProperty("points", String.valueOf(points));
        try {
            props.store(new FileOutputStream(PROPS_FILE), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPoints() {
        return points;
    }

    public boolean canPlaceBet(int betAmount) {
        return (betAmount >= 0 && betAmount <= points);
    }

    public void updateBetResult(boolean won, int betAmount) {
        if (won) {
            points += betAmount * 2;  // Correcting the logic to properly double the winnings.
        } else {
            points -= betAmount;
        }
        savePoints();
    }
}
