public class Main {
    public static void main(String[] args) {
        // Create the race instance with a track length of 50 units
        Race race = new Race(20);

        // Create three horses with different confidence levels
	Horse horse1 = new Horse("Lightning", '\u2658', 0.9);
	Horse horse2 = new Horse("Thunder", '\u265E', 0.8);
	Horse horse3 = new Horse("Storm", '\u2658', 0.85);


        // Add horses to the race
        race.addHorse(horse1, 1);  // Add horse1 to lane 1
        race.addHorse(horse2, 2);  // Add horse2 to lane 2
        race.addHorse(horse3, 3);  // Add horse3 to lane 3

        // Start the race
        race.startRace();
    }
}
