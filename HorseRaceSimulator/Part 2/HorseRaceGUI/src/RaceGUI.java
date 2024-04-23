import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RaceGUI extends Application {
    private Race race;
    private Stage primaryStage;
    private Label pointsLabel;
    private TextField betAmountField;
    private Points pointsManager = new Points();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label labelCharacter = new Label("What character will your horse be?");
        ObservableList<String> options = FXCollections.observableArrayList("Knight", "Bishop", "Castle");
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setPromptText("Select your character");

        Label labelName = new Label("Name your horse:");
        TextField textFieldName = new TextField();
        textFieldName.setPromptText("Enter horse name");

        Label labelDistance = new Label("Set the race distance:");
        TextField textFieldDistance = new TextField();
        textFieldDistance.setPromptText("Enter distance in meters");

        Label betAmountLabel = new Label("Place your bet:");
        betAmountField = new TextField();
        betAmountField.setPromptText("Enter your bet amount");

        pointsLabel = new Label("Your Points: " + pointsManager.getPoints());

        Button buttonStartRace = new Button("Start Race");
        buttonStartRace.setOnAction(event -> {
            handleRaceStart(comboBox, textFieldName, textFieldDistance);
        });

        root.getChildren().addAll(labelCharacter, comboBox, labelName, textFieldName, labelDistance, textFieldDistance, betAmountLabel, betAmountField, pointsLabel, buttonStartRace);

        Scene scene = new Scene(root, 300, 400);
        stage.setTitle("Character and Race Setup");
        stage.setScene(scene);
        stage.show();
    }

    private void handleRaceStart(ComboBox<String> comboBox, TextField textFieldName, TextField textFieldDistance) {
        try {
            int bet = Integer.parseInt(betAmountField.getText().trim());
            if (bet > 0 && !pointsManager.canPlaceBet(bet)) {
                pointsLabel.setText("Error: Insufficient points for this bet");
                return;
            }

            int distance = Integer.parseInt(textFieldDistance.getText()); // Capture distance from text field
            Pane raceTrackPane = new Pane();
            raceTrackPane.setPrefSize(600, 400); // You might want to adjust or remove this line depending on how distance is handled visually

            Stage raceStage = new Stage();
            String horseName = textFieldName.getText().isEmpty() ? "Lightning" : textFieldName.getText();
            char selectedSymbol = comboBox.getValue().equals("Knight") ? '\u2658' :
                                  comboBox.getValue().equals("Bishop") ? '\u2657' : '\u2656';
            race = new Race(distance, raceTrackPane, raceStage, primaryStage, pointsManager);
            race.addHorse(new Horse(horseName, selectedSymbol, 0.9), 1);
            race.addHorse(new Horse("Thunder", '\u2658', 0.8), 2);
            race.addHorse(new Horse("Storm", '\u2658', 0.7), 3);

            raceStage.setTitle("Race Track");
            raceStage.setScene(new Scene(raceTrackPane, 600, 400));
            raceStage.show();

            raceStage.setOnHidden(e -> {
                updatePointsAfterRace(horseName, bet);
            });
        } catch (NumberFormatException e) {
            pointsLabel.setText("Error: Invalid bet amount");
        }
    }

    private void updatePointsAfterRace(String horseName, int bet) {
        boolean won = race.didYourHorseWin(horseName);
        pointsManager.updatePoints(won, bet);
        updatePointsDisplay();
    }

    private void updatePointsDisplay() {
        pointsLabel.setText("Your Points: " + pointsManager.getPoints());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
