package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        /* PART ONE: WRITING CODE IN PLACE OF FXML
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(10); // vertical gap
        root.setHgap(10); // horizontal gap
        */

        primaryStage.setTitle("Hello FX");
        primaryStage.setScene(new Scene(root, 300, 275));

        /* PART TWO: WRITING CODE IN PLACE OF FXML
        Label greeting = new Label("Welcome to JavaFX!");
        greeting.setTextFill(Color.GREEN);
        greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
        root.getChildren().add(greeting);
        */

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
