package com.zapcorp.start;

import com.zapcorp.game.ContextCareTaker;
import com.zapcorp.game.ContextOriginator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class GameApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        ContextOriginator.loadState(ContextCareTaker.getLast());
        Parent root = FXMLLoader.load(Paths.get("src/main/resources/com/zapcorp/start/LogInForm.fxml").toUri().toURL());
        //Parent root = FXMLLoader.load(getClass().getResource("/menu/MainMenu.fxml"));
        stage.setTitle("Coolest game ever");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
        ContextCareTaker.add(ContextOriginator.saveState());
    }
}
