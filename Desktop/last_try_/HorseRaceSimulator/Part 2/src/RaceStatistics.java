import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RaceStatistics {
    private Map<String, HorseStatistics> horseStats;
    private static final String STATS_FILE = "horse_stats.txt";

    public RaceStatistics() {
        horseStats = new HashMap<>();
        loadStatistics();
    }

    private void loadStatistics() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STATS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0].trim();
                    int races = Integer.parseInt(parts[1].trim());
                    int wins = Integer.parseInt(parts[2].trim());
                    double distance = Double.parseDouble(parts[3].trim());
                    double speed = Double.parseDouble(parts[4].trim());
                    horseStats.put(name, new HorseStatistics(name, races, wins, distance, speed));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing statistics file found, starting new.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading statistics: " + e.getMessage());
        }
    }

    public void saveStatistics() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STATS_FILE))) {
            for (HorseStatistics stats : horseStats.values()) {
                writer.println(stats);
                System.out.println("Writing stats for " + stats.name + ": " + stats);
            }
        } catch (IOException e) {
            System.out.println("Error saving statistics: " + e.getMessage());
        }
    }
    
    public void updateRaceStats(Race race) {
        for (Horse horse : race.horses) {
            HorseStatistics stats = horseStats.getOrDefault(horse.getName(), new HorseStatistics(horse.getName()));
            boolean won = horse.getName().equals(race.getWinningHorse());
            stats.updateStatistics(horse, won);
            horseStats.put(horse.getName(), stats);
        }
        saveStatistics();  // Save after updating
    }

    static class HorseStatistics {
        private String name;
        private int racesParticipated;
        private int racesWon;
        private double totalDistanceTravelled;
        private double fastestSpeed;

        public HorseStatistics(String name) {
            this.name = name;
            this.racesParticipated = 0;
            this.racesWon = 0;
            this.totalDistanceTravelled = 0;
            this.fastestSpeed = 0;
        }

        public HorseStatistics(String name, int races, int wins, double distance, double speed) {
            this.name = name;
            this.racesParticipated = races;
            this.racesWon = wins;
            this.totalDistanceTravelled = distance;
            this.fastestSpeed = speed;
        }

        public void updateStatistics(Horse horse, boolean won) {
            racesParticipated++;
            if (won) racesWon++;
            totalDistanceTravelled += horse.getDistanceTravelled();
        
            if (horse.getLastRaceTime() > 0 && horse.getDistanceTravelled() > 0) {
                double currentSpeed = horse.getDistanceTravelled() / horse.getLastRaceTime();
                fastestSpeed = Math.max(fastestSpeed, currentSpeed);
                System.out.println("Updated speed for " + horse.getName() + ": " + currentSpeed + " km/h");
            } else {
                System.out.println("Invalid data for " + horse.getName() + ": Time=" + horse.getLastRaceTime() + ", Distance=" + horse.getDistanceTravelled());
            }
        }
        
        
        

        @Override
        public String toString() {
            return String.format("%s,%d,%d,%.2f,%.2f", name, racesParticipated, racesWon, totalDistanceTravelled, fastestSpeed);
        }
    }
}
