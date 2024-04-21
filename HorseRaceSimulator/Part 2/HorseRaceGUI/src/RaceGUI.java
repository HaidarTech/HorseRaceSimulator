import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class RaceGUI extends Application {
    private Race race;
    private Stage primaryStage; // Reference to the primary stage

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage; // Store the primary stage

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label labelCharacter = new Label("What character will your horse be?");
        ObservableList<String> options = FXCollections.observableArrayList("Knight", "Horse", "Castle");
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setPromptText("Select your character");

        Label labelDistance = new Label("Set the race distance:");
        TextField textFieldDistance = new TextField();
        textFieldDistance.setPromptText("Enter distance in meters");

        Button buttonStartRace = new Button("Start Race");
        buttonStartRace.setOnAction(event -> {
            int distance = Integer.parseInt(textFieldDistance.getText());
            Pane raceTrack = new Pane();
            raceTrack.setPrefSize(600, 400);

            Stage raceStage = new Stage();
            race = new Race(distance, raceTrack, raceStage, primaryStage); // Pass both stages
            race.addHorse(new Horse("Lightning", '\u2658', 0.9), 1);
            race.addHorse(new Horse("Thunder", '\u2658', 0.8), 2);
            race.addHorse(new Horse("Storm", '\u2658', 0.7), 3);

            raceStage.setTitle("Race Track");
            raceStage.setScene(new Scene(raceTrack, 600, 400));
            raceStage.show();
        });

        root.getChildren().addAll(labelCharacter, comboBox, labelDistance, textFieldDistance, buttonStartRace);

        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("Character and Race Setup");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
