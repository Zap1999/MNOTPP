package com.zapcorp.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;

    @FXML
    private BorderPane mainPane;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    //TODO
    /*@FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }*/

    @FXML
    private void toHero () throws IOException {
        stage = (Stage) mainPane.getScene().getWindow();
        BorderPane root;
        root = (BorderPane) FXMLLoader.load(
                Paths.get("src/main/resources/com/zapcorp/myhero/MyHero.fxml").toUri().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void toMissions () throws IOException {
        stage = (Stage) mainPane.getScene().getWindow();
        BorderPane root;
        root = (BorderPane) FXMLLoader.load(
                Paths.get("src/main/resources/com/zapcorp/missions/MissionBattle.fxml").toUri().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void toShop() throws IOException {
        stage = (Stage) mainPane.getScene().getWindow();
        BorderPane root;
        root = (BorderPane) FXMLLoader.load(
                Paths.get("src/main/resources/com/zapcorp/items/ItemShop.fxml").toUri().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void toChat() throws IOException {
        stage = (Stage) mainPane.getScene().getWindow();
        BorderPane root;
        root = (BorderPane) FXMLLoader.load(
                Paths.get("src/main/resources/com/zapcorp/chat/ChatView.fxml").toUri().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
