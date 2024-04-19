import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class RaceGUI extends Application {
    private Race race;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // Label and ComboBox for character selection
        Label labelCharacter = new Label("What character will your horse be?");
        ObservableList<String> options = FXCollections.observableArrayList("Knight", "Horse", "Castle");
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setPromptText("Select your character");

        /// for colour

        Label label_colour = new Label("What colour will your horse be?");
    
        ObservableList<String> options_colour = FXCollections.observableArrayList(
            "White",
            "Black",
            "Blue"
        );
        
        ComboBox<String> comboBox_colour = new ComboBox<>(options_colour);
        comboBox_colour.setPromptText("Select colour");

        // Listener to handle ComboBox selection changes
        comboBox_colour.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("User selected: " + newVal); // Print or handle the selected item
            }
        });
        
        VBox root_colour = new VBox(10); // VBox layout with spacing between children
        root.getChildren().addAll(label_colour, comboBox_colour); // Add both label and combobox to the layout

        // Label and TextField for setting the race distance
        Label labelDistance = new Label("Set the race distance:");
        TextField textFieldDistance = new TextField();
        textFieldDistance.setPromptText("Enter distance in meters");

        // Button to start the race
        Button buttonStartRace = new Button("Start Race");
        buttonStartRace.setOnAction(event -> {
            int distance = Integer.parseInt(textFieldDistance.getText()); // Get the distance from the text field
            String character = comboBox.getValue(); // Get the selected character
            race = new Race(distance); // Initialize race with the given distance
            race.addHorse(new Horse(character, 'H', 0.8), 1); // Adding horse as an example
            race.startRace(); // Start the race
            System.out.println("Race started with distance: " + distance + " and character: " + character);
        });

        // Adding components to the VBox
        root.getChildren().addAll(labelCharacter, comboBox, labelDistance, root_colour,textFieldDistance, buttonStartRace);

        Scene scene = new Scene(root, 300, 600);
        stage.setTitle("Character and Race Setup");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
