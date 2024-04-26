# Horse Racing Simulator

## Description
Welcome to my horse racing simulator. Part one shows a terminal version of the game while part two uses JavaFX to integrate GUI into the game.

## How to Install
Firstly, make sure JavaFX is installed on your PC. This can be done via this link:
- [JavaFX](https://openjfx.io/)

Begin by cloning the project to your desired folder, this can be done using:


```git clone https://github.com/HaidarTech/HorseRaceSimulator```

Make sure JavaFX libraries are present within the `lib` folder, these will be used to set up the GUI environment.

Once completed, we need to use the terminal to actually run the code, this can be done by firstly navigating to the folder which you cloned the project into using:

```cd (your_file_path)/HorseRaceSimulator/"Part 2"/src```

Once inside, make sure to edit the `horse.png` file paths within `RaceGUI` to your own file path within the `determineImagePath`.

Lastly, to run the code use (add your file path to the JavaFX library):

```java --module-path "(your_file_path)/javafx-sdk-22.0.1/lib" --add-modules javafx.controls,javafx.fxml -cp bin RaceGUI```


I did add javafx files to the lib files within the project code just incase there is any issue with installation.


A window should open up and you’re ready to race!