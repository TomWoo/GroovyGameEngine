package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
        //Parent root = FXMLLoader.load(new URL(location + "main.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml")); //FXMLLoader.load(Utilities.getResourceURL("ui/main.fxml"));
        primaryStage.setTitle("Custom Groovy Game Engine");
        primaryStage.setScene(new Scene(root, 960, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
